package br.com.desafio.entregamercadoria.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.neo4j.graphdb.GraphDatabaseService;

import br.com.desafio.entregamercadoria.dao.Neo4jDAO;

public class DbConfigListener implements ServletContextListener{
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		for(GraphDatabaseService graphDb : Neo4jDAO.getDatabaseMap().values()){
			graphDb.shutdown();
		};
		
		Neo4jDAO.getDatabaseMap().clear();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	}

}
