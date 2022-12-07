package carsharing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    Persistance persistance;
    List<Company> companies;

    public CompanyDAOImpl(String[] args){
        this.persistance = new Persistance(args);
        this.persistance.connect();
    }


    @Override
    public List<Company> getAllCompanies() throws SQLException {
        this.companies = new ArrayList<>();
        ResultSet resultSet = persistance.execute("SELECT * FROM COMPANY");
        boolean empty = true;
        while(resultSet.next()) {
            empty = false;
            Company company = new Company();
            company.setId(resultSet.getLong(1));
            company.setName(resultSet.getString(2));
            companies.add(company);
        }
        if(empty) {
            System.out.println("The company list is empty!");
            return null;
        }

        return companies;
    }

    @Override
    public void insertCompany(String company) throws SQLException {
        persistance.insert(company);
    }
}