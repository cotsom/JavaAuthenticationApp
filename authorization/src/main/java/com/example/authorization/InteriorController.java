package com.example.authorization;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class InteriorController extends Main{

    @FXML
    private TextField agilityInput;

    @FXML
    private TextField defenseInput;

    @FXML
    private TextField intelligenceInput;

    @FXML
    private TextField strengthInput;

    @FXML
    private TextField playerNameField;

    @FXML
    private Button submitButton;

    Player player = new Player();
    User user = new User();

    @FXML
    void initialize() {
        submitButton.setOnAction(actionEvent -> {
            String playerName = user.hashPassword(playerNameField.getText(), "");
            int intelligence = Integer.parseInt(intelligenceInput.getText());
            int agility = Integer.parseInt(agilityInput.getText());
            int defense = Integer.parseInt(defenseInput.getText());
            int strength = Integer.parseInt(strengthInput.getText());

            try {
                setUserCharacteristics(playerName, intelligence, agility, defense, strength);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setUserCharacteristics(String playerName, int intelligence, int agility, int defense, int strength) throws IOException {
        player.setPlayerName(playerName);
        player.setIntelligence(intelligence);
        player.setAgility(agility);
        player.setDefense(defense);
        player.setStrength(strength);
    }

    private void saveUserCharacteristics(Player player) throws IOException {
        String playerName = player.getPlayerName();
        File myFile = new File(playerName + ".dto");
        FileOutputStream fileOutputStream = new FileOutputStream(myFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(player);
    }
}
