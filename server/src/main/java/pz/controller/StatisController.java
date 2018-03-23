package pz.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pz.config.ResponseCodeEnum;
import pz.service.PeiZaiService;
import pz.service.ResponseService;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/statis")
public class StatisController {

    @Autowired
    private PeiZaiService peiZaiService;

    @Autowired
    private ResponseService responseService;

//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public HashMap<String, Object> statisAll() {
//        List<HashMap<String, String>> lm = peiZaiService.findAll();
//        HashMap<String, HashMap<String, Object>> map = parseRecord(lm);
//        return responseService.success(map);
//    }
//
//
//    private static HashMap<String, HashMap<String, Object>> parseRecord(
//            List<HashMap<String, String>> lm
//    ) {
//        HashMap<String, HashMap<String, Object>> map = new HashMap<>();
//        for (HashMap<String, String> m : lm) {
//            String airline = m.get("PLANE_AIRLINES");
//            if (map.containsKey(airline)) {
//                HashMap<String, Object> h = map.get(airline);
//                h.put("PLANE_RECORD", (int)h.get("PLANE_RECORD")+1);
//                h.put("PLANE_REG", (int)h.get("PLANE_REG")+1);
//                h.put("PLANE_TYPE", (int)h.get("PLANE_TYPE")+1);
//                map.put(airline,h);
//            }else {
//                HashMap<String,Object> temp = new HashMap<>();
//                temp.put("PLANE_RECORD", 1);
//                temp.put("PLANE_REG", 1);
//                temp.put("PLANE_TYPE", 1);
//                map.put(airline,temp);
//            }
//        }
//        return map;
//    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public HashMap<String, Object> statisAll() {
        ArrayList<HashMap<String, Object>> lh = new ArrayList<>();
        List<HashMap<String, String>> lm = peiZaiService.findAll();
        try {
            lh = parseStatis(lm);
        }catch (Exception e){
            e.printStackTrace();
            return responseService.fail(ResponseCodeEnum.UNKNOWN_ERROR, "statistics error");
        }

        return  responseService.success(lh);
    }

    @RequestMapping(value = "/mod-date", method = RequestMethod.POST)
    public HashMap<String, Object> statisAllByModDate(
            @RequestBody String date
    ) {
        try {
            JSONObject dateJson = new JSONObject(date);
//            String s = dateJson.getString("START_DATE");
//            String e = dateJson.getString("END_DATE");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date startDate = sdf.parse(s);
//            Date endDate = sdf.parse(e);
            Long s = dateJson.getLong("START_DATE");
            Long e = dateJson.getLong("END_DATE");
            List<HashMap<String, String>> lm =
                    peiZaiService.findAllByModDate(new Date(s), new Date(e));
            ArrayList<HashMap<String, Object>> lh = parseStatis(lm);
            return responseService.success(lh);
        }catch (Exception e){
            e.printStackTrace();
            return responseService.fail(ResponseCodeEnum.UNKNOWN_ERROR, "");
        }
    }

    private static Integer checkAirlines(
            List<HashMap<String, Object>> lh,  String airline) {
        Integer index = null;
        for (int i=0; i<lh.size(); i++) {
            if (airline.equals(lh.get(i).get("PLANE_AIRLINES"))){
               index = i;
               break;
            }
        }
        return index;
    }

    private static boolean checkReg(List<HashMap<String,String>>list, String reg) {
        boolean flag = false;
        for (HashMap<String,String> m : list) {
            if (reg.equals(m.get("PLANE_REG"))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private static Integer caculType(ArrayList<HashMap<String, String>> al) {
        Set<String> typeSet = new HashSet<>();
        for (HashMap<String, String> h : al) {
            typeSet.add(h.get("PLANE_TYPE"));
        }
        return typeSet.size();
    }

    private static  ArrayList<HashMap<String, Object>> parseStatis(
            List<HashMap<String, String>> lm) throws Exception{
        ArrayList<HashMap<String, Object>> lh = new ArrayList<>();
        for (HashMap<String, String> m: lm) {
            Integer index = checkAirlines(lh, m.get("PLANE_AIRLINES"));
            if (index != null) {
                lh.get(index).put("PLANE_RECORDS", (int)lh.get(index).get("PLANE_RECORDS")+1);
                ArrayList<HashMap<String, String>> list =
                        (ArrayList<HashMap<String, String>>)lh.get(index).get("REG");
                boolean flag = checkReg(list, m.get("PLANE_REG"));
                if (!flag) {
                    HashMap<String, String> temp = new HashMap<>();
                    temp.put("PLANE_REG",m.get("PLANE_REG"));
                    temp.put("PLANE_TYPE", m.get("PLANE_TYPE"));
                    ((ArrayList<HashMap<String,String>>) lh.get(index).get("REG"))
                            .add(temp);
                }
            }else {
                HashMap<String, Object> temp = new HashMap<>();
                temp.put("PLANE_AIRLINES", m.get("PLANE_AIRLINES"));
                temp.put("PLANE_RECORDS", 1);
                List<HashMap<String,String>> list = new ArrayList<>();
                HashMap<String, String> tempMap = new HashMap<>();
                tempMap.put("PLANE_REG", m.get("PLANE_REG"));
                tempMap.put("PLANE_TYPE", m.get("PLANE_TYPE"));
                list.add(tempMap);
                temp.put("REG", list);
                lh.add(temp);
            }
        }

        for (int i=0; i<lh.size(); i++) {
            HashMap<String, Object> hm = lh.get(i);
            ArrayList<HashMap<String, String>> al =
                    ( ArrayList<HashMap<String, String>>) hm.get("REG");
            hm.put("PLANE_REG_NUM",al.size());
            hm.put("PLANE_TYPE_NUM",caculType(al));
        }
        return lh;
    }


    /*
    **
    */
    @RequestMapping(value = "/airlines", method = RequestMethod.POST)
    public HashMap<String, Object> statisAirlines (
            @RequestBody String airlines
    ){
        JSONObject jsonObject = new JSONObject(airlines);
        String air = jsonObject.getString("PLANE_AIRLINES");
        ArrayList<HashMap<String, Object>> list = peiZaiService.findAllListByAirlines(air);
        return responseService.success(list);
    }


}

