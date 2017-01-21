package com.professionalbeginner.domain.applicationlayer.impl;

import com.professionalbeginner.TestUtils;
import com.professionalbeginner.data.inmemory.InMemoryUserRepository;
import com.professionalbeginner.domain.applicationlayer.UserService;
import com.professionalbeginner.domain.applicationlayer.exception.ExistingUserException;
import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.core.user.UserInfo;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Kempenich Florian
 */
public class UserServiceImplTest {

    private UserService userService;
    private TestUtils testUtils;
    private UserRepository inMemRepo;

    @Before
    public void setUp() throws Exception {
        testUtils = new TestUtils();
        inMemRepo = new InMemoryUserRepository();
        userService = new UserServiceImpl(inMemRepo);
    }

    @Test
    public void saveNewUser_andFindDetails() throws Exception {
        // Given
        UserId username = new UserId("patrick");
        UserInfo info = new UserInfo("Patrick", "Smith", testUtils.defaultDate());

        // When
        UserId savedId = userService.createNewUser(username, info);
        User fromRepo = userService.getDetails(savedId);

        // Then
        assertEquals(username, fromRepo.getUserId());
        assertEquals(info, fromRepo.getInfo());
    }

    @Test
    public void usernameExisting_preventNewUserCreation() throws Exception {
        // Given: Save user in repo
        UserId existingUsername = new UserId("existing");
        User existing = testUtils.makeRandomUser(existingUsername);
        inMemRepo.save(existing);

        // When
        try {
            UserInfo otherInfo = new UserInfo("asdf", "asdf", testUtils.defaultDate().plusMonths(2));
            userService.createNewUser(new UserId("existing"), otherInfo);
            fail("Should throw exception");
        }
        // Then
        catch (ExistingUserException e) {
            assertEquals("Username already existing", e.getMessage());
            assertEquals(existingUsername, e.getExistingUsername());
        }
    }
}