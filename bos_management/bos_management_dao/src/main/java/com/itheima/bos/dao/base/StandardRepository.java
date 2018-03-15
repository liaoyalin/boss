package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午8:45:29 <br/>       
 */
//泛型1：封装数据的对象的类型
//泛型2：对象的主键的类型
public interface StandardRepository extends JpaRepository<Standard, Long> {
    
    Standard findByName(String name);
    //查询语句：JPQL==HQL
    @Query("from Standard where name=? and maxWeight=?")
    List<Standard> findByNameAndMaxWeight111(String name,Integer maxWeight);
    //原生sql
    @Query(value="select * from  T_STANDARD where C_NAME=? and C_MAX_WEIGHT=?",nativeQuery=true)
    List<Standard> findByNameAndMaxWeight33342343(String name,Integer maxWeight);
    
    @Modifying
    @Transactional
    @Query("update Standard set maxWeight=? where name=?")
    void updateWeightByName(Integer maxWeight,String name);

}
  
