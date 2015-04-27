package br.com.desafio.entregamercadoria.dao;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.springframework.beans.factory.annotation.Value;

/**
 * Classe abstrata contendo funções de uso comum a outras classes DAO que façam uso da ferramenta Neo4J 
 * para persistência de dados.
 * 
 * @author Carlos Vinícius
 *
 */
public abstract class Neo4jDAO {
	
	/**
	 * Mapa de grafos criados e recuperados ao longo da execução da aplicação. Cada instância criada a partir
	 * de um nome será armazenada no mapa e indexada por esse nome, para que possa ser recuperado em futuras transa
	 * ções, afim de evitar a criação de mais de uma instância acessando um mesmo grafo.
	 */
	private static final Map<String, GraphDatabaseService> DB_MAP = new HashMap<String, GraphDatabaseService>();
	
	/**
	 * Caminho do diretório onde os grafos serão salvos
	 */
	private String dbPath;
	
	/**
	 * Recupera ou cria um novo grafo a partir de um nome. Os novos grafos são salvos em um singleton 
	 * de {@link Map} e recuperados quando novas transações forem executadas neste mesmo grafo. Um método 
	 * pra liberação dos recursos da instância do grafo é registrado e executado quando a JVM for 
	 * desligada.
	 * 
	 * @param databaseName nome do grafo
	 * @return uma instância de {@link GraphDatabaseService} correpondente ao grafo criado ou recuperado.
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
	 * Libera os recursos do grafo fornecido como parâmetro durante o desligamento da JVM.
	 * 
	 * @param graphDb instância de {@link GraphDatabaseService} que terá seus recursos liberados
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
