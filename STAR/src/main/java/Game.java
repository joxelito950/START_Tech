import lombok.extern.java.Log;
import models.Player;

import java.util.HashMap;
import java.util.Map;

@Log
public class Game {
    private Player playerOne;
    private Player playerTwo;
    private GameStatus status;
    private Map points;
    private boolean game = false;

    private enum GameStatus {
        LOVE,
        QUINCE,
        TREINTA,
        CUARENTA,
        DEUCE,
        VENTAJA,
        WINNER,
    }

    public Game(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.play();
    }

    public void play() {
        game = true;
        status = GameStatus.LOVE;
        points = new HashMap<Player, Integer>();
        points.put(this.playerOne, 0);
        points.put(this.playerTwo, 0);
        log.info(this.toString());
    }

    public String end() {
        this.game = false;
        log.warning("juego finalizado por el ususrio " + this.toString());
        return this.status();
    }

    private void updateStatus() {
        switch (status) {
            case LOVE: {
                status = isLove(playerOne, playerTwo) ? GameStatus.LOVE : GameStatus.QUINCE;
                break;
            }
            case QUINCE: {
                status = isQuince(playerOne, playerTwo) ? GameStatus.QUINCE : GameStatus.TREINTA;
                break;
            }
            case TREINTA: {
                status = isTreinta(playerOne, playerTwo) ? GameStatus.TREINTA : GameStatus.CUARENTA;
                break;
            }
            case CUARENTA: {
                status = isDeuce(playerOne, playerTwo)
                        ? GameStatus.DEUCE
                        : isWinner(playerOne, playerTwo)
                        ? GameStatus.WINNER
                        : GameStatus.CUARENTA;
                break;
            }
            case DEUCE: {
                status = isDeuce(playerOne, playerTwo) ? GameStatus.DEUCE : GameStatus.VENTAJA;
                break;
            }
            case VENTAJA: {
                status = isDeuce(playerOne, playerTwo)
                        ? GameStatus.DEUCE
                        : isWinner(playerOne, playerTwo)
                        ? GameStatus.WINNER
                        : GameStatus.VENTAJA;
                break;
            }
            default: {
                log.severe("Se intento acceder a un estado no mapeado" + this.toString());
                break;
            }
        }
        log.info(this.toString());
    }

    private boolean isWinner(Player playerOne, Player playerTwo) {
        int pointsPlayer1 = (int) points.get(playerOne);
        int pointsPlayer2 = (int) points.get(playerTwo);
        if (pointsPlayer1 >= 4 || pointsPlayer2 >= 4) {
            if (2 >= pointsPlayer1 - pointsPlayer2 || 2 >= pointsPlayer2 - pointsPlayer1) {
                this.game = false;
                return true;
            }
        }
        return false;
    }

    private boolean isDeuce(Player playerOne, Player playerTwo) {
        return (int) points.get(playerOne) >= 3 && points.get(playerOne).equals(points.get(playerTwo));
    }

    private boolean isTreinta(Player playerOne, Player playerTwo) {
        return 2 >= (int) points.get(playerOne) && 2 >= (int) points.get(playerTwo);
    }

    private boolean isQuince(Player player1, Player player2) {
        return 1 >= (int) points.get(player1) && 1 >= (int) points.get(player2);
    }

    private boolean isLove(Player player1, Player player2) {
        return 0 == (int) points.get(player1) && 0 == (int) points.get(player2);
    }

    public String setPoints(Player player) {
        if (game) {
            points.put(player, (int) points.get(player) + 1);
            log.info(player.toString() + points.get(player));
            updateStatus();
        }
        return status();
    }

    public String status() {
        String status = game ? this.status.toString() : "El juego ya termino";
        return (int) points.get(playerOne) < (int) points.get(playerTwo)
                ? String.format(status + "\n%s: %d %s: %d", playerTwo, points.get(playerTwo), playerOne, points.get(playerOne))
                : String.format(status + "\n%s: %d %s: %d", playerOne, points.get(playerOne), playerTwo, points.get(playerTwo));
    }

    @Override
    public String toString() {
        return String.format(
                "juegoActivo: %b, %s: %d, %s: %d, %s",
                game,
                playerOne,
                points.get(playerOne),
                playerTwo,
                points.get(playerTwo),
                status
        );
    }
}
