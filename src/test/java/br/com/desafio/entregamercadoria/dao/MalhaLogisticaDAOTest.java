package br.com.desafio.entregamercadoria.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.desafio.entregamercadoria.entity.Rota;
import br.com.desafio.entregamercadoria.entity.MalhaLogistica;

/**
 * Testes unit�rios da classe DAO {@link MalhaLogisticaDAOImpl}.
 * 
 * @author Carlos Vin�cius
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-test.xml"})
public class MalhaLogisticaDAOTest {
	
	private final String NOME_MAPA = "teste_graph_db";
	
	@Resource
	private MalhaLogisticaDAO malhaLogisticaDAO;
	
	/**
	 * Ap�s os testes, todas as inst�ncias dos grafos ter�o 
	 * seus recursos liberados e seus n�s contendo as localidades removidos.
	 */
	@After
	public void removeMalhaLogistica(){
		this.removeRotas();
		this.shutdownGraphDb();
	}
	
	/**
	 * Teste de sucesso de cria��o e busca de um grafo correspondente a uma malha log�stica 
	 * e inser��o dos n�s correspondentes as rotas.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSaveAndFindMalhaLogisticaSuccess() throws IOException{
		List<Rota> rotas = this.createRotas();
		malhaLogisticaDAO.save(NOME_MAPA, rotas);

		MalhaLogistica malhaLogistica =  malhaLogisticaDAO.findByNomMapa(NOME_MAPA);
		Assert.assertNotNull(malhaLogistica);

		rotas = IteratorUtils.toList(malhaLogistica.getRotas().iterator());
		Assert.assertTrue(CollectionUtils.isNotEmpty(rotas));
	}

	/**
	 * cria uma lista de entidades {@link Rota}
	 * 
	 * @return lista de objetos {@link Rota}
	 */
	private List<Rota> createRotas() {
		List<Rota> rotas = new ArrayList<>();
		rotas.add(this.createRota("A","B",10d));
		rotas.add(this.createRota("B","D",15d));
		rotas.add(this.createRota("A","C",20d));
		rotas.add(this.createRota("C","D",30d));
		rotas.add(this.createRota("B","E",50d));
		rotas.add(this.createRota("D","E",30d));
		return rotas;
	}
	
	/**
	 * cria uma inst�ncia da entidade {@link Rota}
	 * @param v �ndice num�rico do local de origem
	 * @param w �ndice num�rico do local de destino
	 * @param origem nome do local de origem
	 * @param destino nome do local de destino
	 * @param distancia quantidade de quilometros que separam a origem do destino
	 * @return uma inst�ncia da entidade {@link Rota}
	 */
	private Rota createRota(String origem, String destino, Double distancia){
		return new Rota(distancia, origem, destino);
	}
	
	/**
	 * Libera os recursos do grafo.
	 * 
	 */
	private void shutdownGraphDb(){
		GraphDatabaseService db = Neo4jDAO.getDatabaseMap().get(NOME_MAPA);
		db.shutdown();
		Neo4jDAO.getDatabaseMap().remove(NOME_MAPA);
	}
	
	/**
	 * Remove todos os n�s com a propriedade 'local' e seus relacionamentos do grafo.
	 */
	private void removeRotas(){
		GraphDatabaseService db = Neo4jDAO.getDatabaseMap().get(NOME_MAPA);

		try ( Transaction tx = db.beginTx();
      	      Result result = db.execute( "MATCH (n)-[r]-()	WHERE HAS (n.local)	DELETE n, r" ); )
      	{
	        tx.success();
      	}
		
	}

}
