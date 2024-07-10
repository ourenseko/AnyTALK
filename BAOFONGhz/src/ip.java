/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manue
 */

     import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
public class ip {

     public String getIp() {
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
        return "0.0.0.0";
     }
}
