package andreynachevny.testTask.controllers;

import andreynachevny.testTask.dto.TokenDto;
import andreynachevny.testTask.dto.UserLogin;
import andreynachevny.testTask.jwt.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenDto> signIn(@RequestBody UserLogin userLogin){
        String token = authService.authentication(userLogin);
        return ResponseEntity.ok(new TokenDto(token));
    }


}
