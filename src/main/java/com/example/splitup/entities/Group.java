package com.example.splitup.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * POJO for user Groups.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "splitgroup")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "groupid")
    private int groupId;

    @Column(name = "groupname", unique = true)
    private String groupName;

    @ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_groups",
            joinColumns = @JoinColumn(name = "groupId"))
    @MapKeyColumn(name = "userId")
    @Column(name = "balance")
    private Map<UserSplit, Integer> splits;

    @OneToMany(fetch = FetchType.EAGER)
    Set<Transaction> transactions;

    public Group(String groupName, Map<UserSplit, Integer> splits, Set<Transaction> transactions) {
        this.groupName = groupName;
        this.splits = splits;
        this.transactions = transactions;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
               Objects.equals(groupName, group.groupName) &&
               Objects.equals(splits, group.splits) &&
               Objects.equals(transactions, group.transactions);
    }

    @Override
    public String toString() {
        return "Group{" +
               "groupId=" + groupId +
               ", groupName='" + groupName + '\'' +
               ", usersOfGroup=" + splits.keySet().stream()
                                         .map(UserSplit::getUserName)
                                         .collect(Collectors.joining(", ")) +
               ", transactions=" + transactions +
               '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, splits, transactions);
    }
}
