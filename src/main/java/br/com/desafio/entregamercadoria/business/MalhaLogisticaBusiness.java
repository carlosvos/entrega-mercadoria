package br.com.desafio.entregamercadoria.business;

import java.util.List;

import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;

public interface MalhaLogisticaBusiness {
	
	void cadastraMalhaLogistica(CadastraMalhaLogisticaInputVO input);
	
	List<RotaTO> consultarMenorCaminho(String nomeMapa, String origem, String destino);

	Double calcularCustoPercurso(Double distancia, Double autonomia, Double precoCombustivel);
	
	Double calcularCustoPercurso(List<RotaTO> rotas, Double autonomia, Double precoCombustivel);
}
