package andreynachevny.testTask.dto;

public class TokenDto {
    private String accessToken;

    public TokenDto(String accessToken){
        this.accessToken = accessToken;
    }

    public TokenDto(){

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
