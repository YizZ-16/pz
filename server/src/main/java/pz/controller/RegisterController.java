package pz.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pz.config.ResponseCodeEnum;
import pz.model.UserModel;
import pz.service.ResponseService;
import pz.service.UserService;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/register")
public class RegisterController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private ResponseService responseService;
    @Autowired
    public void setResponseService(ResponseService responseService) {
        this.responseService = responseService;
    }


    @RequestMapping(method = RequestMethod.POST)
    public HashMap<String, Object> RegisterAction (
            @RequestBody String userInfo
    ) {
        JSONObject user = new JSONObject(userInfo);
        UserModel um = new UserModel();
        um.setAccount(user.getString("ACCOUNT"));
        um.setName(user.getString("NAME"));
        um.setAirport(user.getString("AIRPORT"));
        um.setPassword(user.getString("PASSWORD"));
        um.setType(user.getString("TYPE"));
        boolean flag = userService.addOne(um);
        if (flag) {
            return responseService.success("register success");
        }
        return responseService.fail(
                ResponseCodeEnum.REGISTER_ERROR, "register error");
    }
}
