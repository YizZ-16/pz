package pz.controller;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pz.config.ResponseCodeEnum;
import pz.model.UserModel;
import pz.service.ResponseService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public HashMap<String, Object>login(
            @RequestBody String user
    ) {
        JSONObject jsonObject = new JSONObject(user);
        UsernamePasswordToken token = new UsernamePasswordToken(
                jsonObject.getString("ACCOUNT"),
                jsonObject.getString("PASSWORD")
        );
        Subject subject = SecurityUtils.getSubject();
        token.setRememberMe(true);
        subject.login(token);
        Session session = subject.getSession();
        UserModel loginUser = (UserModel) subject.getPrincipal();
        return responseService.success(loginUser.getMap());
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public HashMap<String, Object> logout() {
        try {
            //退出
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            responseService.fail(ResponseCodeEnum.LOGOUT_ERROR, "logout failed");
            System.err.println(e.getMessage());
        }
        return responseService.success("logout success");
    }
}
