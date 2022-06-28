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
import com.example.p3l.Volley.models.Promo;

import java.util.List;

public class PromoAdapter extends RecyclerView.Adapter<PromoAdapter.ViewHolder> {
    private List<Promo> promoList;
    private Context context;

    public PromoAdapter(List<Promo> promoList, Context context) {
        this.promoList = promoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cardview_promo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Promo promo = promoList.get(position);
        if(promo != null) {
            holder.tvJenisPromo.setText(promo.getJenis_promo());
            holder.tvKodePromo.setText(promo.getKode_promo());
            holder.tvPotonganPromo.setText(String.valueOf(promo.getPotongan_promo())+"%");
            holder.tvKeterangan.setText(promo.getKeterangan());
        }
    }

    @Override
    public int getItemCount() {
        return promoList.size();
    }

    public void setPromoList(List<Promo> promoList) {
        this.promoList = promoList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJenisPromo, tvKodePromo, tvPotonganPromo, tvKeterangan;
        CardView cvPromo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvJenisPromo = itemView.findViewById(R.id.tv_jenisPromo);
            tvKodePromo = itemView.findViewById(R.id.tv_kodePromo);
            tvPotonganPromo = itemView.findViewById(R.id.tv_potonganPromo);
            tvKeterangan = itemView.findViewById(R.id.tv_keterangan);
            cvPromo = itemView.findViewById(R.id.cv_promo);
        }
    }
}
