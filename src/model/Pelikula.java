/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eider
 */

public class Pelikula {
    
    private String izena;
    private String direktorea;
    private int urtea;
    private String generoa;
    private String ikusita;
    
    public Pelikula(String izena,String direktorea, String urtea, String generoa, String ikusita) {
        
        this.izena = izena;
        this.direktorea = direktorea;
        this.urtea = Integer.parseInt(urtea);
        this.generoa = generoa;
        this.ikusita = ikusita;
    }

    public String getIzena() {
        return izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getDirektorea() {
        return direktorea;
    }

    public void setDirektorea(String direktorea) {
        this.direktorea = direktorea;
    }

    public String getUrtea() {
        return String.valueOf(urtea);
    }

    public void setUrtea(String urtea) {
        this.urtea = Integer.parseInt(urtea);
    }

    public String getGeneroa() {
        return generoa;
    }

    public void setGeneroa(String generoa) {
        this.generoa = generoa;
    }

    public String getIkusita() {
        return ikusita;
    }

    public void setIkusita(String ikusita) {
        this.ikusita = ikusita;
    }    
}