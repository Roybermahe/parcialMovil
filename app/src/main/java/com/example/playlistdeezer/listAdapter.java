package com.example.playlistdeezer;

import android.content.Context;
import android.content.Intent;
import android.example.playlistdeezer.data.musicModel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class listAdapter extends RecyclerView.Adapter<listAdapter.MusicViewHolder> {
    private int positionActual;
    private Context contextActual;
    private LayoutInflater mInflater;
    private final LinkedList<musicModel> listaMusica ;

    public listAdapter(Context context, LinkedList<musicModel> lista) {
        contextActual = context;
        mInflater = LayoutInflater.from(context);
        this.listaMusica = lista;
    }

    @NonNull
    @Override
    public listAdapter.MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.listitem, parent, false);
        return new MusicViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull listAdapter.MusicViewHolder holder,int position) {
        musicModel music = this.listaMusica.get(position);
        holder.Name.setText(music.Artista + " - " + music.cancion);
        String startTime = "00:00";
        int minutes = music.Duracion;
        int h = minutes / 60 + Integer.parseInt(startTime.substring(0,1));
        int m = minutes % 60 + Integer.parseInt(startTime.substring(3,4));
        String newtime = h+":"+m;
        holder.Duration.setText("duración: " + newtime);
    }

    @Override
    public int getItemCount() {
        return listaMusica.size();
    }


    class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView Name;
        private TextView Duration;
        private listAdapter adapter;

        public MusicViewHolder(@NonNull View itemView, listAdapter adapter) {
            super(itemView);
            Name = itemView.findViewById(R.id.title);
            Duration = itemView.findViewById(R.id.duration_label);
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
