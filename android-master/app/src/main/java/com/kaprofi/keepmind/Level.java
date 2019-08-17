package com.kaprofi.keepmind;

public class Level {

    private int combo;
    private int score;
    private int level;
    private int life;
    private int tekrar;
    private int AcilacakKartSayisi;
    private int NumRows;
    private int NumCols;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private  int ID;

    public int getCombo() {
        return combo;
    }

    public void setCombo(int combo) {
        this.combo = combo;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getTekrar() {
        return tekrar;
    }

    public void setTekrar(int tekrar) {
        this.tekrar = tekrar;
    }

    public int getAcilacakKartSayisi() {
        return AcilacakKartSayisi;
    }

    public void setAcilacakKartSayisi(int acilacakKartSayisi) {
        AcilacakKartSayisi = acilacakKartSayisi;
    }

    public int getNumRows() {
        return NumRows;
    }

    public void setNumRows(int numRows) {
        NumRows = numRows;
    }

    public int getNumCols() {
        return NumCols;
    }

    public void setNumCols(int numCols) {
        NumCols = numCols;
    }



    public Level(int tekrar, int combo, int score, int level, int life, int acilacakKartSayisi, int numRows, int numCols) {
        this.tekrar = tekrar;
        this.combo = combo;
        this.score = score;
        this.level = level;
        this.life = life;
        this.AcilacakKartSayisi = acilacakKartSayisi;
        this.NumRows = numRows;
        this.NumCols = numCols;
    }

    public Level() {

    }






}
