package br.com.votessystem.services;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votessystem.client.AssociatedVoteStatusClient;
import br.com.votessystem.dto.AssociatedVoteStatus;
import br.com.votessystem.dto.AssociatedVoteStatusEnum;
import br.com.votessystem.dto.VoteDTO;
import br.com.votessystem.entity.AssociatedEntity;
import br.com.votessystem.entity.PautaEntity;
import br.com.votessystem.entity.VoteEntity;
import br.com.votessystem.repository.AssociatedRepository;
import br.com.votessystem.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VoteService {

	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private AssociatedRepository associatedRepository;
	
	@Autowired
	private PautaService pautaService;
	
	@Autowired
	private AssociatedVoteStatusClient voteStatusClient;
	
	public VoteEntity vote(Integer pautaId,VoteDTO voteDTO) throws Exception {
		log.info("VotoService.iniciarVoto - pautaId: {}, votoDTO:{}", pautaId, voteDTO);
		
		
		AssociatedEntity associatedEntity = getAssociated(voteDTO); 
		PautaEntity pautaEntity = pautaService.getPauta(pautaId);
				
		validateVote(pautaEntity, associatedEntity);
		
		VoteEntity vote = VoteEntity.builder().associated(associatedEntity).pauta(pautaEntity).vote(voteDTO.getVote()).build();
		vote = voteRepository.save(vote);

		log.info("VotoService.iniciarVoto - pautaId: {}, votoEntity:{}",pautaId , vote);
		return vote;
		
	}

	private void validateVote(PautaEntity pauta,AssociatedEntity associado) throws Exception {
		Optional<VoteEntity> voto = voteRepository.findByPautaIdAndAssociatedId(pauta.getId(), associado.getId());

		if (voto.isPresent()) {
			log.error("VotoService.iniciarVoto - Associado ja votou nessa pauta - pauta: {}, associado: {}", pauta, associado);
			throw new Exception("Associado ja votou nessa pauta");
		}
		
		if(pauta.getTime().isBefore(ZonedDateTime.now())) {
			log.error("VotoService.iniciarVoto - Votaçao encerrada - pauta: {}", pauta);
			throw new Exception("Votaçao encerrada");
		}
		
		AssociatedVoteStatus associatedVoteStatus = voteStatusClient.getAssociatedVoteStatus(associado.getCpf());
		if(associatedVoteStatus.getStatus().equals(AssociatedVoteStatusEnum.UNABLE_TO_VOTE)) {
			log.warn("VotoService.getAssociado - Associado impossibilitado de votar - cpf: {}", associado.getCpf());
			throw new Exception("Associado Impossibilitado de Votar");
		}
	}

	private AssociatedEntity getAssociated(VoteDTO votoDTO) throws Exception {
		
		AssociatedEntity associadoEntity = associatedRepository.findByCpf(votoDTO.getCpf()).orElse(new AssociatedEntity(null, votoDTO.getCpf()));
		if(Objects.isNull(associadoEntity.getId())) {
			log.info("VotoService.getAssociado - Novo Associado - cpf: {}", votoDTO.getCpf());
			associatedRepository.save(associadoEntity);
		}
		
		return associadoEntity;
	}

}
