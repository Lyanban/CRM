package com.lyanba.crm.workbench.dao;

import com.lyanba.crm.workbench.domain.Customer;

public interface CustomerDao {
    Customer getCustomerByName(String company);

    int save(Customer customer);
}
