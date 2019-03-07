package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;



public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);


//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GameZone","1234");
//			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD", "COMP214_W18_004_19",   "password");
//			System.out.println("Successfull connection");
//
//
//			Statement retrieveStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE );
//			ResultSet resultSet = retrieveStatement.executeQuery("select player_Id ,first_name from player");
//			System.out.println(resultSet);
//
//
//			ResultSetMetaData metaData = resultSet.getMetaData();
//			int numberOfColumns = metaData.getColumnCount();
//
//			System.out.printf("Person Table:%n%n");
//
//			// display the names of the columns in the ResultSet
//			for (int i = 1; i <= numberOfColumns; i++){
//				System.out.printf("%-14s\t",metaData.getColumnName(i) );
//			}
//			System.out.println();
//			resultSet.beforeFirst();
//			// display query results
//			while (resultSet.next())
//			{
//				//for (int i = 1; i <= numberOfColumns; i++)
//					System.out.println( resultSet.getObject(1));
//				//System.out.println();
//			}
//
//
//
//
//		}
//		catch(ClassNotFoundException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//			e.printStackTrace();
//		}
//		catch(SQLException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//			e.printStackTrace();
//		}


	}
}
