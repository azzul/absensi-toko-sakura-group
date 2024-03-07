package com.kgs.absensisakura.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    Context Context;
    Integer no;
    public static String DATABASE_NAME = "dbabsensi";
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ID = "id";
    private static final String TABLE_KRY = "tbkaryawan";
    private static final String KEY_NIK = "fcnik";
    private static final String KEY_NAMA = "fcnama";
    private static final String KEY_ALAMAT = "fcalamat";
    private static final String KEY_TELEPON = "fctelp";
    private static final String KEY_JABATAN = "fcjabatan";
    private static final String KEY_GROUP = "fcnmgrp";

    //tbl Periode
    private static final String TABLE_PRD = "tbperiode";
    private static final String KEY_IDPRD = "idPeriode";
    private static final String KEY_NMPRD= "namaPeriode";
    private static final String KEY_DTPRD= "dtPeriode";
    private static final String KEY_DTFROM = "dtFrom";
    private static final String KEY_DTTO = "dtTo";

    //tbl Kode absen
    private static final String TABLE_KD= "tbkdabsen";
    private static final String KEY_KDABS = "kdabsen";
    private static final String KEY_NMKDABS = "nmkdabsen";



    //tbl Kode absen

    private static final String TABLE_ABSENSI= "tbabsensi";
    private static final String KEY_IDABSEN = "idabsen";
    private static final String KEY_DTABSEN = "tglabsen";
    private static final String KEY_DTGRULE = "dtgrule";
    private static final String KEY_PLGRULE = "plgrule";
    private static final String KEY_DTGACT = "dtgact";
    private static final String KEY_PLGACT = "plgact";
    private static final String KEY_LBRR = "lbrr";
    private static final String KEY_LBRH = "lbrh";
    private static final String KEY_IZIN = "izin";
    private static final String KEY_KTRGABS = "ketabsen";
    private static final String KEY_FNKK = "fnkk";
    private static final String KEY_FNOFF = "fnoff";
    private static final String KEY_FNKH = "fnkh";
    private static final String KEY_FNIT= "fnit";
    private static final String KEY_FNALPHA = "fnalpha";
    private static final String KEY_FNSD = "fnsd";
    private static final String CREATE_TABLE_KARYAWAN = "CREATE TABLE "
            + TABLE_KRY + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," +  KEY_NIK
            + " VARCHAR," + KEY_NAMA + " TEXT, " + KEY_ALAMAT + " TEXT, " + KEY_TELEPON + " TEXT, " + KEY_JABATAN + " TEXT, " + KEY_GROUP + " TEXT );";

    private static final String CREATE_TABLE_PERIODE = "CREATE TABLE "
            + TABLE_PRD + "(" +  KEY_DTPRD
            + " TEXT," + KEY_NMPRD + " TEXT, " + KEY_DTFROM + " TEXT, " + KEY_DTTO + " TEXT );";
    private static final String CREATE_TABLE_KD = "CREATE TABLE "
            + TABLE_KD + "(" +  KEY_KDABS
            + " TEXT," + KEY_NMKDABS + " TEXT );";
    private static final String CREATE_TABLE_ABSENSI = "CREATE TABLE "
            + TABLE_ABSENSI + "(" +  KEY_IDABSEN
            + " TEXT PRIMARY KEY NOT NULL," +  KEY_NIK
            + " VARCHAR," + KEY_NAMA + " TEXT, " + KEY_DTABSEN + " TEXT, " + KEY_DTGRULE
            + " TEXT," + KEY_PLGRULE + " TEXT," + KEY_DTGACT + " TEXT,"
            + KEY_PLGACT + " TEXT," + KEY_KDABS + " TEXT," + KEY_NMKDABS + " TEXT," + KEY_LBRR + " TEXT," + KEY_LBRH + " TEXT,"
            + KEY_IZIN + " TEXT," + KEY_FNKK + " INT," + KEY_FNOFF + " INT," + KEY_FNKH + " INT,"
            + KEY_FNIT + " INT,"  + KEY_FNALPHA + " INT," + KEY_FNSD + " INT," + KEY_KTRGABS + " TEXT);";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_KARYAWAN);
        db.execSQL(CREATE_TABLE_PERIODE);
        db.execSQL(CREATE_TABLE_KD);
        db.execSQL(CREATE_TABLE_ABSENSI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_KRY + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_PRD + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_KD + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_ABSENSI + "'");
        onCreate(db);
    }

    public long addKaryawanDetail(Integer id, String fcnik ,String fcnama, String fcalamat, String fctelp, String fcjabatan, String fcnmgrp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_NIK, fcnik);
        values.put(KEY_NAMA, fcnama);
        values.put(KEY_ALAMAT, fcalamat);
        values.put(KEY_TELEPON, fctelp);
        values.put(KEY_JABATAN, fcjabatan);
        values.put(KEY_GROUP, fcnmgrp);

        long insert = db.insert(TABLE_KRY, null, values);
        Log.e("data", fcnik);
        return insert;
    }

    public ArrayList<Karyawan> getAllUsers() {
        ArrayList<Karyawan> userModelArrayList = new ArrayList<Karyawan>();

        String selectQuery = "SELECT * FROM " + TABLE_KRY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        no = 0;

        if (c.moveToFirst()) {
            do {
                Karyawan kry = new Karyawan();
                no++;
                kry.setFcnik(c.getString(c.getColumnIndexOrThrow(KEY_NIK)));
                kry.setFcnama(c.getString(c.getColumnIndexOrThrow(KEY_NAMA)));
                kry.setFcalamat(c.getString(c.getColumnIndexOrThrow(KEY_ALAMAT)));
                kry.setFctelp(c.getString(c.getColumnIndexOrThrow(KEY_TELEPON)));
                kry.setFcjabatan(c.getString(c.getColumnIndexOrThrow(KEY_JABATAN)));
                kry.setFcnmgrp(c.getString(c.getColumnIndexOrThrow(KEY_GROUP)));
                // adding to Students list
                userModelArrayList.add(kry);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }
    public ArrayList<Karyawan> getAllKaryawanAbsen(String tanggal) {
        ArrayList<Karyawan> userModelArrayList = new ArrayList<Karyawan>();

        String selectQuery = "SELECT * FROM " + TABLE_KRY + " WHERE fcnik NOT IN (SELECT fcnik FROM tbabsensi WHERE tglabsen like '%"+tanggal+"%')";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        no = 0;
        Log.e("query", selectQuery);
        if (c.moveToFirst()) {
            do {
                Karyawan kry = new Karyawan();
                no++;
                kry.setFcnik(c.getString(c.getColumnIndexOrThrow(KEY_NIK)));
                kry.setFcnama(c.getString(c.getColumnIndexOrThrow(KEY_NAMA)));
                kry.setFcalamat(c.getString(c.getColumnIndexOrThrow(KEY_ALAMAT)));
                kry.setFctelp(c.getString(c.getColumnIndexOrThrow(KEY_TELEPON)));
                kry.setFcjabatan(c.getString(c.getColumnIndexOrThrow(KEY_JABATAN)));
                kry.setFcnmgrp(c.getString(c.getColumnIndexOrThrow(KEY_GROUP)));
                // adding to Students list
                userModelArrayList.add(kry);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }
    public void deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KRY, KEY_ID + " = ?",

                new String[]{String.valueOf(id)});
        Log.e("data", "id");
    }

    public int updateUser(int id, String nim, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAMA, name);
        values.put(KEY_NIK, nim);
        return db.update(TABLE_KRY, values, KEY_NIK + " = ?",
                new String[]{String.valueOf(id)});
    }


    public long addPeriode(String nmPeriode , String dtPeriode, String dtFrom, String dtTo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NMPRD, nmPeriode);
        values.put(KEY_DTPRD, dtPeriode);
        values.put(KEY_DTFROM, dtFrom);
        values.put(KEY_DTTO, dtTo);

        long insert2 = db.insert(TABLE_PRD, null, values);
        Log.e("data Periode", dtPeriode);
        Log.e("data From", dtFrom);
        Log.e("data To", dtTo);
        Log.e("insert", "SUKSES");
        return insert2;
    }
    public ArrayList<Periode> getAllPeriode() {
        ArrayList<Periode> periodeModelArrayList = new ArrayList<Periode>();

        String selectQuery = "SELECT * FROM " + TABLE_PRD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        no = 0;
        if (c.moveToFirst()) {
            do {
                Periode prd = new Periode();
                no++;
                prd.setNamaPeriode(c.getString(c.getColumnIndexOrThrow(KEY_NMPRD)));
                prd.setDatePeriode(c.getString(c.getColumnIndexOrThrow(KEY_DTPRD)));
                prd.setDateFrom(c.getString(c.getColumnIndexOrThrow(KEY_DTFROM)));
                prd.setDateTo(c.getString(c.getColumnIndexOrThrow(KEY_DTTO)));

                // adding to Students list
                periodeModelArrayList.add(prd);
            } while (c.moveToNext());
        }
        return periodeModelArrayList;
    }
    public void deletePeriode(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRD, KEY_DTPRD + " = ?",

                new String[]{String.valueOf(id)});
        Log.e("data", "id");
    }

    public List<Karyawan> getAllKaryawan(){
        List<Karyawan> list = new ArrayList<Karyawan>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_KRY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c= db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Karyawan kry = new Karyawan();
                kry.setFcnama(c.getString(c.getColumnIndexOrThrow(KEY_NAMA)));
                // adding to Students list

               list.add(kry);
                // adding to Students list
            } while (c.moveToNext());
        }
        // closing connection
        c.close();
        db.close();
        // returning lables
        return list;
    }
    public Integer cekKaryawanAda() {
        String selectQuery = "SELECT  COUNT(*) AS count FROM " + TABLE_KRY ;
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor c = db2.rawQuery(selectQuery, null);
        c.moveToFirst();
        int count = c.getInt(0);
        Log.e( "cekKaryawanAda: ", String.valueOf(count));
        c.close();
        return count;
    }
    //Untuk absensi

    public long addAbsensi(String fcidabsen, String fdtglabsen, String fddtgrule, String fdplgrule, String fddtgact, String fdplgact, String fnlbrr, String fnlbrh, String fnizin, String fcket, String fcnik, String fcnama,
                           String fckdabsen, String fcnmkdabsen, Integer fnkk, Integer fnoff, Integer fnkh, Integer fnit, Integer fnalpha, Integer fnsd ) {
        String selectQuery = "SELECT  COUNT(*) AS count FROM " + TABLE_ABSENSI + " WHERE idabsen=" +fcidabsen;
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor c = db2.rawQuery(selectQuery, null);
        Integer jum=0;
        if (c.moveToFirst()) {
            do {
                jum=c.getInt(c.getColumnIndexOrThrow("count"));
            } while (c.moveToNext());
            }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDABSEN, fcidabsen);
        values.put(KEY_DTABSEN, fdtglabsen);
        values.put(KEY_DTGRULE, fddtgrule);
        values.put(KEY_PLGRULE, fdplgrule);
        values.put(KEY_DTGACT, fddtgact);
        values.put(KEY_PLGACT, fdplgact);
        values.put(KEY_LBRR, fnlbrr);
        values.put(KEY_LBRH, fnlbrh);
        values.put(KEY_IZIN, fnizin);
        values.put(KEY_KTRGABS, fcket);
        values.put(KEY_NIK, fcnik);
        values.put(KEY_NAMA, fcnama);
        values.put(KEY_KDABS, fckdabsen);
        values.put(KEY_NMKDABS, fcnmkdabsen);
        values.put(KEY_FNKK, fnkk);
        values.put(KEY_FNOFF, fnoff);
        values.put(KEY_FNKH, fnkh);
        values.put(KEY_FNIT, fnit);
        values.put(KEY_FNALPHA, fnalpha);
        values.put(KEY_FNSD, fnsd);

        long insert = db.insert(TABLE_ABSENSI, null, values);
        Log.e("absensi", fcnmkdabsen);
        return insert;
    }

    public ArrayList<Absensi> getAllAbsensi() {
        ArrayList<Absensi> absensiModelArrayList = new ArrayList<Absensi>();
        String selectQuery = "SELECT  * FROM " + TABLE_ABSENSI + " ORDER BY tglabsen ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        no = 0;
        Log.e("query", selectQuery);
        if (c.moveToFirst()) {
            do {
                Absensi abs = new Absensi();
                no++;
                abs.setFcnik(c.getString(c.getColumnIndexOrThrow(KEY_NIK)));
                abs.setFcnama(c.getString(c.getColumnIndexOrThrow(KEY_NAMA)));
                abs.setIdAbsen(c.getString(c.getColumnIndexOrThrow(KEY_IDABSEN)));
                abs.setFdtglabsen(c.getString(c.getColumnIndexOrThrow(KEY_DTABSEN)));
                abs.setFddtgrule(c.getString(c.getColumnIndexOrThrow(KEY_DTGRULE)));
                abs.setFdplgrule(c.getString(c.getColumnIndexOrThrow(KEY_PLGRULE)));
                abs.setFddtgact(c.getString(c.getColumnIndexOrThrow(KEY_DTGACT)));
                abs.setFdplgact(c.getString(c.getColumnIndexOrThrow(KEY_PLGACT)));
                abs.setFnlbrr(c.getString(c.getColumnIndexOrThrow(KEY_LBRR)));
                abs.setFnlbrh(c.getString(c.getColumnIndexOrThrow(KEY_LBRH)));
                abs.setFnizin(c.getString(c.getColumnIndexOrThrow(KEY_IZIN)));
                abs.setFcket(c.getString(c.getColumnIndexOrThrow(KEY_KTRGABS)));
                abs.setFnkk(c.getString(c.getColumnIndexOrThrow(KEY_FNKK)));
                abs.setFnoff(c.getString(c.getColumnIndexOrThrow(KEY_FNOFF)));
                abs.setFnkh(c.getString(c.getColumnIndexOrThrow(KEY_FNKH)));
                abs.setFnit(c.getString(c.getColumnIndexOrThrow(KEY_FNIT)));
                abs.setFnalpha(c.getString(c.getColumnIndexOrThrow(KEY_FNALPHA)));
                abs.setFnsd(c.getString(c.getColumnIndexOrThrow(KEY_FNSD)));
                abs.setFcnmkdabsen(c.getString(c.getColumnIndexOrThrow(KEY_NMKDABS)));
                abs.setFckdabsen(c.getString(c.getColumnIndexOrThrow(KEY_KDABS)));
                Log.e("klo", c.getString(c.getColumnIndexOrThrow(KEY_NMKDABS)));
                // adding to Students list
                absensiModelArrayList.add(abs);
            } while (c.moveToNext());
        }
        return absensiModelArrayList;
    }
    public ArrayList<Absensi> getAbsensiList() {
        ArrayList<Absensi> absensiModelArrayList = new ArrayList<Absensi>();

        String selectQuery = "SELECT t1.fcnik as fcnik, t1.fcnama as fcnama, CASE t1.tglabsen WHEN t1.tglabsen==t2.tglabsen " +
                " THEN t1.tglabsen='' ELSE t1.tglabsen END as tglabsen, t1.dtgact as dtgact, t1.plgact as plgact, t1.nmkdabsen FROM tbabsensi t1, tbabsensi t2 WHERE t1.idabsen = t2.idabsen-1 ORDER BY t2.tglabsen DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        no = 0;
        Log.e("query", selectQuery);
        if (c.moveToFirst()) {
            do {
                Absensi abs = new Absensi();
                no++;
                abs.setFcnik(c.getString(c.getColumnIndexOrThrow(KEY_NIK)));
                abs.setFcnama(c.getString(c.getColumnIndexOrThrow(KEY_NAMA)));
                abs.setIdAbsen(c.getString(c.getColumnIndexOrThrow(KEY_IDABSEN)));
                abs.setFdtglabsen(c.getString(c.getColumnIndexOrThrow(KEY_DTABSEN)));
                abs.setFddtgact(c.getString(c.getColumnIndexOrThrow(KEY_DTGACT)));
                abs.setFdplgact(c.getString(c.getColumnIndexOrThrow(KEY_PLGACT)));
                abs.setFcnmkdabsen(c.getString(c.getColumnIndexOrThrow(KEY_NMKDABS)));
                Log.e("klo", c.getString(c.getColumnIndexOrThrow(KEY_NMKDABS)));
                // adding to Students list
                absensiModelArrayList.add(abs);
            } while (c.moveToNext());
        }
        return absensiModelArrayList;
    }
    public void deleteAbsensi(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ABSENSI, KEY_IDABSEN+ " = ?",

                new String[]{String.valueOf(id)});
        Log.e("data", "id");
    }
    public Integer cekKaryawan(String fcnik) {
        String selectQuery = "SELECT COUNT(*) as count FROM " + TABLE_KRY + " WHERE fcnik LIKE '%" +fcnik+ "%' LIMIT 1";
        Log.e("query", selectQuery);
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor c = db2.rawQuery(selectQuery, null);
        c.moveToFirst();
        int count = c.getInt(c.getColumnIndexOrThrow("count"));
        Log.e( "cek absensi : ", String.valueOf(count));
        c.close();
        return count;
    }
    public Integer cek(String idabsen) {
        String selectQuery = "SELECT COUNT(*) as count FROM " + TABLE_ABSENSI + " WHERE idabsen LIKE '%" +idabsen+ "%' LIMIT 1";
        Log.e("query", selectQuery);
        SQLiteDatabase db2 = this.getReadableDatabase();
        Cursor c = db2.rawQuery(selectQuery, null);
        c.moveToFirst();
        int count = c.getInt(c.getColumnIndexOrThrow("count"));
        Log.e( "cek absensi : ", String.valueOf(count));
        c.close();
        return count;
    }
    public int updateAbsensi(String fcidabsen, String fdtglabsen, String fddtgrule, String fdplgrule, String fddtgact, String fdplgact, String fnlbrr, String fnlbrh, String fnizin, String fcket, String fcnik, String fcnama,
                             String fckdabsen, String fcnmkdabsen, Integer fnkk, Integer fnoff, Integer fnkh, Integer fnit, Integer fnalpha, Integer fnsd ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IDABSEN, fcidabsen);
        values.put(KEY_DTABSEN, fdtglabsen);
        values.put(KEY_DTGRULE, fddtgrule);
        values.put(KEY_PLGRULE, fdplgrule);
        values.put(KEY_DTGACT, fddtgact);
        values.put(KEY_PLGACT, fdplgact);
        values.put(KEY_LBRR, fnlbrr);
        values.put(KEY_LBRH, fnlbrh);
        values.put(KEY_IZIN, fnizin);
        values.put(KEY_KTRGABS, fcket);
        values.put(KEY_NIK, fcnik);
        values.put(KEY_NAMA, fcnama);
        values.put(KEY_KDABS, fckdabsen);
        values.put(KEY_NMKDABS, fcnmkdabsen);
        values.put(KEY_FNKK, fnkk);
        values.put(KEY_FNOFF, fnoff);
        values.put(KEY_FNKH, fnkh);
        values.put(KEY_FNIT, fnit);
        values.put(KEY_FNALPHA, fnalpha);
        values.put(KEY_FNSD, fnsd);

        return db.update(TABLE_ABSENSI, values, KEY_IDABSEN + " = ?",
                new String[]{fcidabsen});
    }

    public ArrayList<Absensi> getRekap(String nmperiode, String dtperiode, String dtfrom, String dtto) {
        ArrayList<Absensi> absensiModelArrayList = new ArrayList<Absensi>();
        String selectQuery = "SELECT tb.fcnik, tb.fcnama, tk.fcalamat, tk.fctelp, tk.fcnmgrp, tk.fcjabatan, SUM(tb.fnkk) as fnkk, SUM(tb.fnkk) as fnkk," +
                "SUM(tb.fnkh) as fnkh, SUM(tb.fnoff) as fnoff,SUM(tb.izin) as izin," +
                "SUM(tb.fnit) as fnit, SUM(tb.fnsd) as fnsd, SUM(tb.fnalpha) as fnalpha," +
                "SUM(tb.lbrr) as lbrr, SUM(tb.lbrh) as lbrh, tk.fcnmgrp, tk.fcalamat, tk.fctelp FROM tbabsensi tb, tbkaryawan tk WHERE tb.fcnik=tk.fcnik AND  datetime(substr(tb.tglabsen, 7, 4) || '-' || substr(tb.tglabsen, 4, 2) || '-' || substr(tb.tglabsen, 1, 2)) BETWEEN '"+dtfrom+"' AND '"+dtto+"' GROUP BY tb.fcnama ORDER BY tb.fcnik ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        no = 0;
        Log.e("query", selectQuery);
        if (c.moveToFirst()) {
            do {
                Absensi abs = new Absensi();
                no++;
                abs.setFcnik(c.getString(c.getColumnIndexOrThrow(KEY_NIK)));
                abs.setFcnik(c.getString(c.getColumnIndexOrThrow(KEY_NIK)));
                abs.setFcnama(c.getString(c.getColumnIndexOrThrow(KEY_NAMA)));
                abs.setFnlbrr(c.getString(c.getColumnIndexOrThrow(KEY_LBRR)));
                abs.setFnlbrh(c.getString(c.getColumnIndexOrThrow(KEY_LBRH)));
                abs.setFnizin(c.getString(c.getColumnIndexOrThrow(KEY_IZIN)));
                abs.setFnkk(c.getString(c.getColumnIndexOrThrow(KEY_FNKK)));
                abs.setFnoff(c.getString(c.getColumnIndexOrThrow(KEY_FNOFF)));
                abs.setFnkh(c.getString(c.getColumnIndexOrThrow(KEY_FNKH)));
                abs.setFnit(c.getString(c.getColumnIndexOrThrow(KEY_FNIT)));
                abs.setFnalpha(c.getString(c.getColumnIndexOrThrow(KEY_FNALPHA)));
                abs.setFnsd(c.getString(c.getColumnIndexOrThrow(KEY_FNSD)));
                Log.e("klo", c.getString(c.getColumnIndexOrThrow(KEY_NIK)));
                abs.setDtperiode(dtperiode);
                abs.setNmperiode(nmperiode);

                abs.setDtFrom(dtfrom.replaceAll("-",""));
                abs.setDtTo(dtto.replaceAll("-",""));
                abs.setFcnmgrp(c.getString(c.getColumnIndexOrThrow(KEY_GROUP)));
                abs.setFcalamat(c.getString(c.getColumnIndexOrThrow(KEY_ALAMAT)));
                abs.setFctelp(c.getString(c.getColumnIndexOrThrow(KEY_TELEPON)));
                // adding to Students list
                absensiModelArrayList.add(abs);
            } while (c.moveToNext());
        }
        return absensiModelArrayList;
    }
    public List <PeriodeSpinner> getAllData(){

        List<PeriodeSpinner> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRD +" ORDER BY dtFrom DESC";
        String [] columns = { KEY_NMPRD, KEY_DTPRD, KEY_DTFROM, KEY_DTTO};
        Cursor cursor =  db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            int index = cursor.getColumnIndex(KEY_NMPRD);
            int index2 = cursor.getColumnIndex(KEY_DTPRD);
            int index3 = cursor.getColumnIndex(KEY_DTFROM);
            int index4 = cursor.getColumnIndex(KEY_DTTO);
            int cid = cursor.getInt(index);

            String namaPeriode = cursor.getString(index);
            String dtPeriode = cursor.getString(index2);
            String dtFrom = cursor.getString(index3);
            String dtTo = cursor.getString(index4);

            PeriodeSpinner prd = new PeriodeSpinner( namaPeriode, dtPeriode, dtFrom, dtTo);
            Log.e( "getAllData: ", namaPeriode);
            list.add(prd);
        }

        return list;
    }
}
