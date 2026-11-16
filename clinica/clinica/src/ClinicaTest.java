import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClinicaTest {
    private Clinica clinica;

    @BeforeEach
    void setUp() throws ClinicaException {
        clinica = new Clinica("Clínica Central", LocalDate.now().toString());
    }

    @Test
    void testAgregarVeterinariosYLimite() throws ClinicaException {
        for (int i = 1; i <= 10; i++) {
            clinica.registrarVeterinario(new Veterinario("V" + i, "Dr. " + i, "General"));
        }
        assertEquals(10, clinica.getVeterinarios().size());
        assertTrue(clinica.repOK());

        assertThrows(LimiteExcedidoException.class, () -> {
            clinica.registrarVeterinario(new Veterinario("V11", "Dr. Extra", "Cirugía"));
        });
    }

    @Test
    void testRegistrarMascotaAVeterinarioInexistente() {
        assertThrows(NoEncontradoException.class, () -> {
            clinica.asignarMascota("V999", new Mascota("M1", "Firulais", "Perro"));
        });
    }

    @Test
    void testLimiteMascotasPorVeterinarioYTotal() throws ClinicaException {
        clinica.registrarVeterinario(new Veterinario("Dr. Ana", "V1", "Cirugía"));
        clinica.registrarVeterinario(new Veterinario("Dr. Luis", "V2", "Oftalmología"));

        for (int i = 1; i <= 10; i++) {
            clinica.asignarMascota("V1", new Mascota("M" + i, "Mascota" + i, "Gato"));
        }

        // La mascota 11 debe lanzar LimiteExcedidoException
        assertThrows(LimiteExcedidoException.class, () -> {
            clinica.asignarMascota("V1", new Mascota("M11", "Extra", "Perro"));
        });

        clinica.asignarMascota("V2", new Mascota("M20", "Rex", "Perro"));
        assertEquals(11, clinica.totalMascotas());
    }

    @Test
    void testPersistenciaJson() throws ClinicaException {
        clinica.registrarVeterinario(new Veterinario("Dr. Ana", "V1", "Cirugía"));
        clinica.asignarMascota("V1", new Mascota("M1", "Toby", "Perro"));

        String ruta = "test_clinica.json";
        ArchivoJson.guardar(clinica, ruta);

        Clinica cargada = ArchivoJson.cargar(ruta);
        assertEquals("Clínica Central", cargada.getNombre());
        assertEquals(1, cargada.totalMascotas());
        assertTrue(cargada.repOK());

        new File(ruta).delete();
    }
}
