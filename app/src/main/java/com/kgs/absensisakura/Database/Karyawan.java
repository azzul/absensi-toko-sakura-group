package com.kgs.absensisakura.Database;

import java.io.Serializable;

public class Karyawan implements Serializable {
    private int id;
    private String fcnik, fcnama, fcalamat, fctelp, fcnmgrp, fcjabatan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getFcnik() {
        return fcnik;
    }

    public void setFcnik(String fcnik) {
        this.fcnik = fcnik;
    }

    public String getFcnama() {
        return fcnama;
    }

    public void setFcnama(String fcnama) {
        this.fcnama = fcnama;
    }

    public String getFcalamat() {
        return fcalamat;
    }

    public void setFcalamat(String fcalamat) {
        this.fcalamat = fcalamat;
    }
    public String getFctelp() {
        return fctelp;
    }

    public void setFctelp(String fctelp) {
        this.fctelp= fctelp;
    }
    public String getFcnmgrp() {
        return fcnmgrp;
    }

    public void setFcnmgrp(String fcnmgrp) {
        this.fcnmgrp= fcnmgrp;
    }
    public String getFcjabatan() {
        return fcjabatan;
    }

    public void setFcjabatan(String fcjabatan) {
        this.fcjabatan= fcjabatan;
    }
}