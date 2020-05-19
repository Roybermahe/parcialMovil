package android.example.playlistdeezer.data;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.playlistdeezer.R;
import com.example.playlistdeezer.listAdapter;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class getMusicList extends AsyncTask<Void, Void, Void> {
    LinkedList<musicModel> dataIntern = new LinkedList<>();
    LinkedList<musicModel> musicList = new LinkedList<>();
    Activity activity;

    public getMusicList(Activity activityContext, LinkedList<musicModel> dataIntern) {
        this.activity = activityContext;
        this.dataIntern = dataIntern;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(this.activity.getBaseContext(), "cargando canciones ...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
       StringBuilder canciones = new StringBuilder();
        try {
            URL url = new URL("https://api.deezer.com/playlist/93489551/tracks");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                canciones.append(scanner.next());
            }

            JSONObject jsonObj = new JSONObject(canciones.toString());
            JSONArray listadetracks = jsonObj.getJSONArray("data");
            for (int i = 0; i < listadetracks.length(); i++) {

                JSONObject track = listadetracks.getJSONObject(i);
                musicModel data = new musicModel(track.getInt("id"));

                JSONObject artista = track.getJSONObject("artist");
                data.Artista = artista.getString("name");

                JSONObject album = track.getJSONObject("album");
                data.Album = album.getString("title");

                data.cancion = track.getString("title");
                data.Duracion = track.getInt("duration");

                musicList.add(data);
            }
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(this.dataIntern.size()>0) {
            for (int i = 0; i < this.dataIntern.size(); i++) {
                musicList.addLast(this.dataIntern.get(i));
            }
        }
        Toast.makeText(this.activity.getBaseContext(), "se encontraron: "+this.musicList.size()+" canciones", Toast.LENGTH_SHORT).show();
        RecyclerView list;
        listAdapter adapter = new listAdapter(this.activity.getBaseContext(), musicList);
        list = activity.findViewById(R.id.listaMusica);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this.activity.getBaseContext()));
    }
}
