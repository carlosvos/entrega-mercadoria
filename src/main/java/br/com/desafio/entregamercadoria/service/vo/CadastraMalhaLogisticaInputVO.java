package br.com.desafio.entregamercadoria.service.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objeto de entrada do serviço de cadsatro contendo as informações de origem, destino e distância das rotas
 * e a malha logística que pertencem.
 * 
 * @author Carlos Vinícius
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class CadastraMalhaLogisticaInputVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * nome da malha logística onde serão salvas as rotas.
	 */
	private String nomMalhaLogistica;
	
	/**
	 * lista de {@link RotaInputVO} contendo as informações de origem, destino e distância
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
