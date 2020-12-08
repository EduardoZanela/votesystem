package br.com.votessystem.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VOTO")
public class VoteEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VOTE_ID_SEQ")
	@SequenceGenerator(name = "VOTE_ID_SEQ", sequenceName = "VOTE_ID_SEQ", allocationSize = 1)
	@Column(name = "VOTE_ID")
	private Integer id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PAUTA_ID")
	private PautaEntity pauta;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ASSOCIATED_ID",nullable = true)
	private AssociatedEntity associated;

	@Column(name = "VOTE")
	private boolean vote;
	
}
