package br.com.desafio.entregamercadoria.dao;

import java.io.IOException;
import java.util.List;

import br.com.desafio.entregamercadoria.entity.Rota;
import br.com.desafio.entregamercadoria.entity.MalhaLogistica;

/**
 * Interface de acesso a base de dados que executa as opera��es de cadastro e busca de uma
 * malha log�stica e suas rotas.
 * 
 * @author Carlos Vin�cius
 *
 */
public interface MalhaLogisticaDAO {
	
	/**
	 * Cria ou recupera o grafo representando uma malha log�sitca e realiza
	 * a inclus�o dos locais de origem e destino das rotas e a dist�ncia entre esses pontos.
	 * Os locais s�o representados pelos n�s e a dist�ncia pelos relacionamentos entre eles. 
	 * Caso um determinado local ja tenha sido cadastrado, ele ser� recuperado e poder� ter novos
	 * relacionamentos criados para ele ou ter o valor de alguma dist�ncia atualizado.
	 * 
	 * @param nomMapa {@link String} referente ao nome da malha log�sitca que ser� utilizado para cria��o 
	 * ou recupera��o do grafo.
	 * @param rotas lista de entidades {@link Rota} de onde ser� retirado as informa��es de origem,
	 * destino e distancia das rotas. Os locais de origem e destino ser�o persistidos como n�s e a dist�ncia
	 * como uma propriedade do relacionamento dos n�s no grafo.
	 * @throws IOException exce��o lan�ada caso haja algum problema durante o acesso a base de dados.
	 */
	void save(String nomMapa, List<Rota> rotas) throws IOException;
	
	/**
	 * Busca um grafo a partir do nome da malha log�sitca e recupera todas as informa��es correspondentes
	 * aos n�s das localidades, convertendo-os em uma entidade {@link Rota}. Durante a convers�o
	 * � atribuido um �ndice num�rico para cada n� encontrado de acordo com a quantidade de n�s encontrado. 
	 * Estas informa��es ser�o atribuidas nas propriedades da entidade e encapsuladas em um objeto {@link MalhaLogistica}
	 * correspondente a entidade da malha log�sitca.
	 * 
	 * @param nomMapa {@link String} referente ao nome da malha log�sitca que ser� utilizado para cria��o 
	 * ou recupera��o do grafo.
	 * @return entidade {@link MalhaLogistica} contendo os obejtos {@link Rota} correspondetes
	 * as rotas, contendo as informa��es de origem, destino, dist�ncia e o �ndice dos locais.
	 * @throws IOException exce��o lan�ada caso haja algum problema durante o acesso a base de dados.
	 */
	MalhaLogistica findByNomMapa(String nomMapa) throws IOException;

}
