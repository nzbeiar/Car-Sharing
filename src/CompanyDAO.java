
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class CompanyDAO extends DAO {
    public CompanyDAO(String[] persistance) {
        super(persistance);
    }

    public abstract List<Company> getAllCompanies() throws SQLException;
    public abstract void insertCompany(String company) throws SQLException;
}