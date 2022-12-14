
import java.sql.SQLException;
import java.util.List;

public abstract class CustomerDAO extends DAO {

    public CustomerDAO(String[] args) {
        super(args);
    }

    public abstract List<Customer> getAllCustomers() throws SQLException;

    public abstract void insertCustomer(String company) throws SQLException;

    public abstract void rentCar(long idCustomer, Long idCar) throws SQLException;
    public abstract void unrentCar(long idCustomer) throws SQLException;


    public abstract String getCar(long idCar) throws SQLException;
}
