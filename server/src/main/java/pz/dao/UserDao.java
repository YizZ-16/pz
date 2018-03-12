package pz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pz.model.UserModel;

public interface UserDao
        extends JpaRepository<UserModel, String>{
}
