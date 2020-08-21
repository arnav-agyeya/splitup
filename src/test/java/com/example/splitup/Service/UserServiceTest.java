package com.example.splitup.Service;

import com.example.splitup.entities.LoginUserInfo;
import com.example.splitup.entities.SignUpWrapper;
import com.example.splitup.entities.UserSplit;
import com.example.splitup.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private UserSplit userSplit;
    private LoginUserInfo loginInfo;
    private SignUpWrapper signUpWrapper;

    @Before
    public void setUp(){
        userSplit = new UserSplit("Arnav", "9709635635", "test@gmail.com");
        loginInfo = new LoginUserInfo(userSplit, "TN1", "Password");
        signUpWrapper = getNewSignUpWrapper().withUser(userSplit).withLoginInfo(loginInfo);
    }

    @Test
    public void testUserServiceInstantiation(){
        assertNotNull(userService);
    }

    @Test
    public void testAddUserShouldAddOneUser(){

        ResponseEntity<UserSplit> userSplitResponseEntity = userService.signUp(signUpWrapper);

        assertEquals(userSplit,userSplitResponseEntity.getBody());
        assertEquals(HttpStatus.ACCEPTED, userSplitResponseEntity.getStatusCode());
    }

    @Test
    public void testAddUserShouldReturnNullBodyAndBadHttpStatusCode(){
        UserSplit userSplit1 = new UserSplit("Var", "9709635635", "test1@gmail.com");
        SignUpWrapper signUpWrapper1 = getNewSignUpWrapper().withUser(userSplit1).withLoginInfo(loginInfo);

        ResponseEntity<UserSplit> userSplitResponseEntity = userService.signUp(signUpWrapper);
        ResponseEntity<UserSplit> userSplitResponseEntity1 = userService.signUp(signUpWrapper1);

        assertNotNull(userSplitResponseEntity.getBody());
        assertNull(userSplitResponseEntity1.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, userSplitResponseEntity1.getStatusCode());
    }

    private static SignUpWrapper getNewSignUpWrapper() {
        return new SignUpWrapper();
    }

    @Test
    public void testLoginUserShouldSuccessfullyLoginIfPasswordIsCorrect(){
        userService.signUp(signUpWrapper);
        ResponseEntity<LoginUserInfo> loginUserInfoResponseEntity = userService.loginUser(loginInfo);
        assertEquals(loginUserInfoResponseEntity.getBody(), loginInfo);
    }
}
