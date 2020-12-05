package br.com.votessystem.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

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
@Table(name = "PAUTA")
public class PautaEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAUTA_ID_SEQ")
	@SequenceGenerator(name = "PAUTA_ID_SEQ", sequenceName = "PAUTA_ID_SEQ", allocationSize = 1)
	@Column(name = "PAUTA_ID")
	private Integer id;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "TIME")
	private ZonedDateTime time;

}
