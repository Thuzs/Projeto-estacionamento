package br.batpark.sp.jandira.estacionamento.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicialApp extends Application {

    TextField textFieldPlaca;
    TextField textFieldProprietario;
    TextField textFieldModelo;
    TableView<String> veiculosEstacionados = new TableView<>();


    BorderPane root = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("BAT Park - Gestão de Estacionamento");
        stage.setMinWidth(1000);
        stage.setMinHeight(600);

        root.setCenter(criarTelaPrincipal());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private VBox criarTelaPrincipal() {

        veiculosEstacionados.setPrefHeight(400);

        Button btnEntrada = new Button("Registrar Nova Entrada");
        Button btnSaida = new Button("Registrar Saída / Pagamento");

        btnEntrada.setOnAction(e -> root.setCenter(criarTelaEntrada()));

        HBox boxBotoes = new HBox(30, btnEntrada, btnSaida);
        boxBotoes.setAlignment(Pos.CENTER);


        VBox dashboardLayout = new VBox(20, veiculosEstacionados,  boxBotoes);
        dashboardLayout.setPadding(new Insets(30));
        dashboardLayout.setAlignment(Pos.CENTER);


        return dashboardLayout;
    }
    private VBox criarTelaEntrada() {
        Label titulo = new Label("Registrar entrada");
        titulo.setFont(new Font("Adamina", 18));

        GridPane formLayout = new GridPane();
        formLayout.setHgap(30);
        formLayout.setVgap(15);
        formLayout.setPadding(new Insets(80));
        formLayout.setAlignment(Pos.CENTER);

        textFieldPlaca = new TextField();
        textFieldProprietario = new TextField();
        textFieldModelo = new TextField();

        textFieldPlaca.setPrefWidth(300);



        formLayout.addRow(0, new Label("Placa:"), textFieldPlaca);
        formLayout.addRow(1, new Label("Proprietário:"), textFieldProprietario);
        formLayout.addRow(2, new Label("Modelo:"), textFieldModelo);

        Button btnConfirmar = new Button("Confirmar Entrada");

        btnConfirmar.setOnAction(e -> {
            // validação e CSV é aqui.
            root.setCenter(criarTelaPrincipal());
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> root.setCenter(criarTelaPrincipal()));

        HBox boxBotoes = new HBox(20, btnVoltar, btnConfirmar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);

        VBox entradaLayout = new VBox(20, titulo, formLayout, boxBotoes);
        entradaLayout.setPadding(new Insets(30));
        entradaLayout.setAlignment(Pos.TOP_CENTER);

        return entradaLayout;
    }

}