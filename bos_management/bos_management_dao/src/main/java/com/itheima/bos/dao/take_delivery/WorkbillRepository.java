package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;

/**  
 * ClassName:WorkbillRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月24日 上午11:13:32 <br/>       
 */
public interface WorkbillRepository extends JpaRepository<WorkBill, Long> {

}
  
