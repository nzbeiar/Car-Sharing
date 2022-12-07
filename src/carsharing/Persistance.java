package carsharing;

import java.sql.*;
import java.util.Arrays;
import java.util.List;


public class Persistance {

    String JDBC_DRIVER;
    String DB_URL;
    final String[] args;


    private void dbSelect(String[] args) {
        int index = List.of(args).indexOf("-databaseFileName");
        if (index != -1) {
            DB_URL+= args[index+1];
        } else {
            DB_URL+= "test";
        }
    }

    public Persistance(String[] args) {
        this.args = args;
        JDBC_DRIVER = "org.h2.Driver";
        DB_URL = "jdbc:h2:./src/carsharing/db/";

    }

    public ResultSet execute(String statement) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(statement);
        conn.setAutoCommit(true);
        return resultSet;
    }

    public void insert(String statement) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO COMPANY (NAME) VALUES ('%s')".formatted(statement));
        conn.setAutoCommit(true);
    }

    public void insertCar(String name, long id) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('%s', '%d')".formatted(name,id));
        conn.setAutoCommit(true);
    }

    public void connect() {
        dbSelect(args);
        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            stmt = conn.createStatement();
            String sqlCreate =  "CREATE TABLE IF NOT EXISTS COMPANY(" +
                    " ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " NAME VARCHAR(255) NOT NULL UNIQUE)";
            stmt.executeUpdate(sqlCreate);
            String sqlCreate1 =  "CREATE TABLE IF NOT EXISTS CAR(" +
                    "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "NAME VARCHAR(255) NOT NULL UNIQUE," +
                    "COMPANY_ID INTEGER NOT NULL," +
                    "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";
            stmt.executeUpdate(sqlCreate1);
            conn.setAutoCommit(true);
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(conn!=null) conn.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
    }
}