package br.batpark.sp.jandira.estacionamento.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Registro_saida {
    // -Veículo -
    private String placa;
    private String proprietario;
    private String modelo;

    // Tempo e Financeiro
    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private Duration tempoPermanencia;
    private double valorPago;
}
