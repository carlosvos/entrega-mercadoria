package br.com.desafio.entregamercadoria.business.exception;

import java.text.MessageFormat;

public class ValidationException extends Exception{
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     * @param msg Mensagem
     */
    public ValidationException(String msg) {
        super(msg);
    }
    
    public ValidationException(String msg, Object... args) {
        super(MessageFormat.format(msg, args));
    }
    
	/**
     * Constructor.
     *
     * @param msg Mensagem
     * @param cause Motivo da exceção
     */
    public ValidationException(String msg, Throwable cause) {
        super(msg,cause);
    }

}
