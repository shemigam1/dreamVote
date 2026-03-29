package dreamVote.dreamdev.data.models;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

public class Election {
    @Id
    private String id;
    private ArrayList<Vote> votes;
    private Map<String, Integer> poll;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean isActive;
}
