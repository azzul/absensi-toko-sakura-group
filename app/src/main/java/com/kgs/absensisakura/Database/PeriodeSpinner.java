package com.kgs.absensisakura.Database;

import java.io.Serializable;
import java.sql.Date;
public class PeriodeSpinner {

    protected int id;
    protected String namaPeriode;
    protected String dtPeriode;
    protected String dtFrom;
    protected String dtTo;
public PeriodeSpinner() {

        }

public PeriodeSpinner (String namaPeriode, String dtPeriode, String dtFrom, String dtTo ) {
        this.namaPeriode = namaPeriode;
        this.dtPeriode = dtPeriode;
        this.dtFrom = dtFrom;
        this.dtTo = dtTo;
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
