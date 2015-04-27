package br.com.desafio.entregamercadoria.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.desafio.entregamercadoria.entity.Rota;
import br.com.desafio.entregamercadoria.entity.MalhaLogistica;

/**
 * Teste unit�rio da classe utilit�ria {@link DijkstraSP}
 * 
 * @author Carlos Vin�cius
 *
 */
public class DijkstraSPTest {
	
	private final int S = 0;
	private final int W = 2;
	private final int E = 12;
	private final int V = 5;
	
	/**
	 * Teste de sucesso de obten��o do menor caminho.
	 * 
	 */
	@Test
	public void testPathToSuccess(){
		Iterable<Rota> iterableMenorCaminho = new DijkstraSP(createMalhaLogistica(), S).pathTo(W);
		Assert.assertNotNull(iterableMenorCaminho);

		Iterator<Rota> menorcaminho = iterableMenorCaminho.iterator();
		Assert.assertTrue(menorcaminho.hasNext());
	}
	
	/**
	 * Teste falho com um valor negativo de dist�ncia de uma rota
	 * durante a obten��o do menor caminho.
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPathToDistanciaMenorZeroFailure(){
		MalhaLogistica malhaLogistica = createMalhaLogistica();
		malhaLogistica.addRota(createRota(5, 6, "F", "G", -10d));
		
		new DijkstraSP(malhaLogistica, S).pathTo(W);
		
	}
	
	/**
	 * Teste de sucesso de obten��o do menor caminho
	 */
	@Test
	public void testDijkstraSP(){
        MalhaLogistica G = new MalhaLogistica(V,E);

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, S);


        // print shortest path
        for (int t = 0; t < G.getQtdLocais(); t++) {
            if (sp.hasPathTo(t)) {
                System.out.printf("%d to %d (%.2f)  ", S, t, sp.distTo(t));
                if (sp.hasPathTo(t)) {
                    for (Rota e : sp.pathTo(t)) {
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
	
	/**
	 * cria uma inst�ncia da entidade {@link MalhaLogistica}
	 * 
	 * @return uma inst�ncia de {@link MalhaLogistica}
	 */
	private MalhaLogistica createMalhaLogistica(){
		List<Rota> listRota = this.createListRota();
		MalhaLogistica malhaLogistica = new MalhaLogistica(listRota.size());
		for(Rota rota : listRota){
			malhaLogistica.addRota(rota);
		}
		return malhaLogistica;

	}
	
	/**
	 * cria uma lista de objetos da entidade {@link Rota}
	 * 
	 * @return lista contendo inst�ncias de {@link Rota}
	 */
	private List<Rota> createListRota() {
		List<Rota> listRotaTO = new ArrayList<>();
		listRotaTO.add(this.createRota(0,1,"A","B",10d));
		listRotaTO.add(this.createRota(1,3,"B","D",15d));
		listRotaTO.add(this.createRota(0,2,"A","C",20d));
		listRotaTO.add(this.createRota(2,3,"C","D",30d));
		listRotaTO.add(this.createRota(1,4,"B","E",50d));
		listRotaTO.add(this.createRota(3,4,"D","E",30d));
		listRotaTO.add(this.createRota(2,4,"C","E",0d));
		return listRotaTO;
	}
	
	/**
	 * cria uma inst�ncia da entidade {@link Rota}
	 * 
	 * @return uma inst�ncia de {@link Rota}
	 */
	private Rota createRota(int v, int w, String origem, String destino, Double distancia){
		Rota rota = new Rota(v,w,distancia);
		rota.setOrigem(origem);
		rota.setDestino(destino);
		return rota;
	}

}
