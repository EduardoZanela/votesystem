package br.com.votessystem.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;
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
import br.com.votessystem.dto.PautaDTO;
import br.com.votessystem.dto.PautaResult;
import br.com.votessystem.entity.AssociatedEntity;
import br.com.votessystem.entity.PautaEntity;
import br.com.votessystem.entity.VoteEntity;
import br.com.votessystem.repository.PautaRepository;
import br.com.votessystem.repository.VoteRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class PautaTest {

		@Autowired
		private PautaController pautaController;

		@MockBean
		private PautaRepository pautaRepository;
		
		@MockBean
		private VoteRepository voteRepository;
		
		@MockBean
		private AssociatedVoteStatusClient voteStatusClient;

		@Before
		public void setup() {
			MockHttpServletRequest request = new MockHttpServletRequest();
			RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		}
		@Test
		public void getPautaTest() throws Exception {

			when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new PautaEntity()));

			ResponseEntity<PautaEntity> responseEntity = pautaController.getPauta(1);

			Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		}
		
		@Test
		public void getPautaTestException() throws Exception {

			when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
			Assertions.assertThrows(Exception.class, () -> pautaController.getPauta(1));

		}
		
		@Test
		public void postPautaTest() throws Exception {

			when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new PautaEntity()));

			ResponseEntity<PautaEntity> responseEntity = pautaController.postPauta(new PautaDTO("nome", 2L));

			Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		}
		
		@Test
		public void getPautaResultadoTest() throws Exception {

			when(voteRepository.findByPautaId(Mockito.any())).thenReturn(Collections.singletonList(new VoteEntity(1,new PautaEntity(),new AssociatedEntity(),true)));

			when(pautaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new PautaEntity()));

			ResponseEntity<PautaResult> responseEntity = pautaController.getPautaResult(1);

			Assert.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		}
		
}
