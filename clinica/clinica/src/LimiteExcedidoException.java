/** Se lanza cuando se supera un máximo permitido (veterinarios o mascotas). */
public class LimiteExcedidoException extends ClinicaException {
    public LimiteExcedidoException(String mensaje) {
        super(mensaje);
    }
}
