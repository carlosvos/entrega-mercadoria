package br.com.desafio.entregamercadoria.entity;

import org.junit.Test;

/**
 * Teste unitário da entidade {@link DirectedEdge}
 * 
 * @author Carlos Vinícius
 *
 */
public class DirectedEdgeTest {
	
	private final int V = 12;
	private final int V_NEGATIVO = -12;
	private final int W = 23;
	private final int W_NEGATIVO = -23;
	private final Double DISTANCIA = 3.14d;
	
	/**
	 * Teste de sucesso de incialização de uma instância da entidade 
	 */
	@Test
	public void testNewRotaSuccess(){
        new DirectedEdge(V,W,DISTANCIA);
	}
	
	/**
	 * Teste falho de incialização de uma instância da entidade 
	 * contendo um valor negativo para o índice do local de origem,
	 * 
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexOrigemMenorZero(){
        new DirectedEdge(V_NEGATIVO,W,DISTANCIA);
	}
	
	/**
	 * Teste falho de incialização de uma instância da entidade 
	 * contendo um valor negativo para o índice do local de destino,
	 * 
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexDestinoMenorZero(){
        new DirectedEdge(V,W_NEGATIVO,DISTANCIA);
	}

}
