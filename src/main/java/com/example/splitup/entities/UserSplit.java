package com.example.splitup.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * POJO to handle users of APP
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "usersplit")
public class UserSplit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    private int userId;

    @Column(name = "username")
    private String userName = null;

    @Column(name = "isactive")
    private String isActive = null;

    @Column(name = "mobilenumber", unique = true)
    private String mobileNumber;

    @Column(name = "emailid", unique = true)
    private String emailId;

    @ElementCollection(fetch = FetchType.EAGER )
    private Set<Group> userGroups = new LinkedHashSet<>();

    public UserSplit(String userName, String mobileNumber, String emailId) {
        this.userName = userName;
        this.isActive = Boolean.toString(true);
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSplit)) return false;
        UserSplit userSplit = (UserSplit) o;
        //TODO: Needs rework
        return userId == userSplit.userId;
    }

    @Override
    public String toString() {
        return "UserSplit{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userGroups=" + userGroups.stream().map(Group::getGroupName).collect(Collectors.toSet()) +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName);
    }
}
