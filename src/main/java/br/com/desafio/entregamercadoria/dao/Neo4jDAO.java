package br.com.desafio.entregamercadoria.dao;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.springframework.beans.factory.annotation.Value;

/**
 * Classe abstrata contendo fun��es de uso comum a outras classes DAO que fa�am uso da ferramenta Neo4J 
 * para persist�ncia de dados.
 * 
 * @author Carlos Vin�cius
 *
 */
public abstract class Neo4jDAO {
	
	/**
	 * Mapa de grafos criados e recuperados ao longo da execu��o da aplica��o. Cada inst�ncia criada a partir
	 * de um nome ser� armazenada no mapa e indexada por esse nome, para que possa ser recuperado em futuras transa
	 * ��es, afim de evitar a cria��o de mais de uma inst�ncia acessando um mesmo grafo.
	 */
	private static final Map<String, GraphDatabaseService> DB_MAP = new HashMap<String, GraphDatabaseService>();
	
	/**
	 * Caminho do diret�rio onde os grafos ser�o salvos
	 */
	private String dbPath;
	
	/**
	 * Recupera ou cria um novo grafo a partir de um nome. Os novos grafos s�o salvos em um singleton 
	 * de {@link Map} e recuperados quando novas transa��es forem executadas neste mesmo grafo. Um m�todo 
	 * pra libera��o dos recursos da inst�ncia do grafo � registrado e executado quando a JVM for 
	 * desligada.
	 * 
	 * @param databaseName nome do grafo
	 * @return uma inst�ncia de {@link GraphDatabaseService} correpondente ao grafo criado ou recuperado.
	 */
	protected GraphDatabaseService getGraphDatabase(String databaseName){
		GraphDatabaseService db = DB_MAP.get(databaseName);
		
		if(db == null){
			db = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder( this.dbPath.concat(databaseName) )
					.setConfig(GraphDatabaseSettings.keep_logical_logs, "false")
					.setConfig(GraphDatabaseSettings.keep_logical_logs, "1 files")
					.newGraphDatabase();
	        registerShutdownHook( db );
	        DB_MAP.put(databaseName, db);
		}
		
		return db;
	}
	
	/**
	 * Libera os recursos do grafo fornecido como par�metro durante o desligamento da JVM.
	 * 
	 * @param graphDb inst�ncia de {@link GraphDatabaseService} que ter� seus recursos liberados
	 * durante o desligamento da JVM
	 */
	// START SNIPPET: shutdownHook
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    
    public static Map<String, GraphDatabaseService> getDatabaseMap(){
    	return DB_MAP;
    }

	public String getDbPath() {
		return dbPath;
	}

	@Value("${dataBase.path}")
	private void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}
    
}
