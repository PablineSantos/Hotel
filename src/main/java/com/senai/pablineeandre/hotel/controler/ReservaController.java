package com.senai.pablineeandre.hotel.controler;

import com.senai.pablineeandre.hotel.entity.Reserva;
import com.senai.pablineeandre.hotel.enums.Status;
import com.senai.pablineeandre.hotel.enums.TipoQuarto;
import com.senai.pablineeandre.hotel.services.ReservaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva criarReserva(@RequestBody Reserva reserva) {
        return reservaService.criarReserva(reserva);
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.ListarReservas();
    }

    @GetMapping("/{id}")
    public Reserva buscarReservaPorId(@PathVariable Long id) {
        return reservaService.buscarReserva(id);
    }

    @PutMapping("/{id}")
    public Reserva atualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaAtualizada) {
        return reservaService.atualizarReserva(id, reservaAtualizada);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarReserva(@PathVariable Long id) {
        reservaService.deletarReserva(id);
    }

    @GetMapping("/status/{status}")
    public List<Reserva> buscarPorStatus(@PathVariable Status status) {
        return reservaService.buscarPorStatus(status);
    }

    @GetMapping("/tipo/{tipoQuarto}")
    public List<Reserva> buscarPorTipoQuarto(@PathVariable TipoQuarto tipoQuarto) {
        return reservaService.buscarPorTipoQuarto(tipoQuarto);
    }

    @GetMapping("/hoje")
    public List<Reserva> reservasHoje() {
        return reservaService.reservasHoje();
    }

    @GetMapping("/proximas")
    public List<Reserva> reservasProximas(@RequestParam int dias) {
        return reservaService.reservasProximas(dias);
    }

    @GetMapping("/buscar")
    public List<Reserva> buscar(@RequestParam String termo) {
        return reservaService.buscar(termo);
    }

    @GetMapping("/em-hospedagem")
    public List<Reserva> emHospedagem() {
        return reservaService.emHospedagem();
    }

    @PatchMapping("/{id}/confirmar")
    public Reserva confirmar(@PathVariable Long id) {
        return reservaService.confirmar(id);
    }

    @PatchMapping("/{id}/checkin")
    public Reserva checkIn(@PathVariable Long id) {
        return reservaService.checkIn(id, LocalDateTime.now());
    }

    @PatchMapping("/{id}/checkout")
    public Reserva checkOut(@PathVariable Long id) {
        return reservaService.checkOut(id, LocalDateTime.now());
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelar(id));
    }

    @PatchMapping("/{id}/tipo/{tipoQuarto}")
    public ResponseEntity<Reserva> alterarTipo(@PathVariable Long id, @PathVariable TipoQuarto tipoQuarto) {
        return ResponseEntity.ok(reservaService.alterarTipoQuarto(id, tipoQuarto));
    }
}