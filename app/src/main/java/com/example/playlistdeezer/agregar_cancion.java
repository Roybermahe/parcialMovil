package com.example.playlistdeezer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.example.playlistdeezer.data.musicContract;
import android.example.playlistdeezer.data.musicDBhelper;
public class agregar_cancion extends AppCompatActivity {

    EditText cancion;
    EditText artista;
    EditText album;
    EditText duracion;
    musicDBhelper DbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cancion);

        DbHelper = new musicDBhelper(this);
        cancion = (EditText) findViewById(R.id.cancion);
         artista = (EditText) findViewById(R.id.artista);
        album = (EditText) findViewById(R.id.album);
        duracion =  (EditText) findViewById(R.id.duracion);
    }


    public void guardarCancion(View view) {
            String cancion = this.cancion.getText().toString().trim();
            String artista = this.artista.getText().toString().trim();
            String album = this.album.getText().toString().trim();
            int duracion = Integer.parseInt(this.duracion.getText().toString());

            SQLiteDatabase db = DbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(musicContract.musicEntry.COLUMN_ALBUM, album);
            values.put(musicContract.musicEntry.COLUMN_ARTISTA, artista);
            values.put(musicContract.musicEntry.COLUMN_CANCION, cancion);
            values.put(musicContract.musicEntry.COLUMN_DURACION, duracion);

            // -1 Error
            long id = db.insert(musicContract.musicEntry.TABLE_NAME, null, values);
            if( id == -1 ) Toast.makeText(this,"Error with saving la cancion",Toast.LENGTH_SHORT).show();
            else Toast.makeText(this,"cancion saved!", Toast.LENGTH_SHORT).show();
    }
}
