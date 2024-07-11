import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manue
 */
public class servidor {
    //private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 4096;

    public void VoiceChatServer(int PORT) {
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
            JOptionPane.showMessageDialog(null, "Fatal error VoIP", "BAOFONGhz", JOptionPane.ERROR_MESSAGE);
            
        }
    }
}
