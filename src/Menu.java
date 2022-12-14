

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner scanner;

    Persistance persistance;
    CompanyDAO companyDAOImpl;
    CarDAO carDAOImpl;
    CustomerDAO customerDAOImpl;

    public Menu(String[] args) throws SQLException {
        this.scanner = new Scanner(System.in);
        companyDAOImpl = new CompanyDAOImpl(args);
        carDAOImpl = new CarDAOImpl(args);
        customerDAOImpl = new CustomerDAOImpl(args);
        this.persistance = companyDAOImpl.persistance;


    }

    public void mainMenu() throws SQLException {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> companyMenu();
                case "2" -> customerMenu();
                case "3" -> createCustomer();
                case "0" -> {
                    return;
                }
            }
        }
    }

    public void createCustomer() throws SQLException {
        System.out.println("Enter the customer name: ");
        String name = scanner.nextLine();
        customerDAOImpl.insertCustomer(name);
        System.out.println("The customer was added!");
        System.out.println();
    }

    public void customerMenu() throws SQLException {
        while (true) {
            List<Customer> customers = customerDAOImpl.getAllCustomers();
            if (customers == null) {
               return;
            }
            System.out.println("Customer list: ");
            for (Customer customer : customers) {
                System.out.print(customers.indexOf(customer) + 1 + ". ");
                System.out.println(customer.getName());
            }
            System.out.println("0. Back");
            int index = Integer.parseInt(scanner.nextLine());
            if (index == 0) {
                return;
            }
            Customer customer = customers.get(index-1);
            rentCarMenu(customer);
        }

    }

    public void rentCarMenu(Customer customer) throws SQLException {
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented cars");
            System.out.println("0. Back");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    if (customer.getRentedCarId() != 0) {
                        System.out.println("You've already rented a car!");
                        break;
                    }
                    System.out.println("Choose a company: ");
                    List<Company> companies = companyDAOImpl.getAllCompanies();
                    if (companies == null){
                        continue;
                    }
                    for (Company company : companies) {
                        System.out.print(companies.indexOf(company)+1 + ". ");
                        System.out.println(company.getName());
                    }
                    System.out.println("0. Back");
                    int companyIndex = Integer.parseInt(scanner.nextLine());
                    if (companyIndex == 0) {
                        return;
                    }
                    Company company = companies.get(companyIndex-1);
                    List<Car> companyCars = carDAOImpl.getAllCars(company.getId());
                    if (companyCars == null) {
                        System.out.printf("No available cars in the %s company\n", company.getName());
                        continue;
                    }
                    System.out.println("Choose a car: ");
                    for (Car car : companyCars) {
                        System.out.print(companyCars.indexOf(car)+1 + ". ");
                        System.out.println(car.getName());
                    }
                    System.out.println("0. Back");
                    int carIndex = Integer.parseInt(scanner.nextLine());
                    if (carIndex == 0) {
                        return;
                    }
                    Car carToRent = companyCars.get(carIndex-1);
                    customerDAOImpl.rentCar(customer.getId(),carToRent.getId());
                    customer.setRentedCarId(carToRent.getId());
                    System.out.printf("You rented '%s'", carToRent.getName());

                }
                case "2" -> {
                    String result = customerDAOImpl.getCar(customer.getRentedCarId());
                    if (result == null) {
                        System.out.println("You didn't rent a car!");
                    } else {
                        customerDAOImpl.unrentCar(customer.getId());
                        customer.setRentedCarId(null);
                        System.out.println("You've returned a rented car!");
                    }
                }
                case "3" -> {
                    if (customer.getRentedCarId() == null) {
                        System.out.println("You didn't rent a car!");
                        break;
                    }
                    String result = customerDAOImpl.getCar(customer.getRentedCarId());
                    if (result == null) {
                        System.out.println("You didn't rent a car!");
                    } else {
                        System.out.println(result);
                    }
                }

                default -> {
                    return;
                }
            }
            System.out.println();

        }
    }


//        while (true) {
//            System.out.println("1. Company list");
//            System.out.println("2. Create a company");
//            System.out.println("0. Back");
//            String choice2 = scanner.nextLine();
//            switch (choice2) {
//                case "1" -> {
//                    System.out.println("Choose a company: ");
//                    List<Company> companies = companyDAOImpl.getAllCompanies();
//                    if (companies == null){
//                        continue;
//                    }
//                    for (Company company : companies) {
//                        System.out.print(companies.indexOf(company)+1 + ". ");
//                        System.out.println(company.getName());
//                    }
//                    System.out.println("0. Back");
//                    int index = Integer.parseInt(scanner.nextLine());
//                    if (index == 0) {
//                        continue;
//                    }
//                    Company company = companies.get(index-1);
//                    carMenu(company.getId());
//                }
//                case "2" -> {
//                    System.out.println("Enter the company name: ");
//                    String name = scanner.nextLine();
//                    companyDAOImpl.insertCompany(name);
//                    System.out.println("The company was created!");
//                    System.out.println();
//                }
//                case "0" -> {
//                    return;
//                }
//            }

    public void chooseCompanyWorkflow(){

    }
    public void companyMenu() throws SQLException {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.println("Choose a company: ");
                    List<Company> companies = companyDAOImpl.getAllCompanies();
                    if (companies == null){
                        continue;
                    }
                    for (Company company : companies) {
                        System.out.print(companies.indexOf(company)+1 + ". ");
                        System.out.println(company.getName());
                    }
                    System.out.println("0. Back");
                    int index = Integer.parseInt(scanner.nextLine());
                    if (index == 0) {
                        continue;
                    }
                    Company company = companies.get(index-1);
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
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> {
                    List<Car> cars = carDAOImpl.getAllCars(index);
                    if (cars == null) {
                        System.out.println("The car list is empty!");
                        break;
                    }
                    for (Car car : cars) {
                        System.out.print(cars.indexOf(car)+1 + ". ");
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
