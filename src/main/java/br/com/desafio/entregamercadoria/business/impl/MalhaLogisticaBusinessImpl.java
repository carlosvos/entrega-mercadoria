package br.com.desafio.entregamercadoria.business.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.desafio.entregamercadoria.business.MalhaLogisticaBusiness;
import br.com.desafio.entregamercadoria.business.exception.ValidationException;
import br.com.desafio.entregamercadoria.business.to.RotaTO;
import br.com.desafio.entregamercadoria.dao.MalhaLogisticaDAO;
import br.com.desafio.entregamercadoria.entity.Rota;
import br.com.desafio.entregamercadoria.entity.MalhaLogistica;
import br.com.desafio.entregamercadoria.utils.DijkstraSP;

/**
 * Implementa��o da interface de neg�cio para cadastro das malhas log�sticas e consulta de menor caminho 
 * considerando um determinado percurso.
 * 
 * @author Carlos Vin�cus
 *
 */
@Service
public class MalhaLogisticaBusinessImpl implements MalhaLogisticaBusiness {
	
	@Resource
	private MalhaLogisticaDAO malhaLogisticaDAO;
	
	private static final Logger LOG = LoggerFactory.getLogger(MalhaLogisticaBusinessImpl.class);
	
	private static final String MSG_NOME_MAPA_INVALIDO = "O nome do mapa n�o pode ser vazio ou nulo.";
	private static final String MSG_ORIGEM_INVALIDA = "O local de origem n�o pode ser vazio ou nulo.";
	private static final String MSG_DESTINO_INVALIDO = "O local de destino n�o pode ser vazio ou nulo.";

	private static final String MSG_DISTANCIA_NEGATIVA = "O valor da dist�ncia n�o pode ser negativa. [Rota %s -> %s]";
	private static final String MSG_AUTONOMIA_ZERO_NEGATIVA = "O valor da autonomia n�o pode ser menor ou igual a zero.";
	private static final String MSG_PRECO_COMBUSTIVEL_ZERO_NEGATIVO = "O pre�o do combust�vel n�o pode ser menor ou igual a zero.";
	
	private static final String MSG_MAPA_SEM_ROTAS = "Nenhuma rota foi encontrada para o mapa ''{0}''.";
	private static final String MSG_LOCAL_SEM_ROTAS = "Nenhuma rota para {0} foi encontrada.";
	private static final String MSG_ORIGEM_DESTINO_SEM_ROTAS = "Nenhuma rota de {0} para {1} foi encontrada.";
	
