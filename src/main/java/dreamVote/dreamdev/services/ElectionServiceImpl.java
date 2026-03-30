package dreamVote.dreamdev.services;

import dreamVote.dreamdev.data.models.Candidate;
import dreamVote.dreamdev.data.models.Election;
import dreamVote.dreamdev.data.models.Voter;
import dreamVote.dreamdev.data.repositories.CandidateRepository;
import dreamVote.dreamdev.data.repositories.ElectionRepository;
import dreamVote.dreamdev.data.repositories.VoteRepository;
import dreamVote.dreamdev.data.repositories.VoterRepository;
import dreamVote.dreamdev.dtos.requests.CreateElectionRequest;
import dreamVote.dreamdev.dtos.requests.GetAllCandidatesRequest;
import dreamVote.dreamdev.dtos.requests.NominateCandidateRequest;
import dreamVote.dreamdev.dtos.requests.VoteForCandidateRequest;
import dreamVote.dreamdev.dtos.responses.ApiResponse;
import dreamVote.dreamdev.dtos.responses.CreateElectionResponse;
import dreamVote.dreamdev.dtos.responses.VoteForCandidateResponse;
import dreamVote.dreamdev.exceptions.InvalidElectionIdException;
import dreamVote.dreamdev.exceptions.InvalidLoginDetailsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static dreamVote.dreamdev.utils.Mapper.mapToCreateElectionResponse;

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

    private void validateVoter(Voter voter){

    }

    @Override
    public VoteForCandidateResponse vote(VoteForCandidateRequest voteForCandidateRequest) {
        return null;
    }

    @Override
    public ApiResponse nominateCandidate(NominateCandidateRequest nominateCandidateRequest) {
        Candidate newCandidate = new Candidate();
        newCandidate.setFirstName(nominateCandidateRequest.getFirstName());
    }

    @Override
    public ApiResponse getAllCandidates(GetAllCandidatesRequest getAllCandidatesRequest) {
        return null;
    }

    @Override
    public CreateElectionResponse createElection(CreateElectionRequest createElectionRequest) {
        Optional<Voter> optionalVoter = voterRepository.findById(createElectionRequest.getVoterId());
        if (optionalVoter.isEmpty()) throw new InvalidLoginDetailsException("Voter not found");
        if (!optionalVoter.get().isLoggedIn()) throw new InvalidLoginDetailsException("Voter is not logged in");

        Election election = new Election();
        election.setActive(false);
        Election savedElection = electionRepository.save(election);
        return mapToCreateElectionResponse(savedElection);
    }

    @Override
    public ApiResponse activate(String electionId) {
        Optional<Election> savedElection = electionRepository.findById(electionId);
        if (savedElection.isEmpty()) throw new InvalidElectionIdException("Election does not exist");

        Election election = savedElection.get();
        if (election.isActive()) throw new InvalidElectionIdException("Election is already Active");
        election.setActive(true);
        electionRepository.save(election);
        return new ApiResponse(true, "Election activated successfully");
    }
}
