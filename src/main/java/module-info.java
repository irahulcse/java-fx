module com.example.javafxsetup {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires kafka.clients;

    opens thesis to javafx.fxml;
    exports thesis.context.data;
    exports thesis.context.controller;

}