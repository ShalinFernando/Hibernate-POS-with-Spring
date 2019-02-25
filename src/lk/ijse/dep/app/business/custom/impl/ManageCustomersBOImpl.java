package lk.ijse.dep.app.business.custom.impl;

import lk.ijse.dep.app.business.Converter;
import lk.ijse.dep.app.business.custom.ManageCustomersBO;
import lk.ijse.dep.app.dao.custom.CustomerDAO;
import lk.ijse.dep.app.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class ManageCustomersBOImpl implements ManageCustomersBO {

    private CustomerDAO customerDAO;

    @Autowired
    public ManageCustomersBOImpl(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public List<CustomerDTO> getCustomers() throws Exception {
        return customerDAO.findAll().map(Converter::<CustomerDTO>getDTOList).get();

    }

    public void createCustomer(CustomerDTO dto) throws Exception {
        customerDAO.save(Converter.getEntity(dto));
    }

    public void updateCustomer(CustomerDTO dto) throws Exception {
        customerDAO.update(Converter.getEntity(dto));
    }

    public void deleteCustomer(String customerID) throws Exception {
        customerDAO.delete(customerID);
    }

    public CustomerDTO findCustomer(String id) throws Exception {
        return customerDAO.find(id).map(Converter::<CustomerDTO>getDTO).orElse(null);
    }

}
