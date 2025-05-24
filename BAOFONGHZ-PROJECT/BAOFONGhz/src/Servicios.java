/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Manuel
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;



public class Servicios {
    
    /*** Metricas y herramientas para control de versiones Pro+
     Servicios.java
     * "Front/backtend metricas del sistema
     * comunicación online cifrando con Base64Masking.java 
     * IMPLEMENTAR llaveLicencia guardada en local cifra los datos de base64 a online
     SystemInfo.java
     * Constructor de metricas del sistema y ususario
     Servidor.java
     * Guardar y mostrar archivos utilizando JSON de Google 
     * usando addToJson.java y JsonToTable.java
     
     ***/
    

    public void WebRequestScheduler() { //String mac, String ip, String os, String nav, String user, String typeLang, String timeReg
       
        //Recopilamos la información y la cargamos en memoria
        // Obtener la dirección IP
        String ipAddress = SystemInfo.getIPAddress();

        // Obtener la dirección MAC
        String macAddress = SystemInfo.getMACAddress();

        // Obtener el sistema operativo
        String os = System.getProperty("os.name");

        // Obtener el nombre de usuario
        String username = System.getProperty("user.name");

        // Obtener el idioma del teclado
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();

        // Obtener la región horaria principal
        String timeZone = TimeZone.getDefault().getID();

        // Obtener el navegador por defecto (opcional, requiere implementación adicional)
        String navegador = "N/A";  // No hay forma directa de obtener el navegador por defecto en Java

        
        String ssid = "";
                String arquitecture = "";
                String ram = "";
                String freeRam = "";
                String disk = "";
                String freeDisk = "";
                String procesador = "";
                String gpuName = "";
        String gpuRam = "";
        String llaveLicencia = ""; 
        
        
        
        /*  ESTO NO VA AQUI...
        
        
        String llaveLicencia = ""; // ejecutar baofonghz genera el json con la licencia y aqui la leemos
        
        try {
            //llaveLicencia = ""; //jsonGetData.java
        } catch (Exception e) {
            
            llaveLicencia = "#NOT_COMMERCIAL#";
        }
        
        if (llaveLicencia.equals("")) {
            JOptionPane.showMessageDialog(null, "Walki talkie descompuesto.");
            System.exit(0);
        }
        
        ---
        Permisos de la APP
        
        *Acceder al almacenamiento:
        - Escritura/lectura de archivos para el funcionamiento
        *Acceder a la red
        - E/S de datos en local/internet para el funcionamiento
        *Identificación del dispositivo y estadisticas
        - Lectura de las caracteristicas del hardware y software
        - Tiempo de funcionamiento de la aplicación
        - Reportes de fallos
        
        
        
        --------
        
        
        
        
        */
        
        
        // Crear una instancia de SystemInfo
        SystemInfo systemInfo = new SystemInfo(
            macAddress,
            ipAddress,
            os,
            navegador,
            username,
            language,
            timeZone
        );

        // Imprimir datos en memoria para verificar
        System.out.println("Datos en memoria de SystemInfo:");
        System.out.println("Dirección IP: " + systemInfo.getIp());
        System.out.println("Dirección MAC: " + systemInfo.getMac());
        System.out.println("Sistema Operativo: " + systemInfo.getOs());
        System.out.println("Navegador: " + systemInfo.getNavegador());
        System.out.println("Nombre de Usuario: " + systemInfo.getNameuser());
        System.out.println("Idioma del Teclado: " + systemInfo.getTypeLanguaje());
        System.out.println("Región Horaria Principal: " + systemInfo.getTimeRegion());
    
        


        
        // Crear un executor para manejar la ejecución periódica de tareas
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Definir la tarea que se ejecutará cada 8 horas
        Runnable task = () -> {
            try {
                
                // Parámetro a enviar codificado
                String llaveLicencia64 = new Base64Masking().mask(llaveLicencia); //clave local que cifra los siguientes
                String mac64 = new Base64Masking().mask(systemInfo.getMac());
                String ip64 = new Base64Masking().mask(systemInfo.getIp());
                String os64 = new Base64Masking().mask(systemInfo.getOs());
                String nav64 = new Base64Masking().mask(systemInfo.getNavegador());
                String user64 = new Base64Masking().mask(systemInfo.getNameuser());
                String typeLang64 = new Base64Masking().mask(systemInfo.getTypeLanguaje());
                String timeReg64 = new Base64Masking().mask(systemInfo.getTimeRegion());
                String ssid64 = "";
                String arquitecture64 = "";
                String ram64 = "";
                String freeRam64 = "";
                String disk64 = "";
                String freeDisk64 = "";
                String procesador64 = "";
                String gpuName64 = "";
                String gpuRam64 = "";
                
                
                
                // Definir la URL base
                
                //String urlBase = "https://<SITE.COM>/baofonghzServicios.php?";
                String urlBase = "baofonghzServicios.php?";
                
                // Añadir parámetros a la URL
                String parametros = "mac=" + mac64
                            + "&ip=" + ip64
                            + "&os=" + os64
                            + "&nav=" + nav64
                            + "&user=" + user64
                            + "&typeLang=" + typeLang64
                            + "&timeReg=" + timeReg64;
                String urlConParametros = urlBase + parametros;
                
                //System.out.println("Ping: "+urlConParametros);
                
                
                // Crear la URL con los parámetros
                URL url = new URL(urlConParametros);
                System.out.println("Ping: "+url);
                // Abrir la conexión
                HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("GET");
                
                // Leer la respuesta de la petición
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                // Imprimir la respuesta (esto sería lo que devolvería el servidor)
                System.out.println("Respuesta del servidor: " + response.toString());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Programar la tarea para que se ejecute cada 8 horas
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.HOURS);
        //scheduler.scheduleAtFixedRate(task, 0, 60, TimeUnit.SECONDS);

        
        
/* Servicio gratuito indefinido para fines no profesionales.
   Free indefinite service for non-professional purposes.
        
        // Mantener las metricas en ejecución
        try {
            Thread.sleep(TimeUnit.DAYS.toMillis(1)); // Mantener el programa corriendo un día, por ejemplo
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            scheduler.shutdown();
            JOptionPane.showMessageDialog(null, "SORRY! Trial versión ended.");
            System.exit(0);
        }
*/
        
    }   
}