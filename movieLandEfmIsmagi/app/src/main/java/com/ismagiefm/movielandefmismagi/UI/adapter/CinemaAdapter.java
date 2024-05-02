package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.ismagiefm.movielandefmismagi.Datas.models.Cinema;
import com.ismagiefm.movielandefmismagi.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder>{
    private List<Cinema> cinemaList;

    public CinemaAdapter(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    @NonNull
    @NotNull
    @Override
    public CinemaAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View  view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cinema,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CinemaAdapter.ViewHolder viewHolder, int i) {
        Cinema cinema=cinemaList.get(i);
        viewHolder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt("cineid", cinema.getId());
            Navigation.findNavController(v).navigate(R.id.salleFragment, bundle);
        });
        viewHolder.cinemaName.setText(cinema.getName());
    }

    @Override
    public int getItemCount() {
        return cinemaList.size();
    }


    public void setCinemaList(List<Cinema> cinemaList){
        this.cinemaList=cinemaList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cinemaName;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cinemaName=itemView.findViewById(R.id.cinema_name);


        }
    }
}
