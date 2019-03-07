package application;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

//import application.MainController.ComboHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.sql.*;
import java.time.ZoneId;

public class MainController {

	private ArrayList<Player> _playersList = new ArrayList<Player>();
	private ArrayList<String> _playersNameList = new ArrayList<String>();

	@FXML
	private TabPane _tabPane;
	@FXML
	private Tab _vpTab ;

	@FXML
	private Tab _grTab ;

	@FXML
	private Tab _apTab ;

	@FXML
	private Tab _agTab ;

	@FXML
	private Tab _gpTab ;

	private ArrayList<Game> _gamesList = new ArrayList<Game>();
	private ArrayList<String> _gamesNameList = new ArrayList<String>();
	@FXML
	private ListView  _grPlayerGameIdListView;
	@FXML
	private ListView  _grGameIdListView;
	@FXML
	private ListView  _grPlayerIdListView;
	@FXML
	private ListView  _grPlayingDateListView;
	@FXML
	private ListView  _grScoreListView;
	@FXML
	private ComboBox _playersCombo = new ComboBox<>();
	@FXML
	private TextField _vpPlayerIdTextField;
	@FXML
	private TextField _vpFirstNameTextField;
	@FXML
	private TextField _vpLastNameTextField;
	@FXML
	private TextField _vpAddressTextField;
	@FXML
	private TextField _vpPostalCodeTextField;
	@FXML
	private TextField _vpProvinceTextField;
	@FXML
	private TextField _vpPhoneNumberTextField;
	@FXML
	private Button _vpEditButton ;
	@FXML
	private Button _vpUpdateButton ;



	//add player Buttons
	@FXML
	private TextField _apPlayerIdTextField;
	@FXML
	private TextField _apFirstNameTextField;
	@FXML
	private TextField _apLastNameTextField;
	@FXML
	private TextField _apAddressTextField;
	@FXML
	private TextField _apPostalCodeTextField;
	@FXML
	private TextField _apProvinceTextField;
	@FXML
	private TextField _apPhoneNumberTextField;
	@FXML
	private Button _apAddButton ;
	@FXML
	private Label _apMessage;
	// add game tab
	@FXML
	private TextField _agGameNameTextField;
	@FXML
	private Button _agAddButton ;
	@FXML
	private Label _agMessage;
	@FXML
	private ComboBox _grPlayersComboBox  = new ComboBox<>();
	@FXML
	private DatePicker _grStartDatePicker = new DatePicker();
	@FXML
	private DatePicker _grEndDatePicker = new DatePicker();
	@FXML
	private TableView _grResultsTable = new TableView();

	//game played tab
	@FXML
	private ComboBox _gpPlayersComboBox  = new ComboBox<>();

	@FXML
	private ComboBox _gpGameComboBox  = new ComboBox<>();

	@FXML
	private DatePicker _gpDatePicker = new DatePicker();

	@FXML
	private TextField _gpGameScore ;
	@FXML
	private Button _gpAddPlayedGameRecord;

	@FXML
	private Button _gpFilterButton = new Button();

	private ObservableList data ;

	// private ArrayList<Player> _playersArr = new ArrayList<Player>();



