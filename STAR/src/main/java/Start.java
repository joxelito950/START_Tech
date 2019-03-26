import models.Player;
import utils.ScreenUtils;

import javax.swing.*;


public class Start {
    public static final String STAR = "STAR";
    private static Player player1 = new Player();
    private static Player player2 = new Player();
    private final static String menu = "" +
            "0. Terminar Juego\n" +
            "1. Ingresar Puntos\n" +
            "2. Estado del juego\n" +
            "3. Volver a Jugar\n";

    public static void main(String[] args) {
        boolean initGame = getPlayers();
        if (initGame) {
            Game game = new Game(player1, player2);
            game.play();
            int option;
            do {
                option = ScreenUtils.getNumberByScreen(menu);
                switch (option) {
                    case 0:
                        JOptionPane.showMessageDialog(null, game.end(), STAR, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 1:
                        JOptionPane.showMessageDialog(null, game.setPoints(getPlayer()), STAR, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, game.status(), STAR, JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case 3:
                        game.play();
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no valida", "No existe opción", JOptionPane.WARNING_MESSAGE);
                        break;
                }
            } while (option != 0);
        }
    }

    private static boolean getPlayers() {
        String name;
        name = ScreenUtils.getStringByScreen("Nombre del jugador 1");
        if (name.isEmpty()) {
            return false;
        }
        player1.setName(name);
        name = ScreenUtils.getStringByScreen("Nombre del jugador 2");
        player2.setName(name);
        return true;
    }

    private static Player getPlayer() {
        String name = ScreenUtils.getStringByScreen("¿Que jugador anoto el punto?");
        if (name.isEmpty()) {
            return getPlayer();
        }
        return player1.getName().equalsIgnoreCase(name) ? player1 : player2.getName().equalsIgnoreCase(name) ? player2 : getPlayer();
    }
}
