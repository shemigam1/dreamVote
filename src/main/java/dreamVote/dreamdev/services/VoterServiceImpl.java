package dreamVote.dreamdev.services;

import dreamVote.dreamdev.data.models.Voter;
import dreamVote.dreamdev.data.repositories.VoterRepository;
import dreamVote.dreamdev.dtos.requests.*;
import dreamVote.dreamdev.dtos.responses.*;
import dreamVote.dreamdev.exceptions.DuplicateVoterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dreamVote.dreamdev.utils.Mapper.map;

@Service
public class VoterServiceImpl implements VoterService{
    @Autowired
    private VoterRepository voterRepository;

    @Override
    public VoterRegisterationResponse register(VoterRegisterationRequest voterRegisterationRequest) {
        if(voterRepository.findByEmail(voterRegisterationRequest.getEmail()).isPresent())
            throw new DuplicateVoterException("Voter with email " +
                voterRegisterationRequest.getEmail() + " already exists");
        Voter savedVoter =  voterRepository.save(map(voterRegisterationRequest));
        return map(savedVoter);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public LogoutResponse logout(LogoutRequest logoutRequest) {
        return null;
    }

    @Override
    public VoteForCandidateResponse vote(VoteForCandidateRequest voteForCandidateRequest) {
        return null;
    }

    @Override
    public ApiResponse nominateCandidate(NominateCandidateRequest nominateCandidateRequest) {
        return null;
    }

    @Override
    public ApiResponse getAllCandidates(GetAllCandidatesRequest getAllCandidatesRequest) {
        return null;
    }
}
