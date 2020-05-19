package com.example.playlistdeezer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.example.playlistdeezer.data.musicModel;
import android.example.playlistdeezer.data.searchSong;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class agregar_cancion extends AppCompatActivity {

    String nombreCancion = "";
    EditText cancion;
    EditText artista;
    EditText album;
    EditText duracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cancion);
        cancion = (EditText) findViewById(R.id.cancion);
        artista = (EditText) findViewById(R.id.artista);
        album = (EditText) findViewById(R.id.album);
        duracion =  (EditText) findViewById(R.id.duracion);
        SearchView buscador = (SearchView) findViewById ( R.id.searchMusic);
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<EditText> editTexts = new ArrayList<>();
                editTexts.add(cancion);
                editTexts.add(artista);
                editTexts.add(album);
                editTexts.add(duracion);
                new searchSong(query,agregar_cancion.this, editTexts).execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    public void guardarCancion(View view) {
        musicModel data = new musicModel(1);
        data.cancion = cancion.getText().toString();
        data.Duracion = Integer.valueOf(duracion.getText().toString());
        data.Album = album.getText().toString();
        data.Artista = artista.getText().toString();
        MainActivity.listaMusica.addLast(data);
        finish();
    }
}
