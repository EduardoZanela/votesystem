package br.com.votessystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votessystem.dto.PautaDTO;
import br.com.votessystem.dto.PautaResult;
import br.com.votessystem.dto.VoteDTO;
import br.com.votessystem.entity.PautaEntity;
import br.com.votessystem.entity.VoteEntity;
import br.com.votessystem.services.PautaService;
import br.com.votessystem.services.VoteService;

@RestController
@RequestMapping("/v1/")
public class PautaController {
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private VoteService voteService;

	@GetMapping(value = "pauta/{pautaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PautaEntity> getPauta(@PathVariable(value = "pautaId") Integer pautaId) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(pautaService.getPauta(pautaId));
	}
	
	@GetMapping(value = "pauta/{pautaId}/resultado", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PautaResult> getPautaResult(@PathVariable(value = "pautaId") Integer pautaId) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(pautaService.getPautaResult(pautaId));
	}
	
	@PostMapping(value = "pauta", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PautaEntity> postPauta(@RequestBody PautaDTO pauta) {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(pautaService.postPauta(pauta));
	}
	
	@PostMapping(value = "pauta/{pautaId}/votar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<VoteEntity> votePauta(@PathVariable(value = "pautaId") Integer pautaId,@RequestBody VoteDTO voteDTO) throws Exception {
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(voteService.vote(pautaId,voteDTO));
	}
	
}
