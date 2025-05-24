import javax.sound.sampled.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class clienteIP {
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
                System.out.println("Novo fio:"+clientThread); // +"\nnome:"+Thread.currentThread().getName()+""
                System.out.println("Cliente de chat de voz iniciado...");
                try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
                    System.out.println("Conectado o servidor");

                    AudioFormat format = new AudioFormat(44100, 16, 2, true, true);
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
                    speakers.open(format);
                    speakers.start();
                    

                    InputStream in = socket.getInputStream();
                    byte[] buffer = new byte[BUFFER_SIZE];

                    System.out.println("Recibindo audio...");
                    while (running) {
                        synchronized (pauseLock) {
                            while (paused) {
                                try {
                                    pauseLock.wait();
                                } catch (InterruptedException e) {
                                    //e.printStackTrace();
                                    Thread.currentThread().interrupt();
                                    System.out.println(Thread.currentThread().getName() + " foi detido ao non poder pausalo.");
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
                    //e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Unknown Host:\n " + SERVER_ADDRESS + ":" + PORT, "BAOFONGhz", JOptionPane.WARNING_MESSAGE);
                }
            }
        }, "Fio_"+SERVER_ADDRESS+":"+PORT);
        clientThread.start();
    }

    public void stop() {
        running = false;
        clientThread.interrupt();
        System.out.println("Cliente detido.");
        resume(); // Asegura que el hilo se detenga incluso si está pausado

    }

    public void pause() {
        paused = true;
        System.out.println("Cliente pausado.");
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notify();
            System.out.println("Cliente reanudado.");
        }
    }
}
