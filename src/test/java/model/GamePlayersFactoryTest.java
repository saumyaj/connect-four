package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class GamePlayersFactoryTest {

    GamePlayersFactory gamePlayersFactory = GamePlayersFactory.getInstance();

    @Mock
    ConnectFourModel connectFourModel;

    @Test
    void testPlayersForSinglePlayerGameWithDefaultNames() {
        List<Player> list = gamePlayersFactory.createPlayers("SINGLE_PLAYER_GAME", connectFourModel);
        assertEquals("User", list.get(0).getName());
        assertEquals("Computer", list.get(1).getName());
        assertTrue(list.get(1) instanceof ComputerPlayer);
        assertTrue(list.get(0) instanceof HumanPlayer);
    }

    @Test
    void testPlayersForTwoPlayerGameWithDefaultNames() {
        List<Player> list = gamePlayersFactory.createPlayers("TWO_PLAYER_GAME", connectFourModel);
        assertEquals("Player1", list.get(0).getName());
        assertEquals("Player2", list.get(1).getName());
        assertTrue(list.get(1) instanceof HumanPlayer);
        assertTrue(list.get(0) instanceof HumanPlayer);
    }

    @Test
    void testPlayersForTwoPlayerGameWithGivenNames() {
        List<String> names = new ArrayList<>();
        names.add("A");
        names.add("B");
        List<Player> list = gamePlayersFactory.createPlayers("TWO_PLAYER_GAME", connectFourModel, names);
        assertEquals("A", list.get(0).getName());
        assertEquals("B", list.get(1).getName());
        assertTrue(list.get(1) instanceof HumanPlayer);
        assertTrue(list.get(0) instanceof HumanPlayer);
    }

    @Test
    void testPlayersForSinglePlayerGameWithGivenNames() {
        List<String> names = new ArrayList<>();
        names.add("A");
        names.add("B");
        List<Player> list = gamePlayersFactory.createPlayers("SINGLE_PLAYER_GAME", connectFourModel, names);
        assertEquals("A", list.get(0).getName());
        assertEquals("B", list.get(1).getName());
        assertTrue(list.get(1) instanceof ComputerPlayer);
        assertTrue(list.get(0) instanceof HumanPlayer);
    }
}
