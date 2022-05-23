package com.example.demotexteditorbi2;


import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import java.io.File;
import java.util.function.DoublePredicate;

public class HelloController extends Application {

    @Override
    public void start(Stage primaryStage) {
        var mediaView = new MediaView();

        var progressBar = new ProgressBar();
        progressBar.setMaxWidth(Double.MAX_VALUE);
        StackPane.setAlignment(progressBar, Pos.BOTTOM_CENTER);
        StackPane.setMargin(progressBar, new Insets(10));

        var root = new StackPane(mediaView, progressBar);
        primaryStage.setScene(new Scene(root, 1000, 650));
        primaryStage.setTitle("Video Progress Demo");
        primaryStage.show();

        chooseMediaFile(primaryStage)
                .ifPresentOrElse(
                        uri -> {
                            var media = new Media(uri);
                            var mediaPlayer = new MediaPlayer(media);
                            mediaPlayer.setAutoPlay(true);
                            mediaView.setMediaPlayer(mediaPlayer);

                            bindProgress(mediaPlayer, progressBar);
                            addSeekBehavior(mediaPlayer, progressBar);
                        },
                        Platform::exit);
    }

    private void bindProgress(MediaPlayer player, ProgressBar bar) {
        var binding =
                Bindings.createDoubleBinding(
                        () -> {
                            var currentTime = player.getCurrentTime();
                            var duration = player.getMedia().getDuration();
                            if (isValidDuration(currentTime) && isValidDuration(duration)) {
                                return currentTime.toMillis() / duration.toMillis();
                            }
                            return ProgressBar.INDETERMINATE_PROGRESS;
                        },
                        player.currentTimeProperty(),
                        player.getMedia().durationProperty());
        bar.progressProperty().bind(binding);
    }

    private void addSeekBehavior(MediaPlayer player, ProgressBar bar) {
        EventHandler<MouseEvent> onClickAndOnDragHandler =
                e -> {
                    var duration = player.getMedia().getDuration();
                    if (isValidDuration(duration)) {
                        var seekTime = duration.multiply(e.getX() / bar.getWidth());
                        player.seek(seekTime);
                        e.consume();
                    }
                };
        bar.addEventHandler(MouseEvent.MOUSE_CLICKED, onClickAndOnDragHandler);
        bar.addEventHandler(MouseEvent.MOUSE_DRAGGED, onClickAndOnDragHandler);
    }

    private boolean isValidDuration(Duration d) {
        return d != null && !d.isIndefinite() && !d.isUnknown();
    }

    private Optional<String> chooseMediaFile(Stage owner) {
        var chooser = new FileChooser();
        chooser
                .getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Media Files", "*.mp4", "*.mp3", "*.wav"));
        var file = chooser.showOpenDialog(owner);
        return Optional.ofNullable(file).map(f -> f.toPath().toUri().toString());
    }
}