package br.com.desafio.entregamercadoria.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.service.RotaService;
import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;
import br.com.desafio.entregamercadoria.service.vo.ConsultaMenorCaminhoInputVO;
import br.com.desafio.entregamercadoria.service.vo.RotaInputVO;

/**
 * Implementação da interface dos serviços para cadastro de uma malha logística e suas rotas e recuperação
 * do menor caminho entre um ponto de origem e um ponto de destino.
 * 
 * @author Carlos Vinícius
 *
 */
@Service
@Path("/")
public class RotaServiceImpl implements RotaService{
	
	private static final String CHARSET_UTF8 = "charset=UTF-8";
	private final String MSG_MENOR_CAMINHO = "Rota {0} com custo de {1}";
	private final String MSG_CADASTRO_MALHA_LOGISTICA_SUCESSO = "Malha logistica cadastrada com sucesso";
	private final String MSG_CADASTRO_MALHA_LOGISTICA_ERRO = "Não foi possível realizar o cadastro da malha logistica. [ {0} ]";
	private final String MSG_ERRO_IO = "Problemas de acesso aos dados salvos em banco.";
	private final String MSG_ERRO_INESPERADO = "Erro inesperado.";

	@Resource
	private MalhaLogisticaBusiness malhaLogisticaBusiness;
	
	@POST
	@Path("/cadastraMalhaLogistica")
	@Produces({MediaType.APPLICATION_JSON + ";" + CHARSET_UTF8})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Override
	public Response cadastraMalhaLogistica(CadastraMalhaLogisticaInputVO input) {
		
		ResponseBuilder responseBuilder = null;
		
		List<RotaTO> rotas = new ArrayList<>();
		for(RotaInputVO rotaInput : input.getRotas()){
			rotas.add(this.parseRotaInputVOtoRotaTO(rotaInput));
		}

		try {           
			malhaLogisticaBusiness.cadastraMalhaLogistica(input.getNomMalhaLogistica(), rotas);
			responseBuilder =  Response.ok(MSG_CADASTRO_MALHA_LOGISTICA_SUCESSO);			
		}catch (ValidationException | IllegalArgumentException e){
			responseBuilder = Response.serverError();
			responseBuilder.entity( MessageFormat.format(MSG_CADASTRO_MALHA_LOGISTICA_ERRO, e.getMessage()));
		}
		catch (IOException e){
			responseBuilder = Response.serverError();
			responseBuilder.entity( MessageFormat.format(MSG_CADASTRO_MALHA_LOGISTICA_ERRO, MSG_ERRO_IO));
		}
		catch (Exception e) {
			responseBuilder = Response.serverError();
			responseBuilder.entity( MessageFormat.format(MSG_CADASTRO_MALHA_LOGISTICA_ERRO, MSG_ERRO_INESPERADO));			
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
		}catch (ValidationException | IllegalArgumentException e){
			responseBuilder = Response.serverError();
			responseBuilder.entity( MessageFormat.format(MSG_CADASTRO_MALHA_LOGISTICA_ERRO, e.getMessage()));
		}
		catch (IOException e){
			responseBuilder = Response.serverError();
			responseBuilder.entity( MessageFormat.format(MSG_CADASTRO_MALHA_LOGISTICA_ERRO, MSG_ERRO_IO));
		}
		catch (Exception e) {
			responseBuilder = Response.serverError();
			responseBuilder.entity( MessageFormat.format(MSG_CADASTRO_MALHA_LOGISTICA_ERRO, MSG_ERRO_INESPERADO));			
		}
				
		return responseBuilder.build();
	}
	
	/**
	 * Monta a mensagem de resposta do serviço contendo contendo a ordem das localidades considerando o menor caminho e 
	 * o gasto total para se fazer este percurso
	 * 
	 * @param menorCaminho lista contendo os objetos das rotas do menor caminho. 
	 * 
	 * @param custoPercurso custo total do percuso do menor caminho.
	 * 
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
	
	/**
	 * Converte os objetos de entrada {@link RotaInputVO} recebidos na requisição em instãncias de {@link RotaTO}
	 * para transporte das informações das rotas para a camada de negócio.
	 * 
	 * @param rotaInput objeto de entrada das requisições contendo as informações das rotas.
	 * @return instância de {@link RotaTO} para transporte dos dados para a camada de negócio.
	 */
	private RotaTO parseRotaInputVOtoRotaTO(RotaInputVO rotaInput){
		return new RotaTO(rotaInput.getOrigem(), rotaInput.getDestino(), rotaInput.getDistancia());
	}

}
