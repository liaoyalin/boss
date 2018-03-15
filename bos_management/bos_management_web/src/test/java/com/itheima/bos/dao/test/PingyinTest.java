package com.itheima.bos.dao.test;

import com.itheima.utils.PinYin4jUtils;

/**  
 * ClassName:PingyinTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:02:57 <br/>       
 */
public class PingyinTest {

    public static void main(String[] args) {
        String province="湖北省";
        String city="天门市";
        String discrict="佛祖山镇";
        //城市编码：TIANMEN  简码：HBTMFZS
        province = province.substring(0, province.length()-1);
        city = city.substring(0, city.length()-1);
        discrict = discrict.substring(0, discrict.length()-1);
        //String pinyin = PinYin4jUtils.hanziToPinyin(province,"").toUpperCase();
       //城市编码 HUBEI
      //  System.out.println(pinyin);
        
        String[] headByString = PinYin4jUtils.getHeadByString(province+city+discrict);
        String toString = PinYin4jUtils.stringArrayToString(headByString);
        System.out.println(toString);


    }

}
  
