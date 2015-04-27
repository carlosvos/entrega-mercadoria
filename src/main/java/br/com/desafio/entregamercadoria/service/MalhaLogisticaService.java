package br.com.desafio.entregamercadoria.service;

import javax.ws.rs.core.Response;

import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;
import br.com.desafio.entregamercadoria.service.vo.ConsultaMenorCaminhoInputVO;

/**
 * Interface que expõe os serviços disponível para cadastro de uma malha logística e suas rotas e recuperação
 * do menor caminho entre um ponto de origem e um ponto de destino.
 * 
 * @author Carlos Vinícius
 *
 */
public interface RotaService {
	
	/**
	 * Método referenciado na requisição web para cadastro de uma malha logística e suas rotas. O obejto
	 * de entrada {@link CadastraMalhaLogisticaInputVO} será processado e enviado para a camada de negócio
	 * que executará a inserção das rotas na base de dados.
	 *
	 * No fim do processo, uma mensagem de sucesso será enviada como parte da resposta caso a inserção tenha
	 * sido bem sucessida. Caso contrário, uma mensagem de erro contendo o motivo serão enviados na resposta
	 * notificando que não foi possível realizar a operação.
	 * 
	 * @param input objeto {@link CadastraMalhaLogisticaInputVO} recebido durante a requisação contendo
	 * as informações referentes a malha logística e suas rotas.
	 * 
	 * @return resposta contendo uma mensagem de sucesso ou falha da operação
	 */
	Response cadastraMalhaLogistica(CadastraMalhaLogisticaInputVO input);
	
	/**
	 * Método referenciado na requisição web para consulta do menor caminho considerando um local de origem e um local
	 * de destino. Após obter as rotas do menor caminho, será feito um cálculo do gastos considerando
	 * a soma das distâncas das rotas, a autonomia do veículo e o preço do combustível.
	 * O obejto de entrada {@link ConsultaMenorCaminhoInputVO} será processado e enviado para a camada de negócio
	 * que fará a pesquisa do menor caminho e cálculo do gasto total do percurso.
	 * 
	 * No fim do processo, uma mensagem de sucesso será enviada como parte da resposta caso a consulta tenha
	 * sido bem sucessida. Caso contrário, uma mensagem de erro contendo o motivo serão enviados na resposta
	 * notificando que não foi possível realizar a operação.
	 * 
	 * @param input objeto {@link ConsultaMenorCaminhoInputVO} recebido durante a requisação contendo
	 * as informações referentes aos pontos de origem e destino, a malha logística onde encontram-se esses
	 * locais, a autonomia do veículo e o preço do combustível para busca do menor caminho e cálculo do
	 * custo do percurso.
	 * 
	 * @return resposta contendo uma mensagem de sucesso ou falha da operação
	 */
	Response consultaMenorCaminho(ConsultaMenorCaminhoInputVO input);

}
