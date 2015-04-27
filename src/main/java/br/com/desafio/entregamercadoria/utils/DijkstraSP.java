package br.com.desafio.entregamercadoria.utils;

import java.util.Stack;


import br.com.desafio.entregamercadoria.entity.Rota;
import br.com.desafio.entregamercadoria.entity.MalhaLogistica;

/**
 * 
 *  Código extraído do site <a href="http://algs4.cs.princeton.edu/44sp/">http://algs4.cs.princeton.edu/44sp/</a>
 * 	para auxíliar no cálculo matemático de obtenção do menor caminho considerando dois pontos em um grafo composto
 * 	por vértices e arestas de diferentes pesos conectando-os.
 *  <br></br>
 *  Classe utilitária que implementa o algoritmo de DijkstraSP para obtenção do menor caminho entre dois pontos.
 *  Para realização do cáculo, a classe deve ser inicializada passando-se um objeto {@link MalhaLogistica} e 
 *  o índice o ponto de origem como parâmetros do construtor.
 *  <br></br>
 *  The <tt>DijkstraSP</tt> class represents a data type for solving the
 *  single-source shortest paths problem in edge-weighted digraphs
 *  where the edge weights are nonnegative.
 *  <p>
 *  This implementation uses Dijkstra's algorithm with a binary heap.
 *  The constructor takes time proportional to <em>E</em> log <em>V</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  Afterwards, the <tt>distTo()</tt> and <tt>hasPathTo()</tt> methods take
 *  constant time and the <tt>pathTo()</tt> method takes time proportional to the
 *  number of edges in the shortest path returned.
 *  <p>
 *  For additional documentation, see <a href="/algs4/44sp">Section 4.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */

public class DijkstraSP {
    private double[] distTo;          // distTo[v] = distance  of shortest s->v path
    private Rota[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
    private IndexMinPQ<Double> pq;    // priority queue of vertices

    /**
     * Inicializa um objeto {@link DijkstraSP} para cálculo do
     * menor caminho considerando um objeto {@link MalhaLogistica}, contendo as
     * rotas utilizadas no cálculo, e um índice numérico do local de origem passados 
     * como parâmetro.
     * 
     * @param G objeto {@link MalhaLogistica} correspondente a malha logística
     * contendo as rotas que serão utilizadas no cálculo do algoritimo.
     * @param s índice númerico do local de origem
     * @throws IllegalArgumentException exceção lançada caso alguma das rotas possua 
     * distância negativa
     * @throws IllegalArgumentException exceção lançada o índece do local de origem
     * serja menor que zero
     */
    public DijkstraSP(MalhaLogistica G, int s) {
        for (Rota e : G.getRotas()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("rota " + e + " possui distância negativa");
        }

        distTo = new double[G.getQtdLocais()];
        edgeTo = new Rota[G.getQtdLocais()];
        for (int v = 0; v < G.getQtdLocais(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        // relax vertices in order of distance from s
        pq = new IndexMinPQ<Double>(G.getQtdLocais());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Rota e : G.adj(v))
                relax(e);
        }

        // check optimality conditions
        assert check(G, s);
    }

    // relax edge e and update pq if changed
    private void relax(Rota e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    /**
     * Returns the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return the length of a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>;
     *    <tt>Double.POSITIVE_INFINITY</tt> if no such path
     */
    public double distTo(int v) {
        return distTo[v];
    }

    /**
     * Is there a path from the source vertex <tt>s</tt> to vertex <tt>v</tt>?
     * @param v the destination vertex
     * @return <tt>true</tt> if there is a path from the source vertex
     *    <tt>s</tt> to vertex <tt>v</tt>, and <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * 
     * Returns a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>.
     * @param v the destination vertex
     * @return a shortest path from the source vertex <tt>s</tt> to vertex <tt>v</tt>
     *    as an iterable of edges, and <tt>null</tt> if no such path
     */
    public Iterable<Rota> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Rota> path = new Stack<Rota>();
        for (Rota e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }


    // check optimality conditions:
    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
    private boolean check(MalhaLogistica G, int s) {

        // check that edge weights are nonnegative
        for (Rota e : G.getRotas()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.getQtdLocais(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.getQtdLocais(); v++) {
            for (Rota e : G.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.getQtdLocais(); w++) {
            if (edgeTo[w] == null) continue;
            Rota e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

}
