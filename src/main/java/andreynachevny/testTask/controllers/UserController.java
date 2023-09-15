package andreynachevny.testTask.controllers;

import andreynachevny.testTask.dto.TokenDto;
import andreynachevny.testTask.dto.UserRegistrationDto;
import andreynachevny.testTask.dto.UserResponseDto;
import andreynachevny.testTask.dto.UserUpdateDto;
import andreynachevny.testTask.models.User;
import andreynachevny.testTask.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @PostMapping("/signup")
    public HttpStatus registrationUser(@RequestBody UserRegistrationDto userRegistration){
        User userToSave = modelMapper.map(userRegistration, User.class);
        userService.registration(userToSave);
        return HttpStatus.CREATED;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable int id,
                                                   @RequestBody TokenDto token){
        return ResponseEntity.ok(modelMapper.map(userService.getUser(id, token.getAccessToken()), UserResponseDto.class));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUSer(@PathVariable int id,
                                             @RequestBody UserUpdateDto userUpdateDto){
        return ResponseEntity.ok(userService.update(userUpdateDto,userUpdateDto.getAccessToken(),id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id,
                                             @RequestBody TokenDto tokenDto){
        return ResponseEntity.ok(userService.delete(id, tokenDto.getAccessToken()));
    }

}
