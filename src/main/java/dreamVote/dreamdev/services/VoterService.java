package dreamVote.dreamdev.services;

import dreamVote.dreamdev.dtos.requests.*;
import dreamVote.dreamdev.dtos.responses.*;

public interface VoterService {
    VoterRegisterationResponse register(VoterRegisterationRequest voterRegisterationRequest);

    LoginResponse login(LoginRequest loginRequest);

    LogoutResponse logout(LogoutRequest logoutRequest);

    VoteForCandidateResponse vote(VoteForCandidateRequest voteForCandidateRequest);

    ApiResponse nominateCandidate(NominateCandidateRequest nominateCandidateRequest);

    ApiResponse getAllCandidates(GetAllCandidatesRequest getAllCandidatesRequest);
}
