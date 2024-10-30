package ej4; // Paquete donde se encuentra la clase Main

import java.io.IOException; // Importa la clase para manejar excepciones de entrada/salida
import java.util.Scanner; // Importa la clase Scanner para la entrada de datos del usuario

// Clase principal que ejecuta la aplicación de la biblioteca
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Crea un objeto Scanner para leer la entrada del usuario
        Biblioteca biblioteca = null; // Inicializa la variable para la biblioteca

        try {
            // Inicializa la biblioteca con un archivo para almacenar los datos
            biblioteca = new Biblioteca("biblioteca.dat");
            int opcion; // Variable para almacenar la opción seleccionada por el usuario

            do {
                // Muestra el menú de opciones
                System.out.println("1. Añadir libro");
                System.out.println("2. Consultar libro por ID");
                System.out.println("3. Modificar libro");
                System.out.println("4. Eliminar libro");
                System.out.println("5. Listar libros disponibles");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt(); // Lee la opción del usuario

                // Maneja la opción seleccionada
                switch (opcion) {
                    case 1: // Añadir un nuevo libro
                        System.out.print("ID: ");
                        int id = scanner.nextInt(); // Lee el ID del libro
                        scanner.nextLine(); // Consumir nueva línea
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine(); // Lee el título del libro
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine(); // Lee el autor del libro
                        System.out.print("Año de publicación: ");
                        int ano = scanner.nextInt(); // Lee el año de publicación
                        // Agrega el nuevo libro a la biblioteca
                        biblioteca.agregarLibro(new Libro(id, titulo, autor, ano, true));
                        break;

                    case 2: // Consultar un libro por ID
                        System.out.print("ID: ");
                        id = scanner.nextInt(); // Lee el ID del libro
                        Libro libro = biblioteca.buscarLibroPorId(id); // Busca el libro por ID
                        // Muestra la información del libro encontrado
                        if (libro != null) {
                            System.out.println("ID: " + libro.getId() + ", Título: " + libro.getTitulo() +
                                               ", Autor: " + libro.getAutor() + ", Año: " + libro.getAnoPublicacion() +
                                               ", Disponibilidad: " + (libro.isDisponibilidad() ? "Sí" : "No"));
                        } else {
                            System.out.println("Libro no encontrado."); // Mensaje si el libro no se encuentra
                        }
                        break;

                    case 3: // Modificar un libro existente
                        System.out.print("ID del libro a modificar: ");
                        id = scanner.nextInt(); // Lee el ID del libro a modificar
                        scanner.nextLine(); // Consumir nueva línea
                        System.out.print("Nuevo título: ");
                        titulo = scanner.nextLine(); // Lee el nuevo título
                        System.out.print("Nuevo autor: ");
                        autor = scanner.nextLine(); // Lee el nuevo autor
                        System.out.print("Nuevo año de publicación: ");
                        ano = scanner.nextInt(); // Lee el nuevo año de publicación
                        biblioteca.modificarLibro(id, titulo, autor, ano); // Modifica el libro en la biblioteca
                        break;

                    case 4: // Eliminar un libro
                        System.out.print("ID del libro a eliminar: ");
                        id = scanner.nextInt(); // Lee el ID del libro a eliminar
                        biblioteca.eliminarLibro(id); // Elimina el libro en la biblioteca
                        break;

                    case 5: // Listar todos los libros disponibles
                        biblioteca.listarLibrosDisponibles(); // Llama al método para listar libros
                        break;

                    case 0: // Salir del programa
                        System.out.println("Saliendo..."); // Mensaje de salida
                        break;

                    default: // Opción no válida
                        System.out.println("Opción no válida."); // Mensaje de error
                }
            } while (opcion != 0); // Continúa el ciclo hasta que el usuario elija salir

        } catch (IOException e) { // Manejo de excepciones de entrada/salida
            e.printStackTrace(); // Imprime la traza del error
        } finally {
            if (biblioteca != null) { // Verifica si la biblioteca fue inicializada
                try {
                    biblioteca.cerrar(); // Cierra el archivo de la biblioteca
                } catch (IOException e) {
                    e.printStackTrace(); // Manejo de excepciones al cerrar el archivo
                }
            }
            scanner.close(); // Cierra el objeto Scanner
        }
    }
}
