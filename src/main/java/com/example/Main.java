package com.example;

import java.util.Scanner;

public class Main {

    // Mida del bloc en bytes (16 bytes = 128 bits)
    private static final int BLOCK_SIZE = 16;

    public static void main(String[] args) {
        
        // 1. Definir una clau (16 bytes)
        byte[] clau = "AquestaEsUnaClau".getBytes();
        String textPla; 
        try (// 2. Demanar el text pla (a xifrar)
        Scanner scanner = new Scanner(System.in)) {
            System.out.println("Quin text vols xifrar?");
            textPla = scanner.nextLine();
            System.out.println("Text pla: " + textPla);
        }

            // 3. Convertir el text pla a bytes
            byte[] textPlaBytes = textPla.getBytes();

            // 4. Aplicar el padding manualment si el text no és múltiple de BLOCK_SIZE
            byte[] textAmbPadding = aplicarPadding(textPlaBytes);

            // 5. Xifrar cada bloc
            byte[] textXifrat = xifrarBlocsECB(textAmbPadding, clau);

            // 6. Mostrar els blocs xifrats en hexadecimal
            System.out.println("\nBlocs xifrats:");
            for (int i = 0; i < textXifrat.length; i += BLOCK_SIZE) {
                byte[] blocXifrat = obtenirBloc(textXifrat, i, BLOCK_SIZE);
                System.out.println("Bloc " + (i / BLOCK_SIZE + 1) + ": " + bytesToHex(blocXifrat));
            }
        
    }

    // Mètode per aplicar padding manualment si el text no és múltiple de BLOCK_SIZE
    private static byte[] aplicarPadding(byte[] input) {
        int faltant = input.length % BLOCK_SIZE;
        int paddedlenght = BLOCK_SIZE - faltant;
        byte[] paddedInput = new byte[input.length + paddedlenght];
        
        System.arraycopy(input, 0, paddedInput, 0, input.length);

        byte paddingvalue = (byte) paddedlenght;

        for (int i = input.length; i <paddedInput.length; i++){
            paddedInput[i] = paddingvalue;
        }
        
        return paddedInput;
    }

    // Mètode per xifrar els blocs de text pla usant una operació XOR simple amb la clau
    private static byte[] xifrarBlocsECB(byte[] text, byte[] clau) {
        byte[] resultat = new byte[text.length];

        // Recorrer cada bloc de text de BLOCK_SIZE bytes
        for (int i = 0; i < text.length; i += BLOCK_SIZE) {
            byte[] bloc = obtenirBloc(text, i, BLOCK_SIZE);
            byte[] blocXifrat = xifrarBloc(bloc, clau);
            copiarBloc(blocXifrat, resultat, i);
        }

        return resultat;
    }

    // Mètode per obtenir un bloc de mida BLOCK_SIZE a partir de l'índex inicial
    private static byte[] obtenirBloc(byte[] text, int indexInici, int blockSize) {
        byte[] bloc = new byte[blockSize];
        for (int i = 0; i < blockSize; i++) {
            bloc[i] = text[indexInici + i];
        }
        return bloc;
    }

    // Mètode per copiar un bloc xifrat al text xifrat complet
    private static void copiarBloc(byte[] blocXifrat, byte[] textXifrat, int indexInici) {
        for (int i = 0; i < blocXifrat.length; i++) {
            textXifrat[indexInici + i] = blocXifrat[i];
        }
    }

    // Simular una "ronda de xifratge" aplicant XOR entre el bloc de text pla i la clau
    private static byte[] xifrarBloc(byte[] bloc, byte[] clau) {
        byte[] blocXifrat = new byte[bloc.length]; 
        for (int i = 0; i < bloc.length;i++){
            blocXifrat[i] = (byte) (bloc[i] ^ clau[i]);
        }
        return blocXifrat;
    }

    // Mètode auxiliar per convertir bytes a representació hexadecimal
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}