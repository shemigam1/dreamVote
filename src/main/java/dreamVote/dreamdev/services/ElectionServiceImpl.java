package dreamVote.dreamdev.services;

import dreamVote.dreamdev.data.repositories.CandidateRepository;
import dreamVote.dreamdev.data.repositories.ElectionRepository;
import dreamVote.dreamdev.data.repositories.VoteRepository;
import dreamVote.dreamdev.data.repositories.VoterRepository;
import dreamVote.dreamdev.dtos.requests.GetAllCandidatesRequest;
import dreamVote.dreamdev.dtos.requests.NominateCandidateRequest;
import dreamVote.dreamdev.dtos.requests.VoteForCandidateRequest;
import dreamVote.dreamdev.dtos.responses.ApiResponse;
import dreamVote.dreamdev.dtos.responses.VoteForCandidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionServiceImpl implements ElectionService{
    @Autowired
    private ElectionRepository electionRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private VoterRepository voterRepository;
    @Autowired
    private VoteRepository voteRepository;

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
