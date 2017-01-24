package com.professionalbeginner.data.hibernate;

import com.professionalbeginner.TestUtils;
import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.core.user.UserInfo;
import com.professionalbeginner.domain.interfacelayer.repository.UserNotFoundException;
import com.professionalbeginner.domain.interfacelayer.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Florian Kempenich
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-tests")
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HibernateUserRepositoryIntegrationTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TestUtils testUtils;

    @Test
    public void userNotFound_throwException() throws Exception {
        UserId notFoundId = new UserId("Not found");
        try {
            userRepository.findByUsername(notFoundId);
            fail("Should throw exception");
        } catch (UserNotFoundException e) {
            assertEquals("User could not be found: " + notFoundId, e.getMessage());
            assertEquals(notFoundId, e.getSearchedUsername());
        }
    }

    @Test(expected = NullPointerException.class)
    public void userIdNull_throwException() throws Exception {
        userRepository.findByUsername(null);
    }

    @Test(expected = NullPointerException.class)
    public void userNull_throwException() throws Exception {
        userRepository.save(null);
    }

    @Test
    public void saveAndRetrieve() throws Exception {
        UserId username = new UserId("frank");
        User user = testUtils.makeRandomUser(username);
        userRepository.save(user);

        User fromRepo = userRepository.findByUsername(username);
        assertEquals(user, fromRepo);
    }

    @Test
    public void save_doesNotChangeId() throws Exception {
        User user = testUtils.makeRandomUser(new UserId("patrick"));

        UserId savedId = userRepository.save(user);
        assertEquals(user.getUserId(), savedId);
    }

    @Test
    public void save_ExistingUser_update() throws Exception {
        // Given: User saved in Repo
        UserId username = new UserId("mark");
        User user = testUtils.makeRandomUser(username);
        userRepository.save(user);

        // When: Update user and save
        UserInfo newInfo = new UserInfo("other f name", "other l name", testUtils.defaultDate().plusMonths(4));
        user.setInfo(newInfo);
        userRepository.save(user);

        // Then: User is updated
        User fromRepo = userRepository.findByUsername(username);
        assertEquals(newInfo, fromRepo.getInfo());
    }

}