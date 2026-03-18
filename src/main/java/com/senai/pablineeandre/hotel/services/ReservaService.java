package com.senai.pablineeandre.hotel.services;

import com.senai.pablineeandre.hotel.entity.Detalhe;
import com.senai.pablineeandre.hotel.entity.Reserva;
import com.senai.pablineeandre.hotel.enums.Status;
import com.senai.pablineeandre.hotel.enums.TipoQuarto;
import com.senai.pablineeandre.hotel.repository.ReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;


    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }


    public Reserva atualizarReserva(Long id, Reserva reservaAtualizada) {

        Reserva reservaExiste = buscarReserva(id);

        if (reservaAtualizada.getNomeHospede() != null) {
            reservaExiste.setNomeHospede(reservaAtualizada.getNomeHospede());
        }

        if (reservaAtualizada.getEmailHospede() != null) {
            reservaExiste.setEmailHospede(reservaAtualizada.getEmailHospede());
        }

        if (reservaAtualizada.getTelefoneHospede() != null) {
            reservaExiste.setTelefoneHospede(reservaAtualizada.getTelefoneHospede());
        }

        if (reservaAtualizada.getDataEntrada() != null) {
            if (reservaAtualizada.getDataEntrada().isBefore(LocalDate.now())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Data de entrada deve ser hoje ou futura"
                );
            }

            reservaExiste.setDataEntrada(reservaAtualizada.getDataEntrada());
        }

        if (reservaAtualizada.getDataSaida() != null) {
            LocalDate dataEntradaParaComparar = reservaExiste.getDataEntrada();

            if (reservaAtualizada.getDataSaida().isBefore(dataEntradaParaComparar)
                    || reservaAtualizada.getDataSaida().isEqual(dataEntradaParaComparar)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Data de saída deve ser posterior à data de entrada"
                );
            }

            reservaExiste.setDataSaida(reservaAtualizada.getDataSaida());
        }
        if (reservaAtualizada.getTipoQuarto() != null) {
            reservaExiste.setTipoQuarto(reservaAtualizada.getTipoQuarto());
        }

        if (reservaAtualizada.getObservacoes() != null) {
            reservaExiste.setObservacoes(reservaAtualizada.getObservacoes());
        }
        return reservaRepository.save(reservaExiste);
    }

    public Reserva checkIn(Long id, LocalDateTime checkIn) {
        Reserva reservaExiste = buscarReserva(id);
        LocalDate dataHoje = LocalDate.now();
        if (dataHoje.isBefore(reservaExiste.getDataEntrada())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Data de entrada ainda não chegou");
        }
        if (!reservaExiste.getStatus().equals(Status.CONFIRMADA)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Reserva não confirmada");
        }
        reservaExiste.setDataCheckIn(checkIn);
        reservaExiste.setStatus(Status.EM_HOSPEDAGEM);

        return reservaRepository.save(reservaExiste);
    }

    public Reserva checkOut(Long id, LocalDateTime checkOut) {
        Reserva reservaExiste = buscarReserva(id);
        if (!reservaExiste.getStatus().equals(Status.EM_HOSPEDAGEM)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Reserva nunca foi Hospedada");
        }
        reservaExiste.setDataCheckOut(checkOut);
        reservaExiste.setStatus(Status.CONCLUIDA);

        return reservaRepository.save(reservaExiste);
    }

    public Reserva criarReserva(Reserva reservaAtualizada) {
        if (reservaAtualizada.getDataEntrada().isBefore(LocalDate.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Data de entrada deve ser hoje ou futura"
            );
        }

        if (!reservaAtualizada.getDataSaida().isAfter(reservaAtualizada.getDataEntrada())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Data de saída deve ser posterior à data de entrada"
            );
        }

        reservaAtualizada.setDataCriacao(LocalDateTime.now());
        reservaAtualizada.setStatus(Status.PENDENTE);

        reservaAtualizada.setDataCheckIn(null);
        reservaAtualizada.setDataCheckOut(null);
        return reservaRepository.save(reservaAtualizada);
    }

    public void deletarReserva(Long id) {

        Reserva reservaExiste = buscarReserva(id);

        if (reservaExiste.getStatus() == Status.EM_HOSPEDAGEM) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir uma reserva em hospedagem"
            );
        }

        reservaRepository.delete(reservaExiste);
    }

    public List<Reserva> buscarPorStatus(Status status) {
        return reservaRepository.findByStatus(status);
    }

    public List<Reserva> buscarPorTipoQuarto(TipoQuarto tipo) {
        return reservaRepository.findByTipoQuarto(tipo);
    }

    public List<Reserva> reservasHoje() {
        return reservaRepository.findByDataEntrada(LocalDate.now());
    }

    public List<Reserva> reservasProximas(int dias) {

        LocalDate hoje = LocalDate.now();
        LocalDate limite = hoje.plusDays(dias);

        return reservaRepository.findByDataEntradaBetween(hoje, limite);
    }

    public List<Reserva> buscar(String termo) {

        return reservaRepository
                .findByNomeHospedeContainingIgnoreCaseOrEmailHospedeContainingIgnoreCase(
                        termo,
                        termo
                );
    }

    public List<Reserva> emHospedagem() {
        return reservaRepository.findByStatus(Status.EM_HOSPEDAGEM);
    }

    public List<Reserva> ListarReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> ListarReservaID(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva confirmar(Long id) {

        Reserva reservaExiste = buscarReserva(id);

        if (reservaExiste.getStatus() != Status.PENDENTE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Apenas reservas pendentes podem ser confirmadas"
            );

        }

        reservaExiste.setStatus(Status.CONFIRMADA);

        return reservaRepository.save(reservaExiste);
    }

    public Reserva buscarReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Reserva não encontrada"
                ));
    }

    public Reserva cancelar(Long id) {
        Reserva reservaExiste = buscarReserva(id);

        if (reservaExiste.getStatus() != Status.PENDENTE && reservaExiste.getStatus() != Status.CONFIRMADA) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Só é possível cancelar reservas com status PENDENTE ou CONFIRMADA"
            );
        }

        reservaExiste.setStatus(Status.CANCELADA);

        return reservaRepository.save(reservaExiste);
    }

    public Reserva alterarTipoQuarto(Long id, TipoQuarto novoTipo) {
        Reserva reservaExiste = buscarReserva(id);

        reservaExiste.setTipoQuarto(novoTipo);

        return reservaRepository.save(reservaExiste);
    }

    public Reserva adicionarDetalhes(Long id, Detalhe detalhe){

        Reserva reservaExiste = buscarReserva(id);
        reservaExiste.setDetalhe(detalhe);
        detalhe.setReserva(reservaExiste);
        return reservaRepository.save(reservaExiste);
    }

}
