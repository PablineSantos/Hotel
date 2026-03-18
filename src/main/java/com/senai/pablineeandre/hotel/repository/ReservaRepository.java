package com.senai.pablineeandre.hotel.repository;

import com.senai.pablineeandre.hotel.entity.Reserva;
import com.senai.pablineeandre.hotel.enums.Status;
import com.senai.pablineeandre.hotel.enums.TipoQuarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

    @Repository
    public interface ReservaRepository extends JpaRepository<Reserva, Long> {

        List<Reserva> findByStatus(Status status);

        List<Reserva> findByTipoQuarto(TipoQuarto tipoQuarto);

        List<Reserva> findByDataEntrada(LocalDate data);

        List<Reserva> findByDataEntradaBetween(LocalDate inicio, LocalDate fim);

        List<Reserva> findByNomeHospedeContainingIgnoreCaseOrEmailHospedeContainingIgnoreCase(
                String nome, String email);

    }

