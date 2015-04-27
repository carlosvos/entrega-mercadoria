package br.com.desafio.entregamercadoria.dao;

import java.io.IOException;
import java.util.List;

import br.com.desafio.entregamercadoria.entity.Rota;
import br.com.desafio.entregamercadoria.entity.MalhaLogistica;

/**
 * Interface de acesso a base de dados que executa as operações de cadastro e busca de uma
 * malha logística e suas rotas.
 * 
 * @author Carlos Vinícius
 *
 */
public interface MalhaLogisticaDAO {
	
	/**
	 * Cria ou recupera o grafo representando uma malha logísitca e realiza
	 * a inclusão dos locais de origem e destino das rotas e a distância entre esses pontos.
	 * Os locais são representados pelos nós e a distância pelos relacionamentos entre eles. 
	 * Caso um determinado local ja tenha sido cadastrado, ele será recuperado e poderá ter novos
	 * relacionamentos criados para ele ou ter o valor de alguma distância atualizado.
	 * 
	 * @param nomMapa {@link String} referente ao nome da malha logísitca que será utilizado para criação 
	 * ou recuperação do grafo.
	 * @param rotas lista de entidades {@link Rota} de onde será retirado as informações de origem,
	 * destino e distancia das rotas. Os locais de origem e destino serão persistidos como nós e a distância
	 * como uma propriedade do relacionamento dos nós no grafo.
	 * @throws IOException exceção lançada caso haja algum problema durante o acesso a base de dados.
	 */
	void save(String nomMapa, List<Rota> rotas) throws IOException;
	
	/**
	 * Busca um grafo a partir do nome da malha logísitca e recupera todas as informações correspondentes
	 * aos nós das localidades, convertendo-os em uma entidade {@link Rota}. Durante a conversão
	 * é atribuido um índice numérico para cada nó encontrado de acordo com a quantidade de nós encontrado. 
	 * Estas informações serão atribuidas nas propriedades da entidade e encapsuladas em um objeto {@link MalhaLogistica}
	 * correspondente a entidade da malha logísitca.
	 * 
	 * @param nomMapa {@link String} referente ao nome da malha logísitca que será utilizado para criação 
	 * ou recuperação do grafo.
	 * @return entidade {@link MalhaLogistica} contendo os obejtos {@link Rota} correspondetes
	 * as rotas, contendo as informações de origem, destino, distância e o índice dos locais.
	 * @throws IOException exceção lançada caso haja algum problema durante o acesso a base de dados.
	 */
	MalhaLogistica findByNomMapa(String nomMapa) throws IOException;

}
