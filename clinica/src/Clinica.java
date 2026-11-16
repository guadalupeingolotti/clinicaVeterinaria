import java.util.ArrayList;
import java.util.List;

/**
 * TAD Clinica
 * Sistema que administra hasta MAX_VETERINARIOS veterinarios y sus mascotas.
 *
 * Observadores: getNombre, getFechaCreacion, getVeterinarios,
 *               buscarVeterinario, totalMascotas
 * Modificadores: registrarVeterinario, asignarMascota
 *
 * Invariante de representación:
 *   - veterinarios no es null
 *   - veterinarios.size() <= MAX_VETERINARIOS (10)
 *   - no hay dos veterinarios con el mismo numeroId
 *   - no hay dos mascotas con el mismo id en toda la clínica
 *   - cada veterinario cumple su repOK()
 */
public class Clinica {
    public static final int MAX_VETERINARIOS = 10;

    private String nombre;
    private String fechaCreacion;
    private List<Veterinario> veterinarios;

    public Clinica(String nombre, String fechaCreacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.veterinarios = new ArrayList<>();
    }

    public boolean repOK() {
        if (veterinarios == null || veterinarios.size() > MAX_VETERINARIOS) return false;
        List<String> idsMascotas = new ArrayList<>();
        for (int i = 0; i < veterinarios.size(); i++) {
            Veterinario v = veterinarios.get(i);
            if (v == null || !v.repOK()) return false;
            for (int j = i + 1; j < veterinarios.size(); j++) {
                if (veterinarios.get(j).getNumeroId().equals(v.getNumeroId())) return false;
            }
            for (Mascota m : v.getPacientes()) {
                if (idsMascotas.contains(m.getId())) return false;
                idsMascotas.add(m.getId());
            }
        }
        return true;
    }

    /**
     * Pre: veterinario no null y con número no repetido.
     * Post: el veterinario queda registrado.
     * @throws LimiteExcedidoException si ya hay MAX_VETERINARIOS.
     * @throws ClinicaException si es null o el número ya existe.
     */
    public void registrarVeterinario(Veterinario veterinario) throws ClinicaException {
        if (veterinario == null) {
            throw new ClinicaException("El veterinario no puede ser nulo.");
        }
        if (veterinarios.size() >= MAX_VETERINARIOS) {
            throw new LimiteExcedidoException(
                    "La clínica admite como máximo " + MAX_VETERINARIOS + " veterinarios.");
        }
        if (existeVeterinario(veterinario.getNumeroId())) {
            throw new ClinicaException(
                    "Ya existe un veterinario con el número " + veterinario.getNumeroId() + ".");
        }
        veterinarios.add(veterinario);
    }

    /**
     * Pre: el veterinario existe y el id de la mascota no está usado en la clínica.
     * Post: la mascota queda asignada al veterinario.
     * @throws NoEncontradoException si el veterinario no existe.
     */
    public void asignarMascota(String numeroIdVeterinario, Mascota mascota) throws ClinicaException {
        Veterinario veterinario = buscarVeterinario(numeroIdVeterinario);
        if (veterinario == null) {
            throw new NoEncontradoException("No existe el veterinario " + numeroIdVeterinario + ".");
        }
        if (mascota != null && existeMascota(mascota.getId())) {
            throw new ClinicaException("Ya existe una mascota con el id " + mascota.getId() + ".");
        }
        veterinario.agregarPaciente(mascota);
    }

    /** Devuelve el veterinario con ese número, o null si no existe. */
    public Veterinario buscarVeterinario(String numeroId) {
        for (Veterinario v : veterinarios) {
            if (v.getNumeroId().equals(numeroId)) {
                return v;
            }
        }
        return null;
    }

    private boolean existeVeterinario(String numeroId) {
        return buscarVeterinario(numeroId) != null;
    }

    private boolean existeMascota(String id) {
        for (Veterinario v : veterinarios) {
            if (v.buscarPaciente(id) != null) {
                return true;
            }
        }
        return false;
    }

    /** Suma las mascotas atendidas por todos los veterinarios. */
    public int totalMascotas() {
        int total = 0;
        for (Veterinario v : veterinarios) {
            total += v.cantidadPacientes();
        }
        return total;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public void mostrar() {
        System.out.println("Clínica: " + nombre + " (creada el " + fechaCreacion + ")");
        if (veterinarios.isEmpty()) {
            System.out.println("  No hay veterinarios registrados.");
        }
        for (Veterinario v : veterinarios) {
            System.out.println("  " + v);
            for (Mascota m : v.getPacientes()) {
                System.out.println("      - " + m);
            }
        }
        System.out.println("Total de mascotas atendidas: " + totalMascotas());
    }
}
