package dreamVote.dreamdev.utils;

import dreamVote.dreamdev.data.models.Voter;
import dreamVote.dreamdev.dtos.requests.VoterRegisterationRequest;
import dreamVote.dreamdev.dtos.responses.LoginResponse;
import dreamVote.dreamdev.dtos.responses.VoterRegisterationResponse;

public class Mapper {
    public static Voter map(VoterRegisterationRequest request) {
        Voter voter = new Voter();
        voter.setFirstName(request.getFirstName());
        voter.setLastName(request.getLastName());
        voter.setEmail(request.getEmail());
        voter.setPassword(request.getPassword());
        return voter;
    }

    public static VoterRegisterationResponse map(Voter voter) {
        VoterRegisterationResponse response = new VoterRegisterationResponse();
        response.setVoterId(voter.getId());
        response.setEmail(voter.getEmail());
        response.setLoggedIn(voter.isLoggedIn());
        return response;
    }

    public static LoginResponse mapToLoginResponse(Voter savedVoter) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setLoggedIn(savedVoter.isLoggedIn());
        loginResponse.setEmail(savedVoter.getEmail());
        return loginResponse;
    }
}
