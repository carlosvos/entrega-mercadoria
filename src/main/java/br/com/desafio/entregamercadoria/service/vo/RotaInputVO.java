package br.com.desafio.entregamercadoria.service.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objeto de entrada do serviço de cadsatro contendo as informações de origem, destino e distância da rota.
 * 
 * @author Carlos Vinícius
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RotaInputVO implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * nome do local de origem.
	 */
	private String origem;
	
	/**
	 * nome do local de destino.
	 */
	private String destino;
	
	/**
	 * distância entre o ponto de origem e o ponto de destino.
	 */
	private Double distancia;
	
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
