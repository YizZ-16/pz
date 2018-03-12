package pz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pz.dao.UserDao;
import pz.model.UserModel;

@Service
public class UserService {

    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean addOne(UserModel userModel) {
        return userDao.save(userModel) != null;
    }

    public UserModel findOne(String account) {
        return userDao.findOne(account);
    }
}
