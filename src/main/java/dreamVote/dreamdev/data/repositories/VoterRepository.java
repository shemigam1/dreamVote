package dreamVote.dreamdev.data.repositories;

import dreamVote.dreamdev.data.models.Voter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VoterRepository extends MongoRepository<Voter, String> {
    Optional<Voter> findByEmail(String email);

}
