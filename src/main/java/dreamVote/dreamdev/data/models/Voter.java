package dreamVote.dreamdev.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Voter {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean isLoggedIn;
}
