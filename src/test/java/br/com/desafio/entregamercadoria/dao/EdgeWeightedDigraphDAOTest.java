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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-test.xml"})
public class EdgeWeightedDigraphDAOTest {
	
	private final String NOME_MAPA = "teste_graph_db";
	
	@Resource
	private EdgeWeightedDigraphDAO edgeWeightedDigraphDAO;
	
	@After
	public void removeMalhaLogistica(){
		this.removeRotas();
		this.shutdownGraphDb();
	}
	
	@Test
	public void testSaveAndFindMalhaLogisticaSuccess() throws IOException{
		List<DirectedEdge> rotas = this.createRotas();
		edgeWeightedDigraphDAO.save(NOME_MAPA, rotas);

		EdgeWeightedDigraph malhaLogistica =  edgeWeightedDigraphDAO.findByNomMapa(NOME_MAPA);
		Assert.assertNotNull(malhaLogistica);

		rotas = IteratorUtils.toList(malhaLogistica.edges().iterator());
		Assert.assertTrue(CollectionUtils.isNotEmpty(rotas));
	}

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
	
	private DirectedEdge createRota(String origem, String destino, Double distancia){
		return new DirectedEdge(distancia, origem, destino);
	}
	
	private void shutdownGraphDb(){
		GraphDatabaseService db = Neo4jDAO.getDatabaseMap().get(NOME_MAPA);
		db.shutdown();
		Neo4jDAO.getDatabaseMap().remove(NOME_MAPA);
	}
	
	private void removeRotas(){
		GraphDatabaseService db = Neo4jDAO.getDatabaseMap().get(NOME_MAPA);

		try ( Transaction tx = db.beginTx();
      	      Result result = db.execute( "MATCH (n)-[r]-()	WHERE HAS (n.local)	DELETE n, r" ); )
      	{
	        tx.success();
      	}
		
	}

}
