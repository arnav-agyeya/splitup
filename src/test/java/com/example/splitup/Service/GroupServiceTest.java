package com.example.splitup.Service;


import com.example.splitup.dao.IUserSplitDAO;
import com.example.splitup.entities.Group;
import com.example.splitup.entities.UserSplit;
import com.example.splitup.service.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;
    @Autowired
    private IUserSplitDAO userSplitDAO;

    @Test
    public void initializeGroupServiceBean() {
        assertNotNull(groupService);
    }

    @Test
    public void testCreateGroupWith2Users() {
        List<UserSplit> testUsers = createTestUsers(2);
        HashMap<UserSplit, Integer> usersInGroup = new HashMap<>() {{
            put(testUsers.get(0), 0);
            put(testUsers.get(1), 0);
        }};
        userSplitDAO.saveAll(usersInGroup.keySet());
        Group group = new Group("TestGroup", usersInGroup, new HashSet<>());

        ResponseEntity<Group> groupSaved = groupService.createGroup(group);
        assertEquals(groupSaved.getBody(), group);
        assertEquals(HttpStatus.ACCEPTED, groupSaved.getStatusCode());
    }

    @Test
    public void testGroupWith5UsersExpenditureSplitBetween3MentionedUser() {
        List<UserSplit> testUsers = createTestUsers(5);
        Map<UserSplit, Integer> usersInGroup =
                testUsers.stream().collect(Collectors.toMap(Function.identity(), user -> 0));
        userSplitDAO.saveAll(usersInGroup.keySet());
        Group group = new Group("TestGroup", usersInGroup, new HashSet<>());

        ResponseEntity<Group> groupSaved = groupService.createGroup(group);
        assertEquals(groupSaved.getBody(), group);
        assertEquals(HttpStatus.ACCEPTED, groupSaved.getStatusCode());

        ResponseEntity<Group> groupResponseEntity = groupService.addTransactionToGroup(group.getGroupId(), testUsers.get(0).getUserId(), 25000, new HashSet<>() {{
            add(testUsers.get(0).getUserId());
            add(testUsers.get(1).getUserId());
            add(testUsers.get(2).getUserId());
        }});

        System.out.println(groupResponseEntity);

    }

    private static List<UserSplit> createTestUsers(int num) {
        List<UserSplit> users = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            users.add(new UserSplit("TestUser"+i,"TestMobile"+i,"test"+i+"@gmail.com"));
        }
        return users;
    }
}
