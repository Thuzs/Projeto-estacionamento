module  br.batpark.sp.jandira.estacionamento.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens br.batpark.sp.jandira.estacionamento.model to javafx.base;
    exports br.batpark.sp.jandira.estacionamento.ui;
}