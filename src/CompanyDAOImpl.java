import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl extends CompanyDAO {

    public CompanyDAOImpl(String[] args) {
        super(args);
    }

    @Override
    public List<Company> getAllCompanies() throws SQLException {
        List<Company> companies = new ArrayList<>();
        try (ResultSet resultSet = persistance.select("SELECT * FROM COMPANY")) {
            boolean empty = true;
            while (resultSet.next()) {
                empty = false;
                Company company = new Company();
                company.setId(resultSet.getLong(1));
                company.setName(resultSet.getString(2));
                companies.add(company);
            }
            if (empty) {
                System.out.println("The company list is empty!");
                return null;
            }

            return companies;
        }
    }

    @Override
    public void insertCompany(String company) throws SQLException {
        persistance.insertBasic("company",company);
    }
}
