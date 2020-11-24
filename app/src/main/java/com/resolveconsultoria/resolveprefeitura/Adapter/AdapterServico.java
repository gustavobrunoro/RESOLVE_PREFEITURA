package com.resolveconsultoria.resolveprefeitura.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.resolveconsultoria.resolveprefeitura.Model.Servico;
import com.resolveconsultoria.resolveprefeitura.R;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.fontawesome.FontDrawable;

public class AdapterServico extends RecyclerView.Adapter<AdapterServico.MyviewHolder> implements Filterable {

    private Context context;
    private List<Servico> listaServicosList = new ArrayList<>();
    private List<Servico> listaServicosListfilter = new ArrayList<>();
    private Servico servico = new Servico();

    public AdapterServico (List<Servico> listaServicosList, Context context) {
        this.listaServicosList = listaServicosList;
        this.listaServicosListfilter = listaServicosList;
        this.context = context;
    }

    @Override
    public MyviewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        View lista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);

        return new MyviewHolder(lista);
    }

    @Override
    public void onBindViewHolder (@NonNull MyviewHolder holder, int position) {

        servico = listaServicosListfilter.get(position);

        holder.descricao.setText( servico.getDescricao() );
        FontDrawable drawable = new FontDrawable(context, icone( servico.getFontAwesome() ), true, false);
        //FontDrawable drawable = new FontDrawable(context, icone( "fa_beer" ), true, false);
        holder.drawable.setImageDrawable( drawable );
    }

    @Override
    public int getItemCount () {
        return listaServicosListfilter.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        private TextView descricao;
        private ImageView drawable;

        public MyviewHolder(final View itemView) {
            super(itemView);

            descricao = itemView.findViewById(R.id.tv_DetalhamentoDescricaoID);
            drawable  = itemView.findViewById(R.id.ftv_DetalhamentoIconeID);
        }
    }

    @Override
    public Filter getFilter () {

        return new Filter() {
            @Override
            protected FilterResults performFiltering (CharSequence charSequence) {
                String charString = charSequence.toString().toUpperCase();

                if (charString.isEmpty()) {
                    listaServicosListfilter = listaServicosList;
                } else {
                    List<Servico> filterList = new ArrayList<>();
                    for (Servico servico : listaServicosList) {
                        if ( servico.getDescricao().toUpperCase().contains( charString ) ) {
                            filterList.add( servico );
                        }
                    }
                    listaServicosListfilter = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listaServicosListfilter;
                return filterResults;
            }

            @Override
            protected void publishResults (CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };
    }

    public int icone ( String icone ){
        int resID =  context.getResources().getIdentifier(icone, "string",  context.getPackageName());
        return resID;
    }
}
