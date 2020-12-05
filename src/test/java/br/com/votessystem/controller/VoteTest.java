package br.com.votessystem.controller;

import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.votessystem.client.AssociatedVoteStatusClient;
import br.com.votessystem.dto.AssociatedVoteStatus;
import br.com.votessystem.dto.AssociatedVoteStatusEnum;
import br.com.votessystem.dto.VoteDTO;
import br.com.votessystem.entity.PautaEntity;
import br.com.votessystem.entity.VoteEntity;
import br.com.votessystem.repository.AssociatedRepository;
import br.com.votessystem.repository.PautaRepository;
import br.com.votessystem.repository.VoteRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class VoteTest {

	private static final String NAME = "nome";
	private static final String CPF = "123456798";

	@Autowired
	private PautaController pautaController;

	@MockBean
	private VoteRepository voteRepository;
	
	@MockBean
	private PautaRepository pautaRepository;
	
	@MockBean
	private AssociatedRepository associatedRepository;
	
	@MockBean
	private AssociatedVoteStatusClient voteStatusClient;

	@Before
	public void setup() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	
	@Test
	public void votoTest() throws Exception {

		when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new PautaEntity(1,NAME,ZonedDateTime.now().plusMonths(1))));
		when(voteRepository.findByPautaIdAndAssociatedId(Mockito.any(),Mockito.any())).thenReturn(Optional.empty());
		when(voteStatusClient.getAssociatedVoteStatus(Mockito.anyString())).thenReturn(new AssociatedVoteStatus(AssociatedVoteStatusEnum.ABLE_TO_VOTE));

		ResponseEntity<VoteEntity> responseEntity = pautaController.votePauta(1, new VoteDTO(CPF,false));

		Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void votoTestNotAble() throws Exception {

		when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new PautaEntity(1,NAME,ZonedDateTime.now().plusMonths(1))));
		when(voteRepository.findByPautaIdAndAssociatedId(Mockito.any(),Mockito.any())).thenReturn(Optional.empty());
		when(voteStatusClient.getAssociatedVoteStatus(Mockito.anyString())).thenReturn(new AssociatedVoteStatus(AssociatedVoteStatusEnum.UNABLE_TO_VOTE));

		Assertions.assertThrows(Exception.class, () -> pautaController.votePauta(1, new VoteDTO(CPF,false)));
	}
	
	@Test
	public void votoTestTestException() throws Exception {
		when(voteRepository.findByPautaIdAndAssociatedId(Mockito.any(),Mockito.any())).thenReturn(Optional.of(new VoteEntity()));
		when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new PautaEntity(1,NAME,ZonedDateTime.now().plusMonths(1))));

		Assertions.assertThrows(Exception.class, () -> pautaController.votePauta(1, new VoteDTO(CPF,false)));

	}
	
	@Test
	public void votoTestTestException2() throws Exception {
		when(voteRepository.findByPautaIdAndAssociatedId(Mockito.any(),Mockito.any())).thenReturn(Optional.empty());
		when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new PautaEntity(1,NAME,ZonedDateTime.now().minusMonths(1))));

		Assertions.assertThrows(Exception.class, () -> pautaController.votePauta(1, new VoteDTO(CPF,false)));

	}
}
