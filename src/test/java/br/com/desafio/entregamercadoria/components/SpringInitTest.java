package br.com.desafio.entregamercadoria.components;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Teste unitário do contexto de teste do spring.
 * 
 * @author Carlos Vinícius
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-test.xml"})
public class SpringInitTest {

	private static final Logger LOG = LoggerFactory.getLogger(SpringInitTest.class);

	/**
	 * Nome: beforeClass  
	 * Before class.
	 *
	 * @see 
	 */
	@BeforeClass
	public static void beforeClass() {
	}

	@Test
	public void test(){
		LOG.info("Spring inicializado com sucesso");
	}
}
