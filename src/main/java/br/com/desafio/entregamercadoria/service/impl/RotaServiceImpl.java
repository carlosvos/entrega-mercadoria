package br.com.desafio.entregamercadoria.service.impl;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.stereotype.Service;

import br.com.desafio.entregamercadoria.business.MalhaLogisticaBusiness;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.service.RotaService;
import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;
import br.com.desafio.entregamercadoria.service.vo.ConsultaMenorCaminhoInputVO;

@Service
@Path("/")
public class RotaServiceImpl implements RotaService{
	
	private static final String CHARSET_UTF8 = "charset=UTF-8";
	private final String MSG_MENOR_CAMINHO = "Rota {0} com custo de {1}";

	@Resource
	private MalhaLogisticaBusiness malhaLogisticaBusiness;
	
	@POST
	@Path("/cadastraMalhaLogistica")
	@Produces({MediaType.APPLICATION_JSON + ";" + CHARSET_UTF8})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response cadastraMalhaLogistica(CadastraMalhaLogisticaInputVO input) {
		
		ResponseBuilder responseBuilder = null;

		try {           
			malhaLogisticaBusiness.cadastraMalhaLogistica(input);
			responseBuilder =  Response.ok();			
		}
		catch (Exception e) {
			responseBuilder = Response.serverError();
			responseBuilder.entity(e.getMessage());			
		}
				
		return responseBuilder.build();
	}

	@POST
	@Path("/consultaMenorCaminho")
	@Produces({MediaType.APPLICATION_JSON + ";" + CHARSET_UTF8})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response consultaMenorCaminho(ConsultaMenorCaminhoInputVO input) {
		
		ResponseBuilder responseBuilder = null;

		try {           
            List<RotaTO> menorCaminho = malhaLogisticaBusiness.consultarMenorCaminho(
            		input.getNomeMapa(), 
            		input.getOrigem(), 
            		input.getDestino());
            Double custoPercurso = malhaLogisticaBusiness.calcularCustoPercurso(menorCaminho, input.getAutonomia(), input.getVlrCombustivel());
			responseBuilder =  Response.ok(mensagemFormatada(menorCaminho, custoPercurso));			
		}
		catch (Exception e) {
			responseBuilder = Response.serverError();
			responseBuilder.entity(e.getMessage());			
		}
				
		return responseBuilder.build();
	}
	
	/**
	 * Monta a mensagem de resposta do serviço contendo contendo a ordem das localidades considerando o menor caminho e 
	 * o gasto total para se fazer este percurso
	 * 
	 * @param menorCaminho iterador extraido de um objeto {@link Stack} contendo os objetos das rotas do menor caminho. 
	 * (Devido a esta estrutura de dados, a iteração destes dados retornará a ordem inversa do percurso, Destino -> Origem)
	 * 
	 * @param autonomia quantidade de quilômetros percorridos por litro de combustível
	 * @param precoCombustivel valor do litro de combustível
	 * @return {@link String} formatada contendo o menor caminho e o gasto total do percurso.
	 */
	private String mensagemFormatada(List<RotaTO> menorCaminho, Double custoPercurso) {
		StringBuilder sbLocais = new StringBuilder("");

		Iterator<RotaTO> iteratorMenorCaminho = menorCaminho.iterator();
		
		while(iteratorMenorCaminho.hasNext()){
			RotaTO rota = iteratorMenorCaminho.next();
			
			sbLocais.append(rota.getOrigem()).append(" ");

			if(!iteratorMenorCaminho.hasNext()){
				sbLocais.append(rota.getDestino());
			}
			
		}
		
		String locais = sbLocais.toString();

		return MessageFormat.format(MSG_MENOR_CAMINHO, locais, custoPercurso);
	}

}
