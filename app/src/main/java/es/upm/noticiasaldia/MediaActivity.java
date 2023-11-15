package es.upm.noticiasaldia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import model.Noticia;

public class MediaActivity extends AppCompatActivity {
    private Noticia noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        Intent intent = getIntent();
        noticia = (Noticia) intent.getSerializableExtra("noticia");
        if (noticia.getVideoUrl() != null){
            ImageView imageView = findViewById(R.id.imageViewFullView);
            android.view.ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.height = 0;
            imageView.setLayoutParams(params);
            Uri videoUri = Uri.parse(noticia.getVideoUrl());
            VideoView videoView = findViewById(R.id.videoViewFullView);
            videoView.setVideoURI(videoUri);
            Log.i("FULLVIEW", "HAY VIDEO");
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            TextView titulo = findViewById(R.id.tituloMediaView);
            TextView descripcion = findViewById(R.id.descripcionMediaView);
            titulo.setText(noticia.getMediaTitle());
            descripcion.setText(noticia.getMediaDescription());
        } else {
            VideoView videoView = findViewById(R.id.videoViewFullView);
            android.view.ViewGroup.LayoutParams params = videoView.getLayoutParams();
            params.height = 0;
            videoView.setLayoutParams(params);
            ImageView imageView = findViewById(R.id.imageViewFullView);
            if (noticia.getImagenUrl() != null) {
                Glide.with(this)
                        .load(noticia.getImagenUrl())
                        .into(imageView);
            }
            TextView titulo = findViewById(R.id.tituloMediaView);
            TextView descripcion = findViewById(R.id.descripcionMediaView);
            titulo.setText(noticia.getMediaTitle());
            descripcion.setText(noticia.getMediaDescription());
        }
    }
}