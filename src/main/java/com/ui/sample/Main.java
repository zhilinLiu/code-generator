package com.ui.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main  extends  Application{
    @Override
    public void start(Stage stage) throws IOException {
        Parent load = FXMLLoader.load(Main.class.getResource("/sample.fxml"));

        stage.setTitle("代码生成器");
        stage.setScene(new Scene(load));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
