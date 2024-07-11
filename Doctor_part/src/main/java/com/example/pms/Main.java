package com.example.pms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
 
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        
  
  
  Parent root;
  try {
    root = FXMLLoader.load(getClass().getResource("log_proj.fxml"));
    Scene scene = new Scene(root);
   
  
  
  primaryStage.setTitle("PMS");
        primaryStage.setScene(scene);
        primaryStage.show();
  }
  catch(IOException e)
  {
    e.printStackTrace();
  }
  
    }
 
 public static void main(String[] args) {
        launch(args);
    }
}



//package application;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
//        primaryStage.setTitle("Library Management System");
//        primaryStage.setScene(new Scene(root, 700, 500));
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
