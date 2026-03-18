package com.senai.pablineeandre.hotel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.senai.pablineeandre.hotel.enums.Status;
import com.senai.pablineeandre.hotel.enums.TipoQuarto;
import jakarta.persistence.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "tb_reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    @Size
    private String nomeHospede;

    @Column
    @NotBlank
    @Email
    private String emailHospede;

    @Column
    @Size(max=20)
    private String telefoneHospede;

    @NotNull
    private LocalDate dataEntrada;

    @Column
    @NotNull
    private LocalDate dataSaida;

    @Column
    private LocalDateTime dataCheckIn;

    @Column
    private LocalDateTime dataCheckOut;

    @Column
    private LocalDateTime dataCriacao;

    @Column
    @Size(max=500)
    private String observacoes;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoQuarto tipoQuarto;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.LAZY)
    @JsonManagedReference
    private Detalhe detalhe;

    public Reserva() {
    }

    public Reserva(Long id, String nomeHospede, String emailHospede, String telefoneHospede, LocalDate dataEntrada, LocalDate dataSaida, LocalDateTime dataCheckIn, LocalDateTime dataCheckOut, LocalDateTime dataCriacao, String observacoes, TipoQuarto tipoQuarto, Status status, Detalhe detalhe) {
        this.id = id;
        this.nomeHospede = nomeHospede;
        this.emailHospede = emailHospede;
        this.telefoneHospede = telefoneHospede;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.dataCriacao = dataCriacao;
        this.observacoes = observacoes;
        this.tipoQuarto = tipoQuarto;
        this.status = status;
        this.detalhe = detalhe;
    }

    public void setDetalhe(Detalhe detalhe) { this.detalhe = detalhe; }

    public void setStatus(Status status) { this.status = status; }

    public Status getStatus() {
        return status;
    }

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDateTime dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public LocalDateTime getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDateTime dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getTelefoneHospede() {
        return telefoneHospede;
    }

    public void setTelefoneHospede(String telefoneHospede) {
        this.telefoneHospede = telefoneHospede;
    }

    public String getEmailHospede() {
        return emailHospede;
    }

    public void setEmailHospede(String emailHospede) {
        this.emailHospede = emailHospede;
    }

    public @NotBlank @Size String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(@NotBlank @Size String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
