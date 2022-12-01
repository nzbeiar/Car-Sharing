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

    public void execute(String statement) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(statement);
        boolean empty = true;
        while(resultSet.next()) {
                empty = false;
                System.out.print(resultSet.getString(1) + ". ");
                System.out.println(resultSet.getString(2));
        }
        if(empty) {
            System.out.println("The company list is empty!");
        }
        System.out.println();
    }

    public void insert(String statement) throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = null;
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO COMPANY (NAME) VALUES ('%s')".formatted(statement));
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
                    " ID INTEGER NOT NULL AUTO_INCREMENT, " +
                    " NAME VARCHAR(255) NOT NULL UNIQUE, " +
                    " PRIMARY KEY (ID))";
            stmt.executeUpdate(sqlCreate);

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