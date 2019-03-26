import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {
    private Player player1;
    private Player player2;
    private Game game;

    @Before
    public void init() {
        player1 = new Player();
        player1.setName("jose");
        player2 = new Player();
        player2.setName("camila");
        game = new Game(player1, player2);
    }

    @Test
    public void start() {
        assertEquals(
                "se esperaba que el juego inicie como LOVE",
                "LOVE\njose: 0 camila: 0",
                game.status()
        );
    }

    @Test
    public void quince() {
        assertEquals(
                "se esperaba que al ingresar un punto cambie a QUINCE",
                "QUINCE\njose: 1 camila: 0", game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que al empatar siga en QUINCE",
                "QUINCE\njose: 1 camila: 1",
                game.setPoints(player2)
        );
    }

    @Test
    public void treinta() {
        game.setPoints(player1);
        assertEquals(
                "se esperaba que ingresar 2 puntos cambie a TREINTA",
                "TREINTA\njose: 2 camila: 0",
                game.setPoints(player1)
        );
        assertEquals(
                "se espera que cuando el contrincante ingrese un punto continue en TREINTA",
                "TREINTA\njose: 2 camila: 1",
                game.setPoints(player2)
        );
        assertEquals(
                "se espera que al empatar siga siendo TREINTA",
                "TREINTA\njose: 2 camila: 2",
                game.setPoints(player2)
        );
        assertEquals(
                "se espera que al no anotar puntos siga siendo TREINTA",
                "TREINTA\njose: 2 camila: 2",
                game.status()
        );
    }

    @Test
    public void cuarenta() {
        game.setPoints(player2);
        game.setPoints(player2);
        assertEquals(
                "se esperaba que al ingresar el tercer punto cambie a CUARENTA",
                "CUARENTA\ncamila: 3 jose: 0",
                game.setPoints(player2)
        );
        assertEquals(
                "se esperaba que cuando el contrincante ingrese punto siga en CUARENTA",
                "CUARENTA\ncamila: 3 jose: 1",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que cuando el contrincante ingrese un segundo punto siga en CUARENTA",
                "CUARENTA\ncamila: 3 jose: 2",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que cuando se empate cambie a DEUCE",
                "DEUCE\njose: 3 camila: 3",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que cuando nadie puntue siga en DEUCE",
                "DEUCE\njose: 3 camila: 3",
                game.status()
        );
    }

    @Test
    public void deuceVentaja() {
        game.setPoints(player1);
        game.setPoints(player1);
        game.setPoints(player1);
        game.setPoints(player2);
        game.setPoints(player2);
        assertEquals(
                "se esperaba que al ingresar el tercer punto de empate cambie a DEUCE",
                "DEUCE\njose: 3 camila: 3",
                game.setPoints(player2)
        );
        assertEquals(
                "se esperaba que al ingresar el cuarto punto cambie a VENTAJA",
                "VENTAJA\ncamila: 4 jose: 3",
                game.setPoints(player2)
        );
        assertEquals(
                "se esperaba que al empatar cambie a DEUCE",
                "DEUCE\njose: 4 camila: 4",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que al ingresar el quinto punto cambie a VENTAJA",
                "VENTAJA\ncamila: 5 jose: 4",
                game.setPoints(player2)
        );
        assertEquals(
                "se esperaba que al empatar cambie a DEUCE",
                "DEUCE\njose: 5 camila: 5",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que al ingresar el sexto punto cambie a VENTAJA",
                "VENTAJA\njose: 6 camila: 5",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que al ingresar el septimo punto cambie a WINNER",
                "El juego ya termino\njose: 7 camila: 5",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que al ingresar otro punto no cambie y muestre el ultimo estado",
                "El juego ya termino\njose: 7 camila: 5",
                game.setPoints(player1)
        );
        assertEquals(
                "se esperaba que muestre el ultimo estado",
                "El juego ya termino\njose: 7 camila: 5",
                game.status()
        );
    }

    @Test
    public void winner() {
        game.setPoints(player2);
        game.setPoints(player2);
        game.setPoints(player2);
        assertEquals(
                "se espera que cuando se ingrese el cuarto punto retorne estado como ganador",
                "El juego ya termino\ncamila: 4 jose: 0",
                game.setPoints(player2)
        );
        assertEquals(
                "se esperaba que cuando el juego termine no ingrese mas datos y retorne el ultimo estado",
                "El juego ya termino\ncamila: 4 jose: 0",
                game.setPoints(player2)
        );
        game.play();
        assertEquals(
                "se esperaba que el juego se reinicie con los mismos jugadores",
                "LOVE\njose: 0 camila: 0",
                game.status()
        );
        game.setPoints(player1);
        game.setPoints(player1);
        game.setPoints(player1);
        game.setPoints(player2);
        game.setPoints(player2);
        game.setPoints(player2);
        game.setPoints(player2);
        assertEquals(
                "se esperaba que este en VENTAJA",
                "VENTAJA\ncamila: 4 jose: 3",
                game.status()
        );
        assertEquals(
                "se esperaba que de VENTAJA pase a WINNER",
                "El juego ya termino\ncamila: 5 jose: 3",
                game.setPoints(player2)
        );
    }

    @Test
    public void end() {
        game.end();
        assertEquals(
                "se esperaba el juego termine en 0",
                "El juego ya termino\njose: 0 camila: 0",
                game.status()
        );
        assertEquals(
                "se esperaba no permita ingresar puntaje y retorne el final del juego anterior",
                "El juego ya termino\njose: 0 camila: 0",
                game.setPoints(player2)
        );
        game.play();
        game.setPoints(player2);
        assertEquals(
                "se esperaba permita finalizar en cualquier estado",
                "El juego ya termino\ncamila: 1 jose: 0",
                game.end()
        );
    }
}
