package br.batpark.sp.jandira.estacionamento.model;

import br.batpark.sp.jandira.estacionamento.ui.repository.Cadastro;

import java.time.LocalDateTime;

public class VeiculoEstacionado extends Cadastro {

    private String placa;
    private String modelo;
    private String proprietario;
    private String dataHoraEntrada; // como texto (igual ao CSV)

    public VeiculoEstacionado(String placa, String modelo, String proprietario, String dataHoraEntrada) {
        this.placa = placa;
        this.modelo = modelo;
        this.proprietario = proprietario;
        this.dataHoraEntrada = dataHoraEntrada;
    }

    // GETTERS obrigatórios para a TableView
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public String getProprietario() { return proprietario; }
    public String getDataHoraEntrada() { return dataHoraEntrada; }
}