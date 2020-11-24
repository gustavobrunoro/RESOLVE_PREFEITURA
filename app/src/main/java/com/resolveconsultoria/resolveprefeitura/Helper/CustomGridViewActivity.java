package com.resolveconsultoria.resolveprefeitura.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.resolveconsultoria.resolveprefeitura.Model.Categoria;
import com.resolveconsultoria.resolveprefeitura.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomGridViewActivity extends BaseAdapter {

    private Context mContext;
    private List<Categoria> categoriaList = new ArrayList<>();

    public CustomGridViewActivity(Context context, List<Categoria> categoriaList) {
        mContext = context;
        this.categoriaList = categoriaList;
    }

    @Override
    public int getCount() {
        return categoriaList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            String url = mContext.getResources().getString(R.string.url_catalogo_imgem) + categoriaList.get(i).getIcone() ;

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            final ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(categoriaList.get(i).getDescricao());

            Picasso.get().load( url ).into(imageViewAndroid);

        }
        else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }

}