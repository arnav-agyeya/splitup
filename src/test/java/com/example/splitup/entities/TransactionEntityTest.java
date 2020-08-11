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
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

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

        UserSplit userSplit = new UserSplit("Arnav");
        UserSplit userSplit1 = new UserSplit("Var");
        userSplitDAO.save(userSplit);
        userSplitDAO.save(userSplit1);
        ArrayList<UserSplit> users = new ArrayList<>() {{
            add(userSplit);
            add(userSplit1);
        }};
        Transaction transaction = new Transaction(5000, userSplit, users);
        transactionDAO.save(transaction);

        Group group = new Group("TestGroup", new HashSet<>(users), new LinkedHashSet<>() {{
            add(transaction);
        }});
        groupDAO.save(group);
        List<Group> all = groupDAO.findAll();
        System.out.println(transactionDAO.findAll());
        System.out.println(all);

        group.getSplits().remove(userSplit);
        groupDAO.save(group);
        userSplit.setIsActive(Boolean.toString(false));
        userSplitDAO.save(userSplit);

        System.out.println(groupDAO.findAll());
    }

}
