package dreamVote.dreamdev.services;

import dreamVote.dreamdev.data.models.Candidate;
import dreamVote.dreamdev.data.models.Election;
import dreamVote.dreamdev.data.models.Vote;
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

import static dreamVote.dreamdev.utils.Mapper.*;

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
        Optional<Voter> optionalVoter =
                voterRepository.findById(voteForCandidateRequest.getVoterID());
        if (optionalVoter.isEmpty()) throw new InvalidLoginDetailsException("Voter not found");
        if (!optionalVoter.get().isLoggedIn()) throw new InvalidLoginDetailsException("Voter is not logged in");

        Optional<Election> optionalElection = electionRepository.findById(voteForCandidateRequest.getElectionId());
        if (optionalElection.isEmpty()) throw new InvalidElectionIdException("Election does not exist");
        if (!optionalElection.get().isActive()) throw new InvalidElectionIdException("Election is not active");

        Optional<Candidate> optionalCandidate = candidateRepository.findByLastName(voteForCandidateRequest.getCandidateLastName());
        if (optionalCandidate.isEmpty()) throw new InvalidElectionIdException("Candidate not found");

        boolean alreadyVoted = voteRepository.findByVoterIDAndElectionId(
                voteForCandidateRequest.getVoterID(),
                voteForCandidateRequest.getElectionId()
        ).isPresent();
        if (alreadyVoted) throw new InvalidLoginDetailsException("Voter has already voted in this election");

        Vote vote = new Vote();
        vote.setVoterID(voteForCandidateRequest.getVoterID());
        vote.setCandidateId(optionalCandidate.get().getId());
        vote.setElectionId(voteForCandidateRequest.getElectionId());
        Vote savedVote = voteRepository.save(vote);

        Election election = optionalElection.get();
        election.getVotes().add(savedVote);

        String candidateName = optionalCandidate.get().getLastName();
        election.getPoll().merge(candidateName, 1, Integer::sum);

        electionRepository.save(election);

        return mapToVoteForCandidateResponse(optionalVoter.get(), optionalCandidate.get());
    }

    @Override
    public ApiResponse nominateCandidate(NominateCandidateRequest nominateCandidateRequest) {
        Optional<Election> optionalElection = electionRepository.findById(nominateCandidateRequest.getElectionId());
        if (optionalElection.isEmpty()) throw new InvalidElectionIdException("Election does not exist");

        Candidate candidate = map(nominateCandidateRequest);
        Candidate savedCandidate = candidateRepository.save(candidate);

        Election election = optionalElection.get();
        election.getCandidates().add(savedCandidate);
        electionRepository.save(election);
        return new ApiResponse(true, election.getCandidates());
    }

    @Override
    public ApiResponse getAllCandidates(GetAllCandidatesRequest getAllCandidatesRequest) {
        Optional<Voter> optionalVoter = voterRepository.findById(getAllCandidatesRequest.getVoterId());
        if (optionalVoter.isEmpty()) throw new InvalidLoginDetailsException("Voter not found");
        if (!optionalVoter.get().isLoggedIn()) throw new InvalidLoginDetailsException("Voter is not logged in");

        Optional<Election> optionalElection = electionRepository.findById(getAllCandidatesRequest.getElectionId());
        if (optionalElection.isEmpty()) throw new InvalidElectionIdException("Election does not exist");

        return new ApiResponse(true, optionalElection.get().getCandidates());
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

    @Override
    public ApiResponse getPolls(String electionId) {
        Optional<Election> optionalElection = electionRepository.findById(electionId);
        if (optionalElection.isEmpty()) throw new InvalidElectionIdException("Election does not exist");

        return new ApiResponse(true, optionalElection.get().getPoll());
    }
}
