import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Loro {

    private static final float SILENCE_THRESHOLD = 0.9f; // Umbral para detectar el silencio; //3.14159f hace que envie cada SILENCE_DURATION como si fuese TOT Radio; 
    private static final int BUFFER_SIZE = 4096; // Tamaño del buffer de audio
    private static final int SILENCE_DURATION = 1000; // Tiempo en ms para considerar que la persona se ha quedado en silencio

    public void loro() throws LineUnavailableException, IOException {
        AudioFormat format = new AudioFormat(16000, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            System.err.println("Línea no soportada");
            System.exit(1);
        }
      try {
        TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
        line.start();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        long lastNonSilentTime = System.currentTimeMillis();

        System.out.println("Loro escuchando...");

        int iterationCounter = 0; // Contador de iteraciones para depuración

        //while (iterationCounter < 100) { // Limitar a 100 iteraciones durante la depuración
        while (true) {
            int bytesRead = line.read(buffer, 0, buffer.length);
            out.write(buffer, 0, bytesRead);

            if (isSilent(buffer, bytesRead, SILENCE_THRESHOLD)) {
                //System.out.print("Silencio (isSilent=true)");
                if (System.currentTimeMillis() - lastNonSilentTime > SILENCE_DURATION) {
                    System.out.println("Silencio detectado, rebobinando audio.");

                    // Reproduce el audio capturado
                    playCapturedAudio(out.toByteArray(), format);
                    out.reset(); // Reinicia el buffer de audio
                    lastNonSilentTime = System.currentTimeMillis(); // Reset del tiempo para evitar loops
                }
            } else {
                lastNonSilentTime = System.currentTimeMillis();
                System.out.println("Ruido"); //(isSilent=false)
            }
            //System.out.println(" "+iterationCounter);
            iterationCounter++; // Incrementa el contador
             
            
        }

        //line.close();
        
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Loro se durmió.");
    }

    /*
    private static boolean isSilent(byte[] audioData, int bytesRead, float threshold) {
        for (int i = 0; i < bytesRead; i += 2) {
            int audioSample = ((audioData[i + 1] << 8) | audioData[i]) & 0xFFFF;
            float amplitude = Math.abs(audioSample / 32768.0f);
            if (amplitude > threshold) {
                return false;
            }
        }
        return true;
    }*/
    
    private static boolean isSilent(byte[] audioData, int bytesRead, float threshold) {
    for (int i = 0; i < bytesRead; i += 2) {
        int audioSample = ((audioData[i + 1] << 8) | (audioData[i] & 0xFF));
        float amplitude = Math.abs(audioSample / 32768.0f);
        //System.out.println("Amplitud: " + amplitude); // Mensaje de depuración
        if (amplitude > threshold) {
            return false;
        }
    }
    return true;
}


    private static void playCapturedAudio(byte[] audioData, AudioFormat format) throws LineUnavailableException, IOException {
        SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
        sourceLine.open(format);
        sourceLine.start();

        ByteArrayInputStream in = new ByteArrayInputStream(audioData);
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;

        System.out.println("Reproduciendo..."); // Mensaje de depuración

        // Proceso de reproducción
        while ((bytesRead = in.read(buffer)) != -1) {
            sourceLine.write(buffer, 0, bytesRead);
        }

        sourceLine.drain();
        sourceLine.stop();
        sourceLine.close();

        System.out.println("Audio reproducido."); // Mensaje de depuración
    }

    
    /***
    
        Loro repetidor = new Loro();
        Runnable tareaLoro = new Runnable() {
            @Override
            public void run() {
                try {
                    repetidor.loro();
                } catch (LineUnavailableException | IOException ex) {
                    Logger.getLogger(Loro.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        Thread hiloLoro = new Thread(tareaLoro);
        hiloLoro.start(); // Inicia la ejecución del hilo
    
    ***/
}
