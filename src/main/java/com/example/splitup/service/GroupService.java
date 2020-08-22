package com.example.splitup.service;

import com.example.splitup.dao.IGroupDAO;
import com.example.splitup.dao.ITransactionDAO;
import com.example.splitup.dao.IUserSplitDAO;
import com.example.splitup.entities.Group;
import com.example.splitup.entities.Transaction;
import com.example.splitup.entities.UserSplit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping(path="/")
public class GroupService {

    @Autowired
    private IGroupDAO groupDAO;
    @Autowired
    private IUserSplitDAO userSplitDAO;
    @Autowired
    private ITransactionDAO transactionDAO;

    @RequestMapping(path = "/createGroup", method = RequestMethod.POST)
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        try {
            Group groupSaved = groupDAO.save(group);
            return new ResponseEntity<>(groupSaved, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Hibernate Exception", e);
        }
    }

    @RequestMapping(path = "/addTransaction", method = RequestMethod.POST)
    public ResponseEntity<Group> addTransaction(@RequestParam("groupId") int groupId,
                                                @RequestParam("spenderId") int spenderId,
                                                @RequestParam("amount")int amount,
                                                @RequestParam Set<Integer> usersSpent)
    {

        try {
            Group group = groupDAO.findById(groupId).orElse(null);
            assert group != null;
            UserSplit spendingUser = userSplitDAO.findById(spenderId).orElse(null);
            Map<UserSplit, Integer> splitsUsers = group.getSplits();

            splitsUsers.computeIfPresent(spendingUser, (k, v) -> v + amount);

            int amountToDeduct = amount / usersSpent.size();
            updateUserBalance(usersSpent, splitsUsers, amountToDeduct);

            Transaction transaction = recordNewTransaction(spendingUser, amount, usersSpent);
            group.getTransactions().add(transaction);

            Group savedGroup = groupDAO.save(group);


            return new ResponseEntity<>(savedGroup, HttpStatus.ACCEPTED);

        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Hibernate Exception", e);

        }

    }

    private Transaction recordNewTransaction(UserSplit spendingUser, int amount, Set<Integer> usersSpent) {
        Transaction transaction = new Transaction(amount, spendingUser, userSplitDAO.findAllById(usersSpent));
        return transactionDAO.save(transaction);
    }

    private static void updateUserBalance(Set<Integer> usersSpent, Map<UserSplit, Integer> splitsUsers,
                                          int amountToDeduct) {
        splitsUsers.keySet()
                   .stream()
                   .filter(user -> usersSpent.contains(user.getUserId()))
                   .forEach(user -> splitsUsers.computeIfPresent(user, (k, v) -> v - amountToDeduct));
    }
}
