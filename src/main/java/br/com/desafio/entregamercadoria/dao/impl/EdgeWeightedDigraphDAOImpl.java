package br.com.desafio.entregamercadoria.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.tooling.GlobalGraphOperations;
import org.springframework.stereotype.Repository;

import br.com.desafio.entregamercadoria.dao.EdgeWeightedDigraphDAO;
import br.com.desafio.entregamercadoria.dao.Neo4jDAO;
import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;

@Repository
public class EdgeWeightedDigraphDAOImpl extends Neo4jDAO implements EdgeWeightedDigraphDAO{
	
    private static Index<Node> indexService;
    private static final String NAME_KEY = "local";
    private static RelationshipType TEM_ROTA_PARA = DynamicRelationshipType.withName( "TEM_ROTA_PARA" );

	@Override
	public void save(String nomMapa, List<DirectedEdge> rotas) {
	    GraphDatabaseService graphDb = this.getGraphDatabase(nomMapa);
	    indexService = graphDb.index().forNodes( "nodes" );
	    
        Transaction tx = graphDb.beginTx();

        for(DirectedEdge rota : rotas){
    		
            Node firstNode = this.getOrCreateNode(rota.getOrigem(), graphDb);
            Node secondNode = this.getOrCreateNode(rota.getDestino(), graphDb);
            
            Relationship relationship = firstNode.createRelationshipTo( secondNode, TEM_ROTA_PARA );
            relationship.setProperty( "distancia", rota.weight());
                    
    	}

        tx.success();
	}

	@Override
	public EdgeWeightedDigraph findByNomMapa(String nomMapa) {
	    GraphDatabaseService graphDb = this.getGraphDatabase(nomMapa);
        Transaction tx = graphDb.beginTx();

	    ResourceIterable<Node> nodes =  GlobalGraphOperations.at(graphDb).getAllNodes();

	    Map<String, Integer> mapLocalIndex = createMapaLocaisIndexados(nodes);
	    
	    EdgeWeightedDigraph mapaRotas = new EdgeWeightedDigraph(mapLocalIndex.size());
	    
	    for(Node node : nodes){
	    	String origem = node.getProperty("local").toString();
	    	int v = mapLocalIndex.get(origem);
	    	
	    	for(Relationship rota : node.getRelationships(TEM_ROTA_PARA)){
	    		String destino = rota.getEndNode().getProperty("local").toString();
	    		int w = mapLocalIndex.get(destino);
	    		
	    		DirectedEdge edge = new DirectedEdge(v, w, Integer.parseInt(rota.getProperty("distancia").toString()));
	    		edge.setOrigem(origem);
	    		edge.setDestino(destino);
	    		
	    		mapaRotas.addEdge(edge);
	    	}
	    }
	    
        tx.success();

	    return mapaRotas;
	}
	
	private Node getOrCreateNode( String local, GraphDatabaseService graphDb )
    {
        Node node = indexService.get( NAME_KEY, local ).getSingle();
        if ( node == null )
        {
            node = graphDb.createNode();
            node.setProperty( NAME_KEY, local );
            indexService.add( node, NAME_KEY, local );
        }
        return node;
    }
	
	private Map<String, Integer> createMapaLocaisIndexados(ResourceIterable<Node> nodes){
		Map<String, Integer> mapaLocaisIndexados = new HashMap<>();
	    
		int count = 0;
	    for(Node node : nodes){
	    	mapaLocaisIndexados.put(node.getProperty("local").toString(), count++);
	    }
	    
	    return mapaLocaisIndexados;
	}

}
