package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UsersDbStore;

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

    public Optional<User> findUseByEmail(String email) {
        return usersDbStore.findUserByEmail(email);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return usersDbStore.findUserByEmailAndPassword(email, password);
    }
}
