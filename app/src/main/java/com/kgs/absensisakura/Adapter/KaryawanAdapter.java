package com.kgs.absensisakura.Adapter;



import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.kgs.absensisakura.Database.Karyawan;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.MainActivity;
import com.kgs.absensisakura.R;

import java.io.Serializable;
import java.util.ArrayList;

public class KaryawanAdapter extends RecyclerView.Adapter<KaryawanAdapter.KaryawanViewHolder> {

    private ArrayList<Karyawan> listKaryawan = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;

    public KaryawanAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<Karyawan> getListKaryawan() {
        return listKaryawan;
    }

    public void setListKaryawan(ArrayList<Karyawan> listNotes) {
        if (listNotes.size() > 0) {
            this.listKaryawan.clear();
        }
        this.listKaryawan.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KaryawanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_karyawan, parent, false);
        return new KaryawanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KaryawanViewHolder holder, int position) {
        holder.tvNik.setText(listKaryawan.get(position).getFcnik());
        holder.tvNama.setText(listKaryawan.get(position).getFcnama());
        holder.tvTelp.setText(listKaryawan.get(position).getFctelp());
        holder.btnEdit.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("user", (Serializable) listKaryawan.get(position));
            activity.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Konfirmasi hapus");
            builder.setMessage("Apakah yakin akan dihapus?");

            builder.setPositiveButton("YA", (dialog, which) -> {
                dbHelper.deleteUser(listKaryawan.get(position).getId());
                Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(activity, MainActivity.class);
                activity.startActivity(myIntent);
                activity.finish();
            });

            builder.setNegativeButton("TIDAK", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();

        });
    }

    @Override
    public int getItemCount() {
        return listKaryawan.size();
    }

    public class KaryawanViewHolder extends RecyclerView.ViewHolder {

        final TextView tvNik, tvNama, tvTelp;
        final ConstraintLayout btnEdit, btnDelete;

        public KaryawanViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNik = itemView.findViewById(R.id.textnik);
            tvNama = itemView.findViewById(R.id.textnama);
            tvTelp = itemView.findViewById(R.id.texttelp);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
