package util;

import android.os.AsyncTask;
import android.util.Log;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.squareup.picasso.Picasso;

import org.jdom2.Element;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.Noticia;

public class RssReader extends AsyncTask<String, Void, List<Noticia>> {

    private static final String TAG = "RssReader";
    private RssReaderListener listener;

    public RssReader(RssReaderListener listener) {
        this.listener = listener;
    }

    protected List<Noticia> doInBackground(String... urls) {
        String url = urls[0];
        try {
            URL feedUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            List<Noticia> noticias = new ArrayList<>();
            int contador = 0;
            for (SyndEntry entry : feed.getEntries()) {
                if (contador == 10){
                    break;
                } else {
                    contador++;
                }
                Noticia noticia = new Noticia();
                noticia.setTitulo(entry.getTitle());
                noticia.setEnlace(entry.getLink());
                noticia.setDescripcion(entry.getDescription().getValue());
                noticia.setFecha(entry.getPublishedDate());

                // Obtener la URL de la imagen o el video desde la etiqueta media:content
                List<Element> foreignMarkup = entry.getForeignMarkup();
                for (Element element : foreignMarkup) {
                    if ("content".equals(element.getName()) && "media".equals(element.getNamespacePrefix())) {
                        String mediaType = element.getAttributeValue("type");
                        String mediaUrl = element.getAttributeValue("url");

                        if ("image/jpeg".equals(mediaType) || "image/png".equals(mediaType)) {
                            // Cargar el Bitmap para una imagen
                            try {
                                noticia.setImagenBitmap(Picasso.get().load(mediaUrl).get());
                                Log.i("RSSREADER", "Es una imagen");

                            } catch (IOException e) {
                                Log.e(TAG, "Error loading image", e);
                            }
                        } else if ("video/mp4".equals(mediaType)) {
                            // Puedes manejar la URL del video de alguna manera
                            noticia.setImagenBitmap(Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU").get());

                            Log.i("RSSREADER", "Es un video");
                        }
                        break; // Terminar el bucle cuando se encuentre la URL de la imagen o el video
                    }
                    if ("encoded".equals(element.getName()) && "content".equals(element.getNamespacePrefix())) {
                        String contenidoEncoded = element.getText();
                        Log.i("RssReader",contenidoEncoded);
                        // Aquí puedes procesar el contenido según tus necesidades
                        noticia.setContenido(contenidoEncoded);
                    } else {
                        Log.i("RssReader","No entro en ninguna");
                    }
                }

                // Puedes agregar más campos según tus necesidades
                noticias.add(noticia);
            }

            return noticias;
        } catch (IOException | FeedException e) {
            Log.e(TAG, "Error reading RSS feed", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }




    @Override
    protected void onPostExecute(List<Noticia> noticias) {
        if (listener != null) {
            listener.onRssRead(noticias);
        }
    }

    public interface RssReaderListener {
        void onRssRead(List<Noticia> noticias);
    }
}
