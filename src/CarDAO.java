

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class CarDAO extends DAO {

    public CarDAO(String[] args) {
        super(args);
    }

    public abstract List<Car> getAllCars(long index) throws SQLException;
    public abstract void insertCar(String name, long index) throws SQLException;
}
