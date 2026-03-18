package com.senai.pablineeandre.hotel.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "detalhes_estadia")
public class Detalhe {

    @Id
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 20, unique = true)
    private String numeroQuarto;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private Integer andar;

    @Column(nullable = false)
    private boolean possuiFrigobar = false;

    @Column(nullable = false)
    private boolean possuiVaranda = false;

    @Column(nullable = false)
    private boolean acessibilidade = false;

    @Size(max = 300)
    @Column(length = 300)
    private String observacoesQuarto;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private Reserva reserva;



    public Detalhe() {
    }

    public Detalhe(String numeroQuarto, Integer andar, boolean possuiFrigobar,
                           boolean possuiVaranda, boolean acessibilidade, String observacoesQuarto) {
        this.numeroQuarto = numeroQuarto;
        this.andar = andar;
        this.possuiFrigobar = possuiFrigobar;
        this.possuiVaranda = possuiVaranda;
        this.acessibilidade = acessibilidade;
        this.observacoesQuarto = observacoesQuarto;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(String numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }

    public boolean isPossuiFrigobar() {
        return possuiFrigobar;
    }

    public void setPossuiFrigobar(boolean possuiFrigobar) {
        this.possuiFrigobar = possuiFrigobar;
    }

    public boolean isPossuiVaranda() {
        return possuiVaranda;
    }

    public void setPossuiVaranda(boolean possuiVaranda) {
        this.possuiVaranda = possuiVaranda;
    }

    public boolean isAcessibilidade() {
        return acessibilidade;
    }

    public void setAcessibilidade(boolean acessibilidade) {
        this.acessibilidade = acessibilidade;
    }

    public String getObservacoesQuarto() {
        return observacoesQuarto;
    }

    public void setObservacoesQuarto(String observacoesQuarto) {
        this.observacoesQuarto = observacoesQuarto;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}