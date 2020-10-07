package ar.unq.edu.TaskMan.Excepciones;

public class UsuarioDuplicadoException extends RuntimeException {
    public UsuarioDuplicadoException(String mensaje){
        super(mensaje);
    }
}
