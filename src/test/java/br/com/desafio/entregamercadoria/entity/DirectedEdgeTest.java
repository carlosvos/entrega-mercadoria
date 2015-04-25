package br.com.desafio.entregamercadoria.entity;

import org.junit.Test;

public class DirectedEdgeTest {
	
	private final int V = 12;
	private final int V_NEGATIVO = -12;
	private final int W = 23;
	private final int W_NEGATIVO = -23;
	private final Double DISTANCIA = 3.14d;
	
	@Test
	public void testNewRotaSuccess(){
        new DirectedEdge(V,W,DISTANCIA);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexOrigemMenorZero(){
        new DirectedEdge(V_NEGATIVO,W,DISTANCIA);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexDestinoMenorZero(){
        new DirectedEdge(V,W_NEGATIVO,DISTANCIA);
	}

}
