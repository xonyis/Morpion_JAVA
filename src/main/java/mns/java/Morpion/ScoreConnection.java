package mns.java.Morpion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ScoreConnection {
    private String database = "morpion";
    private String table = "score";
    private String user = "root";
    private String password = "";
    private static Connection connection = null;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public void connect() throws SQLException, ClassNotFoundException {
        Class.forName(" com.mysql.cj.jdbc.Driver");
        connection=  DriverManager.getConnection("jdbc:mysql://localhost:8888/"+database,user,password);
    }}