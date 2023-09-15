package andreynachevny.testTask.services;

import andreynachevny.testTask.dto.UserUpdateDto;
import andreynachevny.testTask.jwt.AuthService;
import andreynachevny.testTask.jwt.JwtCore;
import andreynachevny.testTask.models.User;
import andreynachevny.testTask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    private UserRepository userRepository;
    private JwtCore jwtCore;

    private AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository, JwtCore jwtCore, AuthService authService){
        this.userRepository = userRepository;
        this.jwtCore = jwtCore;
        this.authService = authService;
    }

    @Transactional
    public void registration(User user){
        userRepository.save(user);
    }

    @Transactional
    public String update(UserUpdateDto user, String accessToken, int id){
        if (authService.isTokenEqualsUser(id, accessToken)){
            User userToUpdate = userRepository.findById(id).get();
            if(!user.getName().isBlank()){
                userToUpdate.setName(user.getName());
            }
            if (user.getAge() != 0){
                userToUpdate.setAge(user.getAge());
            }
            if (!user.getPassword().isBlank()){
                userToUpdate.setPassword(user.getPassword());
            }
            userRepository.save(userToUpdate);
            return "Update!";
        }
        return "Something went wrong";
    }

    public User getUser(int id, String accessToken){
        if (jwtCore.validateAccessToken(accessToken)){
            return userRepository.findById(id).orElse(null);
        }
        return null;
    }

    @Transactional
    public String delete(int id, String token){
        if (jwtCore.validateAccessToken(token)){
            userRepository.delete(userRepository.getReferenceById(id));
            return "successful removal";
        }
        return "Something went wrong";
    }
}
