package br.com.desafio.entregamercadoria.service.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objeto de entrada do servi�o de consulta do menor caminho contendo as informa��es de origem, destino, autonomia,
 * pre�o do combust�vel e a malha log�stica onde a consulta ser� feita.
 * 
 * @author Carlos Vin�cius
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ConsultaMenorCaminhoInputVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * nome da malha log�stica onde ser� feita a consulta do menor caminho.
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
	 * quantidade de quilometros que o ve�culo percorre por litro de combust�vel.
	 */
	private Double autonomia;
	
	/**
	 * pre�o do litro do combust�vel
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
