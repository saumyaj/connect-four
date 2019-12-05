package model;

import java.util.ArrayList;
import java.util.List;

public class GamePlayersFactory {

    private static GamePlayersFactory instance;

    public static GamePlayersFactory getInstance() {
        if (instance == null) {
            instance = new GamePlayersFactory();
        }
        return instance;
    }

    private GamePlayersFactory() {
    }

    public List<Player> createPlayers(String gameType, ConnectFourModel model, List<String> playerNames) {
        List<Player> players = new ArrayList<>(2);
        Player p1, p2;
        p1 = new HumanPlayer(playerNames.get(0), model, Player.Symbol.CIRCLE);
        players.add(p1);
        if (gameType.equals("SINGLE_PLAYER_GAME")) {
            p2 = new ComputerPlayer(playerNames.get(1), model, Player.Symbol.CROSS);
        } else {
            p2 = new HumanPlayer(playerNames.get(1), model, Player.Symbol.CROSS);
        }
        players.add(p2);
        return players;
    }

    public List<Player> createPlayers(String gameType, ConnectFourModel model) {
        List<Player> players = new ArrayList<>(2);
        Player p1, p2;

        if (gameType.equals("SINGLE_PLAYER_GAME")) {
            p1 = new HumanPlayer("User", model, Player.Symbol.CIRCLE);
            p2 = new ComputerPlayer("Computer", model, Player.Symbol.CROSS);
        } else {
            p1 = new HumanPlayer("Player1", model, Player.Symbol.CIRCLE);
            p2 = new HumanPlayer("Player2", model, Player.Symbol.CROSS);
        }
        players.add(p1);
        players.add(p2);
        return players;
    }
}
