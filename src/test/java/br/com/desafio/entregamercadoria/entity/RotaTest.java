package br.com.desafio.entregamercadoria.entity;

import org.junit.Test;

/**
 * Teste unitário da entidade {@link Rota}
 * 
 * @author Carlos Vinícius
 *
 */
public class RotaTest {
	
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
        new Rota(V,W,DISTANCIA);
	}
	
	/**
	 * Teste falho de incialização de uma instância da entidade 
	 * contendo um valor negativo para o índice do local de origem,
	 * 
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexOrigemMenorZero(){
        new Rota(V_NEGATIVO,W,DISTANCIA);
	}
	
	/**
	 * Teste falho de incialização de uma instância da entidade 
	 * contendo um valor negativo para o índice do local de destino,
	 * 
	 */
	@Test(expected=IndexOutOfBoundsException.class)
	public void testNewRotaIndexDestinoMenorZero(){
        new Rota(V,W_NEGATIVO,DISTANCIA);
	}

}
