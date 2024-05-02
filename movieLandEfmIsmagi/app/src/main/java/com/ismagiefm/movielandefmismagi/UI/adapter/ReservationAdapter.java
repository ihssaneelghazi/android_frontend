package com.ismagiefm.movielandefmismagi.UI.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ismagiefm.movielandefmismagi.Datas.models.Reservation;
import com.ismagiefm.movielandefmismagi.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationHolder> {
    List<Reservation> reservations;

    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    @NonNull
    @NotNull
    @Override
    public ReservationAdapter.ReservationHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation,parent,false);
        return new ReservationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReservationAdapter.ReservationHolder holder, int position) {
        Reservation reservation=reservations.get(position);
        holder.nomClient.setText("Nom du client: " + reservation.getNomClient());
        holder.dateReservation.setText("Date de réservation: " + reservation.getDateReservation());
        holder.prix.setText("Prix: " + reservation.getTicket().getPrix());
        holder.codePayement.setText("Code de paiement: " + reservation.getTicket().getCodePayement());
        holder.tv_nombreTickets.setText("Nombre de tickets: " + reservation.getNombreTickets());


    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public class ReservationHolder extends RecyclerView.ViewHolder {
        private TextView nomClient, dateReservation, prix,codePayement,tv_nombreTickets;
        public ReservationHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nomClient=itemView.findViewById(R.id.nomClient);
            dateReservation=itemView.findViewById(R.id.tv_dateReservation);
            prix=itemView.findViewById(R.id.prix);
            codePayement=itemView.findViewById(R.id.codePayment);
            tv_nombreTickets=itemView.findViewById(R.id.tv_nombreTickets);


        }
    }
}
