package dreamVote.dreamdev.data.repositories;

import dreamVote.dreamdev.data.models.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VoteRepository extends MongoRepository <Vote, String> {
}
