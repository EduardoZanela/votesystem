package br.com.votessystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votessystem.entity.PautaEntity;

@Repository
public interface PautaRepository extends JpaRepository<PautaEntity, Integer>{

	Optional<PautaEntity> findById(Integer id);

}
