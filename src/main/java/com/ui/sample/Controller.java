package com.ui.sample;

import com.github.liuzhilin.generater.Go;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static HashMap<String,Object> MAP= new HashMap<>();

    public static String JAR_PATH=Controller.class.getProtectionDomain().getCodeSource().getLocation()
            .getPath()
            .substring(1)
            .replace("/app/code_generater.jar","");
    private String dataPath;
    
    @FXML
    private Button button;
    @FXML
    private TextField author;
    @FXML
    private TextField dataSourceUrl;
    @FXML
    private TextField dataSourceName;
    @FXML
    private TextField dataSourcePassword;
    @FXML
    private TextField referencePath;
    @FXML
    private TextField moduleName;
    @FXML
    private TextField tableName;
    @FXML
    private TextField outPath;
    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

        MAP.put("areaText",textArea);

        this.dataPath = JAR_PATH+"/data.properties";
      
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(dataPath));
            author.setText(properties.getProperty("authorV"));
            dataSourceUrl.setText(properties.getProperty("dataSourceUrlV"));
            dataSourceName.setText(properties.getProperty("dataSourceNameV"));
            dataSourcePassword.setText(properties.getProperty("dataSourcePasswordV"));
            referencePath.setText(properties.getProperty("referencePathV"));
            moduleName.setText(properties.getProperty("moduleNameV"));
            tableName.setText(properties.getProperty("tableNameV"));
            outPath.setText(properties.getProperty("outPathV"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDateTime(ActionEvent event) {
        String authorV=author.getCharacters().toString();
        String dataSourceUrlV = dataSourceUrl.getCharacters().toString();
        String dataSourceNameV = dataSourceName.getCharacters().toString();
        String dataSourcePasswordV = dataSourcePassword.getCharacters().toString();
        String referencePathV = referencePath.getCharacters().toString();
        String moduleNameV = moduleName.getCharacters().toString();
        String tableNameV = tableName.getCharacters().toString();
        String outPathV = outPath.getCharacters().toString() + "/code.zip";

        Properties properties = new Properties();
        properties.setProperty("authorV",authorV);
        properties.setProperty("dataSourceUrlV",dataSourceUrlV);
        properties.setProperty("dataSourceNameV",dataSourceNameV);
        properties.setProperty("dataSourcePasswordV",dataSourcePasswordV);
        properties.setProperty("referencePathV",referencePathV);
        properties.setProperty("moduleNameV",moduleNameV);
        properties.setProperty("tableNameV",tableNameV);
        properties.setProperty("outPathV",outPath.getCharacters().toString());
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(dataPath));
            properties.store(outputStream,"配置文件");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Go go = new Go();
        try {
            go.go(authorV,
                    dataSourceUrlV,
                    dataSourceNameV,
                    dataSourcePasswordV,
                    referencePathV,
                    moduleNameV,
                    tableNameV,
                    outPathV);
            alert("代码生成完毕");
        } catch (Exception e) {
            alert(e.getMessage());
        }

    }

    private void alert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    private void println(String msg){
        textArea.appendText(msg+"\n");
    }
}
