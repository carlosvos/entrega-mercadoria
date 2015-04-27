package br.com.desafio.entregamercadoria.business;

import java.io.IOException;
import java.util.List;

import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.dao.EdgeWeightedDigraphDAO;

/**
 * Interface de neg�cio respons�vel por cadastrar as malhas log�siticas e suas rotas ou consultar
 * o menor caminho a partir de uma malha informada. As informa��es de entrada ser�o validadas e
 * enviadas para a camada DAO ({@link EdgeWeightedDigraphDAO}), onde ser�o salvas na base de dados ou utilizadas como 
 * filtros para recupera��o de outros dados, fazendo as devidas convers�es caso necess�rio.
 * 
 * @author Carlos Vin�cius
 *
 */
public interface MalhaLogisticaBusiness {
	
	/**
	 * Cadastra uma malha log�stica juntamente com suas rotas.
	 * 
	 * @param nomeMapa {@link String} referente ao nome da malha log�sitca que conter� as rotas.
	 * @param listRotasTO lista de objetos {@link RotaTO} que correspondem as poss�veis rotas de uma malha log�sitca
	 * @throws IOException exce��o lan�ada caso haja algum problema durante o acesso a base de dados.
	 * @throws ValidationException exce��o lan�ada caso ocorra um dos sequintes erros de valida��o:
	 * <ul>
	 * <li>nomeMapa possua um valor nulo ou vazio;</li>
	 * <li>valor de origem de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
	 * <li>valor de destino de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
 	 * <li>valor da dist�ncia de algum dos objetos {@link RotaTO} seja menor que zero</li>
 	 * </ul>
	 */
	void cadastraMalhaLogistica(String nomeMapa, List<RotaTO> listRotasTO) throws IOException, ValidationException;
	
	/**
	 * Consulta o menor caminho de um ponto de origem para um ponto de destino dentro de uma malha log�sitca.
	 * 
	 * @param nomeMapa {@link String} referente ao nome da malha log�sitca que conter� as rotas.
	 * @param origem nome do local de origem
	 * @param destino nome do local de destino
	 * @return lita de objeto {@link RotaTO} contendo o percurso que corresponde ao menor caminho. A ordem dos objetos da lista inicia-se 
	 * com o ponto de origem seguindo a sequencia origem/destino dos objetos subsequentes at� chegar no destino do �ltimo objeto.
	 * @throws IOException exce��o lan�ada caso haja algum problema durante o acesso a base de dados.
	 * @throws ValidationException exce��o lan�ada caso ocorra um dos sequintes erros de valida��o:
	 * <ul>
	 * <li>nomeMapa possua um valor nulo ou vazio;</li>
	 * <li>valor de origem de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
	 * <li>valor de destino de algum dos objetos {@link RotaTO} seja nulo ou vazio;</li>
 	 * <li>malha log�stica encontrada n�o possua nenhuma rota;</li>
 	 * <li>local de origem ou destino n�o possua rotas dentro da malha log�sitca encontrada;</li>
 	 * <li>local de origem e destino n�o possuam uma rota que os liguem dentro da malha log�sitca encontrada</li>
 	 * </ul>
	 */
	List<RotaTO> consultarMenorCaminho(String nomeMapa, String origem, String destino) throws IOException, ValidationException;

	/**
	 * Calcula o custo total para percorrer uma determinada dist�ncia consideranco os valores da autonomia do ve�culo e o pre�o 
	 * do combust�vel.
	 * 
	 * @param distancia dist�ncia em Km do trajeto a ser percorrido.
	 * @param autonomia quantidade de Km por litro de combust�vel que o ve�culo percorre.
	 * @param precoCombustivel custo do litro de combust�vel.
	 * @return gasto total para percorrer este trajeto.
	 */
	Double calcularCustoPercurso(Double distancia, Double autonomia, Double precoCombustivel);
	
	/**
	 * Calcula o custo total para percorrer uma determinada dist�ncia, obtida dos objetos {@link RotaTO} contidos na lista.
	 * O valor individual de cada rota ser� somado para encontrar a dist�ncia total. Ap�s isso, ser� feito o c�lculo do custo total
	 * consideranco os valores da autonomia do ve�culo e o pre�o do combust�vel.
	 * 
	 * @param rotas lista de {@link RotaTO} contendo as rotas cuja soma das dist�ncias dos pontos de origem e destino ser� usada 
	 * para o calculo do custo total do percurso.
	 * @param autonomia quantidade de Km por litro de combust�vel que o ve�culo percorre.
	 * @param precoCombustivel usto do litro de combust�vel.
	 * @return gasto total para percorrer este trajeto.
	 */ 
	Double calcularCustoPercurso(List<RotaTO> rotas, Double autonomia, Double precoCombustivel);
}
