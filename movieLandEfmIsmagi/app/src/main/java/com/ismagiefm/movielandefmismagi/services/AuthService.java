package com.ismagiefm.movielandefmismagi.services;

import com.ismagiefm.movielandefmismagi.Datas.models.*;
import org.json.JSONArray;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface AuthService {

    @GET("seances.json?dateDebut=01-01-2019&dateFin=31-12-2019&id=2314pp")
    Call<Root> getCinemaInfo();
    @POST("users/register")
        Call<Void> registerUser(@Body User user);
    @POST("users/login")
    Call<Void> login(@Body UserModel user);
    @GET("users/verifyEmail/{token}")
    Call<Void> verifyEmail(@Path("token") String token);//pour vérifier l'email on utilise le token le path permet de passer le token en paramètre
    @GET("users/villes")
    Call<List<Ville>> getVilles(@Header("Authorization") String token);
    @GET("users/villes/{cityId}/cinemas")
    Call<List<Cinema>> getCinemasByCityId(@Path("cityId") int cityId, @Header("Authorization") String token);
    @GET("users/cinemas/{cinemaId}/salles")
    Call<List<Salle>> getSallesByCinemaId(@Path("cinemaId") int cinemaId, @Header("Authorization") String token);
    @GET("users/salles/{salleId}/projections?projection=p1")
    Call<List<Projection>> getProjectionsBySalleId(@Path("salleId") int salleId, @Header("Authorization") String token);

    @GET("users/imageFilm/{id}")
    Call<ResponseBody> getFilmImage(@Path("id") int id, @Header("Authorization") String token);

    //recherche de film par contenu
    @GET("/users/films/titreContains/{keyword}")
    Call<List<Film>> getFilmsByKeyword(@Path("keyword") String keyword, @Header("Authorization") String token);

    @POST("users/reservations")
    Call<Reservation> reserveTickets(@Body Reservation reservation, @Header("Authorization") String token);
    @GET("users/reservations/user/{userId}")
    Call<List<Reservation>> getReservationsByUserId(@Path("userId") Long userId, @Header("Authorization") String token);


}
