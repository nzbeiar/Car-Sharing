
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        // write your code here
        Menu menu = new Menu(args);
        menu.mainMenu();
        menu.persistance.close();
    }
}