package br.com.desafio.entregamercadoria.entity;

/**
 * 
 *  Código da classe <tt>DirectedEdge</tt>, extraído e adaptado do site <a href="http://algs4.cs.princeton.edu/44sp/">http://algs4.cs.princeton.edu/44sp/</a>
 * 	para auxíliar no cálculo matemático de obtenção do menor caminho considerando dois pontos em um grafo composto
 * 	por vértices e arestas de diferentes pesos conectando-os.
 *	<br></br>
 * 	Entidade correspondente a uma rota. Contendo as informações referentes ao local de origem e destino, a distância entre
 * 	eles e seus respectivos índices, utilizados na obtenção do menor caminho.
 * 
 *  @author Carlos Vinícius
 */

public class Rota { 
	/**
	 * Índice numérico do local de origem
	 */
    private final int indexOrigem;
    
    /**
	 * Índice numérico do local de destino
	 */
    private final int indexDestino;
    
    /**
	 * Distância entre os locais de origem e destino
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
     * Inicializa uma nova rota com os valores dos índices de origem e destino e 
     * o tamanho do percurso da rota.
     * 
     * @param indexOrigem índice de origem
     * @param indexDestino índice de destino
     * @param distancia tamanho do percurso da rota em quilometros.
     */
    public Rota(int indexOrigem, int indexDestino, double distancia) {
        if (indexOrigem < 0) throw new IndexOutOfBoundsException("Valor do índice não deve ser negativo");
        if (indexDestino < 0) throw new IndexOutOfBoundsException("Valor do índice não deve ser negativo");
        if (Double.isNaN(distancia)) throw new IllegalArgumentException("Distancia não é um número");
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
        if (Double.isNaN(distancia)) throw new IllegalArgumentException("Distancia não é um número");
		this.distancia = distancia;
		this.origem = origem;
		this.destino = destino;
		this.indexOrigem = 0;
        this.indexDestino = 0;
	}



	/**
     * Retorna o índice da origem
     * @return índice da origem
     */
    public int from() {
        return indexOrigem;
    }

    /**
     * Retorna o índice do destino
     * @return índice da destino
     */
    public int to() {
        return indexDestino;
    }

    /**
     * Retorna a distância entre os locais de origem e destino.
     * @return a distância entre os locais de origem e destino em quilometros.
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

