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

import com.almasb.fxgl.core.collection.Array;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Controller {

    private String path;

    private List<File> songs;
    private List<File> newsongs;
    private File cs, cs2;
    private int index =-1;
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
    @FXML
    private TextField txt;
    private Boolean flag = false;

    @FXML
    private ImageView playImg;
    private final Image imagePlay = new Image(new File("UHQP/src/main/resources/com/example/uhqp/images/play.png").toURI().toString());
    private final Image imagePause = new Image(new File("UHQP/src/main/resources/com/example/uhqp/images/pause.png").toURI().toString());
    @FXML
    private ImageView gif2004;
    private final Image gifdance = new Image(new File("UHQP/src/main/resources/com/example/uhqp/images/Girls.gif").toURI().toString());
    private Object Stage;
    private Media media;
    private ChangeListener progressbarlist;


    @FXML
    void HomeButtonPress(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        /*fileChooser.getExtensionFilters().addAll(//
                new FileChooser.ExtensionFilter("MP3",".mp3"),//
                new FileChooser.ExtensionFilter("MP4",".mp4"),//
                new FileChooser.ExtensionFilter("AVI",".avi"),//
                new FileChooser.ExtensionFilter("MOV",".mov"),//
                new FileChooser.ExtensionFilter("WAV",".wav"));*/
        newsongs = fileChooser.showOpenMultipleDialog(null);
        songs = new ArrayList<>(newsongs);
        ListView.getItems().addAll(songs);
        media = new Media(songs.get(0).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

    }
    @FXML
    void TrashButtonPress(ActionEvent event){
        index = ListView.getSelectionModel().getSelectedIndex();
        ListView.getItems().remove(index);
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
       // }
        /*else{
            Random r = new Random();
            int i = r.nextInt(songs.size());
            mediaPlayer.stop();;
            cs=ListView.getItems().get(i);
            playSong(cs);
        }

         */
    }

    private void playSong(File cs) {


        path = cs.toURI().toString();
        String name = cs.getName();
        txt.setText(name);

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
            mediaPlayer.totalDurationProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration oldduration, Duration newduration) {
                    progressBar.setMax(newduration.toSeconds());
                }
            });
            progressBar.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if(!progressBar.isValueChanging()){
                        mediaPlayer.seek(Duration.seconds(progressBar.getValue()));
                    }
                }
            });
            progressBar.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldn, Number newn) {
                    double currenttime = mediaPlayer.getCurrentTime().toSeconds();
                    if(Math.abs(currenttime - newn.doubleValue()) > 0.5){
                        mediaPlayer.seek(Duration.seconds(newn.doubleValue()));
                    }
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

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    nextSong();
                }
            });


            volumeBar.setValue(mediaPlayer.getVolume() * 100);
            volumeBar.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volumeBar.getValue() / 10);
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
        System.out.println(index);
        if(index == -1){
            cs = ListView.getItems().get(0);
        }else {
            cs = ListView.getItems().get(index);}

            if (cs2 != cs) {
                mediaPlayer.stop();
                status = MediaPlayer.Status.READY;
            }
            if (status == MediaPlayer.Status.READY) {
                playSong(cs);
            } else if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                playImg.setImage(imagePause);
            } else if (status == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                playImg.setImage(imagePlay);//апдейт имэдж вью и релоуднуть сцену
            }

            cs2 = cs;

    }





    @FXML
    void PressPlaylistButton(ActionEvent event) {
       /* if(flag == false){
            flag = true;

            System.out.println(flag);
        }
        else{
            flag = false;
            ListView.getItems().remove(0,songs.size());
            ListView.getItems().addAll(songs);
            System.out.println(flag);
        }*/

    }
}