	@FXML
	public void initialize() { // run before startting application
		_playersCombo.setOnAction(new VpComboHandler());
			_vpEditButton.setOnAction(new VpEditButtonHandler() );
			_vpUpdateButton.setOnAction(new VpUpdateButtonHandler() );
			_apAddButton.setOnAction(new ApAddButtonHandler());
			_agAddButton.setOnAction(new AgAddButtonHandler());
			_gpAddPlayedGameRecord.setOnAction(new GpAddPlayedGameRecordHandler() );
			_gpFilterButton.setOnAction(new GpFilterButtonHandler() );

	 	_vpPlayerIdTextField.setEditable(false);
	 	_vpFirstNameTextField.setEditable(false);
	 	_vpLastNameTextField.setEditable(false);
	 	_vpAddressTextField.setEditable(false);
	 	_vpPostalCodeTextField.setEditable(false);
	 	_vpProvinceTextField.setEditable(false);
	 	_vpPhoneNumberTextField.setEditable(false);

		// _playersCombo.getItems().add(_playersArr);

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.print("--------------------------------------->");
		//	Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost", "GameZone",   "1234");
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD", "COMP214_W18_004_19",   "password");
			System.out.println("Successfull connection");

			Statement retrieveStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet resultSet = retrieveStatement.executeQuery("select player_Id ,first_name from player");
			System.out.println(resultSet);

			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount();

			// System.out.printf("Person Table:%n%n");

			// display the names of the columns in the ResultSet
			// for (int i = 1; i <= numberOfColumns; i++){
			// System.out.printf("%-14s\t",metaData.getColumnName(i) );
			// }

			resultSet.beforeFirst();
			// display query results
			// Player[] playerArr = new Player[100];
			_playersList.clear();
			_playersNameList.clear();
			while (resultSet.next()) {

				Player player;
				for (int i = 1; i <= numberOfColumns; i += 2) {
					player = new Player(Integer.parseInt(resultSet.getObject(i).toString()),
							resultSet.getObject(i + 1).toString());
					_playersList.add(player);
					_playersNameList.add(player._playerFirstName);

				}

				System.out.println(resultSet.getObject("Player_Id"));
				// System.out.println(resultSet.getObject(2));
				// _playersArr.add(resultSet.getObject(1));
				// _playerCombo.getItems().add(_playersArr);

			}

			_playersCombo.getItems().clear();
			_grPlayersComboBox.getItems().clear();
			_gpPlayersComboBox.getItems().clear();

			_playersCombo.getItems().addAll(_playersNameList);
			_grPlayersComboBox.getItems().addAll(_playersNameList);
			_gpPlayersComboBox.getItems().addAll(_playersNameList);


			ResultSet resultSetGame = retrieveStatement.executeQuery("select game_Id ,game_title from game");
			ResultSetMetaData metaDataGame = resultSetGame.getMetaData();
			int numberOfColumnsGame = metaData.getColumnCount();

			resultSetGame.beforeFirst();

			while (resultSetGame.next()) {

				Game game;
				for (int i = 1; i <= numberOfColumnsGame; i += 2) {
					game = new Game(Integer.parseInt(resultSetGame.getObject(i).toString()),
							resultSetGame.getObject(i + 1).toString());
					_gamesList.add(game);
					_gamesNameList.add(game._gameTitle);

				}

				_gpGameComboBox.getItems().addAll(_gamesNameList);


				// System.out.println(resultSet.getObject(2));
				// _playersArr.add(resultSet.getObject(1));
				// _playerCombo.getItems().add(_playersArr);

			}


			// System.out.print(_playersArr.toString());

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}


	}

	public class VpComboHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

				onComboChange();
				 initialize();
		}
	}



	public class VpEditButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

				vpOnEdit();

		}
	}





	public class VpUpdateButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

				vpOnUpdate();
				 initialize();
		}
	}

	public class ApAddButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

				apAddButton();
				 initialize();
		}
	}

	public class AgAddButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

				agAddButton();
				 initialize();
		}
	}

	public class GpAddPlayedGameRecordHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {

			gpAddPlayedGameRecord();
			 initialize();
		}
	}

	public class  GpFilterButtonHandler  implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			gpFilterButton();
			 initialize();
		}
	}

	@FXML
	private void onComboChange()  {
		System.out.println("----------------------------->");
		int selectedPlayerId = -1 ;
		if (_playersCombo.getValue() != null) {
			for (int index = 0; index < _playersList.size(); index++) {
				if (_playersCombo.getValue() == _playersList.get(index)._playerFirstName) {
					selectedPlayerId = _playersList.get(index)._playerId;
					break ;
				}
			}
			System.out.println("selectedPlayerId"+selectedPlayerId);
			Connection connection = getCreatedConn();
			try{
			 PreparedStatement preStatement=connection.prepareStatement("Select * from player where player_id=?");
System.out.println("==========>1");
			 preStatement.setInt(1, selectedPlayerId);
			 ResultSet result = preStatement.executeQuery();
			 System.out.println(result + "77777777777");
			 while(result.next()){
				 System.out.println(result.getObject(1).toString());
				 	_vpPlayerIdTextField.setText(result.getObject(1).toString());
				 	_vpFirstNameTextField.setText(result.getObject(2).toString());
				 	_vpLastNameTextField.setText(result.getObject(3).toString());
				 	_vpAddressTextField.setText(result.getObject(4).toString());
				 	_vpPostalCodeTextField.setText(result.getObject(5).toString());
				 	_vpProvinceTextField.setText( result.getObject(6).toString());
				 	_vpPhoneNumberTextField.setText(result.getObject(7).toString());
			 }
//			Statement retrieveStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			ResultSet resultSet = retrieveStatement.executeQuery("select * from player where player_Id = ? ");
//

			connection.close();
			}
			catch(Exception e){
				System.out.print("Exception is " + e.getMessage());
			}

		}

	}

	@FXML
	private void vpOnEdit(){
		System.out.print("edit button pressdd ");

	 	_vpFirstNameTextField.setEditable(true);
	 	_vpLastNameTextField.setEditable(true);
	 	_vpAddressTextField.setEditable(true);
	 	_vpPostalCodeTextField.setEditable(true);
	 	_vpProvinceTextField.setEditable(true);
	 	_vpPhoneNumberTextField.setEditable(true);
	}

	@FXML
	private void vpOnUpdate(){
		System.out.print("edit button pressdd ");

		_vpPlayerIdTextField.setEditable(false);
	 	_vpFirstNameTextField.setEditable(false);
	 	_vpLastNameTextField.setEditable(false);
	 	_vpAddressTextField.setEditable(false);
	 	_vpPostalCodeTextField.setEditable(false);
	 	_vpProvinceTextField.setEditable(false);
	 	_vpPhoneNumberTextField.setEditable(false);
	 	Connection  connection  =  getCreatedConn();

		try{
			 PreparedStatement preStatement=connection.prepareStatement(
					 "Update player set First_Name = ? ,Last_Name = ? ,Address = ? ,postal_Code = ?, province = ? , Phone_Number = ?  where player_id=?");
System.out.println("==========>3");
			 preStatement.setString(1, _vpFirstNameTextField.getText());
			 preStatement.setString(2, _vpLastNameTextField.getText());
			 preStatement.setString(3, _vpAddressTextField.getText());
			 preStatement.setString(4, _vpPostalCodeTextField.getText());
			 preStatement.setString(5, _vpProvinceTextField.getText());
			 preStatement.setString(6, _vpPhoneNumberTextField.getText());
			 preStatement.setString(7, _vpPlayerIdTextField.getText());

			 ResultSet result = preStatement.executeQuery();
			 System.out.println( "update successful ");

//			Statement retrieveStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			ResultSet resultSet = retrieveStatement.executeQuery("select * from player where player_Id = ? ");
//

			connection.close();
			}
			catch(Exception e){
				System.out.print("Exception is " + e.getMessage());
			}



	}
	@FXML
	private void apAddButton(){
		Connection  connection  =  getCreatedConn();

		if(_apFirstNameTextField.getText() !=  null && _apFirstNameTextField.getText() != "" &&
				_apLastNameTextField.getText() !=  null && _apLastNameTextField.getText() != "" &&
				_apAddressTextField.getText() !=  null && _apAddressTextField.getText()!= "" &&
				_apPostalCodeTextField.getText() !=  null && _apPostalCodeTextField.getText() != "" &&
				_apProvinceTextField.getText() !=  null && _apProvinceTextField.getText() != "" &&
				_apPhoneNumberTextField.getText() !=  null && _apPhoneNumberTextField.getText() != "" ){

		try{
			 PreparedStatement preStatement=connection.prepareStatement(
					 "insert into player (Player_Id ,First_Name  ,Last_Name  ,Address  ,postal_Code , province  , Phone_Number) values(GZ_S_playerIdAutoInc.nextval,?,?,?,?,?,?)");
System.out.println("==========>3");
			 preStatement.setString(1, _apFirstNameTextField.getText());
			 preStatement.setString(2, _apLastNameTextField.getText());
			 preStatement.setString(3, _apAddressTextField.getText());
			 preStatement.setString(4, _apPostalCodeTextField.getText());
			 preStatement.setString(5, _apProvinceTextField.getText());
			 preStatement.setString(6, _apPhoneNumberTextField.getText());


			 ResultSet result = preStatement.executeQuery();
			 System.out.println( "update successful ");
			 _apMessage.setText("Player Added");
			 _apFirstNameTextField.setText("");
			 _apLastNameTextField.setText("");
			 _apAddressTextField.setText("");
			 _apPostalCodeTextField.setText("");
			 _apProvinceTextField.setText("");
			 _apPhoneNumberTextField.setText("");


//			Statement retrieveStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			ResultSet resultSet = retrieveStatement.executeQuery("select * from player where player_Id = ? ");
//

			connection.close();
			}
			catch(Exception e){
				System.out.print("Exception is " + e.getMessage());
			}
		}


	}

	@FXML
	private void agAddButton(){
		Connection  connection  =  getCreatedConn();

		if(_agGameNameTextField.getText() !=  null && _agGameNameTextField.getText() != ""  ){

		try{
			 PreparedStatement preStatement=connection.prepareStatement(
					 "insert into game (game_Id ,game_title) values (GZ_S_gameIdAutoInc.nextval,?)");
System.out.println("==========>3");
			 preStatement.setString(1, _agGameNameTextField.getText().toString());
			 System.out.println( _agGameNameTextField.getText().toString());



			 ResultSet result = preStatement.executeQuery();
			 System.out.println( "update successful ");
			 _agMessage.setText("Game Added");
			 _agGameNameTextField.setText("");



//			Statement retrieveStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			ResultSet resultSet = retrieveStatement.executeQuery("select * from player where player_Id = ? ");
//

			connection.close();
			}
			catch(Exception e){
				System.out.print("Exception is " + e.getMessage());
			}
		}


	}
	@FXML
	private void gpAddPlayedGameRecord(){

		int selectedPlayerId= -1;
		for (int index = 0; index < _playersList.size(); index++) {
			if (_gpPlayersComboBox.getValue() == _playersList.get(index)._playerFirstName) {
				selectedPlayerId = _playersList.get(index)._playerId;
				break ;
			}
		}

		int selectedGameId = -1;
		for (int index = 0; index < _gamesList.size(); index++) {
			if (_gpGameComboBox.getValue() == _gamesList.get(index)._gameTitle) {
				selectedGameId = _gamesList.get(index)._gameId;
				break ;
			}
		}

		java.util.Date date =
			    java.util.Date.from(_gpDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());




		Connection connection =  getCreatedConn();
		try{
			 PreparedStatement preStatement=connection.prepareStatement(
					 "insert into playerandgame ( player_game_Id ,Game_Id ,Player_Id ,Playing_Date, score ) values(G_S_PLAYERANDGAMEAUTOINC.nextval ,?,?,?,?)");

			 preStatement.setInt(1, selectedGameId);
			 preStatement.setInt(2, selectedPlayerId);
			 preStatement.setDate(3, sqlDate);
			 preStatement.setString(4, _gpGameScore.getText());

			 ResultSet result = preStatement.executeQuery();
			 System.out.println( "Insert successful  ");


//			Statement retrieveStatement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			ResultSet resultSet = retrieveStatement.executeQuery("select * from player where player_Id = ? ");
//

			connection.close();
			}
			catch(Exception e){
				System.out.print("Exception is " + e.getMessage());
			}


	}
	@FXML
	private void gpFilterButton(){
		  List list = new ArrayList();

		    //    data = getInitialTableData();
		    //    _grResultsTable.setItems(data);

		 System.out.println("==================>filter");
		int selectedPlayerId= -1;
		for (int index = 0; index < _playersList.size(); index++) {
			if (_grPlayersComboBox.getValue() == _playersList.get(index)._playerFirstName) {
				selectedPlayerId = _playersList.get(index)._playerId;
				break ;
			}
		}

		java.util.Date startDate =
			    java.util.Date.from(_grStartDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());


			java.util.Date endDate =
				    java.util.Date.from(_grEndDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

		Connection connection =  getCreatedConn();
		try{
			 PreparedStatement preStatement=connection.prepareStatement(
					 "select * from playerandgame where playing_date >= ? and playing_date <= ? and player_Id = ? ");

			 preStatement.setDate(1, sqlStartDate);
			 preStatement.setDate(2, sqlEndDate);
			 preStatement.setInt(3, selectedPlayerId);

			 ResultSet result = preStatement.executeQuery();
			 System.out.println("==================>");
			 System.out.println(result.toString());
			 ResultSetMetaData metaData = result.getMetaData();
				int numberOfColumns = metaData.getColumnCount();

				 _grPlayerGameIdListView.getItems().clear();
				  _grGameIdListView.getItems().clear();
				  _grPlayerIdListView.getItems().clear();
				  _grPlayingDateListView.getItems().clear();
				  _grScoreListView.getItems().clear();

				  _grPlayerGameIdListView.getItems().add( "Playergameid");
				  _grGameIdListView.getItems().add( "Game Id");
				  _grPlayerIdListView.getItems().add( "Player Id");
				  _grPlayingDateListView.getItems().add( "Date");
				  _grScoreListView.getItems().add( "Score");


			 while(result.next()){
				 System.out.println("result ------------>"+result.getObject(1).toString() );
				  _grPlayerGameIdListView.getItems().add( result.getObject(1).toString());
				  _grGameIdListView.getItems().add( result.getObject(2).toString());
				  _grPlayerIdListView.getItems().add( result.getObject(3).toString());
				  _grPlayingDateListView.getItems().add( result.getObject(4).toString());
				  _grScoreListView.getItems().add( result.getObject(5).toString());



			 }

			// ObservableList<PlayerAndGame> data = FXCollections.observableList(list);
			// _grResultsTable.setItems(data);

		}
		catch(Exception e){
			System.out.print("Exception is " + e.getMessage());
		}

	}



private Connection getCreatedConn(){
		Connection connection = null ;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GameZone",   "1234");
			  connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD", "COMP214_W18_004_19",   "password");
			System.out.println("Successfull connection");




	} catch (ClassNotFoundException e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
		e.printStackTrace();
	} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, e.getMessage());
		e.printStackTrace();
	}
		return connection;
}


