package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:12:24 <br/>       
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerService {
    @GET
    @Path("/findAll")
    List<Customer> findAll();
   
    
    //查询未关联定区的客户
    @GET
    @Path("/findCustomersUnAssocaited")
    List<Customer> findCustomersUnAssocaited();
        
  //查询已关联到指定定区的客户
    @GET
    @Path("/findCustomersAssocaited")
    List<Customer> findCustomersAssocaited(@QueryParam("fixedAreaId") String fixedAreaId);

    //定区id和要关联的数据（右边的）
    //根据定区id把关联到这个定区的所有客户全部解绑
    //要关联的数据和定区id进行绑定
    @PUT
    @Path("/assignCustomers2FixedArea")
    void assignCustomers2FixedArea(@QueryParam("customerIds")Long[] customerIds, @QueryParam("fixedAreaId")String fixedAreaId);
    
    //注册用户（保存）
    @POST
    @Path("/save")
    void save(Customer customer);
    
    //激活
    @PUT
    @Path("/active")
    void active(@QueryParam("telephone")String telephone);
    //检查用户是否激活
    @GET
    @Path("/isActived")
    Customer isActived(@QueryParam("telephone") String telephone);
    //登录
    @GET
    @Path("/login")
    Customer login(@QueryParam("telephone") String telephone,@QueryParam("password") String password);
    
    
    //根据地址查询定区id
    @GET
    @Path("/findFixedAreaByAddress")
    String findFixedAreaByAddress(@QueryParam("address")String address);
}

   

  
