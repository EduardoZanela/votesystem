package br.com.votessystem.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

	@NotBlank
	private String cpf;
	
	@NotBlank
	private Boolean vote;
	
}
