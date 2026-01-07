package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

//    public List<User> getUser1();
    public User getCurrentUser(String email);

    public User save(User user);
}
