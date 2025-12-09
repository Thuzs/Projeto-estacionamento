module com.example.projetoestacionamento {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projetoestacionamento to javafx.fxml;
    exports com.example.projetoestacionamento;
}