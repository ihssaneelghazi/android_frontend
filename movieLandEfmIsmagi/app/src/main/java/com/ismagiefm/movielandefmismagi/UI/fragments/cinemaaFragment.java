package com.ismagiefm.movielandefmismagi.UI.fragments;

// ... other imports ...

public class CinemaFragment extends Fragment {

    private FragmentCinemaBinding binding;
    private RecyclerView recycler_view_villes;
    private Set<String> processedCityIds;

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";

    public CinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCinemaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        recycler_view_villes = binding.recyclerViewVilles;
        processedCityIds = new HashSet<>();

        setupCalendarView(view);
        loadVilles();

        return view;
    }

    private void setupCalendarView(View view) {
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) ->
                Toast.makeText(getActivity(), dayOfMonth + "/" + (month + 1) + "/" + year, Toast.LENGTH_LONG).show());
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, getActivity().MODE_PRIVATE);
        return sharedPreferences.getString(TOKEN_KEY, "");
    }

    private void loadVilles() {
        String token = getToken();
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