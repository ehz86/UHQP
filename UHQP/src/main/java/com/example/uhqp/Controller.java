package com.example.uhqp;
/* Copyright (C) 2022 <Poopy studio>
   This file is part of the UHQP.
   (Yet Another Merge Sort Routines)
    UHQP is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
   UHQP is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with UHQP.  If not, see <http://www.gnu.org/licenses/>.
  (Этот файл — часть UHQP.
   UHQP - свободная программа: вы можете перераспространять ее и/или
   изменять ее на условиях Стандартной общественной лицензии GNU в том виде,
   в каком она была опубликована Фондом свободного программного обеспечения;
   либо версии 3 лицензии, либо (по вашему выбору) любой более поздней
   версии.
   UHQP распространяется в надежде, что она будет полезной,
   но БЕЗО ВСЯКИХ ГАРАНТИЙ; даже без неявной гарантии ТОВАРНОГО ВИДА
   или ПРИГОДНОСТИ ДЛЯ ОПРЕДЕЛЕННЫХ ЦЕЛЕЙ. Подробнее см. в Стандартной
   общественной лицензии GNU.
   Вы должны были получить копию Стандартной общественной лицензии GNU
   вместе с этой программой. Если это не так, см.
   <http://www.gnu.org/licenses/>.)
 */
import java.util.Collections;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


import java.io.File;
import java.net.MalformedURLException;
import java.util.Collection;

import java.util.List;
import java.util.Timer;

public class Controller {

    private String path;

    private List<File> songs;
    private File cs, cs2;
    private int index;
    private MediaPlayer mediaPlayer;
    @FXML
    MediaView mediaView;
    @FXML
    private Slider progressBar;
    @FXML
    private Slider volumeBar;
    @FXML
    private Button playButton;
    private ImageView ivPlay;
    private ImageView ivPause;
    @FXML
    private ListView<File> ListView;
    private TextArea txt;

    @FXML
    private ImageView playImg;
    private final Image imagePlay = new Image(new File("UHQP/src/main/resources/com/example/uhqp/images/play.png").toURI().toString());
    private final Image imagePause = new Image(new File("UHQP/src/main/resources/com/example/uhqp/images/pause.png").toURI().toString());
    @FXML
    private ImageView gif2004;
    private final Image gifdance = new Image(new File("UHQP/src/main/resources/com/example/uhqp/images/Girls.gif").toURI().toString());
    private Object Stage;
    private Media media;


    @FXML
    void HomeButtonPress(ActionEvent event) {
        System.out.println(songs);
        Collections.shuffle(songs);

    }


    @FXML
    void BackButtonPressed(ActionEvent event) {
        if (index > 0) {
            index--;
            mediaPlayer.stop();
            cs = ListView.getItems().get(index);
            playSong(cs);



        } else {
            index = songs.size()-1;
            mediaPlayer.stop();
            cs = ListView.getItems().get(index);
            playSong(cs);
        }

    }

    @FXML
    void ForwardButtonPressed(ActionEvent event) {
        if (index < songs.size() - 1) {
            index++;
            mediaPlayer.stop();
            cs = ListView.getItems().get(index);
            playSong(cs);



        } else {
            index = 0;
            mediaPlayer.stop();
            cs = ListView.getItems().get(index);
            playSong(cs);
        }
    }

    private void playSong(File cs) {


        path = cs.toURI().toString();
        if (path != null) {
            media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
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

         

            volumeBar.setValue(mediaPlayer.getVolume() * 10);
            volumeBar.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeBar.getValue() / 100);
                }
            });
            mediaPlayer.play();

            gif2004.setImage(gifdance);
        }
    }

    @FXML
    void PlayButtonPressed(ActionEvent event) {
        MediaPlayer.Status status = mediaPlayer.getStatus();
        index = ListView.getSelectionModel().getSelectedIndex();
        cs = ListView.getItems().get(index);
         if(cs2 != cs) {
             mediaPlayer.stop();
             status = MediaPlayer.Status.READY;
         }
         if (status == MediaPlayer.Status.READY) {
             playSong(cs);
         }

             else if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playImg.setImage(imagePause);
            }
            else if (status == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                playImg.setImage(imagePlay);//апдейт имэдж вью и релоуднуть сцену
            }

         cs2 = cs;
    }





    @FXML
    void PressPlaylistButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        songs = fileChooser.showOpenMultipleDialog(null);

        ListView.getItems().addAll(songs);
        media = new Media(songs.get(0).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
    }

}


