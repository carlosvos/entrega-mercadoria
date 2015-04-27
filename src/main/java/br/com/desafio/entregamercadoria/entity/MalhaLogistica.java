package br.com.desafio.entregamercadoria.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  C�digo da classe <tt>EdgeWeightedDirected</tt>, extra�do e adaptado do site <a href="http://algs4.cs.princeton.edu/44sp/">http://algs4.cs.princeton.edu/44sp/</a>
 * 	para aux�liar no c�lculo matem�tico de obten��o do menor caminho considerando dois pontos em um grafo composto
 * 	por v�rtices e arestas de diferentes pesos conectando-os.
 *	<br></br>
 * 	Entidade correspondente a uma malha log�stica. Encapsulando as inst�ncias das entidades {@link Rota}, correspondentes
 * 	as rotas.
 * 
 *  @author Carlos Vin�cius
 */
public class MalhaLogistica {
	
	/**
	 * Quantidade localidades que a malha � composta.
	 */
    private final int qtdLocais;
    
    /**
     * Quantidade de rotas entre as localidades
     */
    private int qtdRotas;
    
    /**
     * Inst�ncias das entidades {@link Rota}
     */
    private List<Rota>[] adj;
    
    /**
     * Inicializa o objeto com uma quantidade definida de localidades, mas
     * sem nenhuma rota.
     * 
     * @param qtdLocais quantidade de localidades que a malha log�stica possuir�
     */
    @SuppressWarnings("unchecked")
	public MalhaLogistica(int qtdLocais) {
        if (qtdLocais < 0) throw new IllegalArgumentException("Quantidade de localidades n�o deve ser negativa");
        this.qtdLocais = qtdLocais;
        this.qtdRotas = 0;
        adj = new List[qtdLocais];
        for (int v = 0; v < qtdLocais; v++)
            adj[v] = new ArrayList<Rota>();
    }

    /**
     * Inicializa o objeto com uma quantidade definida de localidades e rotas.
     * 
     * @param qtdLocais quantidade de localidades que a malha log�stica possuir�
     * @param qtdRotas quantidade de rotas que a malha log�stica possuir�
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
     * Retorna o n�mero de localidades que a malha log�sitica possui.
     * @return o n�mero de localidades que a malha log�sitica possui.
     */
    public int getQtdLocais() {
        return qtdLocais;
    }

    /**
     * Retorna o n�mero de rotas que a malha log�sitica possui.
     * @return o n�mero de rotas que a malha log�sitica possui.
     */
    public int getQtdRotas() {
        return qtdRotas;
    }

    /**
     * Valida o �ndece informado. O valor deve ser maior que zero e menor que 
     * a quanitade total de localidades.
     * 
     * @param indexLocal
     */
    private void validateVertex(int indexLocal) {
        if (indexLocal < 0 || indexLocal >= qtdLocais)
            throw new IndexOutOfBoundsException("�ndice " + indexLocal + " n�o esta entre 0 e " + (qtdLocais-1));
    }

    /**
     * Adciona uma nova rota a malha log�stica.
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
     * Retorna as rotas que partem do �ndice informado.
     * 
     * @param indexLocal �ndice da localidade a qual deseja-se obter as rotas que partem dela
     * @return objeto {@link Iterable} referente as rotas do �ndice.
     */
    public Iterable<Rota> adj(int indexLocal) {
        validateVertex(indexLocal);
        return adj[indexLocal];
    }

    /**
     * Retorna o n�mero de rotas a partir do �ndice informado. 
     * 
     * @param indexLocal �ndice da localidade a qual deseja-se obter a quantiade de rotas que partem dela.
     * @return quantiade de rotas que partem do �ndice informado.
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
