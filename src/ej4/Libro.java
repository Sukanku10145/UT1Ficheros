package ej4; // Paquete donde se encuentra la clase Libro

import java.io.RandomAccessFile; // Importa la clase para trabajar con archivos de acceso aleatorio
import java.io.IOException; // Importa la clase para manejar excepciones de entrada/salida
import java.nio.charset.StandardCharsets; // Importa la clase para manejar codificaciones de caracteres

// Clase que representa un libro
public class Libro {
    // Atributos de la clase
    private int id; // Identificador único del libro
    private String titulo; // Título del libro
    private String autor; // Autor del libro
    private int anoPublicacion; // Año de publicación del libro
    private boolean disponibilidad; // Indica si el libro está disponible

    // Constructor que inicializa los atributos del libro
    public Libro(int id, String titulo, String autor, int anoPublicacion, boolean disponibilidad) {
        this.id = id; // Asigna el id proporcionado
        this.titulo = titulo; // Asigna el título proporcionado
        this.autor = autor; // Asigna el autor proporcionado
        this.anoPublicacion = anoPublicacion; // Asigna el año de publicación proporcionado
        this.disponibilidad = disponibilidad; // Asigna la disponibilidad proporcionada
    }

    // Método getter para obtener el id del libro
    public int getId() {
        return id;
    }

    // Método getter para obtener el título del libro
    public String getTitulo() {
        return titulo;
    }

    // Método getter para obtener el autor del libro
    public String getAutor() {
        return autor;
    }

    // Método getter para obtener el año de publicación del libro
    public int getAnoPublicacion() {
        return anoPublicacion;
    }

    // Método getter para comprobar la disponibilidad del libro
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    // Método setter para modificar la disponibilidad del libro
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad; // Actualiza la disponibilidad
    }

    // Método que convierte los atributos del libro en un arreglo de bytes
    public byte[] toByteArray() {
        // Convierte cada atributo a un arreglo de bytes
        byte[] idBytes = intToBytes(id); // Convierte el id a bytes
        byte[] tituloBytes = titulo.getBytes(StandardCharsets.UTF_8); // Convierte el título a bytes
        byte[] autorBytes = autor.getBytes(StandardCharsets.UTF_8); // Convierte el autor a bytes
        byte[] anoBytes = intToBytes(anoPublicacion); // Convierte el año a bytes
        byte[] disponibilidadBytes = new byte[] { (byte) (disponibilidad ? 1 : 0) }; // Convierte la disponibilidad a bytes

        // Crea un arreglo de bytes para almacenar todos los atributos
        byte[] libroBytes = new byte[4 + 50 + 30 + 4 + 1];
        System.arraycopy(idBytes, 0, libroBytes, 0, 4); // Copia el id en la posición correspondiente
        System.arraycopy(tituloBytes, 0, libroBytes, 4, Math.min(tituloBytes.length, 50)); // Copia el título
        System.arraycopy(autorBytes, 0, libroBytes, 54, Math.min(autorBytes.length, 30)); // Copia el autor
        System.arraycopy(anoBytes, 0, libroBytes, 84, 4); // Copia el año de publicación
        System.arraycopy(disponibilidadBytes, 0, libroBytes, 88, 1); // Copia la disponibilidad
        
        return libroBytes; // Devuelve el arreglo de bytes del libro
    }

    // Método estático que convierte un arreglo de bytes en un objeto Libro
    public static Libro fromByteArray(byte[] bytes) {
        int id = bytesToInt(bytes, 0); // Extrae el id del arreglo de bytes
        String titulo = new String(bytes, 4, 50, StandardCharsets.UTF_8).trim(); // Extrae el título
        String autor = new String(bytes, 54, 30, StandardCharsets.UTF_8).trim(); // Extrae el autor
        int anoPublicacion = bytesToInt(bytes, 84); // Extrae el año de publicación
        boolean disponibilidad = bytes[88] != 0; // Extrae la disponibilidad (true si es diferente de 0)

        // Crea y devuelve un nuevo objeto Libro con los atributos extraídos
        return new Libro(id, titulo, autor, anoPublicacion, disponibilidad);
    }

    // Método privado que convierte un entero a un arreglo de 4 bytes
    private static byte[] intToBytes(int value) {
        return new byte[] {
            (byte) (value >> 24), // Byte más significativo
            (byte) (value >> 16), // Segundo byte
            (byte) (value >> 8),  // Tercer byte
            (byte) value          // Byte menos significativo
        };
    }

    // Método privado que convierte un arreglo de bytes a un entero a partir de un desplazamiento
    private static int bytesToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 24) | // Extrae el primer byte y lo desplaza
               ((bytes[offset + 1] & 0xFF) << 16) | // Extrae el segundo byte y lo desplaza
               ((bytes[offset + 2] & 0xFF) << 8) | // Extrae el tercer byte y lo desplaza
               (bytes[offset + 3] & 0xFF); // Extrae el cuarto byte
    }
}
