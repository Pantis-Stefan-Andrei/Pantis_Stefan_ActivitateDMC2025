package com.example.lab1;

import java.io.Serializable;
import java.util.Date;

public class APArtament implements Serializable {
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

    public enum TipLocatie {
        URBAN, RURAL, SEMIURBAN
    }

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

    @Override
    public String toString() {
        return adresa + " - " + numarCamere + " camere, " + suprafata + " m², " +
                "Balcon: " + (areBalcon ? "Da" : "Nu") + ", " +
                "Rating: " + rating + " stele, " +
                "Construit: " + dataConstructie.toString();
    }
}
