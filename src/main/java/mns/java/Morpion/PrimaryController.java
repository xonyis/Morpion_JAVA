package mns.java.Morpion;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class PrimaryController {
	
	@FXML
	private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
