package br.com.desafio.entregamercadoria.utils;

import org.junit.Test;

/**
 * Teste unit�rio da classe utilit�ria {@link IndexMinPQ}
 * 
 * @author Carlos Vin�cius
 *
 */
public class IndexMinPQTest {
	
	/**
	 * Teste de sucesso da execu��o do algoritimo
	 */
	@Test
	public void testIndexMinPQSuccess(){
        // insert a bunch of strings
        String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };

        IndexMinPQ<String> pq = new IndexMinPQ<String>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // delete and print each key
        while (!pq.isEmpty()) {
            int i = pq.delMin();
            System.out.println(i + " " + strings[i]);
        }
        System.out.println();

        // reinsert the same strings
        for (int i = 0; i < strings.length; i++) {
            pq.insert(i, strings[i]);
        }

        // print each key using the iterator
        for (int i : pq) {
        	System.out.println(i + " " + strings[i]);
        }
        while (!pq.isEmpty()) {
            pq.delMin();
        }

	}

}
