package com.mycompany.javafxminiproject;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox rememberMe;

    @FXML
   
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Username dan password tidak boleh kosong!");
            return;
        }else{
              // kalau lolos validasi → pindah halaman
            Parent root = FXMLLoader.load(getClass().getResource("primary.fxml"));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            
        }

        // TODO: logika autentikasi kamu di sini
        System.out.println("Login: " + username);
    }
   
    @FXML
    private void handleForgotPassword() {
        System.out.println("Lupa password diklik");
    }

    @FXML
    private void handleRegister() {
        System.out.println("Daftar diklik");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}