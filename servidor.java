import javax.sound.sampled.*;
import java.io.*;
import java.net.*;

public class VoiceChatServer {
    private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
        System.out.println("Servidor de chat de voz iniciado...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Esperando conexiones...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado");

            AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            OutputStream out = clientSocket.getOutputStream();

            byte[] buffer = new byte[BUFFER_SIZE];

            System.out.println("Transmitiendo audio...");
            while (true) {
                int bytesRead = microphone.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
