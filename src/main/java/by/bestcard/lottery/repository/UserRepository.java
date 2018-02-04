package by.bestcard.lottery.repository;

import by.bestcard.lottery.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User findByUsername(String username);

    void save(User user);
}