	@Override
	public void cadastraMalhaLogistica(String nomeMapa, List<RotaTO> listRotasTO) throws IOException, ValidationException{
		try{
			Validate.notBlank(nomeMapa,MSG_NOME_MAPA_INVALIDO);

			List<Rota> rotas = new ArrayList<>();
			for(RotaTO rotaTO : listRotasTO){
				rotas.add(this.parseRotaTOtoDirectedEdge(rotaTO));
			}
		
			malhaLogisticaDAO.save(nomeMapa, rotas);
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
	}

	/**
	 * Converte o objeto de entrada {@link RotaTO} para a entidade {@link Rota} que sera utilizada
	 * como refer�ncia para persist�ncia das inform��es referentes a rota.
	 * 
	 * @param rotaTO objeto de entrada contendo as informa��es de origem, destino e dist�ncia de um rota.
	 * @return entidade {@link Rota} utilizada para recupera��o dos dados que ser�o salvos na base de dados.
	 * @throws ValidationException exce��o lan�ada caso ocorra um dos sequintes erros de valida��o:
	 * <ul>
	 * <li>valor de origem do objeto {@link RotaTO} seja nulo ou vazio;</li>
	 * <li>valor de destino do objeto {@link RotaTO} seja nulo ou vazio;</li>
 	 * <li>valor da dist�ncia do objeto {@link RotaTO} seja menor que zero</li>
 	 * </ul>
	 */
	private Rota parseRotaTOtoDirectedEdge(RotaTO rotaTO) throws ValidationException{
		Validate.notBlank(rotaTO.getOrigem(),MSG_ORIGEM_INVALIDA);
		Validate.notBlank(rotaTO.getDestino(),MSG_DESTINO_INVALIDO);
		Validate.isTrue(rotaTO.getDistancia() >= 0, MSG_DISTANCIA_NEGATIVA, rotaTO.getOrigem(), rotaTO.getDestino() );
		return new Rota(rotaTO.getDistancia(), rotaTO.getOrigem(), rotaTO.getDestino());
	}
	
	/**
	 * Converte a entidade {@link Rota} para um objeto {@link RotaTO} que ser� utilizado para constru��o 
	 * da mensagem de retorno.
	 * 
	 * @param edge entidade {@link Rota} correspondente ao registro de uma rota.
	 * @return objeto {@link RotaTO} contendo as mesmas informa��es da entidade passada como par�metro.
	 */
	private RotaTO parseDirectedEdgetoRotaTO(Rota edge) {
		return new RotaTO(edge.getOrigem(),edge.getDestino(),edge.weight());
	}
	
	@Override
	public List<RotaTO> consultarMenorCaminho(String nomeMapa, String origem, String destino) throws IOException, ValidationException{
		try{
		
			Validate.notBlank(nomeMapa,MSG_NOME_MAPA_INVALIDO);
			Validate.notBlank(origem,MSG_ORIGEM_INVALIDA);
			Validate.notBlank(destino,MSG_DESTINO_INVALIDO);
		
			MalhaLogistica malhaLogistica = malhaLogisticaDAO.findByNomMapa(nomeMapa);

			List<Rota> rotas = IteratorUtils.toList(malhaLogistica.getRotas().iterator());
			if(CollectionUtils.isEmpty(rotas)){
				throw new ValidationException(MSG_MAPA_SEM_ROTAS, nomeMapa);
			}
			
			int indexOrigem = this.getLocalIndex(origem, rotas);
			int indexDestino = this.getLocalIndex(destino, rotas);
	
			DijkstraSP shortPathUtils = new DijkstraSP(malhaLogistica, indexOrigem);
			
			Iterable<Rota> iterableMenorCaminho = shortPathUtils.pathTo(indexDestino);
			if(iterableMenorCaminho == null){
				throw new ValidationException(MSG_ORIGEM_DESTINO_SEM_ROTAS, origem, destino);
			}
			
			Iterator<Rota> menorCaminho = iterableMenorCaminho.iterator();
			ArrayList<RotaTO> rotasMenorCaminho = new ArrayList<>();
			while (menorCaminho.hasNext()) {
				Rota edge = menorCaminho.next();
				rotasMenorCaminho.add(this.parseDirectedEdgetoRotaTO(edge));
			}
			
			Collections.reverse(rotasMenorCaminho);
			
			return rotasMenorCaminho;
		
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			throw e;
		}
		
	}

	@Override
	public Double calcularCustoPercurso(Double distancia, Double autonomia, Double precoCombustivel){
		Validate.isTrue(autonomia > 0, MSG_AUTONOMIA_ZERO_NEGATIVA);
		Validate.isTrue(precoCombustivel > 0, MSG_PRECO_COMBUSTIVEL_ZERO_NEGATIVO);
		return (distancia / autonomia) * precoCombustivel;
	}
	
	@Override
	public Double calcularCustoPercurso(List<RotaTO> rotas, Double autonomia, Double precoCombustivel){
		Double distancia = 0d;
		
		for(RotaTO rota : rotas){
			distancia += rota.getDistancia();
		}
		return this.calcularCustoPercurso(distancia, autonomia, precoCombustivel);
	}
	
	/**
	 * Recupera o �ndece de um local que ser� utilizado como origem ou destino para
	 * o c�lculo do menor caminho. A pesquisa do �ndice � realizada considerando 
	 * uma lista de entidades {@link Rota} recuperada da camada DAO. 
	 * 
	 * @param local nome da localidade que deseja-se obter o �ndece.
	 * @param rotas lista de entidades {@link Rota} que cont�m as rotas
	 * de um ponto de origem para um ponto de destino com seus respectivos �ndices.
	 * @return representa��o n�merica do �ndice da localidade dentro da lista de rotas.
	 * @throws ValidationException exce��o lan�ada caso nenhum �ndice seja encontrado dentro da lista de rotas.
	 */
	private int getLocalIndex(String local, List<Rota> rotas) throws ValidationException{
		Integer index = null;
		
		for(Rota rota : rotas){
			if(rota.getOrigem().equalsIgnoreCase(local)){
				index = rota.from();
				break;
			}else if(rota.getDestino().equalsIgnoreCase(local)){
				index = rota.to();
				break;
			}
		}
		
		if(index == null){
			throw new ValidationException(MSG_LOCAL_SEM_ROTAS,local);
		}
		
		return index;
	}
}
