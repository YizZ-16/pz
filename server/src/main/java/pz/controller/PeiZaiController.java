package pz.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pz.config.ResponseCodeEnum;
import pz.model.PeiZaiModel;
import pz.service.PeiZaiService;
import pz.service.ResponseService;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private ResponseService responseService;

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public HashMap<String, Object> editOne(
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
            model.setMtow(jsonObject.getString("PLANE_MTOW"));
            if (jsonObject.isNull("PLANE_MZDW") ||
                    "".equals(jsonObject.get("PLANE_MZDW"))) {
                model.setMzdw(null);
            }else{
                model.setMzdw(jsonObject.getInt("PLANE_MZDW"));
            }
            if (jsonObject.isNull("PLANE_MZFW") ||
                    "".equals(jsonObject.get("PLANE_MZFW"))) {
                model.setMzfw(null);
            }else {
                model.setMzfw(jsonObject.getInt("PLANE_MZFW"));
            }
//            String date = jsonObject.getString("PLANE_UPDATE_DATE");
            long time = System.currentTimeMillis();
            Date date1 = new Date(time);
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
            boolean flag = peiZaiService.editOne(model);
            return responseService.success(flag);
        }catch (Exception e){
            return responseService.fail(
                    ResponseCodeEnum.EDIT_ERROR, "edit error");
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public HashMap<String, Object> addOne(
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
            model.setMtow(jsonObject.getString("PLANE_MTOW"));
            if (jsonObject.isNull("PLANE_MZDW") ||
                    "".equals(jsonObject.get("PLANE_MZDW"))) {
                model.setMzdw(null);
            }else{
                model.setMzdw(jsonObject.getInt("PLANE_MZDW"));
            }
            if (jsonObject.isNull("PLANE_MZFW") ||
                    "".equals(jsonObject.get("PLANE_MZFW"))) {
                model.setMzfw(null);
            }else {
                model.setMzfw(jsonObject.getInt("PLANE_MZFW"));
            }
            //String updateStr = jsonObject.getString("PLANE_UPDATE_DATE");
            long time = System.currentTimeMillis();
            Date date1 = new Date(time);
            model.setUpdateDate(date1);
            java.util.Date date = new java.util.Date();
            model.setInsertDate(date);
            model.setModDate(date);
             boolean flag = peiZaiService.addOne(model);
             return responseService.success(flag);
        }catch (Exception e){
            e.printStackTrace();
            return responseService.fail(
                    ResponseCodeEnum.ADD_ERROR, "add error"
            );
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public HashMap<String, Object> deleteOne(
            @RequestBody String jsonString
    ) {
        JSONObject jsonObject = new JSONObject(jsonString);
        Integer id = jsonObject.getInt("PLANE_ID");
        boolean flag = peiZaiService.deleteOne(id);
        return responseService.success(flag);
    }

    @RequestMapping(value = "/recent", method = RequestMethod.GET)
    public HashMap<String, Object> findRecentEditRecord() {
        HashMap<String, String > map = peiZaiService.findRecentEditRecord();
        return responseService.success(map);
    }


}
