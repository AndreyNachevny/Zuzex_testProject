package andreynachevny.testTask.dto;

public class UserResponseDto {

    private String name;
    private int age;
    private String password;
    private HouseResponseDto houseResponseDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HouseResponseDto getHouseResponseDto() {
        return houseResponseDto;
    }

    public void setHouseResponseDto(HouseResponseDto houseResponseDto) {
        this.houseResponseDto = houseResponseDto;
    }
}
