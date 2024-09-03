package com.umpisa.reservation.controller;

import com.umpisa.reservation.constant.ReservationConstants;
import com.umpisa.reservation.model.Reservation;
import com.umpisa.reservation.repositories.ReservationRepository;
import com.umpisa.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/Restaurant")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping(value = ReservationConstants.GET_ALL_RESERVATION)
    public ResponseEntity<List<Reservation>> getReservations(){
        try {
            List<Reservation> reservations = new ArrayList<>();
            reservations.addAll(reservationRepository.findAll());

            if(reservations.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(reservations, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = ReservationConstants.GET_SPECIFIC_RESERVATION)
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id){
        try {
            Optional<Reservation> reservation = reservationRepository.findById(id);

            if (reservation.isPresent()){
                return new ResponseEntity<>(reservation.get(), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping (value = ReservationConstants.ADD_RESERVATION)
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation){
        Reservation newReservation = reservationRepository.save(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @PutMapping(value = ReservationConstants.UPDATE_RESERVATION)
    public ResponseEntity<Reservation> updateReservationById(@PathVariable Long id, @RequestBody Reservation newReservation){
        try {
            Optional<Reservation> currentReservation = reservationRepository.findById(id);

            if (currentReservation.isPresent()){
                Reservation updatedReservation = reservationService.updateReservation(currentReservation, newReservation);

                Reservation reservation = reservationRepository.save(updatedReservation);
                return new ResponseEntity<>(reservation, HttpStatus.ACCEPTED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = ReservationConstants.DELETE_RESERVATION)
    public ResponseEntity<Reservation> cancelReservationById(@PathVariable Long id){
        reservationRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
