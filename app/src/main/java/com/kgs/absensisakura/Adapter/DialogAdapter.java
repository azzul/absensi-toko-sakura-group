package com.kgs.absensisakura.Adapter;


import static android.app.PendingIntent.getActivity;



import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.kgs.absensisakura.Absensi.InputAbsensiFragment;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.Database.Karyawan;
import com.kgs.absensisakura.R;

import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogViewHolder> {

    private ArrayList<Karyawan> listKaryawan = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public DialogAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<Karyawan> getListKaryawan() {
        return listKaryawan;
    }

    public void setListKaryawanDialog(ArrayList<Karyawan> listNotes) {
        if (listNotes.size() > 0) {
            this.listKaryawan.clear();
        }
        this.listKaryawan.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_karyawan_dialog, parent, false);
        return new DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        holder.tvnm_dialog.setText(listKaryawan.get(position).getFcnama());
        holder.tvnik_dialog.setText(listKaryawan.get(position).getFcnik());
        holder.itemView.setOnClickListener((View v) -> {
            //PilihKaryawanFragment.kirimData(String nama, String nik);

            Fragment fragment = new InputAbsensiFragment();
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Bundle b = new Bundle();
            b.putString("identify", "pilih");
            b.putString("nama", listKaryawan.get(position).getFcnama());
            b.putString("nik", listKaryawan.get(position).getFcnik());
            fragment.setArguments(b);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });

    }

    @Override
    public int getItemCount() {
        return listKaryawan.size();
    }

    public class DialogViewHolder extends RecyclerView.ViewHolder {

        final TextView tvnm_dialog, tvnik_dialog;

        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnm_dialog = itemView.findViewById(R.id.tvnm_dialog);
            tvnik_dialog = itemView.findViewById(R.id.tvnik_dialog);

        }
    }
}