package com.umpisa.reservation.service;

import com.umpisa.reservation.model.Reservation;
import com.umpisa.reservation.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

    public final ReservationRepository reservationRepository;


    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation updateReservation(Optional<Reservation> reservation, Reservation newReservation){
        Reservation updatedReservation = reservation.get();
        updatedReservation.setName(newReservation.getName());
        updatedReservation.setEmail(newReservation.getEmail());
        updatedReservation.setPhoneNumber(newReservation.getPhoneNumber());
        updatedReservation.setReservationDate(newReservation.getReservationDate());
        updatedReservation.setNumberOfGuest(newReservation.getNumberOfGuest());

        return updatedReservation;
    }
}
