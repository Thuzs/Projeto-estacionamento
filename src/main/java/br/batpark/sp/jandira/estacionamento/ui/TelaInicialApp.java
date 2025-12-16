package br.batpark.sp.jandira.estacionamento.ui;

import br.batpark.sp.jandira.estacionamento.model.VeiculoEstacionado;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaInicialApp extends Application {


    TextField textFieldPlaca;
    TextField textFieldProprietario;
    TextField textFieldModelo;
    TableView<VeiculoEstacionado> veiculosEstacionados = new TableView<>();

    private final ObservableList<VeiculoEstacionado> dadosCadastro = FXCollections.observableArrayList();
    private final Cadastro cadastro = new Cadastro();
    private final DateTimeFormatter FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
        dadosCadastro.clear();
        dadosCadastro.addAll(cadastro.buscarTodos());
        veiculosEstacionados.setItems(dadosCadastro);
        veiculosEstacionados.setPrefHeight(400);

        TableColumn<VeiculoEstacionado, String> colunaPlaca = new TableColumn<>("Placa");
        colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colunaPlaca.setPrefWidth(250);

        TableColumn<VeiculoEstacionado, String> colunaModelo = new TableColumn<>("Modelo");
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colunaModelo.setPrefWidth(250);

        TableColumn<VeiculoEstacionado, String> colunaProprietario = new TableColumn<>("Proprietario");
        colunaProprietario.setCellValueFactory(new PropertyValueFactory<>("proprietario"));
        colunaProprietario.setPrefWidth(250);

        TableColumn<VeiculoEstacionado, String> colunaHora = new TableColumn<>("Horário de Entrada");
        colunaHora.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));
        colunaHora.setPrefWidth(250);


        veiculosEstacionados.getColumns().addAll(colunaPlaca, colunaModelo, colunaProprietario, colunaHora);

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
            String placa = textFieldPlaca.getText().trim();
            String proprietario = textFieldProprietario.getText().trim();
            String modelo = textFieldModelo.getText().trim();


            if (placa.isEmpty() || proprietario.isEmpty() || modelo.isEmpty()) {
                new Alert(Alert.AlertType.ERROR,"Todos os campos são obrigatórios!" ).show();
                return;
            }

            if (cadastro.buscarPorPlaca(placa) != null) {
                new Alert(Alert.AlertType.WARNING,"Placa já registrada e estacionada!" ).show();
                return;
            }


            cadastro.adicionarRegistro(placa, proprietario, modelo);

            System.out.println("Entrada registrada com sucesso!");
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
    public double[] calcularCobranca(String entradaString, LocalDateTime saida) {

        LocalDateTime entrada = LocalDateTime.parse(entradaString, FORMATTER);
        Duration duracao = Duration.between(entrada, saida);
        long minutosTotal = duracao.toMinutes();

        double valorTotal = 0.0;
        long horasCobranca = 0;
        long minutosExcedentes = 0;

        if (minutosTotal > 0) {
            horasCobranca = minutosTotal / 60;
            minutosExcedentes = minutosTotal % 60;

            if (minutosExcedentes >= 5) {
                horasCobranca++;
            }

            if (horasCobranca > 0) {
                valorTotal += 10.00;

                long horasSubsequentes = horasCobranca - 1;

                if (horasSubsequentes > 0) {
                    valorTotal += horasSubsequentes * 5.00;
                }
            }
        }
        return new double[]{ (double) horasCobranca, valorTotal, (double) minutosTotal };
    }
    public VBox criarTelaSaida() {
        Label titulo = new Label("Registrar Saída / Pagamento");
        titulo.setFont(new Font("Adamina", 18));

        Label labelBusca =  new Label("Buscar Placa");
        TextField inputdBusca = new TextField();
        Button btnBuscar = new Button("Buscar");

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

            final VeiculoEstacionado[] veiculoEncontrado = {null};
            final double[] valorCalculado = {0.0};
            final LocalDateTime[] horaSaida = {null};
            final Duration[] duracaoPermanencia = {null};


            // Lógica de Busca e Cálculo
            btnBuscar.setOnAction(e -> {
                String placa = inputdBusca.getText().trim();
                if (placa.isEmpty()) {
                    new Alert(Alert.AlertType.WARNING, "Digite a placa para buscar.").show();
                    // REMOVIDA: btnConfirmarSaida.setDisable(true);
                    return;
                }

                VeiculoEstacionado veiculo = cadastro.buscarPorPlaca(placa);
                veiculoEncontrado[0] = veiculo;

                if (veiculo != null) {
                    // RF-006: Captura o timestamp de saída
                    horaSaida[0] = LocalDateTime.now();
                    LocalDateTime entradaTime = LocalDateTime.parse(veiculo.getHoraEntrada(), FORMATTER);

                    // Cálculo de Duration e Valor
                    Duration duracao = Duration.between(entradaTime, horaSaida[0]);
                    duracaoPermanencia[0] = duracao; // Armazena a duração real
                    double[] resultadoCalculo = calcularCobranca(veiculo.getHoraEntrada(), horaSaida[0]);

                    double horasCobranca = resultadoCalculo[0];
                    double valorPagar = resultadoCalculo[1];
                    long minutosTotais = (long) resultadoCalculo[2];

                    valorCalculado[0] = valorPagar;


                    long horasReais = minutosTotais / 60;
                    long minutosReais = minutosTotais % 60;
                    infoTempo.setText(String.format("Tempo de Permanência: %d h %d m (Cobrado: %.0f horas)", horasReais, minutosReais, horasCobranca));

                    infoValor.setText(String.format("Valor a Pagar: R$ %.2f", valorPagar));

                    // REMOVIDA QUALQUER ATIVAÇÃO/DESATIVAÇÃO CONDICIONAL AQUI

                } else {
                    new Alert(Alert.AlertType.ERROR, "Placa não encontrada no estacionamento.").show();
                    infoValor.setText("Valor a Pagar: R$ 0,00");
                    System.out.println("sla");
                    // REMOVIDA: btnConfirmarSaida.setDisable(true);
                }
            });
        btnConfirmarSaida.setOnAction(e -> {
            if (veiculoEncontrado[0] != null) {
                try {
                    // RF-014: Salva histórico
                    cadastro.salvarHistorico(veiculoEncontrado[0], horaSaida[0], duracaoPermanencia[0], valorCalculado[0]);

                    // RF-013: Remove do CSV ativo
                    cadastro.removerRegistro(veiculoEncontrado[0].getPlaca());

                    new Alert(Alert.AlertType.INFORMATION,
                            String.format("Saída registrada. Valor pago: R$ %.2f.", valorCalculado[0])).show();

                    root.setCenter(criarTelaPrincipal());

                } catch (IOException ex) {
                    new Alert(Alert.AlertType.ERROR, "Erro ao processar a saída: " + ex.getMessage()).show();
                }
            } else {
                // Adicionado alerta para o caso de o usuário clicar sem buscar a placa (já que o botão está sempre ativo)
                new Alert(Alert.AlertType.WARNING, "Por favor, busque a placa do veículo antes de confirmar a saída/pagamento.").show();
            }
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