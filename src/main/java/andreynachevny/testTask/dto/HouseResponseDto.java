package andreynachevny.testTask.dto;

import andreynachevny.testTask.models.User;
import java.util.List;

public class HouseResponseDto {

    private Integer id;

    private String address;

    private Long idOwner;

    private List<UserResponseDto> residents;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }

    public List<UserResponseDto> getResidents() {
        return residents;
    }

    public void setResidents(List<UserResponseDto> residents) {
        this.residents = residents;
    }
}
