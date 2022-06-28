package com.example.p3l.Volley.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p3l.R;
import com.example.p3l.Volley.models.Transaksi;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    private List<Transaksi> transaksiList;
    private Context context;

    public TransaksiAdapter(List<Transaksi> transaksiList, Context context) {
        this.transaksiList = transaksiList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_transaksi, parent, false);
        return new TransaksiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.ViewHolder holder, int position) {
        Transaksi transaksi = transaksiList.get(position);
        if(transaksi != null) {
            holder.total_transaksi.setText("Rp "+String.format("%.2f",transaksi.getTotal_transaksi()));
            holder.tanggal_transaksi.setText(transaksi.getTanggal_transaksi());
            holder.status_transaksi.setText(transaksi.getStatus_transaksi());
            holder.jenis_penyewaan.setText(transaksi.getJenis_penyewaan());
        }
    }

    @Override
    public int getItemCount() {
        return transaksiList.size();
    }

    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal_transaksi, status_transaksi, jenis_penyewaan, total_transaksi;
        CardView cvTransaksi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tanggal_transaksi = itemView.findViewById(R.id.tanggal_transaksi);
            status_transaksi = itemView.findViewById(R.id.status_transaksi);
            jenis_penyewaan = itemView.findViewById(R.id.jenis_penyewaan);
            total_transaksi = itemView.findViewById(R.id.total_transaksi);
            cvTransaksi = itemView.findViewById(R.id.cv_transaksi);
        }
    }
}
