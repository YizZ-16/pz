package pz.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pz.service.ExcelService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/app")
@CrossOrigin
public class AppController {
    private ExcelService excelService;
    @Autowired
    public void setExcelService(ExcelService e) {
        this.excelService = e;
    }
    //简单的后台接口，用于测试
    @RequestMapping(value = "/info")
    public Object info(){
        Map<String,Object> map = new HashMap<>();
        map.put("info","hello hello hello");
        return map;
    }

    @RequestMapping(value = "/excel")
    public void parseExcel() {
        excelService.parseExcel();
    }
}
