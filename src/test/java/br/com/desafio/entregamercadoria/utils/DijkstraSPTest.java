package br.com.desafio.entregamercadoria.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.desafio.entregamercadoria.entity.DirectedEdge;
import br.com.desafio.entregamercadoria.entity.EdgeWeightedDigraph;

public class DijkstraSPTest {
	
	private final int S = 0;
	private final int W = 2;
	private final int E = 12;
	private final int V = 5;
	
	@Test
	public void testPathToSuccess(){
		Iterable<DirectedEdge> iterableMenorCaminho = new DijkstraSP(createMalhaLogistica(), S).pathTo(W);
		Assert.assertNotNull(iterableMenorCaminho);

		Iterator<DirectedEdge> menorcaminho = iterableMenorCaminho.iterator();
		Assert.assertTrue(menorcaminho.hasNext());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPathToDistanciaMenorZeroFailure(){
		EdgeWeightedDigraph malhaLogistica = createMalhaLogistica();
		malhaLogistica.addEdge(createRota(5, 6, "F", "G", -10d));
		
		new DijkstraSP(malhaLogistica, S).pathTo(W);
		
	}
	
	@Test
	public void testDijkstraSP(){
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V,E);

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, S);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                System.out.printf("%d to %d (%.2f)  ", S, t, sp.distTo(t));
                if (sp.hasPathTo(t)) {
                    for (DirectedEdge e : sp.pathTo(t)) {
                    	System.out.print(e + "   ");
                    }
                }
                System.out.println();
            }
            else {
            	System.out.printf("%d to %d         no path\n", S, t);
            }
        }
    }
	
	private EdgeWeightedDigraph createMalhaLogistica(){
		List<DirectedEdge> listRota = this.createListRota();
		EdgeWeightedDigraph malhaLogistica = new EdgeWeightedDigraph(listRota.size());
		for(DirectedEdge rota : listRota){
			malhaLogistica.addEdge(rota);
		}
		return malhaLogistica;

	}
	
	private List<DirectedEdge> createListRota() {
		List<DirectedEdge> listRotaTO = new ArrayList<>();
		listRotaTO.add(this.createRota(0,1,"A","B",10d));
		listRotaTO.add(this.createRota(1,3,"B","D",15d));
		listRotaTO.add(this.createRota(0,2,"A","C",20d));
		listRotaTO.add(this.createRota(2,3,"C","D",30d));
		listRotaTO.add(this.createRota(1,4,"B","E",50d));
		listRotaTO.add(this.createRota(3,4,"D","E",30d));
		listRotaTO.add(this.createRota(2,4,"C","E",0d));
		return listRotaTO;
	}
	
	private DirectedEdge createRota(int v, int w, String origem, String destino, Double distancia){
		DirectedEdge rota = new DirectedEdge(v,w,distancia);
		rota.setOrigem(origem);
		rota.setDestino(destino);
		return rota;
	}

}
