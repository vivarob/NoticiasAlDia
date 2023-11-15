package es.upm.noticiasaldia;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import model.Noticia;

public class NoticiasAdapter extends BaseAdapter {
    private Activity ctx;
    private List<Noticia> data;

    public NoticiasAdapter(Activity ctx, List<Noticia> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = ctx.getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.noticia_item_list_layout,null);
        }
        String dateFormat = "yyyy-MM-dd HH:mm:ss"; // You can customize the format as needed
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());
        String formattedDate = sdf.format(data.get(position).getFecha());

        ((ImageView)convertView.findViewById(R.id.noticia_item_image)).setImageBitmap(data.get(position).getImagenBitmap());
        ((TextView)convertView.findViewById(R.id.noticia_item_titulo)).setText(data.get(position).getTitulo());
        ((TextView)convertView.findViewById(R.id.noticia_item_fecha)).setText(formattedDate);
        ((TextView)convertView.findViewById(R.id.noticia_item_contenido)).setText(data.get(position).getDescripcion());


        return convertView;
    }
}
