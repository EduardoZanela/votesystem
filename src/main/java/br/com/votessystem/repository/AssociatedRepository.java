package br.com.votessystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votessystem.entity.AssociatedEntity;

@Repository
public interface AssociatedRepository extends JpaRepository<AssociatedEntity, Integer>{

	Optional<AssociatedEntity> findByCpf(String cpf);

}
