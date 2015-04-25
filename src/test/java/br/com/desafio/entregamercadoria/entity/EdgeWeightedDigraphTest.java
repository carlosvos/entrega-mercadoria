package br.com.desafio.entregamercadoria.entity;

import org.junit.Test;

public class EdgeWeightedDigraphTest {
	
	private final int V = 12;
	private final int V_NEGATIVO = -12;
	
	@Test
	public void testNewMalhaLogisticaSuccess(){
        new EdgeWeightedDigraph(V);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNewMalhaLogisticaRotasMenorZero(){
        new EdgeWeightedDigraph(V_NEGATIVO);
	}

}
