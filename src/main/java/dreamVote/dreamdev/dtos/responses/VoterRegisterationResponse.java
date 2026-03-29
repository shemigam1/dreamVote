package dreamVote.dreamdev.dtos.responses;

import lombok.Data;

@Data
public class VoterRegisterationResponse {
    private String voterId;
    private String email;
    private boolean isLoggedIn;
}
