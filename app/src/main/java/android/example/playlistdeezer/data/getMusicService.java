package android.example.playlistdeezer.data;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface getMusicService {
    String APIRoute = "/?method=chart.gettoptracks&api_key=b284db959637031077380e7e2c6f2775&format=json";
    @GET(APIRoute)
    Call<List<Object>> getData();
}
