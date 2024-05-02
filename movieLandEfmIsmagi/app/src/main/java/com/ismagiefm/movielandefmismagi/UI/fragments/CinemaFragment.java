package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ismagiefm.movielandefmismagi.Datas.models.Ville;
import com.ismagiefm.movielandefmismagi.UI.adapter.VilleAdapter;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.databinding.FragmentCinemaBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CinemaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CinemaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentCinemaBinding binding;
    private RecyclerView recycler_view_villes;
    private Set<String> processedCityIds;

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CinemaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CinemaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CinemaFragment newInstance(String param1, String param2) {
        CinemaFragment fragment = new CinemaFragment();
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
        // Gonfler le layout pour ce fragment
        binding = FragmentCinemaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recycler_view_villes = binding.recyclerViewVilles;
        processedCityIds = new HashSet<>();

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // C'est ici que vous pouvez gérer la date sélectionnée
                // Par exemple, vous pouvez l'afficher dans un Toast comme ceci :
                Toast.makeText(getActivity(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
        //Log du token
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");
        Log.d("Tokennn", "Token: " + token);
        loadVilles();

        return view;
    }


    private void loadVilles() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");
        if (token.isEmpty()) {
            Toast.makeText(getActivity(), "Vous n'etes pas connecté, veuillez vous connecter ou creer un compte svp", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
        Call<List<Ville>> call = authService.getVilles("Bearer " + token);

        call.enqueue(new Callback<List<Ville>>() {
            @Override
            public void onResponse(Call<List<Ville>> call, Response<List<Ville>> response) {
                if (response.isSuccessful()) {
                    List<Ville> villesList = response.body();
                    setupRecyclerView(villesList);
                } else {
                    Log.d("Ville", "Erreur réseau: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Ville>> call, Throwable t) {
                Log.d("Ville", "Erreur réseau: " + t.getMessage());
            }
        });
    }


    private void setupRecyclerView(List<Ville> villesList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view_villes.setLayoutManager(layoutManager);
        VilleAdapter adapter = new VilleAdapter(villesList);
        recycler_view_villes.setAdapter(adapter);
    }

}