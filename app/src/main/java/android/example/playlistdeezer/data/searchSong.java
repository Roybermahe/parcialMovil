package android.example.playlistdeezer.data;

        import android.app.Activity;
        import android.os.AsyncTask;
        import android.widget.EditText;
        import android.widget.Toast;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.ProtocolException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.Scanner;

public class searchSong extends AsyncTask<Void, Void, Void> {
    String searchSong = "";
    Activity context;
    ArrayList<EditText> inputs;
    musicModel data = null;
    public searchSong(String searchSong, Activity context, ArrayList<EditText> inputs) {
        this.searchSong = searchSong;
        this.context = context;
        this.inputs = inputs;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        StringBuilder canciones = new StringBuilder();
        try {
            URL url = new URL("https://api.deezer.com/search?q="+this.searchSong);
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
            JSONArray cancion = jsonObj.getJSONArray("data");

            JSONObject track = cancion.getJSONObject(0);
            data = new musicModel(track.getInt("id"));
            JSONObject artista = track.getJSONObject("artist");
            data.Artista = artista.getString("name");
            JSONObject album = track.getJSONObject("album");
            data.Album = album.getString("title");
            data.cancion = track.getString("title");
            data.Duracion = track.getInt("duration");

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
        Toast.makeText(this.context.getBaseContext(), "Se encontró: "+this.searchSong, Toast.LENGTH_SHORT).show();
        inputs.get(0).setText(data.cancion);
        inputs.get(1).setText(data.Artista);
        inputs.get(2).setText(data.Album);
        inputs.get(3).setText(String.valueOf(data.Duracion));
    }
}
