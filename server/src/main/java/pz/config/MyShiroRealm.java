package pz.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import pz.model.UserModel;
import pz.service.UserService;


@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo =
                new SimpleAuthorizationInfo();

        UserModel user = (UserModel) principalCollection.getPrimaryPrincipal();

        String type = user.getType();
        if ("administrator".equals(type)) {
            simpleAuthorizationInfo.addRole("admin");
        }else {
            simpleAuthorizationInfo.addRole("user");
        }
        return  simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token
    ) {
        String account = (String)token.getPrincipal();
        log.info(account);
        UserModel user = userService.findOne(account);
        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(
                        user,
                        user.getPassword(),
                        ByteSource.Util.bytes("salt"),
                        getName());
        return authenticationInfo;
    }

}
