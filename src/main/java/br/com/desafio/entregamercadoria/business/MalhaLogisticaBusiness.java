package br.com.desafio.entregamercadoria.business;

import java.io.IOException;
import java.util.List;

import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.dao.EdgeWeightedDigraphDAO;

/**
 * Interface de negócio responsável por cadastrar as malhas logísiticas e suas rotas ou consultar
 * o menor caminho a partir de uma malha informada. As informações de entrada serão validadas e
 * enviadas para a camada DAO ({@link EdgeWeightedDigraphDAO}), onde serão salvas na base de dados ou utilizadas como 
 * filtros para recuperação de outros dados, fazendo as devidas conversões caso necessário.
 * 
 * @author Carlos Vinícius
 *
 */
public interface MalhaLogisticaBusiness {
	
	/**
	 * Cadastra uma malha logística juntamente com suas rotas.
	 * 
	 * @param nomeMapa {@link String} referente ao nome da malha logísitca que conterá as rotas.
	 * @param listRotasTO lista de objetos {@link RotaTO} que correspondem as possíveis rotas de uma malha logísitca
	 * @throws IOException exceção lançada caso haja algum problema durante o acesso a base de dados.
	 * @throws ValidationException exceção lançada caso ocorra um dos sequintes erros de validação:
	 * <ul>
	 * <li>nomeMapa possua um valor nulo ou vazio;</li>
	 * <li>valor de origem de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
	 * <li>valor de destino de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
 	 * <li>valor da distância de algum dos objetos {@link RotaTO} seja menor que zero</li>
 	 * </ul>
	 */
	void cadastraMalhaLogistica(String nomeMapa, List<RotaTO> listRotasTO) throws IOException, ValidationException;
	
	/**
	 * Consulta o menor caminho de um ponto de origem para um ponto de destino dentro de uma malha logísitca.
	 * 
	 * @param nomeMapa {@link String} referente ao nome da malha logísitca que conterá as rotas.
	 * @param origem nome do local de origem
	 * @param destino nome do local de destino
	 * @return lita de objeto {@link RotaTO} contendo o percurso que corresponde ao menor caminho. A ordem dos objetos da lista inicia-se 
	 * com o ponto de origem seguindo a sequencia origem/destino dos objetos subsequentes até chegar no destino do último objeto.
	 * @throws IOException exceção lançada caso haja algum problema durante o acesso a base de dados.
	 * @throws ValidationException exceção lançada caso ocorra um dos sequintes erros de validação:
	 * <ul>
	 * <li>nomeMapa possua um valor nulo ou vazio;</li>
	 * <li>valor de origem de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
	 * <li>valor de destino de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
 	 * <li>malha logística encontrada não possua nenhuma rota;</li>
 	 * <li>local de origem ou destino não possua rotas dentro da malha logísitca encontrada;</li>
 	 * <li>local de origem e destino não possuam uma rota que os liguem dentro da malha logísitca encontrada</li>
 	 * </ul>
	 */
	List<RotaTO> consultarMenorCaminho(String nomeMapa, String origem, String destino) throws IOException, ValidationException;

	/**
	 * Calcula o custo total para percorrer uma determinada distância consideranco os valores da autonomia do veículo e o preço 
	 * do combustível.
	 * 
	 * @param distancia distância em Km do trajeto a ser percorrido.
	 * @param autonomia quantidade de Km por litro de combustível que o veículo percorre.
	 * @param precoCombustivel custo do litro de combustível.
	 * @return gasto total para percorrer este trajeto.
	 */
	Double calcularCustoPercurso(Double distancia, Double autonomia, Double precoCombustivel);
	
	/**
	 * Calcula o custo total para percorrer uma determinada distância, obtida dos objetos {@link RotaTO} contidos na lista.
	 * O valor individual de cada rota será somado para encontrar a distância total. Após isso, será feito o cálculo do custo total
	 * consideranco os valores da autonomia do veículo e o preço do combustível.
	 * 
	 * @param rotas lista de {@link RotaTO} contendo as rotas cuja soma das distâncias dos pontos de origem e destino será usada 
	 * para o calculo do custo total do percurso.
	 * @param autonomia quantidade de Km por litro de combustível que o veículo percorre.
	 * @param precoCombustivel usto do litro de combustível.
	 * @return gasto total para percorrer este trajeto.
	 */ 
	Double calcularCustoPercurso(List<RotaTO> rotas, Double autonomia, Double precoCombustivel);
}
