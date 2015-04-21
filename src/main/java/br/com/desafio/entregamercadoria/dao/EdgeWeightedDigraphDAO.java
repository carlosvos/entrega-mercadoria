package br.com.desafio.entregamercadoria.dao;

import java.util.List;

import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;

public interface EdgeWeightedDigraphDAO {
	
	void save(String nomMapa, List<DirectedEdge> rotas);
	
	EdgeWeightedDigraph findByNomMapa(String nomMapa);

}
