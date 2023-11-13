package es.upm.noticiasaldia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.rometools.rome.feed.synd.SyndEntry;

import java.util.ArrayList;
import java.util.List;

import model.Noticia;
import util.RssReader;

public class ListViewActivity extends AppCompatActivity implements RssReader.RssReaderListener{
    private List<Noticia> noticias = new ArrayList<Noticia>();
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        String rssUrl = "https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada";
        new RssReader(this).execute(rssUrl);
        progressBar = findViewById(R.id.progressBarListView);
        progressBar.setVisibility(View.VISIBLE);

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