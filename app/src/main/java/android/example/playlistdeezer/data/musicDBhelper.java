package android.example.playlistdeezer.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.example.playlistdeezer.data.musicContract;

public class musicDBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    public musicDBhelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_MUSIC_TABLE = "CREATE TABLE " + musicContract.musicEntry.TABLE_NAME +" ("
                + musicContract.musicEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + musicContract.musicEntry.COLUMN_ALBUM + " TEXT NOT NULL, "
                + musicContract.musicEntry.COLUMN_CANCION + " TEXT NOT NULL, "
                + musicContract.musicEntry.COLUMN_ARTISTA + " TEXT NOT NULL, "
                + musicContract.musicEntry.COLUMN_DURACION + " INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_MUSIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
