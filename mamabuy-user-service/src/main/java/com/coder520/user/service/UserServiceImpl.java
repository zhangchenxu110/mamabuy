package com.coder520.user.service;

import com.coder520.common.constants.Constants;
import com.coder520.common.exception.MamaBuyException;
import com.coder520.common.utils.ZkClient;
import com.coder520.user.dao.UserMapper;
import com.coder520.user.entity.User;
import com.coder520.user.entity.UserElement;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by JackWangon[www.coder520.com] 2018/4/4.
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ZkClient zkClient;

    @Override
    public UserElement login(User user) {
        UserElement ue = null;
        User existUser = userMapper.selectByEmail(user.getEmail());
        if (existUser == null) {
            throw new MamaBuyException("登陆失败！");
        } else {
            boolean isExist = bCryptPasswordEncoder.matches(user.getPassword(), existUser.getPassword());
            if (!isExist) {
                throw new MamaBuyException("登陆失败！");
            }
            //验证通过 赋值ue
            ue = new UserElement();
            ue.setEmail(existUser.getEmail());
            ue.setUserId(existUser.getId());
            ue.setUuid(existUser.getUuid());
            ue.setNickname(existUser.getNickname());
        }
        return ue;
    }

    /**
     * 1、注册邮箱不能重复   a、乐观锁悲观锁在数据库层面锁 b、Email字段设置成union  （这两种方案只在单表时有用）
     * c、分布式锁  Redis的setnx或者zookeeper
     *
     * @param user
     * @throws Exception
     */
    @Override
    public void registerUser(User user) throws Exception {
        InterProcessMutex lock = null;
        try {
            lock = new InterProcessMutex(zkClient.getZkClient(), Constants.USER_REGISTER_DISTRIBUTE_LOCK_PATH);
            int count = 0;
            do {
                //开始抢锁
                if (lock.acquire(3000, TimeUnit.MILLISECONDS)) {
                    //查询重复用户
                    User selectUser = userMapper.selectByEmail(user.getEmail());
                    if (selectUser != null) {
                        throw new MamaBuyException("用户邮箱重复！");
                    }
                    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                    user.setNickname("码码购用户" + user.getEmail());
                    user.setCreateTime(new Date());
                    user.setUpdateTime(new Date());
                    userMapper.insertSelective(user);
                    count = 100;
                }
            } while (count++ < 5);
        } catch (Exception e) {
            log.error("用户注册异常！", e);
            throw e;
        } finally {
            if (lock != null) {
                try {
                    lock.release();
                    log.info(user.getEmail() + Thread.currentThread().getName() + "释放锁");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
