

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO {

    Persistance persistance;

    public DAO(String[] args) {
        this.persistance = new Persistance(args);
        persistance.open();
    }


}
