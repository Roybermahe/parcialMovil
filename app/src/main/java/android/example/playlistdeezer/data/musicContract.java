package android.example.playlistdeezer.data;

import android.provider.BaseColumns;

public final class musicContract {
    private musicContract(){

    }

    public static final class musicEntry implements BaseColumns {

        public final static String TABLE_NAME = "musica";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CANCION = "cancion";
        public final static String COLUMN_ARTISTA = "artista";
        public final static String COLUMN_DURACION = "duracion";
        public final static String COLUMN_ALBUM = "album";
    }
}
