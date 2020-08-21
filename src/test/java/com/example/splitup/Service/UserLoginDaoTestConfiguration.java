package com.example.splitup.Service;

import com.example.splitup.dao.ILoginUserInfoDAO;
import com.example.splitup.dao.IUserSplitDAO;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("UserSignUp-Test")
@Configuration
public class UserLoginDaoTestConfiguration {
    @Bean
    @Primary
    public IUserSplitDAO iUserSplitDAOMock(){
        return Mockito.mock(IUserSplitDAO.class);
    }

    @Bean
    @Primary
    public ILoginUserInfoDAO iLoginUserInfoDAOMock(){
        return Mockito.mock(ILoginUserInfoDAO.class);
    }
}
