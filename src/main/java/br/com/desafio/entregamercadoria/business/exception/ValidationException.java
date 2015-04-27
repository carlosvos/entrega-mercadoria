package br.com.desafio.entregamercadoria.business.exception;

import java.text.MessageFormat;

/**
 * Exceção personalizada disparada quando algum erro de validação ocorre na aplicação.
 * 
 * @author Carlos Vinícius
 *
 */
public class ValidationException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
     * Construtor.
     *
     * @param msg Mensagem
     */
    public ValidationException(String msg) {
        super(msg);
    }
    
    /**
     * Construtor
     * 
     * @param msg Mensagem
     * @param args parâmetros que serão inclusos nas lacunas da mensagem.
     */
    public ValidationException(String msg, Object... args) {
        super(MessageFormat.format(msg, args));
    }
    
	/**
     * Constructor.
     *
     * @param msg Mensagem
     * @param cause {@link Throwable} referente a causa da exceção.
     */
    public ValidationException(String msg, Throwable cause) {
        super(msg,cause);
    }

}
