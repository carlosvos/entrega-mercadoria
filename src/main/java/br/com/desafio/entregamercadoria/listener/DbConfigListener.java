package br.com.desafio.entregamercadoria.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.neo4j.graphdb.GraphDatabaseService;

import br.com.desafio.entregamercadoria.dao.Neo4jDAO;

/**
 * Listener utilit�rio para execu��o de fun��es no in�cio e fim da cria��o do contexto de servlet.
 * 
 * @author Carlos Vin�cius
 *
 */
public class DbConfigListener implements ServletContextListener{
	
	/**
	 * Percorre todo o mapa grafos correspondente as malhas log�sticas criadas e recuperadas
	 * ao longo da execu��o da aplica��o. Cada inst�ncia ser� removida do mapa e seus recursos
	 * ser�o liberados no fim da execu��o do sistema.
	 * 
	 */
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
