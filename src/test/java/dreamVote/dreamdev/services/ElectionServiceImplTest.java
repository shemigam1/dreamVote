package dreamVote.dreamdev.services;

import dreamVote.dreamdev.data.repositories.ElectionRepository;
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

    @BeforeEach
    public void setup() {
        electionRepository.deleteAll();
    }

    @Test
    public void createElection_successTest() {
        assertEquals(0L, electionRepository.count());
        electionService.createElection();
        assertEquals(1L, electionRepository.count());
    }
}