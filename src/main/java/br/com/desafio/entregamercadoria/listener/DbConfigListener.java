package br.com.desafio.entregamercadoria.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.neo4j.graphdb.GraphDatabaseService;

import br.com.desafio.entregamercadoria.dao.Neo4jDAO;

/**
 * Listener utilitário para execução de funções no início e fim da criação do contexto de servlet.
 * 
 * @author Carlos Vinícius
 *
 */
public class DbConfigListener implements ServletContextListener{
	
	/**
	 * Percorre todo o mapa grafos correspondente as malhas logísticas criadas e recuperadas
	 * ao longo da execução da aplicação. Cada instância será removida do mapa e seus recursos
	 * serão liberados no fim da execução do sistema.
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
