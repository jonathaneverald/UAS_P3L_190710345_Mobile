package com.example.p3l.Volley.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p3l.R;
import com.example.p3l.Volley.models.Mobil;

import java.util.List;

public class MobilAdapter extends RecyclerView.Adapter<MobilAdapter.ViewHolder> {
    private List<Mobil> mobilList;
    private Context context;

    public MobilAdapter(List<Mobil> mobilList, Context context) {
        this.mobilList = mobilList;
        this.context = context;
    }

    @NonNull
    @Override
    public MobilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_mobil, parent, false);
        return new MobilAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MobilAdapter.ViewHolder holder, int position) {
        Mobil mobil = mobilList.get(position);
        if(mobil != null) {
            holder.hargaSewa.setText("Harga Sewa : Rp "+String.format("%.2f",mobil.getHarga_sewa())+"/hari");
            holder.namaMobil.setText(mobil.getNama_mobil_sewa());
            holder.tipeMobil.setText(mobil.getTipe_mobil());
            holder.jenisTransmisi.setText(mobil.getJenis_transmisi());
            holder.jenisBahanBakar.setText(mobil.getJenis_bahan_bakar());
            holder.warnaMobil.setText(mobil.getWarna_mobil());
            holder.volumenBagasi.setText(mobil.getVolume_bagasi());
            holder.fasilitas.setText(mobil.getFasilitas());
            Glide.with(holder.fotoMobil)
                    .setDefaultRequestOptions(new RequestOptions())
                    .load("http://63f1-36-75-67-217.ngrok.io/foto_mobil/"+mobil.getFoto_mobil())
                    .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                    .error(android.R.drawable.stat_notify_error)
                    .into(holder.fotoMobil);
        }
    }

    @Override
    public int getItemCount() {
        return mobilList.size();
    }

    public void setMobilList(List<Mobil> mobilList) {
        this.mobilList = mobilList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoMobil;
        TextView hargaSewa, namaMobil, tipeMobil, jenisTransmisi, jenisBahanBakar, warnaMobil, volumenBagasi, fasilitas;
        CardView cvMobil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            hargaSewa = itemView.findViewById(R.id.hargaSewa);
            namaMobil = itemView.findViewById(R.id.namaMobil);
            tipeMobil = itemView.findViewById(R.id.tipeMobil);
            jenisTransmisi = itemView.findViewById(R.id.jenisTransmisi);
            jenisBahanBakar = itemView.findViewById(R.id.jenisBahanBakar);
            warnaMobil = itemView.findViewById(R.id.warnaMobil);
            volumenBagasi = itemView.findViewById(R.id.volumenBagasi);
            fasilitas = itemView.findViewById(R.id.fasilitas);
            fotoMobil = itemView.findViewById(R.id.fotoMobil);
            cvMobil = itemView.findViewById(R.id.cv_mobil);
        }
    }
}
