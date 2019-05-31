package com.example.bezi.leoniapplication;

public class Model {
    String Date, Time, component,variante;
    Float ID,kabaweight,lad,nbre_pieces,poste;

    public Model() {
    }

    public Model(String date, String time, String component, String variante, Float ID, Float kabaweight, Float lad, Float nbre_pieces, Float poste) {
        Date = date;
        Time = time;
        this.component = component;
        this.variante = variante;
        this.ID = ID;
        this.kabaweight = kabaweight;
        this.lad = lad;
        this.nbre_pieces = nbre_pieces;
        this.poste = poste;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getVariante() {
        return variante;
    }

    public void setVariante(String variante) {
        this.variante = variante;
    }

    public Float getID() {
        return ID;
    }

    public void setID(Float ID) {
        this.ID = ID;
    }

    public Float getKabaweight() {
        return kabaweight;
    }

    public void setKabaweight(Float kabaweight) {
        this.kabaweight = kabaweight;
    }

    public Float getLad() {
        return lad;
    }

    public void setLad(Float lad) {
        this.lad = lad;
    }

    public Float getNbre_pieces() {
        return nbre_pieces;
    }

    public void setNbre_pieces(Float nbre_pieces) {
        this.nbre_pieces = nbre_pieces;
    }

    public Float getPoste() {
        return poste;
    }

    public void setPoste(Float poste) {
        this.poste = poste;
    }
}





