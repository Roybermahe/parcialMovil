package android.example.playlistdeezer.data;

public class musicModel {
    public int Id;
    public String Album;
    public String Artista;
    public int Duracion;
    public String cancion;

    public musicModel(int id,String album, String artista, int duracion, String cancion) {
        this.Id = id;
        Album = album;
        Artista = artista;
        Duracion = duracion;
        this.cancion = cancion;
    }

    public musicModel(int id) {
        Id = id;
    }
}
