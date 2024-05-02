package com.ismagiefm.movielandefmismagi.UI.activities;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.databinding.ActivityVerifyRegisterBinding;
import android.app.Dialog;
import android.os.Handler;
import android.widget.Toast;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyRegisterActivity extends AppCompatActivity {
    private TextView welcomeTextView;
    private EditText verificationCodeEditText;
    private Button submitVerificationCodeButton;
    private String username;

    private ActivityVerifyRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        welcomeTextView = binding.welcomeTextView;
        verificationCodeEditText = binding.verificationCodeEditText;
        submitVerificationCodeButton = binding.submitVerificationCodeButton;

        // Récupérer le nom d'utilisateur du Bundle

        String message = String.format("Bienvenue, %s. Veuillez vérifier votre boîte de réception pour activer votre compte.", username);

        // Créez une Dialog
        Dialog dialog = new Dialog(this);
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

        // Gestion du clic sur le bouton de validation
        submitVerificationCodeButton.setOnClickListener(view -> validateAccount());
    }

    private void validateAccount() {
        // Récupérer le token entré par l'utilisateur
        String token = verificationCodeEditText.getText().toString().trim();

        // Vérifier si le champ du token n'est pas vide
        if (token.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer le token de vérification", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer une instance de AuthService
        AuthService authService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);

        // Appeler la méthode verifyEmail
        Call<Void> call = authService.verifyEmail(token);

        // Exécuter l'appel de manière asynchrone
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(VerifyRegisterActivity.this, "Validation réussie", Toast.LENGTH_SHORT).show();

                    loginUser();
                } else {
                    Toast.makeText(VerifyRegisterActivity.this, "Erreur lors de la validation du compte", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(VerifyRegisterActivity.this, "Erreur lors de la validation du compte : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {


    }
}