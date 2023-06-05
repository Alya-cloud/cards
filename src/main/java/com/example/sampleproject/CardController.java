package com.example.sampleproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CardController {
	Pane cardPane;

    private boolean active = true;

	GameWindowController gameWindow;

    @FXML
    private Text nominal;

    @FXML
    private Text mask;

    @FXML
    private ImageView imgBuffer;

    @FXML
    private Pane imgPane;

    public void setCardParameters(String nominal, String mask, GameWindowController gameWindow, Pane cardPane) throws FileNotFoundException, URISyntaxException {
    	this.nominal.setText(nominal);
    	this.mask.setText(mask);
    	this.gameWindow = gameWindow;
    	this.cardPane = cardPane;
    	
    	File img = new File(Objects.requireNonNull(getClass().getResource("EntrancePicture.jpg")).toURI());
    	InputStream isImage = (InputStream) new FileInputStream(img);
    	imgBuffer = new ImageView(new Image(isImage));
    	imgBuffer.setFitWidth(100);
    	imgBuffer.setPreserveRatio(true);
    	imgPane.getChildren().setAll(imgBuffer);
    }
    
    public String getNominal() {
    	return this.nominal.getText();
    }
    public void setNominal(String nominal) {
         this.nominal.setText(nominal);
    }
    
    public String getMask() {
    	return this.mask.getText();
    }

    public Pane getPlane(){
        return cardPane;
    }
    @FXML
    void replaceCardToTable(MouseEvent event) throws IOException, URISyntaxException {
        if (this.isActive()) {
            gameWindow.addCardOnTable(this);
            cardPane.setVisible(false);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
