package br.com.votessystem.dto;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaResult {

	private String name;
	
	private ZonedDateTime time;
	
	private Long votesYes;

	private Long votesNo;

	
}
