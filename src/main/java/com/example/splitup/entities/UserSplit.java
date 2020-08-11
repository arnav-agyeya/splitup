package com.example.splitup.entities;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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

    @ManyToMany(mappedBy = "splits")
    private Set<Group> userGroups;

    public UserSplit(String userName) {
        this.userName = userName;
        this.isActive = Boolean.toString(true);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSplit)) return false;
        UserSplit userSplit = (UserSplit) o;
        return userId == userSplit.userId &&
                Objects.equals(userName, userSplit.userName) &&
                Objects.equals(userGroups, userSplit.userGroups);
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
