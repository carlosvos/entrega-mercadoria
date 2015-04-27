package br.com.desafio.entregamercadoria.entity;

/**
 * 
 * 	C�digo extra�do do site <a href="http://algs4.cs.princeton.edu/44sp/">http://algs4.cs.princeton.edu/44sp/</a>
 * 	para aux�liar no c�lculo matem�tico de obten��o do menor caminho considerando dois pontos em um grafo composto
 * 	por v�rtices e arestas de diferentes pesos conectando-os.
 * 
 * 	Entidade correspondente a uma rota. Contendo as informa��es referentes ao local de origem e destino, a dist�ncia entre
 * 	eles e seus respectivos �ndices, utilizados na obten��o do menor caminho.
 * 
 *  The <tt>DirectedEdge</tt> class represents a weighted edge in an 
 *  {@link EdgeWeightedDigraph}. Each edge consists of two integers
 *  (naming the two vertices) and a real-value weight. The data type
 *  provides methods for accessing the two endpoints of the directed edge and
 *  the weight.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class DirectedEdge { 
	/**
	 * �ndice num�rico do local de origem
	 */
    private final int v;
    
    /**
	 * �ndice num�rico do local de destino
	 */
    private final int w;
    
    /**
	 * Dist�ncia entre os locais de origem e destino
	 */
    private final double weight;
    
    /**
     * Local de origem
     */
    private String origem;
    
    /**
     * Local de Destino
     */
    private String destino;

    /**
     * Initializes a directed edge from vertex <tt>v</tt> to vertex <tt>w</tt> with
     * the given <tt>weight</tt>.
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws java.lang.IndexOutOfBoundsException if either <tt>v</tt> or <tt>w</tt>
     *    is a negative integer
     * @throws IllegalArgumentException if <tt>weight</tt> is <tt>NaN</tt>
     */
    public DirectedEdge(int v, int w, double weight) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    
    public DirectedEdge(double weight, String origem, String destino) {
		super();
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
		this.weight = weight;
		this.origem = origem;
		this.destino = destino;
		this.v = 0;
        this.w = 0;
	}



	/**
     * Returns the tail vertex of the directed edge.
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     * @return the head vertex of the directed edge
     */
    public int to() {
        return w;
    }

    /**
     * Returns the weight of the directed edge.
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
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

	/**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }
    
}

