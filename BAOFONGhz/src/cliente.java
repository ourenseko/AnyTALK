import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class cliente {
    //private static final String SERVER_ADDRESS = "127.0.0.1"; // Cambia esto por la IP del servidor
    //private static final int PORT = 12345;
    private static final int BUFFER_SIZE = 4096;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    private Thread clientThread;
    
        public void VoiceChatClient(String SERVER_ADDRESS, int PORT) {
        clientThread = new Thread(new Runnable() {
            @Override
            public void run() {
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
                        int bytesRead = in.read(buffer, 0, buffer.length);
                        if (bytesRead > 0) {
                            speakers.write(buffer, 0, bytesRead);
                        }
                    }
                    speakers.close();
                } catch (IOException | LineUnavailableException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Unknown Host:\n " + SERVER_ADDRESS + ":" + PORT, "BAOFONGhz", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        clientThread.start();
    }

    public void stop() {
        running = false;
        clientThread.interrupt();
        System.out.println("Cliente detenido.");
    }

    public void pause() {
        paused = true;
        System.out.println("Cliente pausado.");
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
            System.out.println("Cliente reanudado.");
        }
    }
}
