package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ismagiefm.movielandefmismagi.UI.adapter.MovieAdapter;
import com.ismagiefm.movielandefmismagi.Datas.models.Root;
import com.ismagiefm.movielandefmismagi.Datas.models.TheaterMovie;
import com.ismagiefm.movielandefmismagi.UI.adapter.VideoAdapter;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClient;
import com.ismagiefm.movielandefmismagi.services.AuthService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmFragment extends Fragment {

    private AuthService apiService;
    private TextView movieCardTitle;
    private TextView movieCardSynopsis;
    private ImageView movieCardImage;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewVideos;

    private MovieAdapter movieAdapter;
    private TextView movieCardCategory;
    private TextView movieCardDuration;
    private static final String BASE_URL = "http://cinema.apidae-tourisme.com";

    private final List<String> videoIds = Arrays.asList("9bZkp7q19f0", "7wtfhZwyrcc", "8EJ3zbKTWQ8", "8CdcCD5V-d8", "9bZkp7q19f0", "7wtfhZwyrcc", "8EJ3zbKTWQ8", "8CdcCD5V-d8");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film, container, false);

        movieCardTitle = view.findViewById(R.id.movie_card_title);
        movieCardSynopsis = view.findViewById(R.id.movie_card_synopsis);
        movieCardImage = view.findViewById(R.id.movie_card_image);
        movieCardCategory = view.findViewById(R.id.category);
        movieCardDuration = view.findViewById(R.id.length);
        recyclerView = view.findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        movieAdapter = new MovieAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(movieAdapter);
        RecyclerView recyclerViewVideos = view.findViewById(R.id.recycler_view_videos);
        recyclerViewVideos.setLayoutManager(new LinearLayoutManager(requireContext()));
        VideoAdapter adapter = new VideoAdapter(videoIds);
        recyclerViewVideos.setAdapter(adapter);

        fetchData();

        return view;
    }

    private void fetchData() {
        apiService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
        Call<Root> call = apiService.getCinemaInfo();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                if (response.isSuccessful()) {
                    Root root = response.body();
                    if (root != null) {
                        TheaterMovie firstMovie = root.theater_movies.get(27);
                        movieCardTitle.setText(String.format(" Titre : %s", firstMovie.title));
                        movieCardSynopsis.setText(String.format(" Actors :  %s", firstMovie.actors));
                        movieCardCategory.setText(String.format(" Category : %s", firstMovie.category));
                        movieCardDuration.setText(String.format(" Durée : %s minutes", firstMovie.length));

                        String imageUrl = BASE_URL + firstMovie.posters.large;
                        Log.d("APIURL", "URL de l'image: " + imageUrl);
                        Glide.with(requireContext()).load(imageUrl).centerCrop().into(movieCardImage);

                        movieAdapter.setMovies(root.theater_movies.subList(1, root.theater_movies.size()));


                    } else {
                        Log.e("API", "Corps de réponse vide");
                    }
                } else {
                    Log.e("API", "Erreur: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                Log.e("API", "Échec de la récupération des données: " + t.getMessage());
            }
        });
    }





}
