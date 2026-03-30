package dreamVote.dreamdev.dtos.requests;

import lombok.Data;

@Data
public class VoteForCandidateRequest {
    private String voterID;
    private String electionId;
    private String candidateLastName;
}
