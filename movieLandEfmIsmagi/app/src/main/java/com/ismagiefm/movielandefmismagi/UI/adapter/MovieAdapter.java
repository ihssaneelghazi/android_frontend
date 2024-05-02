package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.ismagiefm.movielandefmismagi.Datas.models.TheaterMovie;
import com.ismagiefm.movielandefmismagi.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final Context context;
    private List<TheaterMovie> movies;

    public MovieAdapter(Context context, List<TheaterMovie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheaterMovie movie = movies.get(position);
        String imageUrl = "http://cinema.apidae-tourisme.com" + movie.posters.large;
        //Glide permet de charger des images à partir d'une URL
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMovies(List<TheaterMovie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_categoryImage);
        }
    }
}
