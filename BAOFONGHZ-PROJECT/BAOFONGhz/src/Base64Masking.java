/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Manuel
 */
import java.util.Base64;

public class Base64Masking {

    // Método para enmascarar una cadena de texto en Base64
    public  String mask(String input) {
        // Convertir la cadena de texto a bytes
        byte[] bytes = input.getBytes();

        // Codificar los bytes en Base64
        String base64Encoded = Base64.getEncoder().encodeToString(bytes);
        
        return base64Encoded;
    }

    // Método para desenmascarar una cadena de Base64 a texto original
    public String unmask(String maskedInput) {
        // Decodificar la cadena Base64 a bytes
        byte[] decodedBytes = Base64.getDecoder().decode(maskedInput);

        // Convertir los bytes de vuelta a una cadena de texto
        return new String(decodedBytes);
    }

    public void main() {
        String originalText = "Enmascarar este texto en Base64";
        
        // Enmascarar el texto
        String maskedText = mask(originalText);
        System.out.println("Texto enmascarado en Base64: " + maskedText);

        // Desenmascarar el texto
        String unmaskedText = unmask(maskedText);
        System.out.println("Texto desenmascarado: " + unmaskedText);
    }
}

