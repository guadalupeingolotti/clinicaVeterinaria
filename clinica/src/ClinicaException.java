/** Excepción general del sistema (datos inválidos o repetidos). */
public class ClinicaException extends Exception {
    public ClinicaException(String mensaje) {
        super(mensaje);
    }
}
