package com.example.sampleproject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameWindowController {

    private ArrayList<CardController> commonCards = new ArrayList<CardController>();
    private ArrayList<CardController> attackCards = new ArrayList<CardController>();
    private ArrayList<CardController> defendCards = new ArrayList<CardController>();
    private boolean isAttack = true;

    @FXML
    private Button addCardButton;

    @FXML
    private GridPane deskAttackCardPane;

    @FXML
    private GridPane deskAnswerCardPane;

    @FXML
    private ScrollPane firstPlayerScroll;

    @FXML
    private ScrollPane secondPlayerScroll;

    @FXML
    private FlowPane firstPlayerPane;

    @FXML
    private FlowPane secondPlayerPane;

    @FXML
    void addCard(ActionEvent event) throws IOException, URISyntaxException {
        String[] masks = {"Черви", "Бубны", "Пики", "Крести"};
        for (String mask : masks) {
            for (int nominal = 6; nominal < 15; nominal++) {
                commonCards.add(createCard(Integer.toString(nominal), mask));
            }
        }

        for (CardController card : commonCards) {
            switch (card.getNominal()) {
                case "11":
                    card.setNominal("Валет");
                    break;
                case "12":
                    card.setNominal("Дама");
                    break;
                case "13":
                    card.setNominal("Король");
                    break;
                case "14":
                    card.setNominal("Туз");
                    break;
            }
        }

        addCardButton.setVisible(false);
    }

    private CardController createCard(String nominal, String mask) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
        Pane newPane = loader.load();
        CardController cardController = loader.getController();
        cardController.setCardParameters(nominal, mask, this, newPane);

        return cardController;
    }

    public void giveCards() {
        int cardsCount = 32;
        Random random = new Random();
        firstPlayerScroll = new ScrollPane();
        secondPlayerScroll = new ScrollPane();

        for (int i = 0; i < 6; i++) {
            CardController card = commonCards.remove(random.nextInt(cardsCount));
            cardsCount--;
            firstPlayerPane.getChildren().add(card.cardPane);

            card = commonCards.remove(random.nextInt(cardsCount));
            cardsCount--;
            secondPlayerPane.getChildren().add(card.cardPane);
        }

        firstPlayerScroll.setContent(firstPlayerPane);
        secondPlayerScroll.setContent(secondPlayerPane);
        secondPlayerPane.setDisable(true);
        secondPlayerPane.setOpacity(0.5);
    }

    public void addCardOnTable(CardController card) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Card.fxml"));
        Pane newPane = loader.load();
        card.setActive(false);
        CardController cardController = loader.getController();
        cardController.setCardParameters(card.getNominal(), card.getMask(), this, newPane);

        if (isAttack) {
            attackCards.add(card);
            deskAttackCardPane.add(newPane, deskAttackCardPane.getChildren().size(), 0);
            firstPlayerPane.setDisable(true);
            firstPlayerPane.setOpacity(0.5);
            secondPlayerPane.setDisable(false);
            secondPlayerPane.setOpacity(0.5);
        } else {
            ArrayList<String> usedNominals = new ArrayList<String>();
            for (CardController c : attackCards) {
                if (!usedNominals.contains(c.getNominal())) {
                    usedNominals.add(c.getNominal());
                }
            }

            defendCards.add(card);
            deskAnswerCardPane.add(newPane, deskAnswerCardPane.getChildren().size(), 0);
            secondPlayerPane.setDisable(true);
            secondPlayerPane.setOpacity(0.5);
            firstPlayerPane.setDisable(false);
            firstPlayerPane.setOpacity(1);
        }

        isAttack = !isAttack;
    }
}