class Player {
	public int _playerId;
	public String _playerFirstName;

	Player(int playerId, String playerFirstName) {
		this._playerId = playerId;
		this._playerFirstName = playerFirstName;
	}


}
class Game {
	public int _gameId;
	public String _gameTitle;

	Game(int gameId, String gameTitle) {
		this._gameId = gameId;
		this._gameTitle = gameTitle;
	}
}

class PlayerAndGame {
	private String _playerGameId;
	private String _gameId;
	private String _playerId ;
	private String _playing_Date;
	private String _score;
	public String get_playerGameId() {
		return _playerGameId;
	}
	public void set_playerGameId(String _playerGameId) {
		this._playerGameId = _playerGameId;
	}
	public String get_gameId() {
		return _gameId;
	}
	public void set_gameId(String _gameId) {
		this._gameId = _gameId;
	}
	public String get_playerId() {
		return _playerId;
	}
	public void set_playerId(String _playerId) {
		this._playerId = _playerId;
	}
	public String get_playing_Date() {
		return _playing_Date;
	}
	public void set_playing_Date(String _playing_Date) {
		this._playing_Date = _playing_Date;
	}
	public String get_score() {
		return _score;
	}
	public void set_score(String _score) {
		this._score = _score;
	}
	public PlayerAndGame(String _playerGameId, String _gameId, String _playerId, String _playing_Date, String _score) {
		super();
		this._playerGameId = _playerGameId;
		this._gameId = _gameId;
		this._playerId = _playerId;
		this._playing_Date = _playing_Date;
		this._score = _score;
	}
	@Override
	public String toString() {
		return "PlayerAndGame [_playerGameId=" + _playerGameId + ", _gameId=" + _gameId + ", _playerId=" + _playerId
				+ ", _playing_Date=" + _playing_Date + ", _score=" + _score + "]";
	}


	}
}