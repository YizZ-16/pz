package pz.controller;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pz.service.ResponseService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/login")
@Slf4j
@CrossOrigin
public class LoginController {

    @Autowired
    private ResponseService responseService;

    //简单的后台接口，用于测试
    @RequestMapping(value = "/info")
    public Object info(){
        Map<String,Object> map = new HashMap<>();
        map.put("info","hello hello hello");
        return map;
    }

    @RequestMapping(method = RequestMethod.POST)
    public HashMap<String, Object>login(
            @RequestBody String user
    ) {
        JSONObject jsonObject = new JSONObject(user);
        UsernamePasswordToken token = new UsernamePasswordToken(
                jsonObject.getString("ACCOUNT"),
                jsonObject.getString("PASSWORD")
        );
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        return responseService.success("login success");
    }
}
