/**
 * TAD Mascota
 * Representa una mascota que es atendida por un veterinario.
 *
 * Observadores: getId, getNombre, getTipo
 *
 * Invariante de representación:
 *   - id no es null ni vacío
 *   - nombre no es null ni vacío (obligatorio)
 *   - tipo no es null (perro, gato, ave, reptil, etc.)
 */
public class Mascota {
    private String id;
    private String nombre;
    private String tipo;

    /**
     * Pre: id y nombre no vacíos, tipo no null.
     * Post: crea una mascota que cumple el invariante.
     */
    public Mascota(String id, String nombre, String tipo) throws ClinicaException {
        if (esVacio(id)) {
            throw new ClinicaException("El id de la mascota es obligatorio.");
        }
        if (esVacio(nombre)) {
            throw new ClinicaException("El nombre de la mascota es obligatorio.");
        }
        if (tipo == null) {
            throw new ClinicaException("El tipo de la mascota no puede ser nulo.");
        }
        this.id = id.trim();
        this.nombre = nombre.trim();
        this.tipo = tipo.trim();
    }

    private static boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public boolean repOK() {
        return !esVacio(id) && !esVacio(nombre) && tipo != null;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ", id " + id + ")";
    }
}
