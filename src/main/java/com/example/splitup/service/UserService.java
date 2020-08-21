package com.example.splitup.service;


import com.example.splitup.dao.ILoginUserInfoDAO;
import com.example.splitup.dao.IUserSplitDAO;
import com.example.splitup.entities.LoginUserInfo;
import com.example.splitup.entities.SignUpWrapper;
import com.example.splitup.entities.UserSplit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path="/")
public class UserService {

    @Autowired
    private ILoginUserInfoDAO loginUserInfoDAO;
    @Autowired
    private IUserSplitDAO userSplitDAO;

    @RequestMapping(path = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<UserSplit> signUp(@RequestBody SignUpWrapper signUpWrapper) {
        try {
            UserSplit userSplit = userSplitDAO.save(signUpWrapper.getUser());
            loginUserInfoDAO.save(signUpWrapper.getLoginInfo());
            return new ResponseEntity<>(userSplit, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "HiberNate Exception", e);
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ResponseEntity<LoginUserInfo> loginUser(@RequestBody LoginUserInfo loginInfo) {
        try {
            LoginUserInfo userInfo = loginUserInfoDAO.getLoginUserInfoByUserName(loginInfo.getUserName());
            if (userInfo == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            if (userInfo.getUserName().equals(loginInfo.getPassWord())) {
                return new ResponseEntity<>(userInfo, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(userInfo, HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "HiberNate Exception", e);
        }
    }

    @RequestMapping(path = "/getUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserSplit>> getAllUsers(){
        return new ResponseEntity<>(userSplitDAO.findAll(), HttpStatus.ACCEPTED);
    }
}
