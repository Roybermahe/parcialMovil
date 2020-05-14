package com.example.playlistdeezer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.example.playlistdeezer.data.getMusicService;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.example.playlistdeezer.data.musicDBhelper;
import android.example.playlistdeezer.data.musicModel;
import android.example.playlistdeezer.data.musicContract.musicEntry;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private musicDBhelper DbHelper;

    ProgressDialog pd;
    RecyclerView list;
    LinkedList<musicModel> listaMusica = new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DbHelper = new musicDBhelper(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, agregar_cancion.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
        //new JsonTask().execute("http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=b284db959637031077380e7e2c6f2775&format=json");
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = DbHelper.getReadableDatabase();
        listaMusica.removeAll(listaMusica);

        String[] projection = {
                musicEntry._ID,
                musicEntry.COLUMN_ALBUM,
                musicEntry.COLUMN_ARTISTA,
                musicEntry.COLUMN_CANCION,
                musicEntry.COLUMN_DURACION,
        };

        Cursor cursor = db.query(
                musicEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        try {

            int idColumnIndex = cursor.getColumnIndex(musicEntry._ID);
            int albumColumnIndex = cursor.getColumnIndex(musicEntry.COLUMN_ALBUM);
            int artistaColumnIndex = cursor.getColumnIndex(musicEntry.COLUMN_ARTISTA);
            int cancionColumnIndex = cursor.getColumnIndex(musicEntry.COLUMN_CANCION);
            int duracionColumnIndex = cursor.getColumnIndex(musicEntry.COLUMN_DURACION);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String album = cursor.getString(albumColumnIndex);
                String artista = cursor.getString(artistaColumnIndex);
                String cancion= cursor.getString(cancionColumnIndex);
                int duracion = cursor.getInt(duracionColumnIndex);

                listaMusica.add(new musicModel(currentID, album, artista, duracion, cancion));
            }
            listAdapter adapter = new listAdapter(this, listaMusica);
            list = findViewById(R.id.listaMusica);
            list.setAdapter(adapter);
            list.setLayoutManager(new LinearLayoutManager(this));
        } finally {
            cursor.close();
        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
        }
    }
}


