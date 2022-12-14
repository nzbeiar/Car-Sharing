import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl extends CustomerDAO {

    public CustomerDAOImpl(String[] args) {
        super(args);
    }

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        try (ResultSet resultSet = persistance.select("SELECT * FROM CUSTOMER")) {
            boolean empty = true;
            while (resultSet.next()) {
                empty = false;
                Customer customer = new Customer();
                customer.setId(resultSet.getLong(1));
                customer.setName(resultSet.getString(2));
                customer.setRentedCarId(resultSet.getLong(3));
                customers.add(customer);
            }
            if (empty) {
                System.out.println("The customer list is empty!");
                return null;
            }

            return customers;
        }
    }

    @Override
    public void insertCustomer(String customer) throws SQLException {
        persistance.insertBasic("customer",customer);
    }

    @Override
    public void rentCar(long idCustomer, Long idCar) throws SQLException {
        persistance.rentCar(idCustomer, idCar);
    }

    public void unrentCar(long idCustomer) throws SQLException {
        persistance.unrentCar(idCustomer);
    }


    @Override
    public String getCar(long idCar) throws SQLException {
        String result = "Your rented car: \n";
        String companyName = "";
        try (ResultSet resultSet = persistance.select("SELECT * FROM CAR WHERE ID = %d".formatted(idCar))) {
            boolean empty = true;
            while (resultSet.next()) {
                empty = false;
                Car car = new Car();
                car.setName(resultSet.getString(2));
                car.setCompanyID(resultSet.getLong(3));
                long companyId = car.getCompanyID();
                result += car.getName();
                try (ResultSet companyResult = persistance.select("SELECT * FROM COMPANY WHERE ID = %d".formatted(companyId))) {
                    while(companyResult.next()) {
                        companyName = "Company: \n" + companyResult.getString(2);
                        result += "\n" + companyName;
                    }
                }
            }
            if (empty) {
                return null;
            }
        }
        return result;

    }
}
