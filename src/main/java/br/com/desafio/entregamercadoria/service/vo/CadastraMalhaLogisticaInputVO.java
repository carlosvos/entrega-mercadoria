package br.com.desafio.entregamercadoria.service.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objeto de entrada do servi�o de cadsatro contendo as informa��es de origem, destino e dist�ncia das rotas
 * e a malha log�stica que pertencem.
 * 
 * @author Carlos Vin�cius
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CadastraMalhaLogisticaInputVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * nome da malha log�stica onde ser�o salvas as rotas.
	 */
	private String nomMalhaLogistica;
	
	/**
	 * lista de {@link RotaInputVO} contendo as informa��es de origem, destino e dist�ncia
	 * das rotas.
	 */
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
