package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    Scanner scanner;
    Persistance persistance;

    public Menu(String[] args) throws SQLException {
        this.scanner = new Scanner(System.in);
        this.persistance = new Persistance(args);
        persistance.connect();

    }

    public void mainInput() throws SQLException {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            String choice1 = scanner.nextLine();
            switch (choice1) {
                case "1" -> secondaryInput(choice1);
                case "0" -> {
                    return;
                }
            }
        }
    }

    public void secondaryInput(String choice) throws SQLException {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            String choice2 = scanner.nextLine();
            switch (choice2) {
                case "1" -> persistance.execute("SELECT ID,NAME FROM COMPANY");
                case "2" -> {
                    System.out.println("Enter the company name: ");
                    String name = scanner.nextLine();
                    persistance.insert(name);
                }
                case "0" -> {
                    return;
                }
            }
        }
    }
}
