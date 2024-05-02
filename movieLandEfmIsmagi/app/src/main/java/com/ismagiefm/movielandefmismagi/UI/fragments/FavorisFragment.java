package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ismagiefm.movielandefmismagi.Datas.models.Reservation;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.UI.adapter.ReservationAdapter;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavorisFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView reservationRecyclerView;
    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    private String mParam1;
    private String mParam2;

    public FavorisFragment() {
        // Required empty public constructor
    }

    public static FavorisFragment newInstance(String param1, String param2) {
        FavorisFragment fragment = new FavorisFragment();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoris, container, false);
        reservationRecyclerView = view.findViewById(R.id.reservationRecyclerView);

        // Retrieve the token from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");

        // Decode the token
        DecodedJWT decodedJWT = JWT.decode(token);

        // Get the user_id from the token
        Long userId = decodedJWT.getClaim("user_id").asLong();

        loadReservations(userId);

        return view;
    }

    private void loadReservations(Long userId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");
        if (token.isEmpty()) {
            Log.e("FavorisFragment", "Token not found in SharedPreferences");
            return ;
        }

        AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
        Call<List<Reservation>> call = authService.getReservationsByUserId(userId, "Bearer " + token);
        call.enqueue(new Callback<List<Reservation>>() {
            @Override
            public void onResponse(Call<List<Reservation>> call, Response<List<Reservation>> response) {
                if (response.isSuccessful()) {
                    List<Reservation> reservationList = response.body();
                    setupRecyclerView(reservationList);
                } else {
                    Log.e("FavorisFragment", "Error fetching reservations");
                }
            }

            @Override
            public void onFailure(Call<List<Reservation>> call, Throwable t) {
                Log.e("FavorisFragment", "Error fetching reservations: " + t.getMessage());
            }
        });
    }

    private void setupRecyclerView(List<Reservation> reservations) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        reservationRecyclerView.setLayoutManager(layoutManager);
        ReservationAdapter adapter = new ReservationAdapter(reservations);
        reservationRecyclerView.setAdapter(adapter);
    }
}