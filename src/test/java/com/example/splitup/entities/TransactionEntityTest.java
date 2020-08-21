package com.example.splitup.entities;

import com.example.splitup.dao.IGroupDAO;
import com.example.splitup.dao.ITransactionDAO;
import com.example.splitup.dao.IUserSplitDAO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionEntityTest {

    @Autowired
    private IUserSplitDAO userSplitDAO;
    @Autowired
    private ITransactionDAO transactionDAO;
    @Autowired
    private IGroupDAO groupDAO;

    TransactionEntityTest() {
    }

    @Test
    public void testCreationOfObjects() {

        UserSplit userSplit = new UserSplit("Arnav", "9709635635", "test@gmail.com");
        UserSplit userSplit1 = new UserSplit("Var","9999999999","test1@gmail.com");
        userSplitDAO.save(userSplit);
        userSplitDAO.save(userSplit1);
        List<UserSplit> users = new ArrayList<>() {{
            add(userSplit);
            add(userSplit1);
        }};
        Transaction transaction = new Transaction(5000, userSplit, users);
        transactionDAO.save(transaction);

        Group group = new Group("TestGroup",
                users.stream()
                     .collect(Collectors.toMap(Function.identity(), u -> 0)),
                new LinkedHashSet<>() {{
                    add(transaction);
                }});

        groupDAO.save(group);
        List<Group> all = groupDAO.findAll();
        userSplit.setIsActive(Boolean.toString(false));
        userSplitDAO.save(userSplit);

        groupDAO.findAll()
                .forEach(TransactionEntityTest::printGroupMemberBalance);
    }

    private static void printGroupMemberBalance(Group g) {
        g.getSplits()
         .forEach((us, integer) -> System.out.println(us + " " + integer));
    }

}
