import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class ClinicaTest {

    private Clinica clinicaDeEjemplo() throws ClinicaException {
        Clinica c = new Clinica("Test", "01/01/2026");
        c.registrarVeterinario(new Veterinario("Ana", "1", "Cirugía"));
        c.registrarVeterinario(new Veterinario("Beto", "2", "Dermatología"));
        c.asignarMascota("1", new Mascota("M1", "Rex", "Perro"));
        c.asignarMascota("1", new Mascota("M2", "Luna", "Gato"));
        c.asignarMascota("2", new Mascota("M3", "Pepe", "Ave"));
        return c;
    }

    // ---- JSON ----

    @Test
    public void cargaElArchivoJson() throws ClinicaException {
        Clinica c = ArchivoJson.cargar("clinica.json");
        assertEquals(3, c.getVeterinarios().size());
        assertEquals(3, c.totalMascotas());
        assertTrue(c.repOK());
    }

    @Test
    public void guardarYVolverACargar() throws ClinicaException {
        Clinica original = clinicaDeEjemplo();
        ArchivoJson.guardar(original, "temporal_test.json");
        Clinica cargada = ArchivoJson.cargar("temporal_test.json");
        new File("temporal_test.json").delete();

        assertEquals(original.totalMascotas(), cargada.totalMascotas());
        assertEquals(2, cargada.getVeterinarios().size());
        assertEquals("Test", cargada.getNombre());
    }

    @Test
    public void cargarArchivoInexistenteLanzaExcepcion() {
        assertThrows(ClinicaException.class, () -> ArchivoJson.cargar("no_existe.json"));
    }

    // ---- Total de mascotas ----

    @Test
    public void totalEnClinicaVaciaEsCero() {
        assertEquals(0, new Clinica("Vacia", "01/01/2026").totalMascotas());
    }

    @Test
    public void totalSumaTodosLosVeterinarios() throws ClinicaException {
        assertEquals(3, clinicaDeEjemplo().totalMascotas());
    }

    // ---- Errores ----

    @Test
    public void mascotaAVeterinarioInexistenteFalla() throws ClinicaException {
        Clinica c = clinicaDeEjemplo();
        Mascota m = new Mascota("M9", "Nina", "Gato");
        assertThrows(NoEncontradoException.class, () -> c.asignarMascota("999", m));
        assertEquals(3, c.totalMascotas());
    }

    @Test
    public void veterinarioRepetidoFalla() throws ClinicaException {
        Clinica c = clinicaDeEjemplo();
        assertThrows(ClinicaException.class,
                () -> c.registrarVeterinario(new Veterinario("Otro", "1", "General")));
    }

    @Test
    public void idDeMascotaRepetidoEnLaClinicaFalla() throws ClinicaException {
        Clinica c = clinicaDeEjemplo();
        Mascota repetida = new Mascota("M3", "Otra", "Perro");
        assertThrows(ClinicaException.class, () -> c.asignarMascota("1", repetida));
    }

    @Test
    public void camposObligatorios() {
        assertThrows(ClinicaException.class, () -> new Veterinario("", "1", "Cirugía"));
        assertThrows(ClinicaException.class, () -> new Veterinario("Ana", null, "Cirugía"));
        assertThrows(ClinicaException.class, () -> new Mascota("M1", "  ", "Perro"));
        assertThrows(ClinicaException.class, () -> new Mascota(null, "Rex", "Perro"));
    }

    // ---- Límites ----

    @Test
    public void maximoDiezMascotasPorVeterinario() throws ClinicaException {
        Clinica c = new Clinica("Test", "01/01/2026");
        c.registrarVeterinario(new Veterinario("Ana", "1", "Cirugía"));
        for (int i = 1; i <= 10; i++) {
            c.asignarMascota("1", new Mascota("M" + i, "Mascota" + i, "Perro"));
        }
        Mascota undecima = new Mascota("M11", "Extra", "Gato");
        assertThrows(LimiteExcedidoException.class, () -> c.asignarMascota("1", undecima));
        assertEquals(10, c.totalMascotas());
        assertTrue(c.repOK());
    }

    @Test
    public void maximoDiezVeterinarios() throws ClinicaException {
        Clinica c = new Clinica("Test", "01/01/2026");
        for (int i = 1; i <= 10; i++) {
            c.registrarVeterinario(new Veterinario("Vet" + i, "" + i, "General"));
        }
        Veterinario extra = new Veterinario("Vet11", "11", "General");
        assertThrows(LimiteExcedidoException.class, () -> c.registrarVeterinario(extra));
        assertEquals(10, c.getVeterinarios().size());
    }

    // ---- Invariante y búsqueda ----

    @Test
    public void invarianteSeCumple() throws ClinicaException {
        assertTrue(clinicaDeEjemplo().repOK());
    }

    @Test
    public void buscarVeterinarioExistente() throws ClinicaException {
        assertNotNull(clinicaDeEjemplo().buscarVeterinario("2"));
    }
}
