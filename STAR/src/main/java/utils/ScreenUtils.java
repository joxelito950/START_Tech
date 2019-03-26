package utils;

import javax.swing.*;

public class ScreenUtils {
    public static int getNumberByScreen(String message) {
        String opcion = getStringByScreen(message);
        if (opcion.isEmpty())
            return 0;
        try {
            return Integer.parseInt(opcion);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ingrese solo numeros", "Error", JOptionPane.ERROR_MESSAGE);
            return getNumberByScreen(message);
        }
    }

    public static String getStringByScreen(String message) {
        String ingresado = JOptionPane.showInputDialog(null, message, "Ingrese", JOptionPane.INFORMATION_MESSAGE);
        try {
            return ingresado.trim();
        } catch (Exception e) {
            System.err.println(e.getCause() + e.getMessage());
            return "";
        }
    }
}
