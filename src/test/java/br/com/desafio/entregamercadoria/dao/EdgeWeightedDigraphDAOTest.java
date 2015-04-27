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

import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;

/**
 * Testes unitários da classe DAO {@link EdgeWeightedDigraphDAOImpl}.
 * 
 * @author Carlos Vinícius
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-test.xml"})
public class EdgeWeightedDigraphDAOTest {
	
	private final String NOME_MAPA = "teste_graph_db";
	
	@Resource
	private EdgeWeightedDigraphDAO edgeWeightedDigraphDAO;
	
	/**
	 * Após os testes, todas as instâncias dos grafos terão 
	 * seus recursos liberados e seus nós contendo as localidades removidos.
	 */
	@After
	public void removeMalhaLogistica(){
		this.removeRotas();
		this.shutdownGraphDb();
	}
	
	/**
	 * Teste de sucesso de criação e busca de um grafo correspondente a uma malha logística 
	 * e inserção dos nós correspondentes as rotas.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSaveAndFindMalhaLogisticaSuccess() throws IOException{
		List<DirectedEdge> rotas = this.createRotas();
		edgeWeightedDigraphDAO.save(NOME_MAPA, rotas);

		EdgeWeightedDigraph malhaLogistica =  edgeWeightedDigraphDAO.findByNomMapa(NOME_MAPA);
		Assert.assertNotNull(malhaLogistica);

		rotas = IteratorUtils.toList(malhaLogistica.edges().iterator());
		Assert.assertTrue(CollectionUtils.isNotEmpty(rotas));
	}

	/**
	 * cria uma lista de entidades {@link DirectedEdge}
	 * 
	 * @return lista de objetos {@link DirectedEdge}
	 */
	private List<DirectedEdge> createRotas() {
		List<DirectedEdge> rotas = new ArrayList<>();
		rotas.add(this.createRota("A","B",10d));
		rotas.add(this.createRota("B","D",15d));
		rotas.add(this.createRota("A","C",20d));
		rotas.add(this.createRota("C","D",30d));
		rotas.add(this.createRota("B","E",50d));
		rotas.add(this.createRota("D","E",30d));
		return rotas;
	}
	
	/**
	 * cria uma instância da entidade {@link DirectedEdge}
	 * @param v índice numérico do local de origem
	 * @param w índice numérico do local de destino
	 * @param origem nome do local de origem
	 * @param destino nome do local de destino
	 * @param distancia quantidade de quilometros que separam a origem do destino
	 * @return uma instância da entidade {@link DirectedEdge}
	 */
	private DirectedEdge createRota(String origem, String destino, Double distancia){
		return new DirectedEdge(distancia, origem, destino);
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
	 * Remove todos os nós com a propriedade 'local' e seus relacionamentos do grafo.
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
