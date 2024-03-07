package com.kgs.absensisakura.Database;

import java.io.Serializable;
import java.sql.Date;

public class Periode implements Serializable {
    private int idPeriode;
    private String namaPeriode, dtPeriode,dtFrom, dtTo;

    public Integer getIdPeriode() {
        return idPeriode;
    }

    public void setIdPeriode(Integer idPeriode) {
        this.idPeriode = idPeriode;
    }


    public String getNamaPeriode() {
        return namaPeriode;
    }

    public void setNamaPeriode(String namaPeriode) {
        this.namaPeriode = namaPeriode;
    }
    public String getDatePeriode() {
        return dtPeriode;
    }

    public void setDatePeriode(String dtPeriode) {
        this.dtPeriode = dtPeriode;
    }

    public String getDateFrom() {
        return dtFrom;
    }

    public void setDateFrom(String dtFrom) {
        this.dtFrom = dtFrom;
    }
    public String getDateTo() {
        return dtTo;
    }

    public void setDateTo(String dtTo) {
        this.dtTo = dtTo;
    }

}
