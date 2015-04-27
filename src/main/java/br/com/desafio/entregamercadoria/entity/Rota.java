package br.com.desafio.entregamercadoria.entity;

/**
 * 
 *  C�digo da classe <tt>DirectedEdge</tt>, extra�do e adaptado do site <a href="http://algs4.cs.princeton.edu/44sp/">http://algs4.cs.princeton.edu/44sp/</a>
 * 	para aux�liar no c�lculo matem�tico de obten��o do menor caminho considerando dois pontos em um grafo composto
 * 	por v�rtices e arestas de diferentes pesos conectando-os.
 *	<br></br>
 * 	Entidade correspondente a uma rota. Contendo as informa��es referentes ao local de origem e destino, a dist�ncia entre
 * 	eles e seus respectivos �ndices, utilizados na obten��o do menor caminho.
 * 
 *  @author Carlos Vin�cius
 */

public class Rota { 
	/**
	 * �ndice num�rico do local de origem
	 */
    private final int indexOrigem;
    
    /**
	 * �ndice num�rico do local de destino
	 */
    private final int indexDestino;
    
    /**
	 * Dist�ncia entre os locais de origem e destino
	 */
    private final double distancia;
    
    /**
     * Local de origem
     */
    private String origem;
    
    /**
     * Local de Destino
     */
    private String destino;

    /**
     * Inicializa uma nova rota com os valores dos �ndices de origem e destino e 
     * o tamanho do percurso da rota.
     * 
     * @param indexOrigem �ndice de origem
     * @param indexDestino �ndice de destino
     * @param distancia tamanho do percurso da rota em quilometros.
     */
    public Rota(int indexOrigem, int indexDestino, double distancia) {
        if (indexOrigem < 0) throw new IndexOutOfBoundsException("Valor do �ndice n�o deve ser negativo");
        if (indexDestino < 0) throw new IndexOutOfBoundsException("Valor do �ndice n�o deve ser negativo");
        if (Double.isNaN(distancia)) throw new IllegalArgumentException("Distancia n�o � um n�mero");
        this.indexOrigem = indexOrigem;
        this.indexDestino = indexDestino;
        this.distancia = distancia;
    }
    
    /**
     * Inicializa uma nova rota com os locais de origem e destino e 
     * o tamanho do percurso da rota.
     * 
     * @param origem local de origem
     * @param destino local de destino
     * @param distancia tamanho do percurso da rota em quilometros.
     */
    public Rota(double distancia, String origem, String destino) {
		super();
        if (Double.isNaN(distancia)) throw new IllegalArgumentException("Distancia n�o � um n�mero");
		this.distancia = distancia;
		this.origem = origem;
		this.destino = destino;
		this.indexOrigem = 0;
        this.indexDestino = 0;
	}



	/**
     * Retorna o �ndice da origem
     * @return �ndice da origem
     */
    public int from() {
        return indexOrigem;
    }

    /**
     * Retorna o �ndice do destino
     * @return �ndice da destino
     */
    public int to() {
        return indexDestino;
    }

    /**
     * Retorna a dist�ncia entre os locais de origem e destino.
     * @return a dist�ncia entre os locais de origem e destino em quilometros.
     */
    public double weight() {
        return distancia;
    }
    
    public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

    public String toString() {
        return origem + "->" + destino + " " + String.format("%5.2f", distancia);
    }
    
}

