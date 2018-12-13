package com.example.springbootstage.service.system;


import com.example.springbootstage.dao.system.UserDao;
import com.example.springbootstage.entity.system.User;
import com.example.springbootstage.utils.Params;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * Created by WangHong on 2018/3/26.
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserDao userDao;

    @Transactional(readOnly = true)
    public User findByUsername(final String username) {
        return userDao.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public List<User> findByState(final int state) {
        return userDao.findByState(state);
    }

    @Transactional(readOnly = true)
    public User findById(final Long id) {
        Optional<User> user = userDao.findById(id);
        if (user.isPresent()) {
            if (user.get().getState() != 0) {
                throw new RuntimeException("用户不存在！");
            }
            return user.get();
        }
        return new User();
    }

    @Transactional(readOnly = true)
    public User findByUsernameAndIdNot(final String username, final Long id) {
        if (id != null) {
            return userDao.findByUsernameAndIdNot(username, id);
        }
        return userDao.findByUsername(username);
    }

    public void changeState(final Long id) {
        Optional<User> user = userDao.findById(id);
        if (user.isPresent()) {
            user.get().setState(1);
            this.save(user.get());
        } else {
            throw new RuntimeException("用户不存在！");
        }
    }

    public void save(User entity) {
        if (entity.getId() == null) {
            String password = this.createPassword(Params.PASSWORD, entity.getUsername(), entity.getSalt());
            entity.setPassword(password);
        }
        userDao.save(entity);
    }

    /**
     * 重置密码
     *
     * @param id
     */
    public void changePassword(final Long id) {
        User user = userDao.findById(id).get();
        user.setPassword(this.createPassword(Params.PASSWORD, user.getUsername(), user.getSalt()));
        userDao.save(user);
    }

    private String createPassword(String password, String username, String salt) {
        password = StringUtils.isBlank(password) ? Params.PASSWORD : password;
        ByteSource byteSource = ByteSource.Util.bytes(username + salt);
        String simpleHash = new SimpleHash("md5", password, byteSource, 2).toString();
        return simpleHash;
    }
}
