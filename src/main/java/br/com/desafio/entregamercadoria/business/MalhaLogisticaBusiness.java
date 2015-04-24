package br.com.desafio.entregamercadoria.business;

import java.io.IOException;
import java.util.List;

import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.to.RotaTO;

public interface MalhaLogisticaBusiness {
	
	void cadastraMalhaLogistica(String nomeMapa, List<RotaTO> listRotasTO) throws IOException, ValidationException;
	
	List<RotaTO> consultarMenorCaminho(String nomeMapa, String origem, String destino) throws IOException, ValidationException;

	Double calcularCustoPercurso(Double distancia, Double autonomia, Double precoCombustivel);
	
	Double calcularCustoPercurso(List<RotaTO> rotas, Double autonomia, Double precoCombustivel);
}
