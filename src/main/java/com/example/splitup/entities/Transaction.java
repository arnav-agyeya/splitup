package com.example.splitup.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transactionid")
    private long transactionId;

    @Column(name = "datetime")
    @CreationTimestamp
    private LocalDateTime dateTime;

    @Column(name = "amount")
    private long amount;

    @OneToOne
    private UserSplit userSpending;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserSplit> usersSpentOnList;

    public Transaction(long amount, UserSplit userSpending, List<UserSplit> usersSpentOnList) {
        this.amount = amount;
        this.userSpending = userSpending;
        this.usersSpentOnList = usersSpentOnList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId &&
                amount == that.amount &&
                dateTime.equals(that.dateTime) &&
                userSpending.equals(that.userSpending) &&
                usersSpentOnList.equals(that.usersSpentOnList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, dateTime, amount, userSpending, usersSpentOnList);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", dateTime=" + dateTime +
                ", amount=" + amount +
                ", userSpending=" + userSpending.getUserName() +
                ", usersSpentOnList=" + usersSpentOnList.stream().map(UserSplit::getUserName).collect(Collectors.toSet()) +
                '}';
    }

}
