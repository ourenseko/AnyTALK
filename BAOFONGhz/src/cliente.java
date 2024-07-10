import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class cliente {
    //private static final String SERVER_ADDRESS = "127.0.0.1"; // Cambia esto por la IP del servidor
    //private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 4096;

    public void VoiceChatClient(String SERVER_ADDRESS, int PORT) {
        System.out.println("Cliente de chat de voz iniciado...");
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            System.out.println("Conectado al servidor");

            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
            speakers.open(format);
            speakers.start();

            InputStream in = socket.getInputStream();

            byte[] buffer = new byte[BUFFER_SIZE];

            System.out.println("Recibiendo audio...");
            while (true) {
                int bytesRead = in.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    speakers.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unknown Host", "BAOFONGhz", JOptionPane.ERROR_MESSAGE);
        }
    }
}
