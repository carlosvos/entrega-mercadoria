package br.com.desafio.entregamercadoria.service;

import javax.ws.rs.core.Response;

import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;
import br.com.desafio.entregamercadoria.service.vo.ConsultaMenorCaminhoInputVO;

public interface RotaService {
	
	Response cadastraMalhaLogistica(CadastraMalhaLogisticaInputVO input);
	
	Response consultaMenorCaminho(ConsultaMenorCaminhoInputVO input);

}
