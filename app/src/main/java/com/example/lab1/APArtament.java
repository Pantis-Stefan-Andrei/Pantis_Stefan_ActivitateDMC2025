package com.example.lab1;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class APArtament implements Parcelable, Serializable {

    private String adresa;
    private int numarCamere;
    private boolean areBalcon;
    private TipLocatie locatie;
    private float suprafata;
    private float rating;
    private boolean inchiriere;
    private boolean mobilat;
    private String tipApartament;
    private Date dataConstructie;
    private boolean favorit;


    public enum TipLocatie {
        URBAN, RURAL, SEMIURBAN
    }

    public APArtament() {}
    public APArtament(String adresa, int numarCamere, boolean areBalcon, TipLocatie locatie, float suprafata,
                      float rating, boolean inchiriere, boolean mobilat, String tipApartament, Date dataConstructie) {
        this.adresa = adresa;
        this.numarCamere = numarCamere;
        this.areBalcon = areBalcon;
        this.locatie = locatie;
        this.suprafata = suprafata;
        this.rating = rating;
        this.inchiriere = inchiriere;
        this.mobilat = mobilat;
        this.tipApartament = tipApartament;
        this.dataConstructie = dataConstructie;
    }


    public APArtament(String adresa, int numarCamere, float rating) {
        this.adresa = adresa;
        this.numarCamere = numarCamere;
        this.rating = rating;


        this.areBalcon = false;
        this.locatie = TipLocatie.URBAN;
        this.suprafata = 0f;
        this.inchiriere = false;
        this.mobilat = false;
        this.tipApartament = "";
        this.dataConstructie = new Date();
    }


    protected APArtament(Parcel in) {
        adresa = in.readString();
        numarCamere = in.readInt();
        areBalcon = in.readByte() != 0;
        locatie = TipLocatie.valueOf(in.readString());
        suprafata = in.readFloat();
        rating = in.readFloat();
        inchiriere = in.readByte() != 0;
        mobilat = in.readByte() != 0;
        tipApartament = in.readString();
        long time = in.readLong();
        dataConstructie = time == -1 ? null : new Date(time);
    }

    public static final Creator<APArtament> CREATOR = new Creator<>() {
        @Override
        public APArtament createFromParcel(Parcel in) {
            return new APArtament(in);
        }

        @Override
        public APArtament[] newArray(int size) {
            return new APArtament[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adresa);
        dest.writeInt(numarCamere);
        dest.writeByte((byte) (areBalcon ? 1 : 0));
        dest.writeString(locatie.name());
        dest.writeFloat(suprafata);
        dest.writeFloat(rating);
        dest.writeByte((byte) (inchiriere ? 1 : 0));
        dest.writeByte((byte) (mobilat ? 1 : 0));
        dest.writeString(tipApartament);
        dest.writeLong(dataConstructie != null ? dataConstructie.getTime() : -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public String getAdresa() { return adresa; }
    public int getNumarCamere() { return numarCamere; }
    public boolean isAreBalcon() { return areBalcon; }
    public TipLocatie getLocatie() { return locatie; }
    public float getSuprafata() { return suprafata; }
    public float getRating() { return rating; }
    public boolean isInchiriere() { return inchiriere; }
    public boolean isMobilat() { return mobilat; }
    public String getTipApartament() { return tipApartament; }
    public Date getDataConstructie() { return dataConstructie; }
    public boolean isFavorit() { return favorit; }
    public void setFavorit(boolean favorit) { this.favorit = favorit; }

}
