package ar.com.ada.creditos.excepciones;

import ar.com.ada.creditos.entities.*;

/**
 * ClienteInfoException
 */
public class ClienteInfoException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Cliente cliente;

    public ClienteInfoException(Cliente cliente, String mensaje) {

        super(cliente.getNombre() + ":" + mensaje);
        this.cliente = cliente;
    }
}
