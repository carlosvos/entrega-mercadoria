package br.com.desafio.entregamercadoria.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.stereotype.Repository;

import br.com.desafio.entregamercadoria.dao.EdgeWeightedDigraphDAO;
import br.com.desafio.entregamercadoria.dao.Neo4jDAO;
import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;

/**
 * Implementa��o da interface de acesso a base de dados que executa as opera��es de cadastro e busca de uma
 * malha log�stica e suas rotas.
 * 
 * @author Carlos Vin�cius
 *
 */
@Repository
public class EdgeWeightedDigraphDAOImpl extends Neo4jDAO implements EdgeWeightedDigraphDAO{
	
    private static Index<Node> indexService;
    private static final String NAME_KEY = "local";
    private static RelationshipType TEM_ROTA_PARA = DynamicRelationshipType.withName( "TEM_ROTA_PARA" );

	@Override
	public void save(String nomMapa, List<DirectedEdge> rotas) throws IOException{
	    GraphDatabaseService graphDb = this.getGraphDatabase(nomMapa);
	    
        try(Transaction tx = graphDb.beginTx()){
        
		    indexService = graphDb.index().forNodes( "nodes" );
	
		    for(DirectedEdge rota : rotas){
	    		
	            Node firstNode = this.getOrCreateNode(rota.getOrigem(), graphDb);
	            Node secondNode = this.getOrCreateNode(rota.getDestino(), graphDb);
	            
	            Relationship relationship = firstNode.createRelationshipTo( secondNode, TEM_ROTA_PARA );
	            relationship.setProperty( "distancia", rota.weight());
	                    
	    	}
	
	        tx.success();
	        
        }
	}

	@Override
	public EdgeWeightedDigraph findByNomMapa(String nomMapa) throws IOException{
	    GraphDatabaseService graphDb = this.getGraphDatabase(nomMapa);
	    EdgeWeightedDigraph mapaRotas = null;
	    
        try ( Transaction tx = graphDb.beginTx();
        	      Result result = graphDb.execute( "match (n) WHERE HAS (n.local) return n" ); )
        	{
    	    	List<Node> nodes = new ArrayList<>();
	        	Iterator<Node> n_column = result.columnAs( "n" );
	        	for ( Node node : IteratorUtil.asIterable( n_column ) )
	        	{
	        		nodes.add(node);
	        	}

			    Map<String, Integer> mapLocalIndex = createMapaLocaisIndexados(nodes);
			    
			    mapaRotas = new EdgeWeightedDigraph(mapLocalIndex.size());
			    
			    for(Node node : nodes){
			    	String origem = node.getProperty("local").toString();
			    	int v = mapLocalIndex.get(origem);
			    	
			    	for(Relationship rota : node.getRelationships(TEM_ROTA_PARA)){
			    		String destino = rota.getEndNode().getProperty("local").toString();
			    		int w = mapLocalIndex.get(destino);
			    		
			    		DirectedEdge edge = new DirectedEdge(v, w, Double.parseDouble(rota.getProperty("distancia").toString()));
			    		edge.setOrigem(origem);
			    		edge.setDestino(destino);
			    		
			    		mapaRotas.addEdge(edge);
			    	}
			    }
			    
		        tx.success();
        	}

	    return mapaRotas;
	}
	
	/**
	 * Busca ou cria um n� no grafo referente a localidade passada como par�metro.
	 * 
	 * @param local nome da origem ou destino que ser�o salvos ou recuperados no grafo.
	 * @param graphDb grafo contendo as informa��es referentes as localidaedes e suas dist�ncias.
	 * Este obejto representa a malha log�stica onde deseja-se salvar as informa��es das rotas.
	 * 
	 * @return n� correspondente ao local salvo ou encontrado no grafo.
	 */
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
	
	/**
	 * Cria um {@link Map} a partir dos n�s encontrados. Para cada n� da lista,
	 * um contador ser� incrementado e atribuido no mapa, correspondendo ao �ndice 
	 * daquela localidade.
	 * 
	 * @param nodes lista de n�s de localidades encontrados no grafo.
	 * @return {@link Map} contendo os nomes dos locais e seus respectivos �ndices gerados.
	 */
	private Map<String, Integer> createMapaLocaisIndexados(List<Node> nodes){
		Map<String, Integer> mapaLocaisIndexados = new HashMap<String, Integer>();
	    
		int count = 0;
	    for(Node node : nodes){
	    	mapaLocaisIndexados.put(node.getProperty("local").toString(), count++);
	    }
	    
	    return mapaLocaisIndexados;
	}
}
