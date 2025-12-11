package br.batpark.sp.jandira.estacionamento.ui;

import br.batpark.sp.jandira.estacionamento.ui.repository.Cadastro;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicialApp extends Application {

    TextField textFieldPlaca;
    TextField textFieldProprietario;
    TextField textFieldModelo;
    TableView<Cadastro> veiculosEstacionados = new TableView<>();

    private final ObservableList<Cadastro> dadosCadastro = FXCollections.observableArrayList();
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


    public VBox criarTelaPrincipal() {

        veiculosEstacionados = new TableView<>();
        veiculosEstacionados.setItems(dadosCadastro);
        veiculosEstacionados.setPrefHeight(400);

        TableColumn<Cadastro, String> colunaPlaca = new TableColumn<>("Placa");
        colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colunaPlaca.setPrefWidth(300);

        TableColumn<Cadastro, String> colunaModelo = new TableColumn<>("Modelo");
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colunaModelo.setPrefWidth(300);

        TableColumn<Cadastro, Double> colunaHora = new TableColumn<>("Horário de Entrada");
        colunaHora.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        colunaHora.setPrefWidth(350);

        veiculosEstacionados.getColumns().addAll(colunaPlaca, colunaModelo, colunaHora);

        Button btnEntrada = new Button("Registrar Nova Entrada");
        Button btnSaida = new Button("Registrar Saída / Pagamento");

        btnEntrada.setOnAction(e -> root.setCenter(criarTelaEntrada()));
        btnSaida.setOnAction(e -> root.setCenter(criarTelaSaida()));

        HBox boxBotoes = new HBox(30, btnEntrada, btnSaida);
        boxBotoes.setAlignment(Pos.CENTER);


        VBox dashboardLayout = new VBox(20, veiculosEstacionados,  boxBotoes);
        dashboardLayout.setPadding(new Insets(30));
        dashboardLayout.setAlignment(Pos.CENTER);


        return dashboardLayout;
    }
    public VBox criarTelaEntrada() {
        Label titulo = new Label("Registrar entrada");
        titulo.setFont(new Font("Adamina", 18));

        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(15);
        grid.setPadding(new Insets(80));
        grid.setAlignment(Pos.CENTER);

        textFieldPlaca = new TextField();
        textFieldProprietario = new TextField();
        textFieldModelo = new TextField();

        textFieldPlaca.setPrefWidth(300);



        grid.addRow(0, new Label("Placa:"), textFieldPlaca);
        grid.addRow(1, new Label("Proprietário:"), textFieldProprietario);
        grid.addRow(2, new Label("Modelo:"), textFieldModelo);

        Button btnConfirmar = new Button("Confirmar Entrada");

        btnConfirmar.setOnAction(e -> {
            // validação e CSV é aqui.
            System.out.println("entrada registrda");
            root.setCenter(criarTelaPrincipal());
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> root.setCenter(criarTelaPrincipal()));

        HBox boxBotoes = new HBox(20, btnVoltar, btnConfirmar);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);

        VBox entradaLayout = new VBox(20, titulo, grid, boxBotoes);
        entradaLayout.setPadding(new Insets(30));
        entradaLayout.setAlignment(Pos.TOP_CENTER);


        return entradaLayout;
    }
    public VBox criarTelaSaida() {
        Label titulo = new Label("Registrar Saída / Pagamento");
        titulo.setFont(new Font("Adamina", 18));

        Label labelBusca =  new Label("Buscar");
        TextField inputdBusca = new TextField();
        Button btnBuscar = new Button("Buscar");
        btnBuscar.setOnAction(e -> {

        });
        HBox boxBusca = new HBox(10, labelBusca, inputdBusca, btnBuscar);
        boxBusca.setAlignment(Pos.CENTER);

        VBox infoCobranca = new VBox(15);
        infoCobranca.setPadding(new Insets(15));
        infoCobranca.setStyle("-fx-border-color: #ccccc; -fx-border-width: 1; -fx-border-radius: 5;");

        Label infoTempo = new Label("Tempo de Permanência: 0h 0m");
        Label infoValor = new Label("Valor a Pagar: R$ 0,00");
        infoValor.setFont(new Font("Arial", 22));
        infoValor.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
        infoCobranca.getChildren().addAll( infoTempo, infoValor);

        Button btnConfirmarSaida = new Button("Confirmar Pagamento/Saída");

        btnConfirmarSaida.setOnAction(e -> {
            // Captura Hora Saída e Cálculo Final
            // Remoção do CSV Ativo e Gravação no Histórico
            System.out.println("Saída Confirmada e Histórico Gravado!");


            root.setCenter(criarTelaPrincipal());
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> root.setCenter(criarTelaPrincipal()));

        HBox boxBotoes = new HBox(20, btnVoltar, btnConfirmarSaida);
        boxBotoes.setAlignment(Pos.CENTER_RIGHT);

        VBox saidaLayout = new VBox(20, titulo, boxBusca, infoCobranca, boxBotoes);
        saidaLayout.setPadding(new Insets(30));
        saidaLayout.setMaxWidth(600);
        saidaLayout.setAlignment(Pos.TOP_CENTER);
        return saidaLayout;
    };
}