package com.example.authorization;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SignUpController {

    @FXML
    private Button authButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordConfirmationField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() {
        authButton.setOnAction(actionEvent -> {
            try {
                try {
                    signUpNewUser();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void signUpNewUser() throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = loginField.getText().replaceAll("[^a-zA-Z ]", "").trim();
        String password = passwordField.getText().trim();
        String confirmPassword = passwordConfirmationField.getText().trim();

        if (password.equals(confirmPassword)) {
            if (checkUser(login) == false) {
                User user = new User();
                user.setLogin(login);
                user.setPassword(user.hashPassword(password, "p0v4r50l17"));

                try {
                    dbHandler.signUpUser(user);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }else {
                System.out.println("User with this login already exist");
            }
        }else {
            System.out.println("Passwords don't match");
            return;
        }
    }

    private boolean checkUser(String login) throws SQLException, ClassNotFoundException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(login);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while (result.next()){
            counter++;
            if (counter >= 1){
                return true;
            }
        }
        return false;
    }
}
