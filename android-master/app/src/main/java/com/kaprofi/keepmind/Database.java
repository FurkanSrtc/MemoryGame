package com.kaprofi.keepmind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String veritabani_adi = "dboyunHafiza";
    private static final String oyun_tablosu = "tbl_oyun";
    private static final int db_version = 1;

    public Database(Context context) {
        super(context, veritabani_adi, null, db_version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//Tabloları oluşturmak için
        String sql_oyuntablosuolustur = "CREATE TABLE " + oyun_tablosu + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, Tekrar INTEGER, Combo INTEGER, Score INTEGER, Level INTEGER, Life INTEGER,  AcilacakKartSayisi INTEGER, NumRows INTEGER, NumCols INTEGER)";
        db.execSQL(sql_oyuntablosuolustur);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + oyun_tablosu);
    }
    // tekrar, int combo, int score, int level, int life, int acilacakKartSayisi, int numRows, int numCol

    public void ekleBilgi(Level levelClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Tekrar", levelClass.getTekrar());
        cv.put("Combo", levelClass.getCombo());
        cv.put("Score", levelClass.getScore());
        cv.put("Level", levelClass.getLevel());
        cv.put("Life", levelClass.getLife());
        cv.put("AcilacakKartSayisi",levelClass.getAcilacakKartSayisi());
        cv.put("NumRows",levelClass.getNumRows());
        cv.put("NumCols",levelClass.getNumCols());
       db.insert(oyun_tablosu, null, cv);

    }
    public void silTabloVerileri() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql_oyuntablosuolustur = "DELETE FROM " + oyun_tablosu;
        db.execSQL(sql_oyuntablosuolustur);

    }

    public Level getirOyunBilgileri() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * From " + oyun_tablosu + " Order by ID DESC LIMIT 1";

        Cursor c = db.rawQuery(query, null);
        int oyunID=c.getColumnIndex("ID");
        int oyunTekrar=c.getColumnIndex("Tekrar");
        int oyuncombo=c.getColumnIndex("Combo");
        int oyunscore=c.getColumnIndex("Score");
        int oyunlevel=c.getColumnIndex("Level");
        int oyunLife=c.getColumnIndex("Life");
        int oyunacilacakKartSayisi=c.getColumnIndex("AcilacakKartSayisi");
        int oyunnumRows=c.getColumnIndex("NumRows");
        int oyunnumCols=c.getColumnIndex("NumCols");


        if (c.getCount() > 0) {
            c.moveToFirst();
            Level _level = new Level();

            _level.setID(c.getInt(oyunID));
            _level.setTekrar(c.getInt(oyunTekrar));
            _level.setCombo(c.getInt(oyuncombo));
            _level.setScore(c.getInt(oyunscore));
            _level.setLevel(c.getInt(oyunlevel));
            _level.setLife(c.getInt(oyunLife));
            _level.setAcilacakKartSayisi(c.getInt(oyunacilacakKartSayisi));
            _level.setNumRows(c.getInt(oyunnumRows));
            _level.setNumCols(c.getInt(oyunnumCols));

            c.close();
            db.close();
            return _level;
        }
        else {
            return null;
        }


    }


}
