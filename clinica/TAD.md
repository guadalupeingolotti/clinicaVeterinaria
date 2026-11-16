# Clínica Veterinaria – TAD

## TAD Mascota
- **Observadores:** getId, getNombre, getTipo
- **Invariante:** id y nombre no nulos ni vacíos; tipo no nulo.

## TAD Veterinario
- **Observadores:** getNombre, getNumeroId, getEspecialidad, getPacientes, cantidadPacientes, buscarPaciente
- **Modificadores:** agregarPaciente(mascota)
- **Invariante:** nombre y numeroId no vacíos; pacientes.size() <= 10; sin ids de mascota repetidos; cada mascota cumple repOK.
- **agregarPaciente** — Pre: mascota no nula, id no repetido. Post: cantidad + 1.
  Excepciones: LimiteExcedidoException (ya tiene 10), ClinicaException (nula o id repetido).

## TAD Clinica
- **Observadores:** getNombre, getFechaCreacion, getVeterinarios, buscarVeterinario, totalMascotas
- **Modificadores:** registrarVeterinario(v), asignarMascota(numeroId, mascota)
- **Invariante:** veterinarios.size() <= 10; numeroId únicos; ids de mascota únicos en toda la clínica; cada veterinario cumple repOK.
- **registrarVeterinario** — Excepciones: LimiteExcedidoException (ya hay 10), ClinicaException (nulo o número repetido).
- **asignarMascota** — Excepciones: NoEncontradoException (veterinario inexistente), ClinicaException (id repetido), LimiteExcedidoException (veterinario con 10).
- **totalMascotas** — devuelve la suma de las mascotas de todos los veterinarios.

## Compilar y ejecutar (desde la carpeta del proyecto)
    javac -cp "lib/*" -d out src/*.java test/*.java
    java -cp "out:lib/gson-2_8_2.jar" MainClinica          (Windows: usar ; en lugar de :)
    java -jar lib/junit-platform-console-standalone-1_10_0.jar execute -cp "out:lib/gson-2_8_2.jar" --scan-classpath out
