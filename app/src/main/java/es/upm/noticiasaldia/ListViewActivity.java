package es.upm.noticiasaldia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Noticia;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.RssFeed;
import util.RssItem;
import util.RssReader;

public class ListViewActivity extends AppCompatActivity implements RssReader.RssReaderListener{
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        String rssUrl = "https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada";
        //new RssReader(this).execute(rssUrl);
        leerRSS();
        progressBar = findViewById(R.id.progressBarListView);
        progressBar.setVisibility(View.VISIBLE);

    }

    private void leerRSS() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        Serializer serializer = new Persister();
                        RssFeed rssFeed = serializer.read(RssFeed.class, response.body().byteStream());
                        List<Noticia> noticiasDescargadas = new ArrayList<Noticia>();
                        int contador = 0;
                        for (RssItem item : rssFeed.getChannel().getItems()) {
                            if (contador == 15){
                                break;
                            } else {
                                contador++;
                            }
                            Noticia noticia = new Noticia();
                            noticia.setTitulo(item.getTitle());
                            noticia.setDescripcion(item.getDescription());
                            noticia.setEnlace(item.getLink());
                            if (item.getPubDate() != null) {
                                noticia.setFecha(item.getPubDate());
                            }

                            if (item.getContenido() != null) {
                                noticia.setContenido(item.getContenido());
                            } else {
                                noticia.setContenido(item.getDescription());
                            }

                            if (item.getMediaContent() != null) {
                                noticia.setImagenUrl(item.getMediaContent().getUrl());
                                String imageUrl = item.getMediaContent().getUrl();
                                String credit = item.getMediaContent().getCredit();
                                String imageTitle = item.getMediaContent().getTitle();
                                String imageText = item.getMediaContent().getText();
                                String imageDescription = item.getMediaContent().getDescription();

                                Log.i("Lector-media-content-url", item.getMediaContent().toString());
                                // Descarga la imagen y conviértela a Bitmap
                                if(item.getMediaContent().getUrl() != null){
                                    if (item.getMediaContent().getUrl().toLowerCase().endsWith(".jpg") || item.getMediaContent().getUrl().toLowerCase().endsWith(".jpeg")) {
                                        // Es una imagen (jpg)
                                        Bitmap bitmap = Picasso.get().load(item.getMediaContent().getUrl()).get();
                                        noticia.setImagenUrl(item.getMediaContent().getUrl());
                                        noticia.setImagenBitmap(bitmap);
                                        if(item.getMediaContent().getTitle() != null){
                                            noticia.setMediaTitle(item.getMediaContent().getTitle());
                                        } else {
                                            noticia.setMediaTitle("NO Title");
                                        }
                                        if(item.getMediaContent().getDescription() != null){
                                            noticia.setMediaDescription(item.getMediaContent().getDescription());
                                        } else {
                                            noticia.setMediaDescription("NO DESCRIPTION");
                                        }
                                        Log.i("Tipo de contenido", "Imagen (jpg)");
                                        Log.i("Tipo de contenido-titulo",noticia.getMediaTitle());
                                        Log.i("Tipo de contenido-descripcion",noticia.getMediaDescription());
                                    } else if (item.getMediaContent().getUrl().toLowerCase().endsWith(".mp4")) {
                                        // Es un video (mp4)
                                        noticia.setImagenUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU");
                                        noticia.setImagenBitmap(Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU").get());
                                        noticia.setVideoUrl(item.getMediaContent().getUrl());
                                        if(item.getMediaContent().getTitle() != null){
                                            noticia.setMediaTitle(item.getMediaContent().getTitle());
                                        } else {
                                            noticia.setMediaTitle("NO Title");
                                        }
                                        if(item.getMediaContent().getDescription() != null){
                                            noticia.setMediaDescription(item.getMediaContent().getDescription());
                                        } else {
                                            noticia.setMediaDescription("NO DESCRIPTION");
                                        }
                                        Log.i("Tipo de contenido", "Video (mp4)");
                                        Log.i("Tipo de contenido-titulo",noticia.getMediaTitle());
                                        Log.i("Tipo de contenido-descripcion",noticia.getMediaDescription());
                                    } else {
                                        // Puedes manejar otros tipos de contenido aquí si es necesario
                                        noticia.setImagenUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU");
                                        noticia.setImagenBitmap(Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU").get());
                                        Log.i("Tipo de contenido", "Otro tipo");
                                    }

                                } else {
                                    noticia.setImagenUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU");
                                    noticia.setImagenBitmap(Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU").get());
                                }
                            } else {
                                Log.i("Error media", item.toString());
                                noticia.setImagenUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU");
                                noticia.setImagenBitmap(Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlkSxmWpyxD95Jw3u_HHvv3J9tcc2GLbec9Xl4Qemj9uR8ATjDH3fae98sfT2KtsIxuGg&usqp=CAU").get());
                            }


                            Log.i("Lector-titulo",item.getTitle());
                            Log.i("Lector-descripcion",item.getDescription());
                            Log.i("Lector-fecha",item.getPubDate().toString());
                            Log.i("Lector-link",item.getLink());
                            Log.i("Lector-contenido",item.getContenido());
                            // Resto del código...

                            noticiasDescargadas.add(noticia);
                        }
                        leerRssYActualizarLista(noticiasDescargadas);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    private void leerRssYActualizarLista(List<Noticia> noticiasfinal) {
        // Tu lógica para leer el feed RSS y obtener la lista de noticias

        // Supongamos que tienes una lista de noticias llamada 'nuevasNoticias'

        // Después de obtener las noticias, actualiza la lista en el hilo principal
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ListView listViewNoticias = findViewById(R.id.ListViewNoticias);
                NoticiasAdapter na = new NoticiasAdapter(ListViewActivity.this,noticiasfinal);
                listViewNoticias.setAdapter(na);
                ((NoticiasAdapter) listViewNoticias.getAdapter()).notifyDataSetChanged();
                progressBar = findViewById(R.id.progressBarListView);
                progressBar.setVisibility(View.INVISIBLE);
                listViewNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent explicitIntent = new Intent(ListViewActivity.this, FullViewNoticiaActivity.class);
                        explicitIntent.putExtra("noticia", noticiasfinal.get(position));
                        startActivity(explicitIntent);
                    }
                });
            }
        });
    }


