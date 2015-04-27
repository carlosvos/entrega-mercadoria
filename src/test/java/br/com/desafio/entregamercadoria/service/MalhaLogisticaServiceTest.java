package br.com.desafio.entregamercadoria.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import br.com.desafio.entregamercadoria.business.MalhaLogisticaBusiness;
import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.service.impl.MalhaLogisticaServiceImpl;
import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;
import br.com.desafio.entregamercadoria.service.vo.ConsultaMenorCaminhoInputVO;
import br.com.desafio.entregamercadoria.service.vo.RotaInputVO;

/**
 * Testes unitários da classe de serviço {@link MalhaLogisticaServiceImpl}.
 * 
 * @author Carlos Vinícius
 *
 */
@RunWith(MockitoJUnitRunner.class)  
public class MalhaLogisticaServiceTest {
	
	@InjectMocks
	private MalhaLogisticaService rotaService = new MalhaLogisticaServiceImpl();
	
	@Mock
	private MalhaLogisticaBusiness malhaLogisticaBusiness;
	
	private final String NOME_MAPA = "teste_graph_db";
	
	private final String ORIGEM = "Test_Org";
	private final String DESTINO = "Test_Dst";
	
	private final Double AUTONOMIA = 10d;
	private final Double PRECO_COMBUSTIVEL = 2.5d;
	
	private final Double TOTAL_CUSTO_PERCURSO = 6.75d;


	
	/**
	 * Teste de sucesso de cadastro de uma malha logísitca e suas rotas.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@Test
	public void testcadastraMalhaLogisticaSuccess() throws IOException, ValidationException{
		
		doNothing().when(malhaLogisticaBusiness).cadastraMalhaLogistica(anyString(), anyListOf(RotaTO.class));
		
		Response resposta = rotaService.cadastraMalhaLogistica(this.createCadastraMalhaLogisticaInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.OK.getStatusCode());
	}
	
	/**
	 * Teste falho com o lançamento de uma exceção do tipo
	 * {@link ValidationException} durante o cadastro de uma malha logísitca e suas rotas.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@Test
	public void testcadastraMalhaLogisticaValidationExceptionFailure() throws IOException, ValidationException{
		
		doThrow(ValidationException.class).when(malhaLogisticaBusiness).cadastraMalhaLogistica(anyString(), anyListOf(RotaTO.class));
		
		Response resposta = rotaService.cadastraMalhaLogistica(this.createCadastraMalhaLogisticaInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste falho com o lançamento de uma exceção do tipo
	 * {@link IllegalArgumentException} durante o cadastro de uma malha logísitca e suas rotas.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@Test
	public void testcadastraMalhaLogisticaIllegalArgumentExceptionFailure() throws IOException, ValidationException{
		
		doThrow(IllegalArgumentException.class).when(malhaLogisticaBusiness).cadastraMalhaLogistica(anyString(), anyListOf(RotaTO.class));
		
		Response resposta = rotaService.cadastraMalhaLogistica(this.createCadastraMalhaLogisticaInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste falho com o lançamento de uma exceção do tipo
	 * {@link IOException} durante o cadastro de uma malha logísitca e suas rotas.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@Test
	public void testcadastraMalhaLogisticaIOExceptionFailure() throws IOException, ValidationException{
		
		doThrow(IOException.class).when(malhaLogisticaBusiness).cadastraMalhaLogistica(anyString(), anyListOf(RotaTO.class));
		
		Response resposta = rotaService.cadastraMalhaLogistica(this.createCadastraMalhaLogisticaInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste falho com o lançamento de uma exceção do tipo
	 * {@link Exception} durante o cadastro de uma malha logísitca e suas rotas.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@Test
	public void testcadastraMalhaLogisticaExceptionFailure() throws IOException, ValidationException{
		
		doThrow(Exception.class).when(malhaLogisticaBusiness).cadastraMalhaLogistica(anyString(), anyListOf(RotaTO.class));
		
		Response resposta = rotaService.cadastraMalhaLogistica(this.createCadastraMalhaLogisticaInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste de sucesso de consulta do menor caminho e cálculo do gasto total do percurso.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@Test
	public void testConsultaMenorCaminhoSuccess() throws IOException, ValidationException{
		
		when(malhaLogisticaBusiness.consultarMenorCaminho(anyString(), anyString(), anyString())).thenReturn(this.createListRotaTO());
		when(malhaLogisticaBusiness.calcularCustoPercurso(anyListOf(RotaTO.class),anyDouble(),anyDouble())).thenReturn(TOTAL_CUSTO_PERCURSO);

		Response resposta = rotaService.consultaMenorCaminho(this.createConsultaMenorCaminhoInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.OK.getStatusCode());
	}
	
	/**
	 * Teste falho com uma exeção do tipo {@link ValidationException} 
	 * sendo lançada durante a consulta do menor caminho.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConsultaMenorCaminhoValidationExceptionFailure() throws IOException, ValidationException{
		
		when(malhaLogisticaBusiness.consultarMenorCaminho(anyString(), anyString(), anyString())).thenThrow(ValidationException.class);
		
		Response resposta = rotaService.consultaMenorCaminho(this.createConsultaMenorCaminhoInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste falho com uma exeção do tipo {@link IllegalArgumentException} 
	 * sendo lançada durante a consulta do menor caminho.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConsultaMenorCaminhoIllegalArgumentExceptionFailure() throws IOException, ValidationException{
		
		when(malhaLogisticaBusiness.consultarMenorCaminho(anyString(), anyString(), anyString())).thenThrow(IllegalArgumentException.class);
		
		Response resposta = rotaService.consultaMenorCaminho(this.createConsultaMenorCaminhoInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste falho com uma exeção do tipo {@link IOException} 
	 * sendo lançada durante a consulta do menor caminho.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConsultaMenorCaminhoIOExceptionFailure() throws IOException, ValidationException{
		
		when(malhaLogisticaBusiness.consultarMenorCaminho(anyString(), anyString(), anyString())).thenThrow(IOException.class);
		
		Response resposta = rotaService.consultaMenorCaminho(this.createConsultaMenorCaminhoInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste falho com uma exeção do tipo {@link Exception} 
	 * sendo lançada durante a consulta do menor caminho.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConsultaMenorCaminhoExceptionFailure() throws IOException, ValidationException{
		
		when(malhaLogisticaBusiness.consultarMenorCaminho(anyString(), anyString(), anyString())).thenThrow(Exception.class);
		
		Response resposta = rotaService.consultaMenorCaminho(this.createConsultaMenorCaminhoInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * Teste falho com uma exeção do tipo {@link IllegalArgumentException} 
	 * sendo lançada durante o cálculo do custo total do percurso.
	 * 
	 * @throws IOException
	 * @throws ValidationException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testCalcularCustoPercursoIllegalArgumentExceptionFailure() throws IOException, ValidationException{
		
		when(malhaLogisticaBusiness.consultarMenorCaminho(anyString(), anyString(), anyString())).thenReturn(this.createListRotaTO());
		when(malhaLogisticaBusiness.calcularCustoPercurso(anyListOf(RotaTO.class),anyDouble(),anyDouble())).thenThrow(IllegalArgumentException.class);
		
		Response resposta = rotaService.consultaMenorCaminho(this.createConsultaMenorCaminhoInputVO());
		
		Assert.assertNotNull(resposta.getEntity());
		Assert.assertTrue(resposta.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}
	
	/**
	 * cria um objeto {@link ConsultaMenorCaminhoInputVO}
	 * 
	 * @return uma instância de {@link ConsultaMenorCaminhoInputVO} 
	 */
	private ConsultaMenorCaminhoInputVO createConsultaMenorCaminhoInputVO() {
		ConsultaMenorCaminhoInputVO consultaInput = new ConsultaMenorCaminhoInputVO();
		consultaInput.setNomeMapa(NOME_MAPA);
		consultaInput.setOrigem(ORIGEM);
		consultaInput.setDestino(DESTINO);
		consultaInput.setAutonomia(AUTONOMIA);
		consultaInput.setVlrCombustivel(PRECO_COMBUSTIVEL);
		return consultaInput;
	}

