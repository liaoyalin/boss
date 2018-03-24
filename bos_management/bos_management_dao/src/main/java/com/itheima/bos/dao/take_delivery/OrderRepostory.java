package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepostory <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:20:25 <br/>       
 */
public interface OrderRepostory extends JpaRepository<Order, Long>{

}
  
