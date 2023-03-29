package mns.java.Morpion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class App extends Application {

    private static Scene scene;
    

	private char whoseTurn = 'X';

		@SuppressWarnings({ })
		public Cell[][] cell =  new Cell[3][3];

		private Label lblStatus = new Label("X's turn to play");

	@SuppressWarnings("exports")
	public BorderPane createScene(Stage primaryStage) {
	
    	 GridPane pane = new GridPane(); 
 	    for (int i = 0; i < 3; i++)
 	      for (int j = 0; j < 3; j++)
 	    	 pane.add(cell[i][j] = new Cell(), j, i);
 	    

 	    Button button = new Button("New Game");
 	    Button buttonTab = new Button("Tableau des scores");
 	    

 	    BorderPane borderPane = new BorderPane();
 	    borderPane.setTop(button);
 	    borderPane.setRight(buttonTab);
 	    borderPane.setCenter(pane);
 	    borderPane.setBottom(lblStatus);
 	    
 	    return borderPane;
  
	}
	
	
    
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException, SQLException {
    	Scene scene = new Scene(createScene(stage), 650, 370);
        stage.setTitle("TicTacToe");
        stage.setScene(scene);
        stage.show();
	

    }
    public boolean isFull() {
        for (int i = 0; i < 3; i++)
          for (int j = 0; j < 3; j++)
            if (cell[i][j].getToken() == ' ')
              return false;

        return true;
      }
      public boolean isWon(char token) {
        for (int i = 0; i < 3; i++)
          if (cell[i][0].getToken() == token
              && cell[i][1].getToken() == token
              && cell[i][2].getToken() == token) {
            return true;
          }

        for (int j = 0; j < 3; j++)
          if (cell[0][j].getToken() ==  token
              && cell[1][j].getToken() == token
              && cell[2][j].getToken() == token) {
            return true;
          }

        if (cell[0][0].getToken() == token 
            && cell[1][1].getToken() == token        
            && cell[2][2].getToken() == token) {
          return true;
        }

        if (cell[0][2].getToken() == token
            && cell[1][1].getToken() == token
            && cell[2][0].getToken() == token) {
          return true;
        }

        return false;
      }
      public class Cell extends Pane {
        private char token = ' ';

        public Cell() {
          setStyle("-fx-border-color: black"); 
          this.setPrefSize(2000, 2000);
          this.setOnMouseClicked(e -> {
			try {
				handleMouseClick();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        }
        public char getToken() {
          return token;
        }
        public void setToken(char c) {
          token = c;      
          if (token == 'X') {
            Line line1 = new Line(10, 10, 
              this.getWidth() - 10, this.getHeight() - 10);
            line1.endXProperty().bind(this.widthProperty().subtract(10));
            line1.endYProperty().bind(this.heightProperty().subtract(10));
            Line line2 = new Line(10, this.getHeight() - 10, 
              this.getWidth() - 10, 10);
            line2.startYProperty().bind(
              this.heightProperty().subtract(10));
            line2.endXProperty().bind(this.widthProperty().subtract(10));
            this.getChildren().addAll(line1, line2); 
          }
          else if (token == 'O') {
            Ellipse ellipse = new Ellipse(this.getWidth() / 2, 
              this.getHeight() / 2, this.getWidth() / 2 - 10, 
              this.getHeight() / 2 - 10);
            ellipse.centerXProperty().bind(
              this.widthProperty().divide(2));
            ellipse.centerYProperty().bind(
                this.heightProperty().divide(2));
            ellipse.radiusXProperty().bind(
                this.widthProperty().divide(2).subtract(10));        
            ellipse.radiusYProperty().bind(
                this.heightProperty().divide(2).subtract(10));   
            ellipse.setStroke(Color.BLACK);
            ellipse.setFill(Color.WHITE);
            
            getChildren().add(ellipse); 
          }
        }

        private void handleMouseClick() throws SQLException {
          if (token == ' ' && whoseTurn != ' ') {
            setToken(whoseTurn);
            if (isWon(whoseTurn)) {
              lblStatus.setText(whoseTurn + " won! The game is over");
              
          	try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			Connection con=DriverManager.getConnection(
    					"jdbc:mysql://localhost:8888/morpion","root","");
    			System.out.println("Connecter");
    			String sql ="INSERT INTO score(vainqueur)"+"VALUES('"+whoseTurn+" a gagné')";
    					PreparedStatement pStatement=null;
    					try {
    					pStatement=con.prepareStatement(sql);
    					pStatement.executeUpdate();
    					}catch(Exception e) {
    						e.printStackTrace();
    						System.out.println("Erreur");
    					}
    					con.close();

    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println("Erreur");
    		}
            }
            else if (isFull()) {
              lblStatus.setText("Draw! The game is over");
              whoseTurn = ' '; 
          	try {
    			Class.forName("com.mysql.cj.jdbc.Driver");
    			Connection con=DriverManager.getConnection(
    					"jdbc:mysql://localhost:8888/morpion","root","");
    			System.out.println("Connecter");
    			String sql ="INSERT INTO score(vainqueur)"+"VALUES('Match nul')";
    					PreparedStatement pStatement=null;
    					try {
    					pStatement=con.prepareStatement(sql);
    					pStatement.executeUpdate();
    					}catch(Exception e) {
    						e.printStackTrace();
    						System.out.println("Erreur");
    					}
    					con.close();

    		} catch (ClassNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			System.out.println("Erreur");
    		}
            }
            else {
              whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
              lblStatus.setText(whoseTurn + "'s turn");
              
            }
          }
        }
      }
      
      


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


}