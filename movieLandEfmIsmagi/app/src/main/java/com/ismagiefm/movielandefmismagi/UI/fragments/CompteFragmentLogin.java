package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.fragment.NavHostFragment;
import com.ismagiefm.movielandefmismagi.Datas.models.UserModel;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.databinding.FragmentCompteLoginBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompteFragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompteFragmentLogin extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentCompteLoginBinding binding;
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView signupLink;
    private static final String SHARED_PREFS_NAME = "MyPrefs";// creation du fichier de sauvegarde
    private static final String TOKEN_KEY = "token";// creation de la clé de sauvegarde

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CompteFragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompteFragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static CompteFragmentLogin newInstance(String param1, String param2) {
        CompteFragmentLogin fragment = new CompteFragmentLogin();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compte_login, container, false);
        ImageButton backButton = view.findViewById(R.id.backButton);
        usernameEditText = view.findViewById(R.id.usernameInput);
        passwordEditText = view.findViewById(R.id.passwordInput);
        loginButton = view.findViewById(R.id.loginButton);
        signupLink = view.findViewById(R.id.signupLink);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retourne à la page précédente
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aller au fragment d'inscription
                NavHostFragment.findNavController(CompteFragmentLogin.this)
                        .navigate(R.id.action_compteFragmentLogin_to_compteFragmentInscription);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                UserModel user = new UserModel(username, password, null);
                AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
                Call<Void> call = authService.login(user);//on appelle la méthode login de l'interface AuthService
                call.enqueue(new Callback<Void>() {//on utilise enqueue pour exécuter l'appel de manière asynchrone
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {

                            saveTokenToSharedPreferences(response.headers().get("Authorization"));//on sauvegarde le token dans les préférences partagées
                            NavHostFragment.findNavController(CompteFragmentLogin.this)
                                    .navigate(R.id.action_compteFragmentLogin_to_cinemaFragment);

                        } else {

                            int statusCode = response.code();
                            Log.e("LoginActivity", "Erreur lors de la connexion. Code de statut : " + statusCode);
                            if (statusCode == 401) {

                            } else {
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Gérer les erreurs de connexion
                        Log.e("LoginActivity", "Erreur lors de la connexion : " + t.getMessage(), t);

                    }
                });
            }
        });


        return view;
    }
    private void saveTokenToSharedPreferences(String token) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);//on sauvegarde le token
        editor.apply();
    }
}