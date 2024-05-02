package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ismagiefm.movielandefmismagi.Datas.models.Cinema;
import com.ismagiefm.movielandefmismagi.Datas.models.Ville;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.UI.adapter.CinemaAdapter;
import com.ismagiefm.movielandefmismagi.UI.adapter.VilleAdapter;
import com.ismagiefm.movielandefmismagi.databinding.FragmentCinemaVilleBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class CinemaVilleFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int cinemaId;
    private FragmentCinemaVilleBinding binding;
    private VideoView videoView;
    private ImageView cinemaImage;
    private RecyclerView recycler_view_cinemas;
    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    public CinemaVilleFragment() {
        // Required empty public constructor
    }

    public static CinemaVilleFragment newInstance(String param1, String param2) {
        CinemaVilleFragment fragment = new CinemaVilleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cinemaId = getArguments().getInt("cinemaId");
            Log.d("CinemaVilleFragment", "Cinema ID: " + cinemaId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCinemaVilleBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        videoView = binding.videoView;
        recycler_view_cinemas = binding.recyclerViewCinemas;
        ImageView closeIcon = view.findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });



        String videoPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.couple;
        binding.videoView.setVideoURI(Uri.parse(videoPath));

        MediaController mediaController = new MediaController(getActivity());
        binding.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(binding.videoView);

        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                binding.videoView.start();
            }
        });



        loadCinemas(cinemaId);

        return view;
    }

    private void loadCinemas(int cinemaId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");
        if (token.isEmpty()) {
            Log.e("CinemaVilleFragment", "Token non trouvé dans les SharedPreferences");
            return;
        }

        AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
        Call<List<Cinema>> call = authService.getCinemasByCityId(cinemaId, "Bearer " + token);
        call.enqueue(new Callback<List<Cinema>>() {
            @Override
            public void onResponse(Call<List<Cinema>> call, Response<List<Cinema>> response) {
                if (response.isSuccessful()) {
                    List<Cinema> cinemaList = response.body();
                    setupRecyclerView(cinemaList);
                } else {
                    Log.e("CinemaVilleFragment", "Erreur lors de la récupération des cinémas");
                }
            }

            @Override
            public void onFailure(Call<List<Cinema>> call, Throwable t) {
                Log.e("CinemaVilleFragment", "Erreur lors de la récupération des cinémas: " + t.getMessage());
            }
        });
    }

    private void setupRecyclerView(List<Cinema> cinemaList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view_cinemas.setLayoutManager(layoutManager);
        CinemaAdapter adapter = new CinemaAdapter(cinemaList);
        recycler_view_cinemas.setAdapter(adapter);
    }

}