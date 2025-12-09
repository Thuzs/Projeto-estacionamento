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

        HBox boxBotoes = new HBox(30, btnEntrada, btnSaida);
        boxBotoes.setAlignment(Pos.CENTER);


        VBox dashboardLayout = new VBox(20, veiculosEstacionados,  boxBotoes);
        dashboardLayout.setPadding(new Insets(30));
        dashboardLayout.setAlignment(Pos.CENTER);


        return dashboardLayout;
    }


}