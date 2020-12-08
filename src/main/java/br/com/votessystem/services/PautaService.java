package br.com.votessystem.services;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.votessystem.dto.PautaDTO;
import br.com.votessystem.dto.PautaResult;
import br.com.votessystem.entity.PautaEntity;
import br.com.votessystem.entity.VoteEntity;
import br.com.votessystem.repository.PautaRepository;
import br.com.votessystem.repository.VoteRepository;
import br.com.votessystem.schedule.ScheduleTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VoteRepository votoRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

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
		PautaEntity pautaEntity = new PautaEntity(pauta.getName(), ZonedDateTime.now().plusMinutes(1));
		
		if(!ObjectUtils.isEmpty(pauta.getMinutes())) {
			pautaEntity.setTime(ZonedDateTime.now().plusMinutes(pauta.getMinutes()));
		}
		pautaEntity = pautaRepository.save(pautaEntity);
		
		ScheduleTask task = new ScheduleTask(this, rabbitTemplate, pautaEntity.getId());
		Timer t = new Timer();
		t.schedule(task, Date.from(pautaEntity.getTime().toInstant()));
		
		log.info("PautaService.postPauta - PautaEntity: {}", pautaEntity);

		return pautaEntity;
	}

	public PautaResult getPautaResult(Integer pautaId) throws Exception {		
		
		log.info("PautaService.getPautaResultado - pautaId: {}", pautaId);

		PautaEntity pauta = getPauta(pautaId);
		
		List<VoteEntity> votes = votoRepository.findByPautaId(pauta.getId());
		Long votesYes = votes.stream().filter(VoteEntity::isVote).count();
		PautaResult pautaResult = PautaResult.builder().name(pauta.getName()).time(pauta.getTime()).votesYes(votesYes).votesNo(votes.size() - votesYes).build();

		log.info("PautaService.getPautaResultado - pautaResultado: {}", pautaResult);

		return pautaResult;
	}

}
