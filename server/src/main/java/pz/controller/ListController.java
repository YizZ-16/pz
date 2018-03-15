package pz.controller;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pz.service.PeiZaiService;
import pz.service.ResponseService;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/list")
@CrossOrigin
@Slf4j
public class ListController {

    private PeiZaiService peiZaiService;
    @Autowired
    public void setPeiZaiService(PeiZaiService peiZaiService) {
        this.peiZaiService = peiZaiService;
    }

    @Autowired
    private ResponseService responseService;

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public HashMap<String, Object> findAll(
            @RequestBody String jsonString
    ) {
        JSONObject json = new JSONObject(jsonString);
        Integer pageIndex = json.getInt("PAGE_INDEX");
        Integer pageSize = json.getInt("PAGE_SIZE");
        HashMap<String, Object> map = null;
        map = peiZaiService.findAll(pageIndex, pageSize);
        return responseService.success(map);
    }

    @RequestMapping(value = "/airlines", method = RequestMethod.POST)
    public HashMap<String, Object> findAllByAirlines(
            @RequestBody String jsonString
    ){

        JSONObject json = new JSONObject(jsonString);
        String airlines = json.getString("PLANE_AIRLINES");
        Integer pageIndex = json.getInt("PAGE_INDEX");
        Integer pageSize = json.getInt("PAGE_SIZE");
        HashMap<String, Object> map = null;
        map = peiZaiService.findAllByAirlines(airlines, pageIndex, pageSize);
        return responseService.success(map);
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public HashMap<String, Object> findAllByReg(
            @RequestBody String jsonString
    ){

        JSONObject json = new JSONObject(jsonString);
        String reg = json.getString("PLANE_REG");
        Integer pageIndex = json.getInt("PAGE_INDEX");
        Integer pageSize = json.getInt("PAGE_SIZE");
        HashMap<String, Object> map = null;
        map = peiZaiService.findAllByReg(reg, pageIndex, pageSize);
        return responseService.success(map);
    }

    @RequestMapping(value = "/airlines-reg", method = RequestMethod.POST)
    public HashMap<String, Object> findAllByAirlinesAndReg(
            @RequestBody String jsonString
    ){

        JSONObject json = new JSONObject(jsonString);
        String airlines = json.getString("PLANE_AIRLINES");
        String reg = json.getString("PLANE_REG");
        Integer pageIndex = json.getInt("PAGE_INDEX");
        Integer pageSize = json.getInt("PAGE_SIZE");
        HashMap<String, Object> map = null;
        map = peiZaiService.findAllByAirlinesAndReg(airlines, reg, pageIndex, pageSize);
        return responseService.success(map);
    }


}
