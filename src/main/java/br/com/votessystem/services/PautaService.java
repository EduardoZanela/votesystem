package br.com.votessystem.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.votessystem.dto.PautaDTO;
import br.com.votessystem.dto.PautaResult;
import br.com.votessystem.entity.PautaEntity;
import br.com.votessystem.entity.VoteEntity;
import br.com.votessystem.repository.PautaRepository;
import br.com.votessystem.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VoteRepository votoRepository;

	public PautaEntity getPauta(Integer pautaId) throws Exception {
	
		log.info("PautaService.getPauta - pautaId: {}", pautaId);

		Optional<PautaEntity> pautaEntity = pautaRepository.findById(pautaId);
		
		if (pautaEntity.isEmpty()) {
			log.error("PautaService.getPauta - Pauta nao encontrada - pautaId: {}", pautaId);
			throw new Exception("Pauta nao encontrada");
		
		}
		
		log.info("PautaService.getPauta - pautaId: {}", pautaId);
		return pautaEntity.get();
	}

	public PautaEntity postPauta(PautaDTO pauta) {
		log.info("PautaService.postPauta - PautaDTO: {}", pauta);
		
		PautaEntity pautaEntity = new PautaEntity();
		pautaEntity.setName(pauta.getName());
		pautaEntity.setTime(ZonedDateTime.now().plusMinutes(1));
		if(!ObjectUtils.isEmpty(pauta.getMinutes())) {
			pautaEntity.setTime(ZonedDateTime.now().plusMinutes(pauta.getMinutes()));
		}
		pautaRepository.save(pautaEntity);
		log.info("PautaService.postPauta - PautaEntity: {}", pautaEntity);

		return pautaEntity;
	}

	public PautaResult getPautaResult(Integer pautaId) throws Exception {		
		
		PautaResult pautaResult = new PautaResult(); 
		log.info("PautaService.getPautaResultado - pautaId: {}", pautaId);

		PautaEntity pauta = getPauta(pautaId);
		pautaResult.setName(pauta.getName());
		pautaResult.setTime(pauta.getTime());
		
		List<VoteEntity> votes = votoRepository.findByPautaId(pauta.getId());
		Long votesYes = votes.stream().filter(VoteEntity::isVote).count();
		
		pautaResult.setVotesYes(votesYes);
		pautaResult.setVotesNo(votes.size() - votesYes);
		
		log.info("PautaService.getPautaResultado - pautaResultado: {}", pautaResult);

		return pautaResult;
	}

}
