package br.com.desafio.entregamercadoria.business.to;

import java.io.Serializable;

/**
 * Objeto de transporte das informações de origem, destino e distância de uma rota.
 * 
 * @author Carlos Vinícius
 *
 */
public class RotaTO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Nome do local de origem da rota.
	 */
	private String origem;
	
	/**
	 * Nome do local de destino da rota.
	 */
	private String destino;
	
	/**
	 * Quantidade de Km que separam o local de origem do local de destino.
	 */
	private Double distancia;
	
	public RotaTO(String origem, String destino, Double distancia) {
		super();
		this.origem = origem;
		this.destino = destino;
		this.distancia = distancia;
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
	public Double getDistancia() {
		return distancia;
	}
	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}
	
}
