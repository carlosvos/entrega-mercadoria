package br.com.desafio.entregamercadoria.service.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CadastraMalhaLogisticaInputVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nomMalhaLogistica;
	private List<RotaInputVO> rotas;
	
	public CadastraMalhaLogisticaInputVO() {
		super();
	}
	public String getNomMalhaLogistica() {
		return nomMalhaLogistica;
	}
	public void setNomMalhaLogistica(String nomMalhaLogistica) {
		this.nomMalhaLogistica = nomMalhaLogistica;
	}
	public List<RotaInputVO> getRotas() {
		return rotas;
	}
	public void setRotas(List<RotaInputVO> rotas) {
		this.rotas = rotas;
	}

}
