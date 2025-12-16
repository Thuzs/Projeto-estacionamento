package br.batpark.sp.jandira.estacionamento.ui.repository;

import br.batpark.sp.jandira.estacionamento.model.VeiculoEstacionado; // Certifique-se de criar esta classe!
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cadastro {

    private Path arquivoVeiculosEstacionados = Paths.get("C:\\Usuários\\User\\Documentos\\CSV\\veiculos_estacionados.csv");
    private Path arquivoHistoricoSaidas = Paths.get("C:\\Usuários\\User\\Documentos\\CSV\\historico_saidas.csv");


    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Cadastro() {
        //Garante que o diretório exista
        try {
            System.out.println("Cadastro Iniciado");
            Files.createDirectories(arquivoVeiculosEstacionados.getParent());
            Files.createDirectories(arquivoHistoricoSaidas.getParent());
        } catch (IOException e) {
            System.err.println("Erro ao criar diretório para CSV: " + e.getMessage());
        }
    }
    public void adicionarRegistro(String placa, String proprietario, String modelo) {
        try {
            LocalDateTime entrada = LocalDateTime.now();

            String registroCsv = String.format("%s;%s;%s;%s%n",
                    placa,
                    modelo,
                    proprietario,
                    entrada.format(FORMATTER)
            );

            Files.write(
                    arquivoVeiculosEstacionados,
                    registroCsv.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            System.out.println("Registro de entrada salvo com sucesso no CSV.");

        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo CSV: " + e.getMessage());
        }
    }
    public Path getArquivoVeiculosEstacionados() {
        return arquivoVeiculosEstacionados;
    }
}