package bookcarupdate.bookcar.services.impl;

import bookcarupdate.bookcar.dto.JwtAuthenticationResponse;
import bookcarupdate.bookcar.dto.RefreshTokenRequest;
import bookcarupdate.bookcar.dto.SignInRequest;
import bookcarupdate.bookcar.dto.SignUpRequest;
import bookcarupdate.bookcar.exception.CloudNotFoundException;
import bookcarupdate.bookcar.models.Role;
import bookcarupdate.bookcar.models.Store;
import bookcarupdate.bookcar.models.User;
import bookcarupdate.bookcar.repositories.StoreRepository;
import bookcarupdate.bookcar.repositories.UserRepository;
import bookcarupdate.bookcar.services.AuthenticationService;
import bookcarupdate.bookcar.services.JWTservice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTservice jwTservice;
    private final StoreRepository storeRepository;
    @Override
    public User signUp(SignUpRequest signUpRequest) {
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new CloudNotFoundException("Email đã được đăng ký, vui lòng dùng email khác");
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUserName(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhone_number(signUpRequest.getPhone_number());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public Store signUpSeller(SignUpRequest signUpRequest) {
        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new CloudNotFoundException("Email đã được đăng ký, vui lòng dùng email khác");
        }
        System.out.println("aaaaaaaaa");
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUserName(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhone_number(signUpRequest.getPhone_number());
        user.setRole(Role.SELLER);
        User user1 = userRepository.save(user);
        Store store = new Store();
        store.setPhoneNumber(signUpRequest.getPhone_number());
        store.setStoreName("Nhà xe "+ signUpRequest.getUsername());
        store.setIntroduce("Nhà xe chạy đường dài");
        store.setCreatedAt(new Date());
        store.setUpdateAt(new Date());
        store.setUser(user1);
        return storeRepository.save(store);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwTservice.generateToken(user);
        var refreshToken = jwTservice.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setAccess_token(jwt);
        jwtAuthenticationResponse.setRefresh_token(refreshToken);

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refresh(RefreshTokenRequest refreshTokenRequest){
        System.out.println(refreshTokenRequest);
        String email = jwTservice.extractUserName(refreshTokenRequest.getAccess_token());
        System.out.println(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        if(jwTservice.isTokenValid(refreshTokenRequest.getAccess_token(), user)){
            var jwt= jwTservice.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setAccess_token(jwt);
            jwtAuthenticationResponse.setRefresh_token(refreshTokenRequest.getAccess_token());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
