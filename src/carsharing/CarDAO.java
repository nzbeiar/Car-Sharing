package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CarDAO {
    public List<Car> getAllCars(long index) throws SQLException;
    public void insertCar(String name, long index) throws SQLException;
}
