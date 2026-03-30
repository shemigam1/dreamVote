package dreamVote.dreamdev.data.repositories;

import dreamVote.dreamdev.data.models.Election;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ElectionRepository extends MongoRepository <Election, String> {
//    Optional<Election> updatePoll (String )
}
