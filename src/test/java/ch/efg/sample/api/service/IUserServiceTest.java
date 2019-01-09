package ch.efg.sample.api.service;

import ch.efg.sample.api.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserServiceTest {

    @Autowired
    private UserService userService;

    private User expectedUser;

    @Before
    public void setUp() throws Exception {
        List<User> users = new ArrayList<>();
        expectedUser = new User("Test", "a");

        users.add(expectedUser);
        users.add(new User("Test2", "b"));

        userService.saveAll(users);
        System.out.println("Initialization data");
    }

    @After
    public void tearDown() throws Exception {
        deleteAll();
    }

    private void deleteAll() {
        List<User> all = userService.findAll();
        for (User user : all) {
            userService.delete(user.getId());
        }
        System.out.println("Delete all data");
    }

    @Test
    public void findAll() throws Exception {
        List<User> findAll = userService.findAll();
        assertEquals(2, findAll.size());
    }

    @Test
    public void findById() throws Exception {
        String id = userService.findAll().get(0).getId();
        User byId = userService.findById(id).get(0);

        assertEquals(expectedUser, byId);
    }

    @Test
    public void saveAll() throws Exception {
        User bob = new User("Bob", "b");
        User jack = new User("Jack", "c");

        List<User> list = new ArrayList<>();
        list.add(bob);
        list.add(jack);

        List<User> users = userService.saveAll(list);

        assertEquals(2, users.size());
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void save() throws Exception {
        User user = new User("Marta", "d");

        User savedUser = userService.save(user);

        assertEquals(user, savedUser);
    }

    @Test
    public void delete() throws Exception {
        User deletedUser = userService.delete(userService.findAll().get(0).getId());

        assertEquals(expectedUser, deletedUser);
    }

    @Test
    public void findAllGroupByGroupId() throws Exception {
        Map<String, List<User>> expected = new HashMap<>();
        List<User> usersA = new ArrayList<>();
        usersA.add(expectedUser);

        expected.put("a", usersA);
        expected.put("b", Arrays.asList(new User("Test2", "b")));

        Map<String, List<User>> actual = userService.findAllGroupByGroupId();

        assertEquals(expected,actual);
    }

}