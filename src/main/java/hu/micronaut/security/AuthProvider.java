package hu.micronaut.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import hu.micronaut.model.entitys.SimpleUser;
import hu.micronaut.repository.SimpleUserRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationFailed;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.rxjava3.core.Flowable;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Singleton
public class AuthProvider implements AuthenticationProvider {

    private final SimpleUserRepository userRepo;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest,
                                                          AuthenticationRequest<?, ?> authReq) {

        final String username = authReq.getIdentity().toString();
        final String password = authReq.getSecret().toString();

        Optional<SimpleUser> userOpt = userRepo.findByUsername(username);

        if(!userOpt.isPresent()) {
            return Flowable.just(new AuthenticationFailed("user not found!"));
        }

        Boolean passIsCorrect = BCrypt.verifyer()
                .verify(password.getBytes(), userOpt.get().getPassword().getBytes())
                .verified;

        List<String> userRoleCodes = userOpt.get()
                .getRoles().stream().map(r -> r.getRoleCode()).toList();

        if(passIsCorrect) {
            return Flowable.just(AuthenticationResponse.success(username, userRoleCodes));
        }

        return Flowable.just(new AuthenticationFailed("Wrong username or password!"));
    }
}
