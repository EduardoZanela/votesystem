package br.com.votessystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votessystem.entity.VoteEntity;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Integer>{

	Optional<VoteEntity> findByPautaIdAndAssociatedId(Integer pautaId, Integer associateddoId);

	List<VoteEntity> findByPautaId(Integer pautaId);

}
