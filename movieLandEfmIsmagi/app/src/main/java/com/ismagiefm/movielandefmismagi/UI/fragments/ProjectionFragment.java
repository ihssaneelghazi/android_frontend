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
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.Datas.models.Projection;
import com.ismagiefm.movielandefmismagi.UI.adapter.CinemaAdapter;
import com.ismagiefm.movielandefmismagi.UI.adapter.ProjectionAdapter;
import com.ismagiefm.movielandefmismagi.services.AuthService;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.databinding.FragmentProjectionBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectionFragment extends Fragment {
    private FragmentProjectionBinding binding;
    private RecyclerView recycler_view_projections;
    private int salleid;
    private AuthService apiService;
    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            salleid = getArguments().getInt("salleid");
            Log.d("Projection", "Projection ID: " + salleid);
        }

        // Create API service
        apiService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projection, container, false);
        recycler_view_projections = view.findViewById(R.id.recycler_view_projections);
        ImageView closeIcon = view.findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");
        if (token.isEmpty()) {
            Log.e("Projection", "Token non trouvé dans les SharedPreferences");
            return view;
        }

        // Make API call to get projections
        Call<List<Projection>> call = apiService.getProjectionsBySalleId(salleid, "Bearer " + token);
        call.enqueue(new Callback<List<Projection>>() {
            @Override
            public void onResponse(Call<List<Projection>> call, Response<List<Projection>> response) {
                if (response.isSuccessful()) {
                    List<Projection> projections = response.body();

                    // Update your RecyclerView with the projections list
                    setupRecyclerView(projections);
                }
            }

            @Override
            public void onFailure(Call<List<Projection>> call, Throwable t) {
                // Handle failure
                Log.e("ProjectionFragment", "Failed to load projections", t);
            }
        });

        return view;
    }


    private void setupRecyclerView(List<Projection> projectionList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view_projections.setLayoutManager(layoutManager);
        ProjectionAdapter adapter = new ProjectionAdapter(projectionList);
        recycler_view_projections.setAdapter(adapter);
    }

}