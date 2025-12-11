package br.batpark.sp.jandira.estacionamento.model;


import java.time.Duration;
import java.time.LocalDateTime;

public class Registro_saida {
    public String placa;
    public String proprietario;
    public String modelo;
    public LocalDateTime dataHoraSaida;
    public Duration tempoPermanencia;
}
