package com.example.uhqp;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.util.Duration;


import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoublePredicate;

public class Controller {

    private String path;
    private MediaPlayer mediaPlayer;
    @FXML MediaView mediaView;
    @FXML private Slider progressBar;
    private Button playButton;
    private ImageView ivPlay;
    private ImageView ivPause;
    @FXML
    void HomeButtonPress(ActionEvent event) throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio and Video Files", "*.mp4", "*.mp3"));
        File file =  fileChooser.showOpenDialog(null);
        path = file.toURI().toString();
        if(path!=null) {
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            DoubleProperty width = mediaView.fitWidthProperty();
            DoubleProperty height = mediaView.fitWidthProperty();

            width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
                    progressBar.setValue(newValue.toSeconds());
                }
            });
            progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                }
            });
            /*if (file.getName().endsWith(".mp3")) {

                Image image = new Image("![](images/Girls.gif)");
                ImageView iv = new ImageView(image);
                StackPane sp = new StackPane();
                Scene sc = new Scene(sp);
                Stage st = new Stage();
                sp.getChildren().add(iv);
                st.setScene(sc);
                st.show();


                mediaPlayer.play();
            }
            else{
                mediaPlayer.play();
            }*/
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
        /*Image imagePlay = new Image(new File("@images/play.png").toString());
        ivPlay = new ImageView(imagePlay);
        Image imagePause = new Image(new File("@images/pause.png").toString());
        ivPause = new ImageView(imagePlay);
        */



        MediaPlayer.Status status = mediaPlayer.getStatus();


        if(status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            //playButton.setGraphic(ivPlay);
        }




        else if(status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.STOPPED){
            mediaPlayer.play();
           //playButton.setGraphic(ivPause);
        }




    }

    @FXML
    void PressPlaylistButton(ActionEvent event) {
    }


}


