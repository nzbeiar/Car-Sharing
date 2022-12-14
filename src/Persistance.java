import java.sql.*;
import java.util.Arrays;
import java.util.List;


public class Persistance {

    private final String[] args;
    private final String JDBC_DRIVER;
    private String DB_URL;

    private Connection conn = null;
    private Statement stmt = null;

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

    public void open() {
        dbSelect(args);
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
            String sqlCreate2 =  "CREATE TABLE IF NOT EXISTS CUSTOMER(" +
                    "ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "NAME VARCHAR(255) NOT NULL UNIQUE," +
                    "RENTED_CAR_ID INTEGER," +
                    "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";
            stmt.executeUpdate(sqlCreate2);
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public ResultSet select(String statement) throws SQLException {
        stmt = conn.createStatement();
        return stmt.executeQuery(statement);
    }

    public void insertBasic(String table,String statement) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO %s (NAME) VALUES ('%s')".formatted(table.toUpperCase(),statement));
    }

    public void insertCar(String name, long id) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('%s', '%d')".formatted(name,id));
    }

    public void rentCar(long idCustomer, long idCar) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = %d WHERE ID = %d".formatted(idCar,idCustomer));
    }

    public void unrentCar(long idCustomer) throws SQLException {
        stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID = %d".formatted(idCustomer));
    }

    public void close() throws SQLException {
        conn.setAutoCommit(true);
        stmt.close();
        conn.close();
    }

}