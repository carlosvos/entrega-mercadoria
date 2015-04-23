package br.com.desafio.entregamercadoria.service.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ConsultaMenorCaminhoInputVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeMapa;
	private String origem;
	private String destino;
	private Double autonomia;
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
	public String getNomeMapa() {
		return nomeMapa;
	}
	public void setNomeMapa(String nomeMapa) {
		this.nomeMapa = nomeMapa;
	}
	
}
