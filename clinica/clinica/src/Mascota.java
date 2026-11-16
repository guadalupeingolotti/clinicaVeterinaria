public class Mascota {
    private String id;
    private String nombre;
    private String tipo;

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
