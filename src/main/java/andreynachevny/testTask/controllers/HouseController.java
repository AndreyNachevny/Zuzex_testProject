package andreynachevny.testTask.controllers;

import andreynachevny.testTask.dto.*;
import andreynachevny.testTask.services.HouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/house")
public class HouseController {

    private HouseService houseService;


    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @PostMapping("/create")
    public HttpStatus createHouse(@RequestBody HouseCreateDto houseCreateDto){
        houseService.create(houseCreateDto);
        return HttpStatus.CREATED;
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addResident(@PathVariable int id, TokenDto tokenDto){
        return ResponseEntity.ok(houseService.addResidents(id,tokenDto.getAccessToken()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseResponseDto> getHouse(@PathVariable int id,
                                                     @RequestBody TokenDto token){
        return ResponseEntity.ok(houseService.getHouse(id,token.getAccessToken()));
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateHouse(@RequestBody HouseUpdateDto houseUpdateDto){

        return ResponseEntity.ok(houseService.update(houseUpdateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHouse(@PathVariable int id,
                                             @RequestBody TokenDto tokenDto){
        return ResponseEntity.ok(houseService.deleteHouse(id,tokenDto.getAccessToken()));
    }
}
