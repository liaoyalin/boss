package com.itheima.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:12:45 <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
          
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersUnAssocaited() {
          
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findCustomersAssocaited(String fixedAreaId) {
          
         return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void assignCustomers2FixedArea(Long[] customerIds, String fixedAreaId) {
      //根据定区id把关联到这个定区的所有客户全部解绑
        if(StringUtils.isNoneEmpty(fixedAreaId)){
            customerRepository.unbindCustomerByFixedArea(fixedAreaId);
        }
        //要关联的数据和定区id进行绑定
        
        if(customerIds!=null && fixedAreaId.length()>0){
            for (Long customerId : customerIds) {
                customerRepository.bindCustomer2FixedArea(customerId,fixedAreaId);
            }
        }
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
        
    }

    @Override
    public void active(String telephone) {
        customerRepository.active(telephone);
        
    }

    @Override
    public Customer isActived(String telephone) {
          
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public Customer login(String telephone, String password) {
          
        return customerRepository.findByTelephoneAndPassword(telephone, password);
    }

    @Override
    public String findFixedAreaByAddress(String address) {
          
        return customerRepository.findFixedAreaByAddress(address);
    }

}
  
