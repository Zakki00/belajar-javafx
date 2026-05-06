module com.mycompany.javafxminiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.javafxminiproject to javafx.fxml;
    exports com.mycompany.javafxminiproject;
}
