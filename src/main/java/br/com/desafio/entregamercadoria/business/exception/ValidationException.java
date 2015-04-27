package br.com.desafio.entregamercadoria.business.exception;

import java.text.MessageFormat;

/**
 * Exce��o personalizada disparada quando algum erro de valida��o ocorre na aplica��o.
 * 
 * @author Carlos Vin�cius
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
     * @param args par�metros que ser�o inclusos nas lacunas da mensagem.
     */
    public ValidationException(String msg, Object... args) {
        super(MessageFormat.format(msg, args));
    }
    
	/**
     * Constructor.
     *
     * @param msg Mensagem
     * @param cause {@link Throwable} referente a causa da exce��o.
     */
    public ValidationException(String msg, Throwable cause) {
        super(msg,cause);
    }

}
