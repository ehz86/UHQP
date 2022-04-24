package com.example.demotexteditorbi2;


import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class HelloController {
    @FXML
    protected TextArea myTextArea;


    @FXML
    protected void onSaveAsButtonClick() throws IOException {
        //System.out.println("Debug: " + myTextArea.getText());
        saveAs();

    }

    private boolean saveAs() throws IOException {

        String s = myTextArea.getText();
        Label label = new Label("TextShow");
        TextArea text = new TextArea(s);
        Group group = new Group(text);
        FlowPane root = new FlowPane(group,label);
        Scene scene = new Scene (root, 300,300);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Hello JavaFX");
        stage.show();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save as");
        File file = chooser.showSaveDialog(null);
        file.createNewFile();

        System.out.println("Debug: " + file.getAbsolutePath());
        System.out.println("Debug: " + myTextArea.getText());
        try {
            Files.writeString(file.toPath(), myTextArea.getText(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.out.println("Something wrong!");
            return false;
        }

        // Advanced example of filtering
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
//                new FileChooser.ExtensionFilter("ZIP", "*.zip"),
//                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
//                new FileChooser.ExtensionFilter("TEXT", "*.txt"),
//                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
//        );
        return true;
    }
}