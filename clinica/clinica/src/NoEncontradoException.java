/** Se lanza cuando se busca un veterinario que no existe. */
public class NoEncontradoException extends ClinicaException {
    public NoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
