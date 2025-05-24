import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class servidorIP {
    private static final int BUFFER_SIZE = 4096;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    private Thread serverThread;
    private TargetDataLine microphone;  // Línea del micrófono

    public void VoiceChatServer(int PORT) {
        serverThread = new Thread(() -> {
            System.out.println("Servidor de chat de voz iniciado...");
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Esperando conexiones...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");

                // Configuración del formato de audio
                AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);
                microphone.start();

                OutputStream out = clientSocket.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];

                System.out.println("Transmitiendo audio...");
                while (running) {
                    synchronized (pauseLock) {
                        while (paused) {
                            try {
                                // Detenemos la captura de audio mientras el servidor está en pausa
                                microphone.stop();
                                System.out.println("Audio pausado...");
                                pauseLock.wait();
                                microphone.start();  // Reanudamos cuando se despausa
                                System.out.println("Audio reanudado...");
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                System.out.println("Hilo interrumpido.");
                                return;  // Terminamos el hilo si es interrumpido
                            }
                        }
                    }

                    // Captura y envío de audio solo si no está pausado
                    if (running && !paused) {
                        int bytesRead = microphone.read(buffer, 0, buffer.length);
                        if (bytesRead > 0) {
                            out.write(buffer, 0, bytesRead);
                        }
                    }
                }
                microphone.close();
                clientSocket.close();
                System.out.println("Conexión cerrada");
            } catch (IOException | LineUnavailableException e) {
                JOptionPane.showMessageDialog(null, "Fatal error VoIP", "BAOFONGhz", JOptionPane.ERROR_MESSAGE);
            }
        }, "ServerThread:" + PORT);
        serverThread.start();
    }

    public void stop() {
        running = false;
        serverThread.interrupt();
        System.out.println("Servidor detenido.");
        resume(); // Para asegurarnos de que no esté en pausa
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
}
