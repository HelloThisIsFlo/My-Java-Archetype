package com.professionalbeginner.data.hibernate.user;

import com.professionalbeginner.domain.core.user.User;
import com.professionalbeginner.domain.core.user.UserId;
import com.professionalbeginner.domain.core.user.UserInfo;
import org.springframework.stereotype.Component;

/**
 * @author Kempenich Florian
 */
@Component
public class UserJpaMapper {

    public UserJpaEntity map(User user) {
        return new UserJpaEntity(
                user.getUserId().username(),
                user.getInfo().firstName,
                user.getInfo().lastName,
                user.getInfo().birthdate
        );
    }

    public User map(UserJpaEntity userEntity) {
        UserInfo userInfo = new UserInfo(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getBirthday()
        );

        UserId username = new UserId(userEntity.getUsername());

        return new User(username, userInfo);
    }
}

