/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Manuel
 */
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddToJson {

    private static final String ARCHIVO_JSON = "servidores.json";

    public void main(String nombre, String ip, String port) {
        // Define el nuevo servidor a añadir
        Servidor nuevoServidor = new Servidor();
        nuevoServidor.setNombre(nombre);
        nuevoServidor.setIp(ip);
        nuevoServidor.setPort(port);

        // Leer el JSON existente, añadir el nuevo objeto, y escribir de nuevo
        try {
            // Leer el archivo JSON existente o crear uno nuevo si no existe
            List<Servidor> servidores = leerJson(ARCHIVO_JSON);

            // Añadir el nuevo servidor a la lista
            servidores.add(nuevoServidor);

            // Escribir la lista actualizada de vuelta al archivo JSON
            escribirJson(ARCHIVO_JSON, servidores);

            System.out.println("Servidor añadido exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Servidor> leerJson(String archivo) throws IOException {
        File file = new File(archivo);
        // Crear el archivo si no existe
        if (!file.exists()) {
            file.createNewFile();
            // Devolver una lista vacía
            return new ArrayList<>();
        }

        // Leer el archivo JSON existente
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(archivo)) {
            Type tipoLista = new TypeToken<List<Servidor>>() {}.getType();
            return gson.fromJson(reader, tipoLista);
        }
    }

    private static void escribirJson(String archivo, List<Servidor> servidores) throws IOException {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(archivo)) {
            gson.toJson(servidores, writer);
        }
    }
}
