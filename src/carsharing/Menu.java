package carsharing;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    Scanner scanner;
    CompanyDAOImpl companyDAOImpl;
    CarDAOImpl carDAOImpl;

    public Menu(String[] args) throws SQLException {
        this.scanner = new Scanner(System.in);
        this.companyDAOImpl = new CompanyDAOImpl(args);
        this.carDAOImpl = new CarDAOImpl(companyDAOImpl.persistance);

    }

    public void mainMenu() throws SQLException {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            String choice1 = scanner.nextLine();
            switch (choice1) {
                case "1" -> companyMenu(choice1);
                case "0" -> {
                    return;
                }
            }
        }
    }
    
    public void companyMenu(String choice) throws SQLException {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            String choice2 = scanner.nextLine();
            switch (choice2) {
                case "1" -> {
                    System.out.println("Choose a company: ");
                    if (companyDAOImpl.getAllCompanies() == null){
                        continue;
                    }
                    for (Company company : companyDAOImpl.getAllCompanies()) {

                        System.out.print(companyDAOImpl.companies.indexOf(company)+1 + ". ");
                        System.out.println(company.getName());
                    }
                    System.out.println("0. Back");
                    int index = Integer.parseInt(scanner.nextLine());
                    if (index == 0) {
                        continue;
                    }
                    Company company = companyDAOImpl.getAllCompanies().get(index-1);
                    carMenu(company.getId());
                }
                case "2" -> {
                    System.out.println("Enter the company name: ");
                    String name = scanner.nextLine();
                    companyDAOImpl.insertCompany(name);
                    System.out.println("The company was created!");
                    System.out.println();
                }
                case "0" -> {
                    return;
                }
            }
        }
    }

    public void carMenu(long index) throws SQLException {
        while (true) {
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            String choice3 = scanner.nextLine();
            switch (choice3) {
                case "1" -> {
                    for (Car car : carDAOImpl.getAllCars(index)) {
                        System.out.print(carDAOImpl.cars.indexOf(car)+1 + ". ");
                        System.out.println(car.getName());
                    };
                }
                case "2" -> {
                    System.out.println("Enter the car name: ");
                    String name = scanner.nextLine();
                    carDAOImpl.insertCar(name, index);
                    System.out.println("The car was added!");
                    System.out.println();
                }
                case "0" -> {
                    return;
                }
            }
            System.out.println();

        }

    }
}
