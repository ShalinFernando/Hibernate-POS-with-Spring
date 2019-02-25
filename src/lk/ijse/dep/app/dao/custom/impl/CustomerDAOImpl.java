package lk.ijse.dep.app.dao.custom.impl;

import lk.ijse.dep.app.dao.CrudDAOImpl;
import lk.ijse.dep.app.dao.custom.CustomerDAO;
import lk.ijse.dep.app.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAOImpl extends CrudDAOImpl<Customer, String> implements CustomerDAO {

}
