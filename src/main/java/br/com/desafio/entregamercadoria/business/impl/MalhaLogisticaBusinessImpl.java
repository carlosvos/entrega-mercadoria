package br.com.desafio.entregamercadoria.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.desafio.entregamercadoria.business.MalhaLogisticaBusiness;
import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.dao.EdgeWeightedDigraphDAO;
import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;
import br.com.desafio.entregamercadoria.utils.DijkstraSP;

@Service
public class MalhaLogisticaBusinessImpl implements MalhaLogisticaBusiness {
	
	@Resource
	private EdgeWeightedDigraphDAO edgeWeightedDigraphDAO;
	
	private static final Logger LOG = LoggerFactory.getLogger(MalhaLogisticaBusinessImpl.class);
	
	private static final String MSG_NOME_MAPA_INVALIDO = "O nome do mapa não pode ser vazio ou nulo.";
	private static final String MSG_ORIGEM_INVALIDA = "O local de origem não pode ser vazio ou nulo.";
	private static final String MSG_DESTINO_INVALIDO = "O local de destino não pode ser vazio ou nulo.";

	private static final String MSG_DISTANCIA_NEGATIVA = "O valor da distância não pode ser negativa. [Rota {0} -> {1}]";
	private static final String MSG_AUTONOMIA_ZERO_NEGATIVA = "O valor da autonomia não pode ser menor ou igual a zero.";
	private static final String MSG_PRECO_COMBUSTIVEL_ZERO_NEGATIVO = "O preço do combustível não pode ser menor ou igual a zero.";
	
	private static final String MSG_MAPA_SEM_ROTAS = "Nenhuma rota foi encontrada para o mapa '{0}'.";
	private static final String MSG_LOCAL_SEM_ROTAS = "Nenhuma rota para {0} foi encontrada.";
	private static final String MSG_ORIGEM_DESTINO_SEM_ROTAS = "Nenhuma rota de {0} para {1} foi encontrada.";
	
	@Override
	public void cadastraMalhaLogistica(String nomeMapa, List<RotaTO> listRotasTO) throws IOException, ValidationException{
		
		Validate.notBlank(nomeMapa,MSG_NOME_MAPA_INVALIDO);

		try{
			List<DirectedEdge> rotas = new ArrayList<>();
			for(RotaTO rotaTO : listRotasTO){
				rotas.add(this.parseRotaTOtoDirectedEdge(rotaTO));
			}
		
			edgeWeightedDigraphDAO.save(nomeMapa, rotas);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}

	private DirectedEdge parseRotaTOtoDirectedEdge(RotaTO rotaTO) throws ValidationException{
		Validate.notBlank(rotaTO.getOrigem(),MSG_ORIGEM_INVALIDA);
		Validate.notBlank(rotaTO.getDestino(),MSG_DESTINO_INVALIDO);
		Validate.isTrue(rotaTO.getDistancia() < 0, MSG_DISTANCIA_NEGATIVA, rotaTO.getOrigem(), rotaTO.getDestino() );
		return new DirectedEdge(rotaTO.getDistancia(), rotaTO.getOrigem(), rotaTO.getDestino());
	}
	
	private RotaTO parseDirectedEdgetoRotaTO(DirectedEdge edge) {
		return new RotaTO(edge.getOrigem(),edge.getDestino(),edge.weight());
	}
	
	@Override
	public List<RotaTO> consultarMenorCaminho(String nomeMapa, String origem, String destino) throws IOException, ValidationException{
		
		Validate.notBlank(nomeMapa,MSG_NOME_MAPA_INVALIDO);
		Validate.notBlank(origem,MSG_ORIGEM_INVALIDA);
		Validate.notBlank(destino,MSG_DESTINO_INVALIDO);
		
		try{
			EdgeWeightedDigraph malhaLogistica = edgeWeightedDigraphDAO.findByNomMapa(nomeMapa);

			List<DirectedEdge> rotas = IteratorUtils.toList(malhaLogistica.edges().iterator());
			if(CollectionUtils.isEmpty(rotas)){
				throw new ValidationException(MSG_MAPA_SEM_ROTAS, nomeMapa);
			}
			
			int indexOrigem = this.getLocalIndex(origem, rotas);
			int indexDestino = this.getLocalIndex(destino, rotas);
	
			DijkstraSP shortPathUtils = new DijkstraSP(malhaLogistica, indexOrigem);
			
			Iterator<DirectedEdge> menorCaminho = shortPathUtils.pathTo(indexDestino).iterator();
			
			ArrayList<RotaTO> rotasMenorCaminho = new ArrayList<>();
			while (menorCaminho.hasNext()) {
				DirectedEdge edge = menorCaminho.next();
				rotasMenorCaminho.add(this.parseDirectedEdgetoRotaTO(edge));
			}
			if(CollectionUtils.isEmpty(rotasMenorCaminho)){
				throw new ValidationException(MSG_ORIGEM_DESTINO_SEM_ROTAS, origem, destino);
			}
			Collections.reverse(rotas);
			
			return rotasMenorCaminho;
		
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
		
	}

	@Override
	public Double calcularCustoPercurso(Double distancia, Double autonomia, Double precoCombustivel){
		Validate.isTrue(autonomia <= 0, MSG_AUTONOMIA_ZERO_NEGATIVA);
		Validate.isTrue(precoCombustivel <= 0, MSG_PRECO_COMBUSTIVEL_ZERO_NEGATIVO);
		return (distancia / autonomia) * precoCombustivel;
	}
	
	@Override
	public Double calcularCustoPercurso(List<RotaTO> rotas, Double autonomia, Double precoCombustivel){
		Double distancia = 0d;
		
		for(RotaTO rota : rotas){
			distancia += rota.getDistancia();
		}
		return this.calcularCustoPercurso(distancia, autonomia, precoCombustivel);
	}
	
	private int getLocalIndex(String local, List<DirectedEdge> rotas) throws ValidationException{
		Integer index = null;
		
		for(DirectedEdge rota : rotas){
			if(rota.getOrigem().equalsIgnoreCase(local)){
				index = rota.from();
				break;
			}
		}
		
		if(index == null){
			throw new ValidationException(MSG_LOCAL_SEM_ROTAS,local);
		}
		
		return index;
	}
}
