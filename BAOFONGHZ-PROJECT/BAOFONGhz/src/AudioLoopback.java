import javax.sound.sampled.*;

public class AudioLoopback {

    public void main() {
        // Parámetros de audio: 44100 Hz, 16 bits, mono, sin compresión
        AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, false);

        try {
            // Línea de entrada (micrófono)
            TargetDataLine mic = AudioSystem.getTargetDataLine(format);
            mic.open(format);
            mic.start();

            // Línea de salida (altavoces)
            SourceDataLine speakers = AudioSystem.getSourceDataLine(format);
            speakers.open(format);
            speakers.start();

            byte[] buffer = new byte[1024];
            int bytesRead;

            System.out.println("Reproduciendo en tiempo real... Ctrl+C para salir.");

            while (true) {
                // Leer del micrófono
                bytesRead = mic.read(buffer, 0, buffer.length);
                // Escribir en los altavoces
                speakers.write(buffer, 0, bytesRead);
            }

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
