package pz.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pz.model.PeiZaiModel;
import pz.service.PeiZaiService;

import java.sql.Date;

@RestController
@RequestMapping(value = "/pz")
@CrossOrigin
@Slf4j
public class PeiZaiController {

    private PeiZaiService peiZaiService;
    @Autowired
    public void setPeiZaiService(PeiZaiService peiZaiService) {
        this.peiZaiService = peiZaiService;
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public boolean editOne(
            @RequestBody String jsonString
    ) {
        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            PeiZaiModel model = new PeiZaiModel();
            model.setId(jsonObject.getInt("PLANE_ID"));
            model.setAirlines(jsonObject.getString("PLANE_AIRLINES"));
            model.setReg(jsonObject.getString("PLANE_REG"));
            model.setType(jsonObject.getString("PLANE_TYPE"));
            model.setBuju(jsonObject.getString("PLANE_BUJU"));
            model.setCnd(jsonObject.getString("PLANE_CND"));
            model.setBoi(jsonObject.getString("PLANE_BOI"));
            model.setBow(jsonObject.getString("PLANE_BOW"));
            model.setMtow(jsonObject.getInt("PLANE_MTOW"));
            model.setMzdw(jsonObject.getInt("PLANE_MZDW"));
            model.setMzfw(jsonObject.getInt("PLANE_MZFW"));
            String date = jsonObject.getString("PLANE_UPDATE_DATE");
            Date date1 = Date.valueOf(date);
            model.setUpdateDate(date1);
            Long inDateLon = jsonObject.getLong("PLANE_INSERT_DATE");
            java.util.Date inDate = new java.util.Date();
            inDate.setTime(inDateLon);
            model.setInsertDate(inDate);
           // Long modDateLon = jsonObject.getLong("PLANE_MOD_DATE");
            java.util.Date mDate = new java.util.Date();
            log.error(mDate.toString());
            //mDate.setTime(modDateLon);
            model.setModDate(mDate);
            return peiZaiService.editOne(model);
        }catch (Exception e){
            return false;
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addOne(
            @RequestBody String jsonString
    ) {
        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            PeiZaiModel model = new PeiZaiModel();
            model.setAirlines(jsonObject.getString("PLANE_AIRLINES"));
            model.setReg(jsonObject.getString("PLANE_REG"));
            model.setType(jsonObject.getString("PLANE_TYPE"));
            model.setBuju(jsonObject.getString("PLANE_BUJU"));
            model.setCnd(jsonObject.getString("PLANE_CND"));
            model.setBoi(jsonObject.getString("PLANE_BOI"));
            model.setBow(jsonObject.getString("PLANE_BOW"));
            model.setMtow(jsonObject.getInt("PLANE_MTOW"));
            model.setMzdw(jsonObject.getInt("PLANE_MZDW"));
            model.setMzfw(jsonObject.getInt("PLANE_MZFW"));
            String updateStr = jsonObject.getString("PLANE_UPDATE_DATE");
            Date date1 = Date.valueOf(updateStr);
            model.setUpdateDate(date1);
            java.util.Date date = new java.util.Date();
            model.setInsertDate(date);
            model.setModDate(date);
            return peiZaiService.addOne(model);
        }catch (Exception e){
            return false;
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public boolean deleteOne(
            @RequestBody String jsonString
    ) {
        JSONObject jsonObject = new JSONObject(jsonString);
        Integer id = jsonObject.getInt("PLANE_ID");
        return peiZaiService.deleteOne(id);
    }


}
