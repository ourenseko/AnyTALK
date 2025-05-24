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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonToTable {

    public void main() {
        // Leer y parsear el JSON
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("servidores.json")) {
            Type servidorListType = new TypeToken<List<Servidor>>() {}.getType();
            List<Servidor> servidores = gson.fromJson(reader, servidorListType);

            // Crear la interfaz gráfica
            SwingUtilities.invokeLater(() -> crearYMostrarTabla(servidores));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void crearYMostrarTabla(List<Servidor> servidores) {
        JFrame frame = new JFrame("Tabla de Servidores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);

        // Crear modelo de tabla
        String[] columnas = {"Nombre", "IP", "Port"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        // Llenar la tabla con los datos
        for (Servidor servidor : servidores) {
            Object[] fila = {servidor.getNombre(), servidor.getIp(), servidor.getPort()};
            model.addRow(fila);
        }

        JTable table = new JTable(model);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}