@Override
    public void onRssRead(List<Noticia> noticias) {
        // Procesa las entradas del feed RSS aquí
        if (noticias != null) {
            for (Noticia noticia : noticias) {
                Log.d("ListViewActivity", "Title: " + noticia.getTitulo());
                Log.d("ListViewActivity", "Link: " + noticia.getEnlace());
                Log.d("ListViewActivity", "ImageUrl: " + noticia.getImagenUrl());
                Log.d("ListViewActivity", "Description: " + noticia.getDescripcion());
                Log.d("ListViewActivity", "-----------------------");
            }
        }
        ListView listViewNoticias = findViewById(R.id.ListViewNoticias);
        NoticiasAdapter na = new NoticiasAdapter(this,noticias);
        listViewNoticias.setAdapter(na);
        ((NoticiasAdapter) listViewNoticias.getAdapter()).notifyDataSetChanged();
        progressBar = findViewById(R.id.progressBarListView);
        progressBar.setVisibility(View.INVISIBLE);
        Button botonLista = findViewById(R.id.botonCargarMas);
        listViewNoticias.addFooterView(botonLista);
        botonLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Boton Cargar mas", "se ha presionado el boton");
            }
        });
        listViewNoticias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent explicitIntent = new Intent(ListViewActivity.this, FullViewNoticiaActivity.class);
                explicitIntent.putExtra("noticia", noticias.get(position));
                startActivity(explicitIntent);
            }
        });
    }

}