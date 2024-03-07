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
import com.kgs.absensisakura.Database.Periode;
import com.kgs.absensisakura.Database.DbHelper;
import com.kgs.absensisakura.MainActivity;
import com.kgs.absensisakura.R;

import java.io.Serializable;
import java.util.ArrayList;

public class PeriodeAdapter extends RecyclerView.Adapter<PeriodeAdapter.PeriodeViewHolder> {

    private ArrayList<Periode> listPeriode = new ArrayList<>();
    private Activity activity;
    private DbHelper dbHelper;


    public PeriodeAdapter(Activity activity) {
        this.activity = activity;
        dbHelper = new DbHelper(activity);
    }

    public ArrayList<Periode> getListPeriode() {
        return listPeriode;
    }

    public void setListPeriode(ArrayList<Periode> listNotes) {
        if (listNotes.size() > 0) {
            this.listPeriode.clear();
        }
        this.listPeriode.addAll(listNotes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PeriodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_periode, parent, false);
        return new PeriodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeriodeViewHolder holder, int position) {
        holder.tvnmPd.setText(listPeriode.get(position).getNamaPeriode());
        holder.tvMulai.setText(listPeriode.get(position).getDateFrom());
        holder.tvAkhir.setText(listPeriode.get(position).getDateTo());

        holder.btnEdit.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.putExtra("user", (Serializable) listPeriode.get(position));
            activity.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Konfirmasi hapus");
            builder.setMessage("Apakah yakin akan dihapus?");

            builder.setPositiveButton("YA", (dialog, which) -> {
                dbHelper.deletePeriode(listPeriode.get(position).getDatePeriode());
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
        return listPeriode.size();
    }

    public class PeriodeViewHolder extends RecyclerView.ViewHolder {

        final TextView tvnmPd, tvMulai, tvAkhir ;
        final ConstraintLayout btnEdit, btnDelete;

        public PeriodeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnmPd = itemView.findViewById(R.id.textperiode);
            tvMulai = itemView.findViewById(R.id.textmulai);
            tvAkhir = itemView.findViewById(R.id.textakhir);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
