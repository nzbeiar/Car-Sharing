package carsharing;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO {
    public List<Company> getAllCompanies() throws SQLException;
    public void insertCompany(String company) throws SQLException;
}