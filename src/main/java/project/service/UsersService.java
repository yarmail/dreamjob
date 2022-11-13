package project.service;

import org.springframework.stereotype.Service;
import project.model.User;
import project.store.UsersDbStore;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersDbStore usersDbStore;

    public UsersService(UsersDbStore usersDbStore) {
        this.usersDbStore = usersDbStore;
    }

    public Optional<User> add(User user) {
        return usersDbStore.add(user);
    }
}
