package com.example.uhqp;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.function.DoublePredicate;

public class Controller {

    private String path;
    private MediaPlayer mediaPlayer;
    @FXML MediaView mediaView;
    @FXML
    void HomeButtonPress(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        File file =  fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if(path!=null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitWidthProperty();

            width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));


            mediaPlayer.play();
        }

    }
    @FXML
    void BackButtonPressed(ActionEvent event) {

    }

    @FXML
    void ForwardButtonPressed(ActionEvent event) {

    }

    @FXML
    void PlayButtonPressed(ActionEvent event) {

    }

    @FXML
    void PressPlaylistButton(ActionEvent event) {

    }


}