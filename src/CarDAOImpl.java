import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl extends CarDAO{

    public CarDAOImpl(String[] args) {
        super(args);
    }

    @Override
    public List<Car> getAllCars(long index) throws SQLException {
        List<Car> cars = new ArrayList<>();
        try (ResultSet resultSet = persistance.select("SELECT * FROM CAR WHERE COMPANY_ID = %s".formatted(String.valueOf(index)))) {
            boolean empty = true;
            while(resultSet.next()) {
                empty = false;
                Car car = new Car();
                car.setId(resultSet.getLong(1));
                car.setName(resultSet.getString(2));
                car.setCompanyID(resultSet.getLong(3));
                cars.add(car);
            }
            if(empty) {
                return null;
            }

            return cars;
        }

    }

    @Override
    public void insertCar(String name, long index) throws SQLException {
        persistance.insertCar(name, index);
    }

}
