package br.com.votessystem.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import br.com.votessystem.dto.AssociatedVoteStatus;

@Component
public class AssociatedVoteStatusClient {
	
	private RestTemplate restTemplate;
	
	@Value("${votessystem.associatedvotestatus.url}")
	private String url;
	
	public AssociatedVoteStatusClient() {
		this.restTemplate = new RestTemplate();
	}
	
	@GetMapping(value = "${votessystem.associatedvotestatus.votestatus}")
	public AssociatedVoteStatus getAssociatedVoteStatus(@PathVariable("cpf") String cpf) {
		ResponseEntity<AssociatedVoteStatus> response = restTemplate.getForEntity(url+"/"+cpf, AssociatedVoteStatus.class);
		return response.getBody();
		
	}
	
}
