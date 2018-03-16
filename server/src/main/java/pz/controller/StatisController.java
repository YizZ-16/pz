package pz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pz.service.PeiZaiService;
import pz.service.ResponseService;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/statis")
public class StatisController {

    @Autowired
    private PeiZaiService peiZaiService;

    @Autowired
    private ResponseService responseService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public HashMap<String, Object> statisAll() {
        List<HashMap<String, String>> lm = peiZaiService.findAll();
        HashMap<String, HashMap<String, Object>> map = parseRecord(lm);
        return responseService.success(map);
    }


    private static HashMap<String, HashMap<String, Object>> parseRecord(
            List<HashMap<String, String>> lm
    ) {
        HashMap<String, HashMap<String, Object>> map = new HashMap<>();
        for (HashMap<String, String> m : lm) {
            String airline = m.get("PLANE_AIRLINES");
            if (map.containsKey(airline)) {
                HashMap<String, Object> h = map.get(airline);
                h.put("PLANE_RECORD", (int)h.get("PLANE_RECORD")+1);
                h.put("PLANE_REG", (int)h.get("PLANE_REG")+1);
                h.put("PLANE_TYPE", (int)h.get("PLANE_TYPE")+1);
                map.put(airline,h);
            }else {
                HashMap<String,Object> temp = new HashMap<>();
                temp.put("PLANE_RECORD", 1);
                temp.put("PLANE_REG", 1);
                temp.put("PLANE_TYPE", 1);
                map.put(airline,temp);
            }
        }
        return map;
    }
}

