package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ismagiefm.movielandefmismagi.Datas.models.Projection;
import com.ismagiefm.movielandefmismagi.R;

import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;

public class ProjectionAdapter extends RecyclerView.Adapter<ProjectionAdapter.ProjectionViewHolder> {
    private List<Projection> projections;
    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    Button btnReserve;

    public ProjectionAdapter(List<Projection> projections) {

        this.projections = projections;


    }

    @NonNull
    @Override
    public ProjectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_projection, parent, false);
        return new ProjectionViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ProjectionViewHolder holder, int position) {
        Projection projection = projections.get(position);
        holder.film_title.setText(projection.getFilm().getTitre());
        holder.film_director.setText(projection.getFilm().getRealisateur());
        holder.film_description.setText(projection.getFilm().getDescription());
        holder.film_language.setText(projection.getLangue());
        holder.film_price.setText(String.valueOf(projection.getPrix()) + " DH");

        holder.btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);


                navController.navigate(R.id.action_projectionFragment_to_reservationFragment);

            }
        });

        int filmId = projection.getFilm().getId();
        String imageUrl = "http://192.168.1.94:8081/users/imageFilm/" + filmId;

        // Get the token from SharedPreferences
        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");

        // Create a GlideUrl adding the token to the header
        GlideUrl glideUrl = new GlideUrl(imageUrl, new LazyHeaders.Builder()
                .addHeader("Authorization", token)
                .build());

        // Use Glide to load the image
        String filmTitle = projection.getFilm().getTitre();
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

    @Override
    public int getItemCount() {
        return projections.size();
    }

    public class ProjectionViewHolder extends RecyclerView.ViewHolder {
        private ImageView film_image;
        private TextView film_title;
        private TextView film_director;
        private TextView film_description;
        private TextView film_language;
        private TextView film_price;
        private Button btnReserve;



        public ProjectionViewHolder(@NonNull View itemView) {
            super(itemView);
            film_image = itemView.findViewById(R.id.film_image);
            film_title = itemView.findViewById(R.id.film_title);
            film_director = itemView.findViewById(R.id.film_director);
            film_description = itemView.findViewById(R.id.film_description);
            film_language = itemView.findViewById(R.id.film_language);
            film_price = itemView.findViewById(R.id.film_price);
            btnReserve = itemView.findViewById(R.id.btn_reserve);


        }
    }
}