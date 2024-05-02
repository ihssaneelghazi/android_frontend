package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ismagiefm.movielandefmismagi.Datas.models.Cinema;
import com.ismagiefm.movielandefmismagi.Datas.models.Salle;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.UI.adapter.CinemaAdapter;
import com.ismagiefm.movielandefmismagi.UI.adapter.SalleAdapter;
import com.ismagiefm.movielandefmismagi.databinding.FragmentSalleBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalleFragment extends Fragment {

    private FragmentSalleBinding binding;
    private RecyclerView recycler_view_salles;
    private int cineid;

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    public SalleFragment() {
        // Required empty public constructor
    }

    public static SalleFragment newInstance(int cineid) {
        SalleFragment fragment = new SalleFragment();
        Bundle args = new Bundle();
        args.putInt("cineid", cineid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cineid = getArguments().getInt("cineid");
            Log.d("SalleVilleFragment", "Salle ID: " + cineid);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSalleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recycler_view_salles = view.findViewById(R.id.recycler_view_salles);
        ImageView closeIcon = view.findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        loadSalles(cineid);

        return view;
    }

    private void loadSalles(int cinemaId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");
        if (token.isEmpty()) {
            Log.e("CinemaVilleFragment", "Token non trouvé dans les SharedPreferences");
            return;
        }

        AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
        Call<List<Salle>> call = authService.getSallesByCinemaId(cinemaId, "Bearer " + token);
        call.enqueue(new Callback<List<Salle>>() {
            @Override
            public void onResponse(Call<List<Salle>> call, Response<List<Salle>> response) {
                if (response.isSuccessful()) {
                    List<Salle> salleList = response.body();
                    setupRecyclerView(salleList);
                } else {
                    Log.e("CinemaVilleFragment", "Erreur lors de la récupération des cinémas");
                }
            }

            @Override
            public void onFailure(Call<List<Salle>> call, Throwable t) {
                Log.e("CinemaVilleFragment", "Erreur lors de la récupération des cinémas: " + t.getMessage());
            }
        });
    }
    private void setupRecyclerView(List<Salle> salleList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view_salles.setLayoutManager(layoutManager);
        SalleAdapter adapter = new SalleAdapter(salleList);
        recycler_view_salles.setAdapter(adapter);
    }
}