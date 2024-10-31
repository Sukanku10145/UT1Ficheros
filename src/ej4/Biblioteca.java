package ej4; // Paquete donde se encuentra la clase Biblioteca

import java.io.RandomAccessFile; // Importa la clase para trabajar con archivos de acceso aleatorio
import java.io.File;
import java.io.IOException; // Importa la clase para manejar excepciones de entrada/salida

// Clase que representa una biblioteca y gestiona libros
public class Biblioteca {
    private RandomAccessFile archivo; // Archivo para almacenar los registros de los libros

    // Constructor que inicializa el archivo de la biblioteca
    public Biblioteca(String nombreArchivo) throws IOException {
        // Eliminar el archivo existente si existe
        File file = new File(nombreArchivo);
        if (file.exists()) {
            file.delete();
        }
        archivo = new RandomAccessFile(nombreArchivo, "rw"); // Abre el archivo en modo lectura y escritura
    }

    // Método para agregar un nuevo libro a la biblioteca
    public void agregarLibro(Libro libro) throws IOException {
        // Validar que no exista un ID duplicado
        if (buscarLibroPorId(libro.getId()) != null) { // Busca si el ID ya existe
            System.out.println("El ID ya existe. No se puede agregar el libro."); // Mensaje de error
            return; // Salir del método
        }

        archivo.seek(archivo.length()); // Mueve el puntero al final del archivo
        archivo.write(libro.toByteArray()); // Escribe el libro en el archivo
    }

    // Método para buscar un libro por su ID
    public Libro buscarLibroPorId(int id) throws IOException {
        archivo.seek(0); // Mueve el puntero al inicio del archivo
        byte[] buffer = new byte[89]; // Tamaño del registro (arreglo de bytes)
        // Recorre el archivo hasta el final
        while (archivo.getFilePointer() < archivo.length()) {
            archivo.read(buffer); // Lee un registro del archivo
            Libro libro = Libro.fromByteArray(buffer); // Convierte el arreglo de bytes a un objeto Libro
            if (libro.getId() == id) { // Comprueba si el ID coincide
                return libro; // Devuelve el libro encontrado
            }
        }
        return null; // Devuelve null si no se encuentra el libro
    }

    // Método para modificar los datos de un libro existente
    public void modificarLibro(int id, String nuevoTitulo, String nuevoAutor, int nuevoAno) throws IOException {
        Libro libro = buscarLibroPorId(id); // Busca el libro por ID
        if (libro != null) { // Verifica si el libro fue encontrado
            // Crea un nuevo libro con los datos modificados
            libro = new Libro(id, nuevoTitulo, nuevoAutor, nuevoAno, libro.isDisponibilidad());
            archivo.seek((id - 1) * 89); // Calcula la posición en el archivo
            archivo.write(libro.toByteArray()); // Escribe los datos del libro modificado
        } else {
            System.out.println("Libro no encontrado."); // Mensaje de error si no se encuentra el libro
        }
    }

    // Método para eliminar un libro (marcándolo como no disponible)
    public void eliminarLibro(int id) throws IOException {
        Libro libro = buscarLibroPorId(id); // Busca el libro por ID
        if (libro != null) { // Verifica si el libro fue encontrado
            libro.setDisponibilidad(false); // Marca el libro como no disponible
            archivo.seek((id - 1) * 89); // Calcula la posición en el archivo
            archivo.write(libro.toByteArray()); // Escribe los datos del libro modificado
        } else {
            System.out.println("Libro no encontrado."); // Mensaje de error si no se encuentra el libro
        }
    }

    // Método para listar todos los libros disponibles en la biblioteca
    public void listarLibrosDisponibles() throws IOException {
        archivo.seek(0); // Mueve el puntero al inicio del archivo
        byte[] buffer = new byte[89]; // Tamaño del registro (arreglo de bytes)
        // Recorre el archivo hasta el final
        while (archivo.getFilePointer() < archivo.length()) {
            archivo.read(buffer); // Lee un registro del archivo
            Libro libro = Libro.fromByteArray(buffer); // Convierte el arreglo de bytes a un objeto Libro
            if (libro.isDisponibilidad()) { // Verifica si el libro está disponible
                // Imprime la información del libro
                System.out.println("ID: " + libro.getId() + ", Título: " + libro.getTitulo() +
                                   ", Autor: " + libro.getAutor() + ", Año: " + libro.getAnoPublicacion());
            }
        }
    }

    // Método para cerrar el archivo de la biblioteca
    public void cerrar() throws IOException {
        archivo.close(); // Cierra el archivo
    }
}
