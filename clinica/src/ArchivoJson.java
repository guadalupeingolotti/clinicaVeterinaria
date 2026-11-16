import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

/** Lee y guarda la clínica en archivos JSON. */
public class ArchivoJson {

    /**
     * Carga la clínica desde un archivo JSON.
     * @throws ClinicaException si el archivo no existe, está mal formado o no cumple el invariante.
     */
    public static Clinica cargar(String ruta) throws ClinicaException {
        Gson gson = new Gson();
        try (FileReader lector = new FileReader(ruta)) {
            Clinica clinica = gson.fromJson(lector, Clinica.class);
            if (clinica == null || !clinica.repOK()) {
                throw new ClinicaException("El contenido de " + ruta + " no es válido.");
            }
            return clinica;
        } catch (IOException e) {
            throw new ClinicaException("No se pudo leer " + ruta + ": " + e.getMessage());
        } catch (JsonParseException e) {
            throw new ClinicaException("El archivo " + ruta + " tiene un formato JSON incorrecto.");
        }
    }

    /** Guarda la clínica en un archivo JSON. */
    public static void guardar(Clinica clinica, String ruta) throws ClinicaException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter escritor = new FileWriter(ruta)) {
            gson.toJson(clinica, escritor);
        } catch (IOException e) {
            throw new ClinicaException("No se pudo guardar " + ruta + ": " + e.getMessage());
        }
    }
}
