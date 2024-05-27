package bookcarupdate.bookcar.services;

import bookcarupdate.bookcar.dto.JwtAuthenticationResponse;
import bookcarupdate.bookcar.dto.RefreshTokenRequest;
import bookcarupdate.bookcar.dto.SignInRequest;
import bookcarupdate.bookcar.dto.SignUpRequest;
import bookcarupdate.bookcar.models.Store;
import bookcarupdate.bookcar.models.User;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);
    Store signUpSeller(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refresh(RefreshTokenRequest refreshTokenRequest);
}
