package br.com.desafio.entregamercadoria.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import br.com.desafio.entregamercadoria.business.MalhaLogisticaBusiness;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.dao.EdgeWeightedDigraphDAO;
import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;
import br.com.desafio.entregamercadoria.service.vo.CadastraMalhaLogisticaInputVO;
import br.com.desafio.entregamercadoria.service.vo.RotaInputVO;
import br.com.desafio.entregamercadoria.utils.DijkstraSP;

@Service
public class MalhaLogisticaBusinessImpl implements MalhaLogisticaBusiness {
	
	@Resource
	private EdgeWeightedDigraphDAO edgeWeightedDigraphDAO;
	
	public void cadastraMalhaLogistica(CadastraMalhaLogisticaInputVO input){
		String nomeMapa = input.getNomMalhaLogistica();
		List<DirectedEdge> rotas = new ArrayList<>();
		
		for(RotaInputVO rotaInput : input.getRotas()){
			rotas.add(this.parseRotaInputVOtoDirectedEdge(rotaInput));
		}
		
		try{
			edgeWeightedDigraphDAO.save(nomeMapa, rotas);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	private DirectedEdge parseRotaInputVOtoDirectedEdge(RotaInputVO rotaInput) {
		return new DirectedEdge(rotaInput.getDistancia(), rotaInput.getOrigem(), rotaInput.getDestino());
	}
	
	private RotaTO parseDirectedEdgetoRotaTO(DirectedEdge edge) {
		return new RotaTO(edge.getOrigem(),edge.getDestino(),edge.weight());
	}
	
	@Override
	public List<RotaTO> consultarMenorCaminho(String nomeMapa, String origem, String destino){
		EdgeWeightedDigraph malhaLogistica = edgeWeightedDigraphDAO.findByNomMapa(nomeMapa);
		
		Iterator<DirectedEdge> rotasIterator = malhaLogistica.edges().iterator();
		int indexOrigem = this.getLocalIndex(origem, rotasIterator);
		
		rotasIterator = malhaLogistica.edges().iterator();
		int indexDestino = this.getLocalIndex(destino, rotasIterator);

		DijkstraSP shortPathUtils = new DijkstraSP(malhaLogistica, indexOrigem);
		
		Iterator<DirectedEdge> menorCaminho = shortPathUtils.pathTo(indexDestino).iterator();
		
		ArrayList<RotaTO> rotas = new ArrayList<>();
		while (menorCaminho.hasNext()) {
			DirectedEdge edge = menorCaminho.next();
			rotas.add(this.parseDirectedEdgetoRotaTO(edge));
		}
		Collections.reverse(rotas);
		
		return rotas;
		
	}

	@Override
	public Double calcularCustoPercurso(Double distancia, Double autonomia, Double precoCombustivel){
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
	
	private int getLocalIndex(String local, Iterator<DirectedEdge> rotasIterator){
		int index = 0;
		
		while(rotasIterator.hasNext()){
			DirectedEdge rota = rotasIterator.next();
			if(rota.getOrigem().equalsIgnoreCase(local)){
				index = rota.from();
			}
		}
		
		return index;
	}

}
