package com.souzs.apptccpassageiro.model;

public class Motorista {
    private String idMLogado;
    private String nomeLinha;
    private String sttLinha;

    private String lat;
    private String lon;

    public Motorista() {
    }


    public String getIdMLogado() {
        return idMLogado;
    }

    public void setIdMLogado(String idMLogado) {
        this.idMLogado = idMLogado;
    }

    public String getNomeLinha() {
        return nomeLinha;
    }

    public void setNomeLinha(String nomeLinha) {
        this.nomeLinha = nomeLinha;
    }

    public String getSttLinha() {
        return sttLinha;
    }

    public void setSttLinha(String sttLinha) {
        this.sttLinha = sttLinha;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
