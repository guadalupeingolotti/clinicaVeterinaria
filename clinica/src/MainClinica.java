import java.util.Scanner;

public class MainClinica {
    private static final String ARCHIVO_ENTRADA = "clinica.json";
    private static final String ARCHIVO_SALIDA = "clinica_resultado.json";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Clinica clinica;

        try {
            clinica = ArchivoJson.cargar(ARCHIVO_ENTRADA);
            System.out.println("Datos cargados desde " + ARCHIVO_ENTRADA);
        } catch (ClinicaException e) {
            System.out.println(e.getMessage());
            System.out.println("Se crea una clínica vacía.");
            clinica = new Clinica("Clínica Patitas", "16/11/2026");
        }

        int opcion = 0;
        while (opcion != 6) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(teclado.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número del 1 al 6.");
                opcion = 0;
                continue;
            }

            try {
                switch (opcion) {
                    case 1:
                        agregarVeterinario(clinica, teclado);
                        break;
                    case 2:
                        agregarMascota(clinica, teclado);
                        break;
                    case 3:
                        clinica.mostrar();
                        break;
                    case 4:
                        System.out.println("Total de mascotas atendidas: " + clinica.totalMascotas());
                        break;
                    case 5:
                        ArchivoJson.guardar(clinica, ARCHIVO_SALIDA);
                        System.out.println("Datos guardados en " + ARCHIVO_SALIDA);
                        break;
                    case 6:
                        ArchivoJson.guardar(clinica, ARCHIVO_SALIDA);
                        System.out.println("Datos guardados en " + ARCHIVO_SALIDA + ". ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inexistente.");
                }
            } catch (LimiteExcedidoException e) {
                System.out.println("Límite alcanzado: " + e.getMessage());
            } catch (NoEncontradoException e) {
                System.out.println("No encontrado: " + e.getMessage());
            } catch (ClinicaException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("---- CLÍNICA VETERINARIA ----");
        System.out.println("1) Agregar veterinario");
        System.out.println("2) Registrar mascota atendida");
        System.out.println("3) Mostrar veterinarios y mascotas");
        System.out.println("4) Ver total de mascotas atendidas");
        System.out.println("5) Guardar en JSON");
        System.out.println("6) Guardar y salir");
        System.out.print("Opción: ");
    }

    private static void agregarVeterinario(Clinica clinica, Scanner teclado) throws ClinicaException {
        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();
        System.out.print("Número de identificación: ");
        String numero = teclado.nextLine();
        System.out.print("Especialidad: ");
        String especialidad = teclado.nextLine();

        clinica.registrarVeterinario(new Veterinario(nombre, numero, especialidad));
        System.out.println("Veterinario agregado.");
    }

    private static void agregarMascota(Clinica clinica, Scanner teclado) throws ClinicaException {
        System.out.print("Número del veterinario que la atiende: ");
        String numero = teclado.nextLine().trim();
        System.out.print("Id de la mascota: ");
        String id = teclado.nextLine();
        System.out.print("Nombre de la mascota: ");
        String nombre = teclado.nextLine();
        System.out.print("Tipo (perro, gato, ave, reptil...): ");
        String tipo = teclado.nextLine();

        clinica.asignarMascota(numero, new Mascota(id, nombre, tipo));
        System.out.println("Mascota registrada.");
    }
}
