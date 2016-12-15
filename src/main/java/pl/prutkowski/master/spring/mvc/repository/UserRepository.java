package pl.prutkowski.master.spring.mvc.repository;

import org.springframework.stereotype.Repository;
import pl.prutkowski.master.spring.mvc.domain.User;
import pl.prutkowski.master.spring.mvc.error.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by programmer on 10/25/16.
 */
@Repository
public class UserRepository {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    public User save(User user) {
        return save(user.getEmail(), user);
    }

    public User update(String email, User user) throws EntityNotFoundException {
        if (!exists(email)) {
            throw new EntityNotFoundException("User " + email + " doesn't exist!");
        }
        user.setEmail(email);
        return save(email, user);
    }

    public void delete(String email) throws EntityNotFoundException {
        if (!exists(email)) {
            throw new EntityNotFoundException("Użytkownik " + email + " nie istnieje");
        }
        userMap.remove(email);
    }

    public User findOne(String email) throws EntityNotFoundException {
        if (!exists(email)) {
            throw new EntityNotFoundException("User " + email + " doesn't exist!");
        }
        return userMap.get(email);
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    public User save(String email, User user) {
        user.setEmail(email);
        return userMap.put(email, user);
    }

    public boolean exists(String email) {
        return userMap.containsKey(email);
    }

    public void reset(User... users) {
        userMap.clear();
        for (User user : users) {
            save(user);
        }
    }
}
