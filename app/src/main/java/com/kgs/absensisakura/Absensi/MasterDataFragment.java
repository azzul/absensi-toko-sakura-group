package com.kgs.absensisakura.Absensi;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

//
public class MasterDataFragment extends Fragment {
    DbHelper dbHelper;
    private ProgressBar progressBar;
    private String selectedToko;
    private int selectedTokoIndex = 0;
    private TextView textNotice;
    private CardView cardDownload;
    private String[] Toko = {"SAKURA SOLO", "SAKURA JOGJA", "SAKURA JAKARTA", "SAKURA BALI", "SAKURA SEMARANG",
            "SAKURA CIREBON"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master_data, container, false);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        dbHelper = new DbHelper(getContext());
        progressBar = view.findViewById(R.id.progress_bar);
        textNotice = view.findViewById(R.id.textNotice);
        int id_cek = dbHelper.cekKaryawanAda();
        Log.e("hitung id", String.valueOf(id_cek));
        if(id_cek==0){
            selectedToko = Toko[selectedTokoIndex];
            new AlertDialog.Builder(getContext())
                    .setTitle("Pilih Toko Untuk Download Data Karyawan")
                    .setSingleChoiceItems(Toko, selectedTokoIndex, (dialog, which) -> {
                        selectedTokoIndex = which;
                        selectedToko = Toko[which];
                    })
                    .setPositiveButton("Ok", (dialog, which) -> {
                        new DisplayProgressBar().execute(selectedToko);
                        Toast.makeText(getContext(), selectedToko + " Selected", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        }else{
            textNotice.setVisibility(View.GONE);
        }
        dbHelper.close();

        view.findViewById(R.id.card_karyawan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbHelper.addPeriode( "Februari 2024", "202402", "2024-01-25", "2024-02-24");
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new DataKaryawanFragment());
                fr.addToBackStack(null);
                fr.commit();
                getFragmentManager().executePendingTransactions();
            }
        });
        view.findViewById(R.id.card_periode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbHelper.cek();
               //dbHelper.addPeriode(1, "Februari 2024", "202402", "2024-01-25", "2024-02-24");
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container,new PeriodeFragment());
                fr.addToBackStack(null);
                fr.commit();
                getFragmentManager().executePendingTransactions();
            }
        });
        view.findViewById(R.id.card_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedToko = Toko[selectedTokoIndex];
                new AlertDialog.Builder(getContext())
                        .setTitle("Pilih Toko Untuk Download Data Karyawan")
                        .setSingleChoiceItems(Toko, selectedTokoIndex, (dialog, which) -> {
                            selectedTokoIndex = which;
                            selectedToko = Toko[which];
                        })
                        .setPositiveButton("Ok", (dialog, which) -> {
                            new DisplayProgressBar().execute(selectedToko);
                            Toast.makeText(getContext(), selectedToko + " Selected", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();

            }
        });
        //ini untuk input data karyawan ketika awal install

    }

    private class DisplayProgressBar extends AsyncTask<String, Void, String> {

        /**
         * This method displays progress bar in UI thread.
         */



        @Override
        protected void onPreExecute() {

            //this method will be running on UI thread

            super.onPreExecute();
        }

        /**
         * This method executes your bot calculation in background thread.
         */
        @Override
        protected String doInBackground(String... strings) {
            //dbHelper.addKaryawanDetail("0355", "azzul", "Solo", "073627", "Skr");
            //progressBar.setVisibility(View.VISIBLE);
            String toko = strings[0];
            Log.e("doInBackground: ", toko);
            switch (toko) {
                case "SAKURA SOLO":

                    String[][] data = {
                            {"050001", "MUJI SRI MINARSIH", "GANJILAN RT.002 RW.006 MOJOSONGO, JEBRES, SURAKARTA", "085878424181", "KEPALA TOKO"},
                            {"050002", "LUCKY PURWANDARI", "ASPOL PANULARAN RT.006 RW.007 PANULARAN, LAWEYAN, SURAKARTA", "085835524988", "STAFF ADMINISTRASI"},
                            {"050003", "RICH ATHENA", "KAMPUNG SEWU RT.004 RW.008 SEWU, JEBRES, SURAKARTA", "08567720046", "STAFF ADMINISTRASI"},
                            {"050007", "DWI SUPRIYADI", "JL. CIKARANG NO.2 GABUDAN RT.002 RW.008 JOYOSURAN, PASAR KLIWON, SURAKARTA", "087879916350", "GUDANG"},
                            {"050008", "PRASETYO BAGUS SUSILO", "GABUDAN RT.002 RW.008 JOYOSURAN, PASAR KLIWON, SURAKARTA", "085802197730", "GUDANG"},
                            {"050009", "ALFIAN NUR CAHYO", "GABUDAN RT.002 RW.008 JOYOSURAN, PASAR KLIWON, SURAKARTA", "081229629639", "GUDANG"},
                            {"050010", "PRASETYO", "KEDUNG TUNGKUL RT.006 RW.007 MOJOSONGO, JEBRES, SURAKARTA", "085800716111", "GUDANG"},
                            {"050012", "ANDREA DICKY PRAYOGA", "KRATONAN RT.006 RW.001 KRATONAN, SERENGAN, SURAKARTA", "085742934971", "STAFF ADMINISTRASI"},
                            {"050013", "AVA TRI PARIANTO", "NGEMPLAK RT.004 RW.007 GADINGAN, MOJOLABAN, SUKOHARJO", "0895635940638", "GUDANG"},
                            {"050015", "CHRISTIAN VICTOR ARIANTO", "PUCANGSAWIT RT.001 RW.012 PUCANGSAWIT, JEBRES, SURAKARTA", "083809694491", "GUDANG"},
                            {"050017", "ALDI JOHAN ZAKARIA", "PANULARAN RT.004 RW.008 LAWEYAN , SURAKARTA", "088228648894", "GUDANG"},
                            {"050018", "DWI RAHMAWATI", "NGLARANGAN RT.006 RW.001 KEBAK, KEBAKKRAMAT, KARANGANYAR", "085728751922", "STAFF ADMINISTRASI"},
                            {"050019", "YOVITA VIKA KRISTIANA", "JEBRES TENGAH RT.002 RW.024 JEBRES, JEBRES, SURAKARTA", "085728751922", "STAFF ADMINISTRASI"},
                    };
                    String[] nik = new String[data.length];
                    String[] nama = new String[data.length];
                    String[] alamat = new String[data.length];
                    String[] noTelp = new String[data.length];
                    String[] bagian = new String[data.length];
                    for (int i = 0; i < data.length; i++) {
                        nik[i] = data[i][0];
                        nama[i] = data[i][1];
                        alamat[i] = data[i][2];
                        noTelp[i] = data[i][3];
                        bagian[i] = data[i][4];
                        Log.e("nik: ", nik[i]);
                    }

                    for (int i = 0; i < data.length; i++) {
                        int nik_cek = dbHelper.cekKaryawan(nik[i]);
                        Log.e("hitung id", String.valueOf(nik_cek));
                        if(nik_cek==0) {
                            dbHelper.addKaryawanDetail(null, nik[i], nama[i], alamat[i], noTelp[i], bagian[i], "SAKURA SOLO");
                            Log.e("doInBackground: ", nik[i]);
                        }else{

                        }
                    }
                    break;

                case "SAKURA JOGJA":
                    String[][] data_jogja = {
                            {"060001", "ANDI ISWANTO", "JL. KOLONEL SUGIYONO NO.72 RT.019 RW.006 BRONTOKUSUMAN, MERGANGSAN, YOGYA", "081392256600", "PENGAWAS TOKO"},
                            {"060002", "PUJIATI", "DAGEN RT.001 RW.007 SURUH , TASIKMADU, KARANGANYAR", "08562811342", "KEPALA TOKO"},
                            {"060007", "WIDARTO", "SAMBIROBYONG RT.002 RW.003 SAMBIREJO, SEMIN, GUNUNG KIDUL", "085713326202", "GUDANG"},
                            {"060008", "SUPRIYANTO", "SAMBIROBYONG RT.001 RW.009 SAMBIREJO, SEMIN, GUNUNG KIDUL", "085747416120", "GUDANG"},
                            {"060009", "FATKHUR ROHMAN", "KALITENGAH RT.005 RW.002 GIRITENGAH , BOROBUDUR, MAGELANG", "085786677094", "GUDANG"},
                            {"060010", "MAHARANI SAPTA JUNIAR", "DAWUNG KULON RT.001 RW.010 SERENGAN, SERENGAN , SURAKARTA", "089675963450", "STAFF ADMINISTRASI"},
                            {"060013", "RISMA ANSARI", "NGADIWINATAN II RT.001 RW.004 KARANGANYAR, BOROBUDUR, MAGELANG", "081575773715", "STAFF ADMINISTRASI"},
                            {"060015", "RIKO TRI ARISKA", "KWARASAN KULON RT.007 RW.003 KEDUNG KERIS, NGLIPAR, GUNUNG KIDUL", "085654016108", "GUDANG"},
                            {"060016", "AGUS RIYADI", "PAGUTAN RT.001 RW.008 PENGKOL, NGLIPAR, GUNUNG KIDUL", "081575656273", "GUDANG"}
                    };
                    String[] nik_jogja = new String[data_jogja.length];
                    String[] nama_jogja = new String[data_jogja.length];
                    String[] alamat_jogja = new String[data_jogja.length];
                    String[] noTelp_jogja = new String[data_jogja.length];
                    String[] bagian_jogja = new String[data_jogja.length];

                    for (int i = 0; i < data_jogja.length; i++) {
                        int nik_cek = dbHelper.cekKaryawan(nik_jogja[i]);
                        Log.e("hitung id", String.valueOf(nik_cek));
                        if(nik_cek==0) {
                            dbHelper.addKaryawanDetail(null, nik_jogja[i], nama_jogja[i], alamat_jogja[i], noTelp_jogja[i], bagian_jogja[i], "SAKURA JOGJA");
                            Log.e("doInBackground: ", nik_jogja[i]);
                        }else{

                        }
                    }
                    break;
                case "SAKURA JAKARTA":
                    String[][] data_jakarta = {
                            {"070001", "RIA CHRISTIYANI", "NGEMINGAN RT.002 RW.001 JEBRES, JEBRES, SURAKARTA", "081298482102", "KEPALA TOKO"},
                            {"070005", "UDIN", "KAMPUNG PASIR , MUNCANG RT.002 RW.005 GUNUNGSARI, MANDALAWANGI, PANDEGLANG", "085773928827", "GUDANG"},
                            {"070009", "BAHRUDIN", "KP. PAGERET RT.003 RW.003 SUKAMANAH , MENES, PANDEGLANG", "083147923010", "GUDANG"},
                            {"070011", "DEDE HERMANSYAH", "JL. RUBAYA KEPUH RT.001 RW.014 KARANGPAWITAN, KARAWANG BARAT", "081393734535", "MARKETING"},
                            {"070016", "LINDA EKA RAHMAWATI", "JANGKUNGAN RT.005 RW.002 GLONGGONG, NOGOSARI, BOYOLALI", "087875822200", "STAFF ADMINISTRASI"},
                            {"070022", "RAHMAT FAUZI BTR", "DS. GUNUNGTUA MANDAILING NATAL KOTA PANYABUNGAN", "081382173121", "GUDANG"},
                            {"070026", "NUR AULIA SHOBRI", "GANG SENTIONG 12A RT.001 RW.002 TANAH SEREAL, TAMBORA", "-", "GUDANG"},
                            {"070027", "ERIKA NUR CAHYANI", "TUGU RT.003 RW.006 PENGKOL , JATIROTO, WONOGIRI", "081297143371", "STAFF ADMINISTRASI"},
                            {"070028", "JAJANG PITRIYADI", "KP. KEPANDEAN RT.007 RW.002 SINDANGSARI , PABUARAN, SERANG", "083893710081", "GUDANG"},
                            {"070030", "ENDANG SULAEMAN", "JL. JATI BARU SUDUN IV JATIMULYA NO.142 RT.001 RW.005 SUKAHARJA, TELUKJAMBE TIMUR, KARAWANG", "085695556869", "GUDANG"},
                            {"070032", "NURUL FADHILAH", "KP. GAGA RT.002 RW.009 SEMANAN, KALI DERES, JAKARTA BARAT", "081314542273", "STAFF ADMINISTRASI"}
                    };
                    String[] nik_jakarta = new String[data_jakarta.length];
                    String[] nama_jakarta = new String[data_jakarta.length];
                    String[] alamat_jakarta = new String[data_jakarta.length];
                    String[] noTelp_jakarta = new String[data_jakarta.length];
                    String[] bagian_jakarta = new String[data_jakarta.length];

                    for (int i = 0; i < data_jakarta.length; i++) {
                        int nik_cek = dbHelper.cekKaryawan(nik_jakarta[i]);
                        Log.e("hitung id", String.valueOf(nik_cek));
                        if(nik_cek==0) {
                            dbHelper.addKaryawanDetail(null, nik_jakarta[i], nama_jakarta[i], alamat_jakarta[i], noTelp_jakarta[i], bagian_jakarta[i], "SAKURA JAKARTA");
                            Log.e("doInBackground: ", nik_jakarta[i]);
                        }else{

                        }
                    }
                    break;
                case "SAKURA BALI":
                    String[][] data_bali = {
                            {"090002", "ARI WIYOGA", "KUWON RT.002 RW.005 TUGU , JUMANTONO, KARANGANYAR", "081339932859", "KEPALA TOKO"},
                            {"090004", "MUDITA MAHARANI", "DAWUNG KULON RT.001 RW.010 SERENGAN, SERENGAN, SURAKARTA", "0895631921788", "STAFF ADMINISTRASI"},
                            {"090005", "LAURENITA MARIA C.D", "KALIRAHMAN RT.002 RW.005 GANDEKAN , JEBRES, SURAKARTA", "081515097830", "STAFF ADMINISTRASI"},
                            {"090006", "YANU SAPUTRA", "DUKUH RT.046 RW.015 DONOMULYO NANGGULAN, KULON PROGO, YOGYAKARTA", "081229360076", "GUDANG"},
                            {"090010", "SHINTA DANIARTI", "PONDOKAN RT.001 RW.006 PONDOKSARI, NGUNTORONADI, WONOGIRI", "081325780217", "STAFF ADMINISTRASI"},
                            {"090011", "ABDULLAH SAPUTRA", "CEPORAN RT.004 RW.007 NGADILUWIH, MATESIH, KARANGANYAR", "085156126697", "GUDANG"},
                            {"090013", "FAJAR AMIRULLAH", "DURENAN RT.003 RW.006 SRINGIN, JUMANTONO, KARANGANYAR", "085869941917", "GUDANG"},
                            {"090014", "YUSUF MUHAMMAD ANDRIANSAH", "KUWON RT.002 RW.005 TUGU , JUMANTONO, KARANGANYAR", "083103848692", "GUDANG"},
                            {"090015", "MIFTAKHUL BACHTIAR", "JURANGREJO RT.002 RW.006 NGADILUWIH, MATESIH, KARANGANYAR", "08155107421", "GUDANG"},
                            {"090016", "IMELDA TRIA DEUTIANA", "BIBIS BARU RT.006 RW.024 NUSUKAN, BANJARSARI, SURAKARTA", "085867989471", "STAFF ADMINISTRASI"}
                    };
                    String[] nik_bali = new String[data_bali.length];
                    String[] nama_bali = new String[data_bali.length];
                    String[] alamat_bali = new String[data_bali.length];
                    String[] noTelp_bali = new String[data_bali.length];
                    String[] bagian_bali = new String[data_bali.length];

                    for (int i = 0; i < data_bali.length; i++) {
                        int nik_cek = dbHelper.cekKaryawan(nik_bali[i]);
                        Log.e("hitung id", String.valueOf(nik_cek));
                        if(nik_cek==0) {
                            dbHelper.addKaryawanDetail(null, nik_bali[i], nama_bali[i], alamat_bali[i], noTelp_bali[i], bagian_bali[i], "SAKURA BALI");
                            Log.e("doInBackground: ", nik_bali[i]);
                        }else{

                        }
                    }
                    break;

                case "SAKURA SEMARANG":
                    String[][] data_semarang = {
                            {"080001", "WILLIS AUFRIDA", "JUMOK RT.005 RW.008 JATEN , KARANGANYAR", "085725610377", "KEPALA TOKO"},
                            {"080008", "IWAN SETIAWAN", "DUDUHAN RT.001 RW.005 MIJEN , SEMARANG", "085740951117", "DRIVER"},
                            {"080017", "GIBEON RAKA PERWIRA", "DUSUN GEBYAR RT.004 RW.004 CREWEK, KRADENAN, GROBOGAN", "081353454732", "GUDANG"},
                            {"080020", "RESA AMELIA BR. SINUHAJI", "BEGANDING RT.0 RW.0 BEGANDING , SIMPANG EMPAT, SEMARANG", "082165371635", "STAFF ADMINISTRASI"},
                            {"080023", "MUHAMMAD AL FARIZI", "JL. BERINGIN I RT.001 RW.004 KARANGROTO, GENUK, SEMARANG", "08988392548", "GUDANG"},
                            {"080026", "MOKH. YOGGI SAPUTRA", "DK. AREN RT.001 RW.002 SIDOMORO, BULUS PESANTREN, KEBUMEN", "085867146093", "GUDANG"},
                            {"080029", "YUNI WULANDARI", "AJI JAYA RT.008 RW.004 AJI JAYA, SIMPANG PEMATANG, MESUJI", "082279918511", "STAFF ADMINISTRASI"},
                            {"080031", "ROHMATULLAH", "KP. KILASAH RT.003 RW.001 KILASAH, KASEMEN, SERANG", "082125263267", "GUDANG"},
                            {"080034", "MUHAMMAD AKBAR", "GURAMI II / 42 RT.008 RW.008 KUNINGAN , SEMARANG UTARA, SEMARANG", "085656736167", "GUDANG"},
                            {"080035", "DIAH AYU MEGANANDA", "JL. PADI UTARA RAYA NO.480 GENUK, SEMARANG", "0895359088871", "STAFF ADMINISTRASI"},
                            {"080036", "NIKEN AYU FIRDAYANTI", "JL. TLOGOTENTREM NO.45 RT.022 RW.030 PUCANGSANTOSO, BATUSARI, MRANGGEN, DEMAK", "085641170846", "STAFF ADMINISTRASI"},
                            {"080039", "HIKMAL", "JL. DEMPEL KIDUL RT.002 RW.016 MUKTIHARJO KIDUL, SEMARANG TIMUR", "0882007281499", "GUDANG"},
                            {"080041", "TRI ELIA PANGESTUTI", "GENUKSARI RT.001 RW.001 GENUKSARI, GENUK, SEMARANG", "089681687667", "STAFF ADMINISTRASI"},
                    };
                    String[] nik_semarang = new String[data_semarang.length];
                    String[] nama_semarang = new String[data_semarang.length];
                    String[] alamat_semarang = new String[data_semarang.length];
                    String[] noTelp_semarang = new String[data_semarang.length];
                    String[] bagian_semarang = new String[data_semarang.length];

                    for (int i = 0; i < data_semarang.length; i++) {
                        int nik_cek = dbHelper.cekKaryawan(nik_semarang[i]);
                        Log.e("hitung id", String.valueOf(nik_cek));
                        if(nik_cek==0) {
                            dbHelper.addKaryawanDetail(null, nik_semarang[i], nama_semarang[i], alamat_semarang[i], noTelp_semarang[i], bagian_semarang[i], "SAKURA SEMARANG");
                            Log.e("doInBackground: ", nik_semarang[i]);
                        }else{

                        }
                    }
                    break;

                case "SAKURA CIREBON":
                    String[][] data_cirebon = {
                            {"100001", "DWI SRI YOHANI", "TITANG RT.002 RW.007 TEGALGEDE KARANGANYAR", "085700007580", "KEPALA TOKO"},
                            {"100002", "SELVI OKTAVIANA", "KARANGMOJO RT.002 RW.007 CEPER, KLATEN", "085693225454", "STAFF ADMINISTRASI"},
                            {"100004", "ABDUL KODIR JAELANI", "BLOK KELEBAKAN RT.001 RW.005 KARANGSARI , WERU", "085722274544", "GUDANG"},
                            {"100005", "SOFYAN", "BLOK LEMAH ABANG RT.017 RW.004 MEGU GEDE, WERU, CIREBON", "-", "GUDANG"},
                            {"100007", "EGI RINALDI", "BLOK KECABEAN RT.021 RW.004 MEGU GEDE, WERU, CIREBON", "085795445154", "GUDANG"},
                            {"100010", "ANGGIE NURMAYANTI", "PERUMAHAN SENTANI REGENCY BLOK H RT.002 RW.012 TUKMUDAL, SUMBER, CIREBON", "085872570639", "STAFF ADMINISTRASI"},
                            {"100011", "RIA CANDRA WIDAYANINGSIH", "TERMES RT.004 RW.002 KEDUNGMULYO , KEMUSU, BOYOLALI", "081575806098", "STAFF ADMINISTRASI"},
                            {"100012", "PAJAR ALKORI", "BLOK KECABEAN RT.021 RW.004 MEGU GEDE, WERU, CIREBON", "089661313531", "GUDANG"},
                            {"100013", "AMANA HUSNA", "KARTOTIYASAN RT.004 RW.004 KRATONAN, SERENGAN, SOLO", "085876378996", "STAFF ADMINISTRASI"},
                            {"100014", "M. DAVID", "BLOK SUNGAPAN RT.016 RW.004 MEGU GEDE, WERU, CIREBON", "087750295145", "GUDANG"}
                    };
                    String[] nik_cirebon = new String[data_cirebon.length];
                    String[] nama_cirebon = new String[data_cirebon.length];
                    String[] alamat_cirebon = new String[data_cirebon.length];
                    String[] noTelp_cirebon = new String[data_cirebon.length];
                    String[] bagian_cirebon = new String[data_cirebon.length];

                    for (int i = 0; i < data_cirebon.length; i++) {
                        int nik_cek = dbHelper.cekKaryawan(nik_cirebon[i]);
                        Log.e("hitung id", String.valueOf(nik_cek));
                        if(nik_cek==0) {
                            dbHelper.addKaryawanDetail(null, nik_cirebon[i], nama_cirebon[i], alamat_cirebon[i], noTelp_cirebon[i], bagian_cirebon[i], "SAKURA CIREBON");
                            Log.e("doInBackground: ", nik_cirebon[i]);
                        }else{

                        }
                    }
                    break;

            }
          return null;
        }

        /**
         * This method removes progress bar from UI thread when calculation is over.
         */
        @Override
        protected void onPostExecute(String urlExce) {
            new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE).setTitleText("BERHASIL DOWNLOAD DATA KARYAWAN!").setContentText("Silahkan cek data pada halaman data karyawan, anda bisa menambah, mengedit dan menghapusnya jika diperlukan di halaman tersebut").show();
            textNotice.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            super.onPostExecute(urlExce);
        }
    }
    public void alertDownload(){
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("DATA KARYAWAN SUDAH TERDAFTAR!").setContentText("Silahkan edit di halaman daftar karyawan").show();
    }
}