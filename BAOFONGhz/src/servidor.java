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

    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    private Thread serverThread;

    public void VoiceChatServer(int PORT) {
        serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
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
                    while (running) {
                        synchronized (pauseLock) {
                            while (paused) {
                                try {
                                    pauseLock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        // Tu código para leer y enviar audio
                        int bytesRead = microphone.read(buffer, 0, buffer.length);
                        if (bytesRead > 0) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                    microphone.close();
                } catch (IOException | LineUnavailableException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Fatal error VoIP", "BAOFONGhz", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        serverThread.start();
    }

    public void stop() {
        running = false;
        serverThread.interrupt();
        System.out.println("Servidor detenido.");
    }

    public void pause() {
        paused = true;
        System.out.println("Servidor pausado.");
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
            System.out.println("Servidor reanudado.");
        }
    }

    
    
    /***
     * Puedes pausar y reanudar el servidor de chat de voz llamando a los métodos pause() y resume():
     * 
        VoiceChatServer server = new VoiceChatServer();
        server.VoiceChatServer(12345);

        // Para pausar el servidor
        server.pause();

        // Para reanudar el servidor
        server.resume();

        // Para detener el servidor
        server.stop();
     * 
     */
    
    
}
