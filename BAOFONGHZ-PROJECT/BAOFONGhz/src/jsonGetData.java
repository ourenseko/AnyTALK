
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Manuel
 */
public class jsonGetData {
    public void main() {
        // Leer y parsear el JSON
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("licencia.json")) {
            Type servidorListType = new TypeToken<List<Servidor>>() {}.getType();
            List<Servidor> servidores = gson.fromJson(reader, servidorListType);

            
            // Crear modelo de tabla
        String[] columnas = {"licencia"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

            // Llenar la tabla con los datos
        for (Servidor servidor : servidores) {
            Object[] fila = {servidor.getNombre(), servidor.getIp(), servidor.getPort()};
            model.addRow(fila);
        }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
