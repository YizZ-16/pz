package pz.controller;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pz.config.ResponseCodeEnum;
import pz.service.PeiZaiModService;
import pz.service.ResponseService;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/pzm")
public class PeiZaiModController {

    @Autowired
    private PeiZaiModService peiZaiModService;

    @Autowired
    private ResponseService responseService;

    @RequestMapping(value = "/plane-id", method = RequestMethod.POST)
    public HashMap<String, Object> findRecordsByPlaneId(
            @RequestBody String planeIdStr
    ) {
        JSONObject jsonObject = new JSONObject(planeIdStr);
        Integer planeId = jsonObject.getInt("PLANE_ID");
        ArrayList<HashMap<String, String>> al =
                peiZaiModService.findByPlaneId(planeId);
        if (null != al) {
            return responseService.success(al);
        }
        return responseService.fail(
                ResponseCodeEnum.UNKNOWN_ERROR, "");
    }
}
