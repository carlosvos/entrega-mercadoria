package br.com.desafio.entregamercadoria.entity;

import org.junit.Test;

/**
 * Teste unit�rio da entidade {@link DirectedEdge}
 * 
 * @author Carlos Vin�cius
 *
 */
public class DirectedEdgeTest {
	
	private final int V = 12;
	private final int V_NEGATIVO = -12;
	private final int W = 23;
	private final int W_NEGATIVO = -23;
	private final Double DISTANCIA = 3.14d;
	
	/**
	 * Teste de sucesso de incializa��o de uma inst�ncia da entidade 
	 */
	@Test
	public void testNewRotaSuccess(){
        new DirectedEdge(V,W,DISTANCIA);
	}
	
	/**
	 * Teste falho de incializa��o de uma inst�ncia da entidade 
	 * contendo um valor negativo para o �ndice do local de origem,
	 * 
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexOrigemMenorZero(){
        new DirectedEdge(V_NEGATIVO,W,DISTANCIA);
	}
	
	/**
	 * Teste falho de incializa��o de uma inst�ncia da entidade 
	 * contendo um valor negativo para o �ndice do local de destino,
	 * 
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexDestinoMenorZero(){
        new DirectedEdge(V,W_NEGATIVO,DISTANCIA);
	}

}
