package dreamVote.dreamdev.services;

import dreamVote.dreamdev.dtos.requests.GetAllCandidatesRequest;
import dreamVote.dreamdev.dtos.requests.NominateCandidateRequest;
import dreamVote.dreamdev.dtos.requests.VoteForCandidateRequest;
import dreamVote.dreamdev.dtos.responses.ApiResponse;
import dreamVote.dreamdev.dtos.responses.VoteForCandidateResponse;

public interface ElectionService {
    VoteForCandidateResponse vote(VoteForCandidateRequest voteForCandidateRequest);

    ApiResponse nominateCandidate(NominateCandidateRequest nominateCandidateRequest);

    ApiResponse getAllCandidates(GetAllCandidatesRequest getAllCandidatesRequest);
}
