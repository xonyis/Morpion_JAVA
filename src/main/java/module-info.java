module mns.java.Morpion {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
    requires mysql.connector.j;

    opens mns.java.Morpion to javafx.fxml;
    exports mns.java.Morpion;
}
