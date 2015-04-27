package br.com.desafio.entregamercadoria.entity;

import org.junit.Test;

/**
 * Teste unit�rio da entidade {@link EdgeWeightedDigraph}
 * 
 * @author T37954
 *
 */
public class EdgeWeightedDigraphTest {
	
	private final int V = 12;
	private final int V_NEGATIVO = -12;
	
	/**
	 * Teste de sucesso de incializa��o de uma inst�ncia da entidade 
	 */
	@Test
	public void testNewMalhaLogisticaSuccess(){
        new EdgeWeightedDigraph(V);
	}
	
	/**
	 * Teste falho de incializa��o de uma inst�ncia da entidade 
	 * contendo um valor negativo para a quantidade de rotas.
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNewMalhaLogisticaRotasMenorZero(){
        new EdgeWeightedDigraph(V_NEGATIVO);
	}

}
