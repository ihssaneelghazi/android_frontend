package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ismagiefm.movielandefmismagi.Datas.models.Salle;
import com.ismagiefm.movielandefmismagi.R;

import java.util.List;

public class SalleAdapter extends RecyclerView.Adapter<SalleAdapter.SalleViewHolder>{
    List<Salle> salles;
    @NonNull
    @Override
    public SalleAdapter.SalleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salle, parent, false);
        return new SalleViewHolder(view);
    }

    public SalleAdapter(List<Salle> salles) {
        this.salles = salles;
    }

    @Override
    public void onBindViewHolder(@NonNull SalleAdapter.SalleViewHolder holder, int position) {
        Salle salle = salles.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("salleid", salle.getId());
                Navigation.findNavController(v).navigate(R.id.action_salleFragment_to_projectionFragment, bundle);

            }
        });

        holder.cinema_salle.setText(salle.getName());
        String seatsText = String.format("%d places", salle.getNombrePlaces());
        holder.cinema_seats.setText(seatsText);




    }

    @Override
    public int getItemCount() {
        return salles.size();
    }

    public class SalleViewHolder extends RecyclerView.ViewHolder {
        private TextView  cinema_salle;
        private TextView  cinema_seats;


        public SalleViewHolder(@NonNull View itemView) {
            super(itemView);
            cinema_salle = itemView.findViewById(R.id.cinema_salle);
            cinema_seats = itemView.findViewById(R.id.cinema_seats);

        }
    }
}
