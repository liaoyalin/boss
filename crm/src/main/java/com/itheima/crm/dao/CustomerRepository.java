package com.itheima.crm.dao;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:11:36 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    //查询未关联定区的客户
    List<Customer> findByFixedAreaIdIsNull();
  //查询已关联定区的客户
    List<Customer> findByFixedAreaId(String fixedAreaId);
    
    //把关联定区的客户解绑
    @Query("update Customer set fixedAreaId=null where fixedAreaId=?")
    @Modifying
    void unbindCustomerByFixedArea(String fixedAreaId);
    //把客户绑定到指定的定区
    @Query("update Customer set fixedAreaId=?2 where id=?1")
    @Modifying
    void bindCustomer2FixedArea(Long customerId, String fixedAreaId);
    
    //激活
    @Query("update Customer set type=1 where telephone=?")
    @Modifying
    void active(String telephone);
    
    //是否激活
    Customer findByTelephone(String telephone);
    
    //登录
    Customer findByTelephoneAndPassword(String telephone,String password);
    
    //根据地址查询定区id
    @Query("select fixedAreaId from Customer where address=?")
    String findFixedAreaByAddress(String address);

}
  
