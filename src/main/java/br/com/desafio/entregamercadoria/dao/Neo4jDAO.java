package br.com.desafio.entregamercadoria.dao;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Value;

public abstract class Neo4jDAO {
	
	private static final Map<String, GraphDatabaseService> DB_MAP = new HashMap<String, GraphDatabaseService>();
	private String dbPath;
	
	protected GraphDatabaseService getGraphDatabase(String databaseName) throws IllegalArgumentException{
		GraphDatabaseService db = DB_MAP.get(databaseName);
		
		if(db == null){
			db = new GraphDatabaseFactory().newEmbeddedDatabase( this.dbPath.concat(databaseName) );
	        registerShutdownHook( db );
	        DB_MAP.put(databaseName, db);
		}
		
		return db;
	}
	
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
