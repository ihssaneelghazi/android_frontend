package com.ismagiefm.movielandefmismagi.UI.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ismagiefm.movielandefmismagi.Datas.models.Reservation;
import com.ismagiefm.movielandefmismagi.Datas.models.Ticket;
import com.ismagiefm.movielandefmismagi.Datas.models.User;
import com.ismagiefm.movielandefmismagi.Datas.models.Userr;
import com.ismagiefm.movielandefmismagi.R;
import com.ismagiefm.movielandefmismagi.databinding.FragmentReservationBinding;
import com.ismagiefm.movielandefmismagi.repository.RetrofitClientBackend;
import com.ismagiefm.movielandefmismagi.services.AuthService;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFragment extends Fragment {

    private static final String SHARED_PREFS_NAME = "MyPrefs";
    private static final String TOKEN_KEY = "token";
    private FragmentReservationBinding binding;

    public ReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReservationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Retrieve the token from shared preferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(TOKEN_KEY, "");

        // Decode the token
        DecodedJWT decodedJWT = JWT.decode(token);

        // Get the username, role, and user_id from the token
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        Long userId = decodedJWT.getClaim("user_id").asLong();

        // Log the username, role, and user_id
        Log.d("JWT", "Username: " + username);
        Log.d("JWT", "Roles: " + roles);
        Log.d("JWTUser", "User ID: " + userId);

        // Initialize the DatePickerDialog
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                (view1, year, month, dayOfMonth) -> {
                    String date = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    binding.dateReservation.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        // Show the DatePickerDialog when the dateReservation field is clicked
        binding.dateReservation.setOnClickListener(v -> datePickerDialog.show());

        binding.btnValider.setOnClickListener(v -> {
            // Retrieve the data from the input fields
            String nomClient = binding.nomClient.getText().toString();
            String date = binding.dateReservation.getText().toString();
            int nombre = Integer.parseInt(binding.nombreTickets.getText().toString());
            String numero = binding.numeroTelephone.getText().toString();

            // Create a Ticket object with the id of the ticket
            Ticket ticket = new Ticket();
            ticket.setId(2);

            // Create a Reservation object with the data from the input fields and the Ticket object
            Reservation reservation = new Reservation();
            reservation.setNomClient(nomClient);
            reservation.setDateReservation(date);
            reservation.setNombreTickets(nombre);
            reservation.setNumeroTelephone(numero);
            reservation.setTicket(ticket);
            User user = new User(
                    userId,
                    null,
                    null,
                    null
            );
            user.setId(userId);
            reservation.setUser(user);

            // Call the reserveTickets method from your service with the Reservation object and the token
            AuthService apiService = RetrofitClientBackend.getRetrofitInstance().create(AuthService.class);
            Call<Reservation> call = apiService.reserveTickets(reservation, "Bearer " + token);
            call.enqueue(new Callback<Reservation>() {
                @Override
                public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Reservation successful!", Toast.LENGTH_LONG).show();


                        NavHostFragment.findNavController(ReservationFragment.this)
                                .navigate(R.id.action_reservationFragment_to_favorisFragment);



                    } else {
                        Toast.makeText(getActivity(), "Reservation failed!", Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(ReservationFragment.this)
                                .navigate(R.id.action_reservationFragment_to_favorisFragment);
                    }
                }

                @Override
                public void onFailure(Call<Reservation> call, Throwable t) {
                    Toast.makeText(getActivity(), "Reservation effectué !", Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(ReservationFragment.this)
                            .navigate(R.id.action_reservationFragment_to_favorisFragment);
                }
            });
        });

        return view;
    }
}