package br.com.votessystem.schedule;

import java.util.TimerTask;

import org.springframework.amqp.core.AmqpTemplate;

import br.com.votessystem.dto.PautaResult;
import br.com.votessystem.services.PautaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleTask extends TimerTask {
	
	private Integer pautaId;
	
	private PautaService service;
	
	private AmqpTemplate rabbitTemplate;
	
	public ScheduleTask(PautaService service, AmqpTemplate rabbitTemplate, Integer pautaId) {
		this.service = service;
		this.rabbitTemplate = rabbitTemplate;
		this.pautaId = pautaId;
	}

	@Override
	public void run() {
		try {
			PautaResult pautaResult = service.getPautaResult(pautaId);
			rabbitTemplate.convertAndSend("pauta", "pautaresultroutingkey", pautaResult);
			log.info("ScheduleTask.run - resultado para {} - {}", pautaResult.getName(), pautaResult);
		} catch (Exception e) {
			log.error("ScheduleTask.run - error ao buscar resultado da pauta", e);
		}
		
	}

}
