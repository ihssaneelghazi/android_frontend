package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.ismagiefm.movielandefmismagi.Datas.models.Ville;
import com.ismagiefm.movielandefmismagi.R;

import java.util.List;

public class VilleAdapter extends RecyclerView.Adapter<VilleAdapter.ViewHolder> {

    private List<Ville> villesList;

    public VilleAdapter(List<Ville> villesList) {
        this.villesList = villesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ville, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ville ville = villesList.get(position);
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("cinemaId", ville.getId());
            Navigation.findNavController(v).navigate(R.id.cinemaVilleFragment, bundle);
        });
        holder.bind(ville);
    }

    @Override
    public int getItemCount() {
        return villesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewVilleName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewVilleName = itemView.findViewById(R.id.textViewVilles);
        }

        public void bind(Ville ville) {
            if (textViewVilleName != null) {
                textViewVilleName.setText(" ❤️❤️❤️ " + ville.getName() + " ➡️");
            }
        }

    }
}
