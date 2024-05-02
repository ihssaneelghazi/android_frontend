package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.databinding.FragmentVerificationBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationFragment extends Fragment {

    private FragmentVerificationBinding binding;
    private EditText verificationCodeEditText;
    private Button submitVerificationCodeButton;
    private String username;

    public VerificationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVerificationBinding.inflate(inflater, container, false);

        verificationCodeEditText = binding.verificationCodeEditText;
        submitVerificationCodeButton = binding.submitVerificationCodeButton;

        if (getArguments() != null) {
            username = getArguments().getString("username");
        }

        submitVerificationCodeButton.setOnClickListener(v -> validateAccount());
        // Définir le message à afficher
        String message = String.format("Bienvenue, %s. Veuillez vérifier votre boîte de réception pour activer votre compte.", username);

        // Créez une Dialog
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_welcome);
        // Récupérez le TextView de la Dialog
        TextView dialogTextView = dialog.findViewById(R.id.dialogTextView);
        // Affichez la Dialog
        dialog.show();
        // Créez un Handler pour mettre à jour le TextView
        final Handler handler = new Handler();
        // Définissez le délai entre chaque caractère (10 secondes / nombre de caractères)
        long delay = 10000 / message.length();
        // Créez un Runnable pour mettre à jour le TextView caractère par caractère
        Runnable runnable = new Runnable() {
            int i = 0;

            @Override
            public void run() {
                if (i < message.length()) {
                    dialogTextView.append(String.valueOf(message.charAt(i)));
                    i++;
                    handler.postDelayed(this, delay);
                }
            }
        };
        // Commencez à mettre à jour le TextView
        handler.postDelayed(runnable, delay);


        return binding.getRoot();
    }

    private void validateAccount() {
        String token = verificationCodeEditText.getText().toString().trim();

        if (token.isEmpty()) {
            return;
        }

        AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
        Call<Void> call = authService.verifyEmail(token);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loginUser();
                } else {
                    // Gérer l'échec de la vérification
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Gérer l'échec de la requête
            }
        });
    }

    private void loginUser() {
        NavHostFragment.findNavController(this).navigate(R.id.action_verificationFragment_to_compteFragmentLogin);
    }
}
