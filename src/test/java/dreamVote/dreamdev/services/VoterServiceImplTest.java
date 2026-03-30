package dreamVote.dreamdev.services;

import dreamVote.dreamdev.data.repositories.VoterRepository;
import dreamVote.dreamdev.dtos.requests.VoterRegisterationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VoterServiceImplTest {
    @Autowired
    private VoterService voterService;
    private VoterRegisterationRequest voterRegisterationRequest;
    @Autowired
    private VoterRepository voterRepository;
    @BeforeEach
    public void setup(){
        voterRepository.deleteAll();
        voterRegisterationRequest = new VoterRegisterationRequest();
        voterRegisterationRequest.setEmail("email");
        voterRegisterationRequest.setFirstName("John");
        voterRegisterationRequest.setLastName("Doe");
        voterRegisterationRequest.setPassword("password");
    }

    @Test
    public void register_successTest(){
        assertEquals(0L, voterRepository.count());
        voterService.register(voterRegisterationRequest);
        assertEquals(1L, voterRepository.count());
    }

}