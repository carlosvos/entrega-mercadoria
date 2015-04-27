package br.com.desafio.entregamercadoria.service;

import javax.ws.rs.core.Response;

import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;
import br.com.desafio.entregamercadoria.service.vo.ConsultaMenorCaminhoInputVO;

/**
 * Interface que exp�e os servi�os dispon�vel para cadastro de uma malha log�stica e suas rotas e recupera��o
 * do menor caminho entre um ponto de origem e um ponto de destino.
 * 
 * @author Carlos Vin�cius
 *
 */
public interface RotaService {
	
	/**
	 * M�todo referenciado na requisi��o web para cadastro de uma malha log�stica e suas rotas. O obejto
	 * de entrada {@link CadastraMalhaLogisticaInputVO} ser� processado e enviado para a camada de neg�cio
	 * que executar� a inser��o das rotas na base de dados.
	 *
	 * No fim do processo, uma mensagem de sucesso ser� enviada como parte da resposta caso a inser��o tenha
	 * sido bem sucessida. Caso contr�rio, uma mensagem de erro contendo o motivo ser�o enviados na resposta
	 * notificando que n�o foi poss�vel realizar a opera��o.
	 * 
	 * @param input objeto {@link CadastraMalhaLogisticaInputVO} recebido durante a requisa��o contendo
	 * as informa��es referentes a malha log�stica e suas rotas.
	 * 
	 * @return resposta contendo uma mensagem de sucesso ou falha da opera��o
	 */
	Response cadastraMalhaLogistica(CadastraMalhaLogisticaInputVO input);
	
	/**
	 * M�todo referenciado na requisi��o web para consulta do menor caminho considerando um local de origem e um local
	 * de destino. Ap�s obter as rotas do menor caminho, ser� feito um c�lculo do gastos considerando
	 * a soma das dist�ncas das rotas, a autonomia do ve�culo e o pre�o do combust�vel.
	 * O obejto de entrada {@link ConsultaMenorCaminhoInputVO} ser� processado e enviado para a camada de neg�cio
	 * que far� a pesquisa do menor caminho e c�lculo do gasto total do percurso.
	 * 
	 * No fim do processo, uma mensagem de sucesso ser� enviada como parte da resposta caso a consulta tenha
	 * sido bem sucessida. Caso contr�rio, uma mensagem de erro contendo o motivo ser�o enviados na resposta
	 * notificando que n�o foi poss�vel realizar a opera��o.
	 * 
	 * @param input objeto {@link ConsultaMenorCaminhoInputVO} recebido durante a requisa��o contendo
	 * as informa��es referentes aos pontos de origem e destino, a malha log�stica onde encontram-se esses
	 * locais, a autonomia do ve�culo e o pre�o do combust�vel para busca do menor caminho e c�lculo do
	 * custo do percurso.
	 * 
	 * @return resposta contendo uma mensagem de sucesso ou falha da opera��o
	 */
	Response consultaMenorCaminho(ConsultaMenorCaminhoInputVO input);

}
