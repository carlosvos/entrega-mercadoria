package br.com.desafio.entregamercadoria.service.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objeto de entrada do serviço de consulta do menor caminho contendo as informações de origem, destino, autonomia,
 * preço do combustível e a malha logística onde a consulta será feita.
 * 
 * @author Carlos Vinícius
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ConsultaMenorCaminhoInputVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * nome da malha logística onde será feita a consulta do menor caminho.
	 */
	private String nomMalhaLogistica;
	
	/**
	 * nome do local de origem.
	 */
	private String origem;
	
	/**
	 * nome do local de destino.
	 */
	private String destino;
	
	/**
	 * quantidade de quilometros que o veículo percorre por litro de combustível.
	 */
	private Double autonomia;
	
	/**
	 * preço do litro do combustível
	 */
	private Double vlrCombustivel;
	
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
	public Double getAutonomia() {
		return autonomia;
	}
	public void setAutonomia(Double autonomia) {
		this.autonomia = autonomia;
	}
	public Double getVlrCombustivel() {
		return vlrCombustivel;
	}
	public void setVlrCombustivel(Double vlrCombustivel) {
		this.vlrCombustivel = vlrCombustivel;
	}
	public String getNomMalhaLogistica() {
		return nomMalhaLogistica;
	}
	public void setNomMalhaLogistica(String nomMalhaLogistica) {
		this.nomMalhaLogistica = nomMalhaLogistica;
	}
	
}
