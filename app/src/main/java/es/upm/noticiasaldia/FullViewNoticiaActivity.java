package es.upm.noticiasaldia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import model.Noticia;

public class FullViewNoticiaActivity extends AppCompatActivity {

    private Noticia noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view_noticia);
        Intent intent = getIntent();
        noticia = (Noticia) intent.getSerializableExtra("noticia");
        TextView tituloTextView = findViewById(R.id.TituloNoticiaFullView);
        tituloTextView.setText(noticia.getTitulo());
        TextView contenidoTextView =  findViewById(R.id.ContenidoNoticiaFullView);
        contenidoTextView.setText(noticia.getDescripcion());
    }
}