package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.base.impl.AreaServiceImpl;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:35:36 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends CommonAction<Area>{
    public AreaAction() {
        super(Area.class);  
        
    }

   
    @Autowired
    private AreaService areaService;
    //使用属性驱动获取用户上传的文件
    private File file;
    public void setFile(File file) {
        this.file = file;
    }
    @Action(value="areaAction_importXLS",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String importXLS(){
        //System.out.println(file.getAbsolutePath());
        try {
            HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
            List<Area>list=new ArrayList<>();
            for (Row row : sheetAt) {
                if(row.getRowNum()==0){
                    continue;
                }
              String province = row.getCell(1).getStringCellValue();
              String city = row.getCell(2).getStringCellValue();
              String district = row.getCell(3).getStringCellValue();
              String postcode = row.getCell(4).getStringCellValue();
               province = province.substring(0, province.length()-1);
               city = city.substring(0, city.length()-1);
               district = district.substring(0, district.length()-1);
               String citycode = PinYin4jUtils.hanziToPinyin(city,"").toUpperCase();
               String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
              String shortcode= PinYin4jUtils.stringArrayToString(headByString);
               Area area=new Area();
               area.setProvince(province);
               area.setCity(city);
               area.setDistrict(district);
               area.setCitycode(citycode);
               area.setShortcode(shortcode);
               area.setPostcode(postcode);
               //为了提高性能，采用集合的方式，而不是一条一条的添加
               list.add(area);
            }
            
            areaService.save(list);
            hssfWorkbook.close();
        } 
         catch (IOException e) {
            e.printStackTrace();  
            
        }
        return SUCCESS;
        
    }
    
   
    //Ajax请求，不需要跳转页面
    @Action(value="areaAction_pageQuery")
    public String pageQuery() throws IOException{
        // EasyUI的页码是从1开始的
        // SPringDataJPA的页码是从0开始的
        // 所以要-1
      Pageable pageable= new PageRequest(page-1, rows);
      
      Page<Area> page=  areaService.findAll(pageable);
      JsonConfig jsonConfig=new JsonConfig();
      jsonConfig.setExcludes(new String[]{"subareas"});
      page2Json(page, jsonConfig);
     
        
        return NONE;
    }
    
    
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    
   // 1.  动态加载区域数据
    @Action(value="areaAction_findAll")
    public String findAll() throws IOException{
        List<Area> list;
        //2. 增强下拉框搜索功能
        if(StringUtils.isNoneEmpty(q)){
            //不为空，就根据用户输入的条件查询，进行模糊匹配
            list = areaService.findByQ(q);
        }else{
            //q为空，就查询所有
            Page<Area> page=  areaService.findAll(null);
            list = page.getContent();
        }
      
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        list2json(list,jsonConfig);
        return NONE;
        
    }

}
  
