module com.example.ejercicioq {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ejercicioq to javafx.fxml;
    exports com.example.ejercicioq;
}