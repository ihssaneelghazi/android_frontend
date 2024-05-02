package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ismagiefm.movielandefmismagi.Datas.models.Cinema;
import com.ismagiefm.movielandefmismagi.Datas.models.Film;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.UI.adapter.CinemaAdapter;
import com.ismagiefm.movielandefmismagi.UI.adapter.FilmAdapter;
import com.ismagiefm.movielandefmismagi.databinding.FragmentSearchBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    private EditText searchEditText;
    private Button searchButton;
    private RecyclerView recycler_view_search;
    private FilmAdapter filmAdapter;
    private FragmentSearchBinding binding;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        searchEditText = binding.searchField;
        searchButton = binding.searchButton;
        recycler_view_search = binding.recyclerViewSearch;

        filmAdapter = new FilmAdapter(new ArrayList<>());
        recycler_view_search.setAdapter(filmAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString();
                searchFilms(searchText);
            }
        });
        return view;
    }

    private void searchFilms(String keyword) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");

        if (token.isEmpty()) {
            Toast.makeText(getActivity(), "Aucun token trouvé", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
        Call<List<Film>> call = authService.getFilmsByKeyword(keyword, "Bearer " + token);

        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                if (response.isSuccessful()) {
                    List<Film> films = response.body();
                    filmAdapter.setFilms(films);
                    setupRecyclerView(films); // Add this line



                } else {
                    Toast.makeText(getActivity(), "Erreur lors de la recherche", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur lors de la recherche", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setupRecyclerView(List<Film> filmList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view_search.setLayoutManager(layoutManager);
        FilmAdapter adapter = new FilmAdapter(filmList);
        recycler_view_search.setAdapter(adapter);
    }
}