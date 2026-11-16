import java.util.ArrayList;
import java.util.List;

/**
 * TAD Veterinario
 * Profesional de la clínica que atiende hasta MAX_PACIENTES mascotas.
 *
 * Observadores: getNombre, getNumeroId, getEspecialidad, getPacientes,
 *               cantidadPacientes, buscarPaciente
 * Modificadores: agregarPaciente
 *
 * Invariante de representación:
 *   - nombre y numeroId no son null ni vacíos (obligatorios)
 *   - pacientes no es null
 *   - pacientes.size() <= MAX_PACIENTES (10)
 *   - no hay dos pacientes con el mismo id
 *   - cada paciente cumple su repOK()
 */
public class Veterinario {
    public static final int MAX_PACIENTES = 10;

    private String nombre;
    private String numeroId;
    private String especialidad;
    private List<Mascota> pacientes;

    /**
     * Pre: nombre y numeroId no vacíos.
     * Post: crea un veterinario sin pacientes.
     */
    public Veterinario(String nombre, String numeroId, String especialidad) throws ClinicaException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ClinicaException("El nombre del veterinario es obligatorio.");
        }
        if (numeroId == null || numeroId.trim().isEmpty()) {
            throw new ClinicaException("El número de identificación del veterinario es obligatorio.");
        }
        this.nombre = nombre.trim();
        this.numeroId = numeroId.trim();
        this.especialidad = especialidad;
        this.pacientes = new ArrayList<>();
    }

    public boolean repOK() {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (numeroId == null || numeroId.trim().isEmpty()) return false;
        if (pacientes == null || pacientes.size() > MAX_PACIENTES) return false;
        for (int i = 0; i < pacientes.size(); i++) {
            Mascota m = pacientes.get(i);
            if (m == null || !m.repOK()) return false;
            for (int j = i + 1; j < pacientes.size(); j++) {
                if (pacientes.get(j).getId().equals(m.getId())) return false;
            }
        }
        return true;
    }

    /**
     * Pre: la mascota no es null y su id no está repetido.
     * Post: la mascota queda en la lista y la cantidad aumenta en 1.
     * @throws LimiteExcedidoException si ya tiene MAX_PACIENTES mascotas.
     * @throws ClinicaException si la mascota es null o el id ya existe.
     */
    public void agregarPaciente(Mascota mascota) throws ClinicaException {
        if (mascota == null) {
            throw new ClinicaException("La mascota no puede ser nula.");
        }
        if (pacientes.size() >= MAX_PACIENTES) {
            throw new LimiteExcedidoException(
                    nombre + " ya atiende el máximo de " + MAX_PACIENTES + " mascotas.");
        }
        if (buscarPaciente(mascota.getId()) != null) {
            throw new ClinicaException("Ya existe una mascota con el id " + mascota.getId() + ".");
        }
        pacientes.add(mascota);
    }

    /** Devuelve la mascota con ese id, o null si este veterinario no la atiende. */
    public Mascota buscarPaciente(String id) {
        for (Mascota m : pacientes) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public int cantidadPacientes() {
        return pacientes.size();
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroId() {
        return numeroId;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public List<Mascota> getPacientes() {
        return pacientes;
    }

    @Override
    public String toString() {
        return nombre + " [" + numeroId + "] - " + especialidad
                + " - " + pacientes.size() + "/" + MAX_PACIENTES + " mascotas";
    }
}
