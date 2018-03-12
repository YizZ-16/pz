package pz.service;

import org.springframework.stereotype.Service;
import pz.config.ResponseCodeEnum;

import java.io.Serializable;
import java.util.HashMap;

@Service
public class ResponseService {

    public HashMap<String, Object> success(Serializable data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("CODE", ResponseCodeEnum.SUCCESS.CODE);
        map.put("DATA", data);
        map.put("MESSAGE","");
        return map;
    }

    public HashMap<String, Object> fail(ResponseCodeEnum code, String message) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("CODE", code.CODE);
        map.put("DATA", "");
        map.put("MESSAGE", message);
        return map;
    }

}
