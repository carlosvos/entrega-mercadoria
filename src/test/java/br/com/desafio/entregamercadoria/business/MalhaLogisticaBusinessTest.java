package br.com.desafio.entregamercadoria.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyListOf;
import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.impl.MalhaLogisticaBusinessImpl;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.dao.EdgeWeightedDigraphDAO;
import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;

@RunWith(MockitoJUnitRunner.class)  
public class MalhaLogisticaBusinessTest {
	
	@InjectMocks
	private MalhaLogisticaBusiness malhaLogisticaBusiness = new MalhaLogisticaBusinessImpl();
	
	@Mock
	private EdgeWeightedDigraphDAO edgeWeightedDigraphDAO;
	
	private final String NOME_MAPA = "teste_graph_db";
	
	private final String ORIGEM = "Test_Org";
	private final String DESTINO = "Test_Dst";
	
	private final Double DISTANCIA = 11d;
	private final Double DISTANCIA_NEGATIVA = -11d;
	private final Double AUTONOMIA = 10d;
	private final Double AUTONOMIA_NEGATIVA = -10d;
	private final Double PRECO_COMBUSTIVEL = 2.5d;
	private final Double PRECO_COMBUSTIVEL_NEGATIVO = -2.5d;


	@Test
	public void testCadastraMalhaLogisticaSuccess() throws IOException, ValidationException{
		String nomeMapa = NOME_MAPA;
		List<RotaTO> listRotaTO = this.createListRotaTO();

		doNothing().when(edgeWeightedDigraphDAO).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=IOException.class)
	public void testCadastraMalhaLogisticaFailure() throws IOException, ValidationException{
		String nomeMapa = NOME_MAPA;
		List<RotaTO> listRotaTO = this.createListRotaTO();

		doThrow(IOException.class).when(edgeWeightedDigraphDAO).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCadastraMalhaLogisticaNomeMapaNuloFailure() throws IOException, ValidationException{
		String nomeMapa = null;
		List<RotaTO> listRotaTO = this.createListRotaTO();

		verify(edgeWeightedDigraphDAO, times(0)).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraMalhaLogisticaNomeMapaVazioFailure() throws IOException, ValidationException{
		String nomeMapa = StringUtils.EMPTY;
		List<RotaTO> listRotaTO = this.createListRotaTO();

		verify(edgeWeightedDigraphDAO, times(0)).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCadastraMalhaLogisticaOrigemNuloFailure() throws IOException, ValidationException{
		String nomeMapa = NOME_MAPA;
		List<RotaTO> listRotaTO = this.createListRotaTO();
		listRotaTO.add(this.createRotaTO(null, DESTINO, DISTANCIA));
		
		verify(edgeWeightedDigraphDAO, times(0)).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraMalhaLogisticaOrigemVazioFailure() throws IOException, ValidationException{
		String nomeMapa = NOME_MAPA;
		List<RotaTO> listRotaTO = this.createListRotaTO();
		listRotaTO.add(this.createRotaTO(StringUtils.EMPTY, DESTINO, DISTANCIA));

		verify(edgeWeightedDigraphDAO, times(0)).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCadastraMalhaLogisticaDestinoNuloFailure() throws IOException, ValidationException{
		String nomeMapa = NOME_MAPA;
		List<RotaTO> listRotaTO = this.createListRotaTO();
		listRotaTO.add(this.createRotaTO(ORIGEM, null, DISTANCIA));

		verify(edgeWeightedDigraphDAO, times(0)).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraMalhaLogisticaDestinoVazioFailure() throws IOException, ValidationException{
		String nomeMapa = NOME_MAPA;
		List<RotaTO> listRotaTO = this.createListRotaTO();
		listRotaTO.add(this.createRotaTO(ORIGEM, StringUtils.EMPTY, DISTANCIA));

		verify(edgeWeightedDigraphDAO, times(0)).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraMalhaLogisticaDistanciaMenorZeroFailure() throws IOException, ValidationException{
		String nomeMapa = NOME_MAPA;
		List<RotaTO> listRotaTO = this.createListRotaTO();
		listRotaTO.add(this.createRotaTO(ORIGEM, DESTINO, DISTANCIA_NEGATIVA));

		verify(edgeWeightedDigraphDAO, times(0)).save(anyString(), anyListOf(DirectedEdge.class));
		
		malhaLogisticaBusiness.cadastraMalhaLogistica(nomeMapa, listRotaTO);
	}
	
	@Test
	public void testConsultarMenorCaminhoSuccess() throws IOException, ValidationException{
		
		when(edgeWeightedDigraphDAO.findByNomMapa(anyString())).thenReturn(this.createMalhaLogistica());
		
		List<RotaTO> menorCaminho = malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, "A", "D");
		
		Assert.assertNotNull(menorCaminho);
		Assert.assertTrue(CollectionUtils.isNotEmpty(menorCaminho));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=IOException.class)
	public void testConsultarMenorCaminhoFailure() throws IOException, ValidationException{
		
		when(edgeWeightedDigraphDAO.findByNomMapa(anyString())).thenThrow(IOException.class);
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, "A", "D");
		
	}
	
	@Test(expected=NullPointerException.class)
	public void testConsultarMenorCaminhoNomeMapaNuloFailure() throws IOException, ValidationException{
		String nomeMapa = null;

		verify(edgeWeightedDigraphDAO, times(0)).findByNomMapa(anyString());
		
		malhaLogisticaBusiness.consultarMenorCaminho(nomeMapa, ORIGEM, DESTINO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultarMenorCaminhoNomeMapaVazioFailure() throws IOException, ValidationException{
		String nomeMapa = StringUtils.EMPTY;

		verify(edgeWeightedDigraphDAO, times(0)).findByNomMapa(anyString());
		
		malhaLogisticaBusiness.consultarMenorCaminho(nomeMapa, ORIGEM, DESTINO);
	}
	
	@Test(expected=NullPointerException.class)
	public void testConsultarMenorCaminhoOrigemNuloFailure() throws IOException, ValidationException{

		verify(edgeWeightedDigraphDAO, times(0)).findByNomMapa(anyString());
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, null, DESTINO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultarMenorCaminhoOrigemVazioFailure() throws IOException, ValidationException{
		
		verify(edgeWeightedDigraphDAO, times(0)).findByNomMapa(anyString());
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, StringUtils.EMPTY, DESTINO);
	}
	
	@Test(expected=NullPointerException.class)
	public void testConsultarMenorCaminhoDestinoNuloFailure() throws IOException, ValidationException{

		verify(edgeWeightedDigraphDAO, times(0)).findByNomMapa(anyString());
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, ORIGEM, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultarMenorCaminhoDestinoVazioFailure() throws IOException, ValidationException{

		verify(edgeWeightedDigraphDAO, times(0)).findByNomMapa(anyString());
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, ORIGEM, StringUtils.EMPTY);
	}
	
	@Test(expected=ValidationException.class)
	public void testConsultarMenorCaminhoMalhaLogisticaVaziaFailure() throws IOException, ValidationException{
		
		when(edgeWeightedDigraphDAO.findByNomMapa(anyString())).thenReturn(new EdgeWeightedDigraph(0));
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, "A", "D");
	}
	
	@Test(expected=ValidationException.class)
	public void testConsultarMenorCaminhoOrigemSemRotaFailure() throws IOException, ValidationException{
		
		when(edgeWeightedDigraphDAO.findByNomMapa(anyString())).thenReturn(this.createMalhaLogistica());
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, ORIGEM, "D");
	}
	
	@Test(expected=ValidationException.class)
	public void testConsultarMenorCaminhoDestinoSemRotaFailure() throws IOException, ValidationException{
		
		when(edgeWeightedDigraphDAO.findByNomMapa(anyString())).thenReturn(this.createMalhaLogistica());
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, "A", DESTINO);
	}
	
	@Test(expected=ValidationException.class)
	public void testConsultarMenorCaminhoOrigemDestinoSemRotaFailure() throws IOException, ValidationException{
		EdgeWeightedDigraph malhaLogistica = this.createMalhaLogistica();
		malhaLogistica.addEdge(this.createRota(5, 6, "F", "G", 10d));
		
		when(edgeWeightedDigraphDAO.findByNomMapa(anyString())).thenReturn(malhaLogistica);
		
		malhaLogisticaBusiness.consultarMenorCaminho(NOME_MAPA, "A", "G");
	}
	
	@Test
	public void testCalcularCustoPercursoSuccess(){
		List<RotaTO> listRotasTO = this.createListRotaTO();
		
		Double totalCustoPercurso = malhaLogisticaBusiness.calcularCustoPercurso(listRotasTO, AUTONOMIA, PRECO_COMBUSTIVEL);
		
		Assert.assertNotNull(totalCustoPercurso);
		Assert.assertTrue(totalCustoPercurso > 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCalcularCustoPercursoAutonomiaMenorZeroFailure(){
		List<RotaTO> listRotasTO = this.createListRotaTO();
		
		malhaLogisticaBusiness.calcularCustoPercurso(listRotasTO, AUTONOMIA_NEGATIVA, PRECO_COMBUSTIVEL);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCalcularCustoPercursoAutonomiaIgualZeroFailure(){
		List<RotaTO> listRotasTO = this.createListRotaTO();
		
		malhaLogisticaBusiness.calcularCustoPercurso(listRotasTO, 0d, PRECO_COMBUSTIVEL);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCalcularCustoPercursoPrecoCombustivelMenorZeroFailure(){
		List<RotaTO> listRotasTO = this.createListRotaTO();
		
		malhaLogisticaBusiness.calcularCustoPercurso(listRotasTO, AUTONOMIA, PRECO_COMBUSTIVEL_NEGATIVO);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCalcularCustoPercursoPrecoCombustivelIgualZeroFailure(){
		List<RotaTO> listRotasTO = this.createListRotaTO();
		
		malhaLogisticaBusiness.calcularCustoPercurso(listRotasTO, AUTONOMIA, 0d);
	}
	
	@Test
	public void testCalcularCustoPercursoDistanciaIgualZeroSuccess(){
		Double totalCustoPercurso = malhaLogisticaBusiness.calcularCustoPercurso(0d, AUTONOMIA, PRECO_COMBUSTIVEL);
		
		Assert.assertNotNull(totalCustoPercurso);
		Assert.assertTrue(totalCustoPercurso == 0);
	}

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
	
	private RotaTO createRotaTO(String origem, String destino, Double distancia){
		return new RotaTO(origem, destino, distancia);
	}
	
	private EdgeWeightedDigraph createMalhaLogistica(){
		List<DirectedEdge> listRota = this.createListRota();
		EdgeWeightedDigraph malhaLogistica = new EdgeWeightedDigraph(listRota.size());
		for(DirectedEdge rota : listRota){
			malhaLogistica.addEdge(rota);
		}
		return malhaLogistica;

	}
	
	private List<DirectedEdge> createListRota() {
		List<DirectedEdge> listRotaTO = new ArrayList<>();
		listRotaTO.add(this.createRota(0,1,"A","B",10d));
		listRotaTO.add(this.createRota(1,3,"B","D",15d));
		listRotaTO.add(this.createRota(0,2,"A","C",20d));
		listRotaTO.add(this.createRota(2,3,"C","D",30d));
		listRotaTO.add(this.createRota(1,4,"B","E",50d));
		listRotaTO.add(this.createRota(3,4,"D","E",30d));
		listRotaTO.add(this.createRota(2,4,"C","E",0d));
		return listRotaTO;
	}
	
	private DirectedEdge createRota(int v, int w, String origem, String destino, Double distancia){
		DirectedEdge rota = new DirectedEdge(v,w,distancia);
		rota.setOrigem(origem);
		rota.setDestino(destino);
		return rota;
	}

}
