package andreynachevny.testTask.jwt;

import andreynachevny.testTask.dto.UserLogin;
import andreynachevny.testTask.models.User;
import andreynachevny.testTask.repositories.UserRepository;
import andreynachevny.testTask.services.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthService {
    private final JwtCore jwtCore;
    private UserRepository userRepository;

    @Autowired
    public AuthService(JwtCore jwtCore, UserRepository userRepository) {
        this.jwtCore = jwtCore;
        this.userRepository = userRepository;
    }

    public boolean isTokenEqualsUser(int id, String token){
        if (jwtCore.validateAccessToken(token)){
            Claims claims = jwtCore.getClaims(token);
            return (int)claims.get("id") == id;
        }
        return false;
    }

    public String authentication(UserLogin userLogin){
        Optional<User> userFromDb = userRepository.findByName(userLogin.getName());
        if (userFromDb.isEmpty())return "user dont exists";
        if(userFromDb.get().getPassword().equals(userLogin.getPassword())){
            return jwtCore.generateAccessToken(userFromDb.get());
        }
        return "incorrect password";
    }
}
