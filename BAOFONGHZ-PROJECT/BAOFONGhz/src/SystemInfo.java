import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.TimeZone;

public class SystemInfo {

    private String mac;
    private String ip;
    private String os;
    private String navegador;
    private String nameuser;
    private String typeLanguaje;
    private String timeRegion;

    // Constructor
    public SystemInfo(String mac, String ip, String os, String navegador, String nameuser, String typeLanguaje, String timeRegion) {
        this.mac = mac;
        this.ip = ip;
        this.os = os;
        this.navegador = navegador;
        this.nameuser = nameuser;
        this.typeLanguaje = typeLanguaje;
        this.timeRegion = timeRegion;
    }

    // Getters
    public String getMac() { return mac; }
    public String getIp() { return ip; }
    public String getOs() { return os; }
    public String getNavegador() { return navegador; }
    public String getNameuser() { return nameuser; }
    public String getTypeLanguaje() { return typeLanguaje; }
    public String getTimeRegion() { return timeRegion; }
    
    // Métodos estáticos para obtener datos del sistema
    public static String getIPAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "No disponible";
        }
    }

    public static String getMACAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                byte[] macAddressBytes = networkInterface.getHardwareAddress();
                if (macAddressBytes != null) {
                    StringBuilder macAddress = new StringBuilder();
                    for (int i = 0; i < macAddressBytes.length; i++) {
                        macAddress.append(String.format("%02X", macAddressBytes[i]));
                        if (i < macAddressBytes.length - 1) {
                            macAddress.append("-");
                        }
                    }
                    return macAddress.toString();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "No disponible";
    }
}
