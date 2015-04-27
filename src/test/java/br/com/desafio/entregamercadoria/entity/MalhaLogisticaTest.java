package br.com.desafio.entregamercadoria.entity;

import org.junit.Test;

/**
 * Teste unitário da entidade {@link MalhaLogistica}
 * 
 * @author T37954
 *
 */
public class MalhaLogisticaTest {
	
	private final int V = 12;
	private final int V_NEGATIVO = -12;
	
	/**
	 * Teste de sucesso de incialização de uma instância da entidade 
	 */
	@Test
	public void testNewMalhaLogisticaSuccess(){
        new MalhaLogistica(V);
	}
	
	/**
	 * Teste falho de incialização de uma instância da entidade 
	 * contendo um valor negativo para a quantidade de rotas.
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNewMalhaLogisticaRotasMenorZero(){
        new MalhaLogistica(V_NEGATIVO);
	}

}
