/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manue
 */

// ipLocal
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
     
     
//ipPublic
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ip {

    
    
    
    public String getIpPublic(){


        try {
            // URL del servicio que devuelve la IP pública
            String urlString = "http://api.ipify.org";

            // Crear un objeto URL
            URL url = new URL(urlString);

            // Abrir la conexión HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Establecer el método de solicitud a GET
            connection.setRequestMethod("GET");

            // Leer la respuesta del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // Cerrar las conexiones
            in.close();
            connection.disconnect();

            // La IP pública del usuario está en la respuesta
            String publicIP = response.toString();
            return publicIP;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<YOUR_PUBLIC_IP>";


    }
     public String getIpLocal() {
        try {
            // Obtener todas las interfaces de red (network interfaces) disponibles
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                
                // Filtrar las direcciones IPv4 y que no sean loopback
                if (!networkInterface.isLoopback() && networkInterface.isUp()) {
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        
                        // Verificar si es una dirección IPv4 y obtenerla
                        if (addr.getHostAddress().contains(".")) {
                            String ipAddress = addr.getHostAddress();
                            return ipAddress;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "<YOUR_LOCAL_IP>";
     }
}
