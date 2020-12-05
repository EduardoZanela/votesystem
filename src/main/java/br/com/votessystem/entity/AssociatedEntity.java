package br.com.votessystem.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ASSOCIATED")
public class AssociatedEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ASSOCIATED_ID_SEQ")
	@SequenceGenerator(name = "ASSOCIATED_ID_SEQ", sequenceName = "ASSOCIATED_ID_SEQ", allocationSize = 1)
	@Column(name = "ASSOCIATED_ID")
	private Integer id;

	@Column(name = "CPF")
	private String cpf;
	
}
