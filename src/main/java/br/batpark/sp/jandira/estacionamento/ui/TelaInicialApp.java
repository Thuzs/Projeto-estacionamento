package br.batpark.sp.jandira.estacionamento.ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicialApp extends Application {
    TextField textFieldPlaca;
    TextField textFieldProprietario;
    TextField textFieldModelo;
    ListView veiculosEstacionados = new ListView();

    @Override
    public void start(Stage stage) throws IOException{
        stage.setFullScreen(true);

        VBox root = new VBox();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Estacionamento");
        stage.show();
    }



}
