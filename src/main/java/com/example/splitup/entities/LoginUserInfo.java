package com.example.splitup.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

/**
 * Class to handle login/security
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "login_user_table")
public class LoginUserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loginid")
    private int loginId;

    @OneToOne
    private UserSplit user;

    @Column(name = "username", unique = true)
    private String userName;

    @Column(name = "password")
    private String passWord;

    public LoginUserInfo(UserSplit user, String userName, String passWord) {
        this.user = user;
        this.userName = userName;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.passWord = encoder.encode(passWord);
    }
}
