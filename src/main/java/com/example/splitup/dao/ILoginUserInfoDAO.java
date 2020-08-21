package com.example.splitup.dao;

import com.example.splitup.entities.LoginUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface DAO class for LoginUserPojo
 */
@Repository
public interface ILoginUserInfoDAO extends JpaRepository<LoginUserInfo, Integer> {
    LoginUserInfo getLoginUserInfoByUserName(String userName);
}
