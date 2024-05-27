package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.UserRepository;
import bookcarupdate.bookcar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

//    @Override
//    public List<User> getUser1() {
//        return userRepository.findAll();
//    }
//
    @Override
    public User getCurrentUser(String email) {
        try{
            System.out.println("Lấy thành công người dùng :"+email);
            return userRepository.findByEmail(email).get();
        }catch (Exception ex){
            throw new CloudNotFoundException("Lỗi tìm user");
        }

    }
}
