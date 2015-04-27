package br.com.desafio.entregamercadoria.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  Código da classe <tt>EdgeWeightedDirected</tt>, extraído e adaptado do site <a href="http://algs4.cs.princeton.edu/44sp/">http://algs4.cs.princeton.edu/44sp/</a>
 * 	para auxíliar no cálculo matemático de obtenção do menor caminho considerando dois pontos em um grafo composto
 * 	por vértices e arestas de diferentes pesos conectando-os.
 *	<br></br>
 * 	Entidade correspondente a uma malha logística. Encapsulando as instâncias das entidades {@link Rota}, correspondentes
 * 	as rotas.
 * 
 *  @author Carlos Vinícius
 */
public class MalhaLogistica {
	
	/**
	 * Quantidade localidades que a malha é composta.
	 */
    private final int qtdLocais;
    
    /**
     * Quantidade de rotas entre as localidades
     */
    private int qtdRotas;
    
    /**
     * Instâncias das entidades {@link Rota}
     */
    private List<Rota>[] adj;
    
    /**
     * Inicializa o objeto com uma quantidade definida de localidades, mas
     * sem nenhuma rota.
     * 
     * @param qtdLocais quantidade de localidades que a malha logística possuirá
     */
    @SuppressWarnings("unchecked")
	public MalhaLogistica(int qtdLocais) {
        if (qtdLocais < 0) throw new IllegalArgumentException("Quantidade de localidades não deve ser negativa");
        this.qtdLocais = qtdLocais;
        this.qtdRotas = 0;
        adj = new List[qtdLocais];
        for (int v = 0; v < qtdLocais; v++)
            adj[v] = new ArrayList<Rota>();
    }

    /**
     * Inicializa o objeto com uma quantidade definida de localidades e rotas.
     * 
     * @param qtdLocais quantidade de localidades que a malha logística possuirá
     * @param qtdRotas quantidade de rotas que a malha logística possuirá
     */
    public MalhaLogistica(int qtdLocais, int qtdRotas) {
        this(qtdLocais);
        if (qtdRotas < 0) throw new IllegalArgumentException("Number of edges in a Digraph must be nonnegative");
        for (int i = 0; i < qtdRotas; i++) {
            int v = (int) (Math.random() * qtdLocais);
            int w = (int) (Math.random() * qtdLocais);
            double weight = Math.round(100 * Math.random()) / 100.0;
            Rota e = new Rota(v, w, weight);
            addRota(e);
        }
    }


    /**
     * Retorna o número de localidades que a malha logísitica possui.
     * @return o número de localidades que a malha logísitica possui.
     */
    public int getQtdLocais() {
        return qtdLocais;
    }

    /**
     * Retorna o número de rotas que a malha logísitica possui.
     * @return o número de rotas que a malha logísitica possui.
     */
    public int getQtdRotas() {
        return qtdRotas;
    }

    /**
     * Valida o índece informado. O valor deve ser maior que zero e menor que 
     * a quanitade total de localidades.
     * 
     * @param indexLocal
     */
    private void validateVertex(int indexLocal) {
        if (indexLocal < 0 || indexLocal >= qtdLocais)
            throw new IndexOutOfBoundsException("índice " + indexLocal + " não esta entre 0 e " + (qtdLocais-1));
    }

    /**
     * Adciona uma nova rota a malha logística.
     * 
     * @param e objeto referente a entidade {@link Rota}
     */
    public void addRota(Rota e) {
        int indexOrigem = e.from();
        int indexDestino = e.to();
        validateVertex(indexOrigem);
        validateVertex(indexDestino);
        adj[indexOrigem].add(e);
        qtdRotas++;
    }


    /**
     * Retorna as rotas que partem do índice informado.
     * 
     * @param indexLocal índice da localidade a qual deseja-se obter as rotas que partem dela
     * @return objeto {@link Iterable} referente as rotas do índice.
     */
    public Iterable<Rota> adj(int indexLocal) {
        validateVertex(indexLocal);
        return adj[indexLocal];
    }

    /**
     * Retorna o número de rotas a partir do índice informado. 
     * 
     * @param indexLocal índice da localidade a qual deseja-se obter a quantiade de rotas que partem dela.
     * @return quantiade de rotas que partem do índice informado.
     */
    public int outdegree(int indexLocal) {
        validateVertex(indexLocal);
        return adj[indexLocal].size();
    }

    /**
     * Retorna todas as rotas.
     * 
     * @return lista de entidades {@link Rotas}
     */
    public Iterable<Rota> getRotas() {
        List<Rota> list = new ArrayList<Rota>();
        for (int v = 0; v < qtdLocais; v++) {
            for (Rota e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    } 

    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(qtdLocais + " " + qtdRotas + NEWLINE);
        for (int v = 0; v < qtdLocais; v++) {
            s.append(v + ": ");
            for (Rota e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

}
