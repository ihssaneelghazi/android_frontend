package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ismagiefm.movielandefmismagi.Datas.models.User;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.databinding.FragmentCompteInscriptionBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CompteFragmentInscription extends Fragment {

    private FragmentCompteInscriptionBinding binding;
    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button signupButton;
    private TextView loginLink;

    public CompteFragmentInscription() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompteInscriptionBinding.inflate(inflater, container, false);

        usernameEditText = binding.usernameInput;
        emailEditText = binding.emailInput;
        passwordEditText = binding.passwordInput;
        signupButton = binding.signupButton;
        loginLink = binding.loginLink;
        ImageButton backButton = binding.backButton;

        backButton.setOnClickListener(view -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_compteFragmentInscription_to_compteFragmentLogin);
        });

        signupButton.setOnClickListener(view -> registerUser());

        loginLink.setOnClickListener(view -> {
            // Aller au fragment de connexion
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_compteFragmentInscription_to_compteFragmentLogin);
        });

        return binding.getRoot();
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(null,username, email, password);
        Retrofit retrofit = RetrofitClientBackend.getRetrofitInstance();
        AuthService authService = retrofit.create(AuthService.class);
        Call<Void> call = authService.registerUser(user);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Rediriger vers le fragment de vérification avec le nom d'utilisateur
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    NavHostFragment.findNavController(CompteFragmentInscription.this)
                            .navigate(R.id.action_compteFragmentInscription_to_verificationFragment, bundle);
                } else {
                    Toast.makeText(getActivity(), "Erreur lors de l'inscription", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Erreur lors de l'inscription: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