	/**
	 * cria um objeto {@link CadastraMalhaLogisticaInputVO}
	 * 
	 * @return uma instância de {@link CadastraMalhaLogisticaInputVO} 
	 */
	private CadastraMalhaLogisticaInputVO createCadastraMalhaLogisticaInputVO(){
		CadastraMalhaLogisticaInputVO cadastroInput = new CadastraMalhaLogisticaInputVO();
		cadastroInput.setNomMalhaLogistica(NOME_MAPA);
		cadastroInput.setRotas(createListRotaInputVO());
		return cadastroInput;
	}
	
	/**
	 * cria uma lista de objetos {@link RotaInputVO}
	 * 
	 * @return lista de {@link RotaInputVO}
	 */
	private List<RotaInputVO> createListRotaInputVO(){
		List<RotaInputVO> listRotaInputVO = new ArrayList<>();
		listRotaInputVO.add(this.createRotaInputVO("A","B",10d));
		listRotaInputVO.add(this.createRotaInputVO("B","D",15d));
		listRotaInputVO.add(this.createRotaInputVO("A","C",20d));
		listRotaInputVO.add(this.createRotaInputVO("C","D",30d));
		listRotaInputVO.add(this.createRotaInputVO("B","E",50d));
		listRotaInputVO.add(this.createRotaInputVO("D","E",30d));
		listRotaInputVO.add(this.createRotaInputVO("C","E",0d));
		return listRotaInputVO;
	}

	/**
	 * cria um objeto {@link RotaInputVO}
	 * 
	 * @return uma instância de {@link RotaInputVO} 
	 */
	private RotaInputVO createRotaInputVO(String origem, String destino, Double distancia) {
		RotaInputVO rotaInputVO = new RotaInputVO();
		rotaInputVO.setOrigem(origem);
		rotaInputVO.setDestino(destino);
		rotaInputVO.setDistancia(distancia);
		return rotaInputVO;
	}
	
	/**
	 * cria uma lista de objetos {@link RotaTO}
	 * 
	 * @return lista de {@link RotaTO}
	 */
	private List<RotaTO> createListRotaTO() {
		List<RotaTO> listRotaTO = new ArrayList<>();
		listRotaTO.add(this.createRotaTO("A","B",10d));
		listRotaTO.add(this.createRotaTO("B","D",15d));
		listRotaTO.add(this.createRotaTO("A","C",20d));
		listRotaTO.add(this.createRotaTO("C","D",30d));
		listRotaTO.add(this.createRotaTO("B","E",50d));
		listRotaTO.add(this.createRotaTO("D","E",30d));
		listRotaTO.add(this.createRotaTO("C","E",0d));
		return listRotaTO;
	}
	
	/**
	 * cria um objeto {@link RotaTO}
	 * 
	 * @return uma instância de {@link RotaTO} 
	 */
	private RotaTO createRotaTO(String origem, String destino, Double distancia){
		return new RotaTO(origem, destino, distancia);
	}

}
