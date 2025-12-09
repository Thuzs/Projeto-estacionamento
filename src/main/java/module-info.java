module  br.batpark.sp.jandira.estacionamento.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens br.batpark.sp.jandira.estacionamento.ui to javafx.fxml;
    exports br.batpark.sp.jandira.estacionamento.ui;
}