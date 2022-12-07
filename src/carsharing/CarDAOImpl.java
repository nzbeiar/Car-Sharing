package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO{

    Persistance persistance;
    List<Car> cars;

    public CarDAOImpl(Persistance persistance){
        this.persistance = persistance;
    }

    @Override
    public List<Car> getAllCars(long index) throws SQLException {
        this.cars = new ArrayList<>();
        ResultSet resultSet = persistance.execute("SELECT * FROM CAR WHERE COMPANY_ID = %s".formatted(String.valueOf(index)));
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
            System.out.println("The car list is empty!");
        }

        return cars;
    }

    @Override
    public void insertCar(String name, long index) throws SQLException {
        persistance.insertCar(name, index);
    }

}
