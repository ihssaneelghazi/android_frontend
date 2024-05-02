package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ismagiefm.movielandefmismagi.Datas.models.Film;
import com.ismagiefm.movielandefmismagi.R;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder>{
    public List<Film> films;

    public FilmAdapter(List<Film> films) {
        this.films = films;
    }

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    @NonNull
    @Override
    public FilmAdapter.FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmAdapter.FilmViewHolder holder, int position) {
        Film film = films.get(position);
        
        holder.film_title.setText("Titre: " + film.getTitre());
        holder.film_director.setText("Réalisateur: " + film.getRealisateur());
        holder.film_description.setText("Description: " + film.getDescription());
        holder.film_category.setText("Catégorie: " + film.getCategory().getName());
        //il faut formater la date de sortie film_datesortie




        int filmId = film.getId();
        String imageUrl = "http://192.168.1.94:8081/users/imageFilm/" + filmId;

        // Get the token from SharedPreferences
        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");

        // Create a GlideUrl adding the token to the header
        GlideUrl glideUrl = new GlideUrl(imageUrl, new LazyHeaders.Builder()
                .addHeader("Authorization", token)
                .build());

        // Use Glide to load the image
        String filmTitle = film.getTitre();
        int placeholderImage;
        int errorImage;

        switch (filmTitle) {
            case "Spiderman":
                placeholderImage = R.drawable.spiderman;
                errorImage = R.drawable.spiderman;
                break;
            case "Seigneur des Anneaux":
                placeholderImage = R.drawable.seigneur;
                errorImage = R.drawable.seigneur;
                break;
            case "Game of Thrones":
                placeholderImage = R.drawable.game;
                errorImage = R.drawable.game;
                break;
            default:
                placeholderImage = R.drawable.seigneur; // Replace with your default placeholder image
                errorImage = R.drawable.seigneur; // Replace with your default error image
                break;
        }

        Glide.with(holder.itemView.getContext())
                .load(glideUrl)
                .placeholder(placeholderImage) // Placeholder image while loading
                .error(errorImage) // Image to display in case of error
                .into(holder.film_image);
    }


    public void setFilms(List<Film> films) {
        this.films = films;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return films.size();
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        private ImageView film_image;
        private TextView film_title;
        private TextView film_director;
        private TextView film_description;

        private TextView film_category;

        private TextView film_datesortie;


        public FilmViewHolder(@NonNull View itemView) {
            super(itemView);
            film_image = itemView.findViewById(R.id.film_image);
            film_title = itemView.findViewById(R.id.film_title);
            film_director = itemView.findViewById(R.id.film_director);
            film_description = itemView.findViewById(R.id.film_description);
            film_category = itemView.findViewById(R.id.film_category);
            film_datesortie = itemView.findViewById(R.id.film_datesortie);

        }
    }
}