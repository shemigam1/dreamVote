package dreamVote.dreamdev.services;

import dreamVote.dreamdev.data.repositories.CandidateRepository;
import dreamVote.dreamdev.data.repositories.ElectionRepository;
import dreamVote.dreamdev.data.repositories.VoterRepository;
import dreamVote.dreamdev.dtos.requests.CreateElectionRequest;
import dreamVote.dreamdev.dtos.requests.LoginRequest;
import dreamVote.dreamdev.dtos.requests.NominateCandidateRequest;
import dreamVote.dreamdev.dtos.requests.VoterRegisterationRequest;
import dreamVote.dreamdev.dtos.responses.CreateElectionResponse;
import dreamVote.dreamdev.dtos.responses.VoterRegisterationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ElectionServiceImplTest {
    @Autowired
    private ElectionService electionService;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private VoterService voterService;
    @Autowired
    private VoterRepository voterRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @BeforeEach
    public void setup() {
        electionRepository.deleteAll();
        voterRepository.deleteAll();

        VoterRegisterationRequest voterRegisterationRequest = new VoterRegisterationRequest();
        voterRegisterationRequest.setEmail("email");
        voterRegisterationRequest.setFirstName("John");
        voterRegisterationRequest.setLastName("Doe");
        voterRegisterationRequest.setPassword("password");
        voterService.register(voterRegisterationRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email");
        loginRequest.setPassword("password");
        voterService.login(loginRequest);
    }

    @Test
    public void createElection_successTest() {
        String voterId = voterRepository.findByEmail("email").get().getId();
        CreateElectionRequest createElectionRequest = new CreateElectionRequest();
        createElectionRequest.setVoterId(voterId);
        assertEquals(0L, electionRepository.count());
        electionService.createElection(createElectionRequest);
        assertEquals(1L, electionRepository.count());
    }

    @Test
    public void createElection_isNotActiveTest(){
        String voterId = voterRepository.findByEmail("email").get().getId();
        CreateElectionRequest createElectionRequest = new CreateElectionRequest();
        createElectionRequest.setVoterId(voterId);
        CreateElectionResponse response = electionService.createElection(createElectionRequest);
        String electionId = response.getElectionId();
        assertFalse(electionRepository.findById(electionId).get().isActive());
    }

    @Test
    public void createElection_activateElectionTest(){
        String voterId = voterRepository.findByEmail("email").get().getId();
        CreateElectionRequest createElectionRequest = new CreateElectionRequest();
        createElectionRequest.setVoterId(voterId);
        CreateElectionResponse response = electionService.createElection(createElectionRequest);
        String electionId = response.getElectionId();
        assertFalse(electionRepository.findById(electionId).get().isActive());
        electionService.activate(electionId);
        assertTrue(electionRepository.findById(electionId).get().isActive());
    }

    private String createActiveElection() {
        String voterId = voterRepository.findByEmail("email").get().getId();
        CreateElectionRequest createElectionRequest = new CreateElectionRequest();
        createElectionRequest.setVoterId(voterId);
        CreateElectionResponse response = electionService.createElection(createElectionRequest);
        String electionId = response.getElectionId();
        electionService.activate(electionId);
        return electionId;
    }

    @Test
    public void nominateCandidate_successTest() {
        String electionId = createActiveElection();

        NominateCandidateRequest request = new NominateCandidateRequest();
        request.setElectionId(electionId);
        request.setFirstName("Alice");
        request.setLastName("Smith");

        assertEquals(0L, candidateRepository.count());
        electionService.nominateCandidate(request);
        assertEquals(1L, candidateRepository.count());
    }


}