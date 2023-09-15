package andreynachevny.testTask.services;

import andreynachevny.testTask.dto.HouseCreateDto;
import andreynachevny.testTask.dto.HouseResponseDto;
import andreynachevny.testTask.dto.HouseUpdateDto;
import andreynachevny.testTask.dto.UserResponseDto;
import andreynachevny.testTask.jwt.JwtCore;
import andreynachevny.testTask.models.House;
import andreynachevny.testTask.models.User;
import andreynachevny.testTask.repositories.HouseRepository;
import andreynachevny.testTask.repositories.UserRepository;
import ch.qos.logback.core.net.server.Client;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class HouseService {

    private HouseRepository houseRepository;

    private JwtCore jwtCore;
    private ModelMapper modelMapper;
    private UserRepository userRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository, JwtCore jwtCore, ModelMapper modelMapper, UserRepository userRepository) {
        this.houseRepository = houseRepository;
        this.jwtCore = jwtCore;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    public void create(HouseCreateDto houseCreateDto){
        if (jwtCore.validateAccessToken(houseCreateDto.getAccessToken())){
            Claims claims = jwtCore.getClaims(houseCreateDto.getAccessToken());
            House house = new House();
            house.setAddress(houseCreateDto.getAddress());
            house.setIdOwner((Integer) claims.get("id"));
            house.setResidents(new ArrayList<>(Collections.singletonList(userRepository.getReferenceById((Integer) claims.get("id")))));
            User user = userRepository.findById((int)claims.get("id")).get();
            user.setHouse(house);
            houseRepository.save(house);
            userRepository.save(user);
        }
    }

    @Transactional
    public String addResidents(int id, String token){
        if(jwtCore.validateAccessToken(token)){
            Claims claims = jwtCore.getClaims(token);
            Optional<House> house = houseRepository.findByIdOwner((int) claims.get("id"));
            if(house.isPresent()){
                User user = userRepository.getReferenceById(id);
                user.setHouse(house.get());
                house.get().getResidents().add(user);
                userRepository.save(user);
                houseRepository.save(house.get());
                return "Added!";

            }
            return "you are not the owner of the house";
        }
        return "invalid token";
    }

    @Transactional
    public String update(HouseUpdateDto houseUpdateDto){
        Optional<House> house = houseRepository.findByAddress(houseUpdateDto.getAddress());
        if(jwtCore.validateAccessToken(houseUpdateDto.getAccessToken()) && house.isPresent()){
            Claims claims = jwtCore.getClaims(houseUpdateDto.getAccessToken());
            if(claims.get("id") == house.get().getIdOwner() && houseUpdateDto.getIdOwner() != null){
                house.get().setIdOwner(houseUpdateDto.getIdOwner());
                houseRepository.save(house.get());
                return "Update!";
            }
        }
        return "Something went wrong";
    }

    public HouseResponseDto getHouse(int id, String token){
        if(jwtCore.validateAccessToken(token)){
            Optional<House> house = houseRepository.findById(id);
            if(house.isPresent()){
                HouseResponseDto houseResponseDto = modelMapper.map(house.get(), HouseResponseDto.class);
                if(house.get().getResidents() != null){
                    List<UserResponseDto> userResponseDtoList = new ArrayList<>();
                    for(User user: house.get().getResidents()){
                        userResponseDtoList.add(modelMapper.map(user,UserResponseDto.class));
                    }
                    houseResponseDto.setResidents(userResponseDtoList);
                }
                return houseResponseDto;
            }
        }
        return null;
    }

    @Transactional
    public String deleteHouse(int id, String token){
        if(jwtCore.validateAccessToken(token)){
            Optional<House> house = houseRepository.findById(id);
            if(house.isPresent() ){
                houseRepository.delete(house.get());
                return "House was delete";
            }
        }
        return "invalid token";
    }

}
