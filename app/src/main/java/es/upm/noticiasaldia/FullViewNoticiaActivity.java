package es.upm.noticiasaldia;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        // Obtén la instancia de WebView desde tu diseño
        WebView webView = findViewById(R.id.webViewFullView);

        // Habilita JavaScript (si es necesario)
        webView.getSettings().setJavaScriptEnabled(true);
        String modifiedHtmlContent = noticia.getContenido().replaceAll("width=\"[^\"]*\" height=\"[^\"]*\"", "");

        modifiedHtmlContent = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\">" +
                "<style>img { max-width: 100%; height: auto; }</style></head><body>" + modifiedHtmlContent + "</body></html>";


        // Carga el contenido HTML en WebView
        webView.loadDataWithBaseURL(null, modifiedHtmlContent, "text/html", "UTF-8", null);
    }
}
