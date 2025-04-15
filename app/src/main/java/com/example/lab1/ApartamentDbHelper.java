package com.example.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.List;

public class ApartamentDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "apartamente.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "apartamente";

    public ApartamentDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "adresa TEXT," +
                "numarCamere INTEGER," +
                "rating REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void insertApartament(APArtament ap) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("adresa", ap.getAdresa());
        cv.put("numarCamere", ap.getNumarCamere());
        cv.put("rating", ap.getRating());
        db.insert(TABLE_NAME, null, cv);
    }


    public List<APArtament> getAll() {
        List<APArtament> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (c.moveToNext()) {
            list.add(new APArtament(
                    c.getString(1),
                    c.getInt(2),
                    c.getFloat(3)
            ));
        }
        c.close();
        return list;
    }


    public List<APArtament> getByAdresa(String adresa) {
        List<APArtament> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE adresa = ?", new String[]{adresa});
        while (c.moveToNext()) {
            list.add(new APArtament(c.getString(1), c.getInt(2), c.getFloat(3)));
        }
        c.close();
        return list;
    }


    public List<APArtament> getByInterval(int min, int max) {
        List<APArtament> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE numarCamere BETWEEN ? AND ?",
                new String[]{String.valueOf(min), String.valueOf(max)});
        while (c.moveToNext()) {
            list.add(new APArtament(c.getString(1), c.getInt(2), c.getFloat(3)));
        }
        c.close();
        return list;
    }


    public void deleteByRatingLessThan(float rating) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "rating < ?", new String[]{String.valueOf(rating)});
    }


    public void incrementCamereAdresaIncepeCu(char litera) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET numarCamere = numarCamere + 1 WHERE adresa LIKE ?",
                new Object[]{litera + "%"});
    }
}
