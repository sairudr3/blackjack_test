package test.blackjack.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

    Game game;
    @Mock
    CardPack pack;
    @Mock
    Player player;
    @Mock
    Dealer dealer;

    @BeforeEach
    void setup(){
        game = new Game(List.of(player), dealer, pack);
        when(player.toString()).thenReturn("1");
        when(dealer.toString()).thenReturn("computer");

    }

    @Test
    void shouldDealerBustedAndPLayerShouldWin(){
        when(player.hasOverPoints()).thenReturn(false);
        when(dealer.hasOverPoints()).thenReturn(false).thenReturn(false).thenReturn(true);

        when(pack.drawCard())
                .thenReturn(buildCard("A", 1, CardPack.Type.CLUB)) // for player
                .thenReturn(buildCard("Jack", 10, CardPack.Type.DIAMOND)) // for dealer
                .thenReturn(buildCard("A", 1, CardPack.Type.HEARTS)) // for player
                .thenReturn(buildCard("Jack", 10, CardPack.Type.HEARTS)) // for player
                .thenReturn(buildCard("8", 8, CardPack.Type.HEARTS)) // for player
                .thenReturn(buildCard("Queen", 10, CardPack.Type.HEARTS)) // for dealer
                .thenReturn(buildCard("King", 10, CardPack.Type.HEARTS));// for dealer
        when(player.getAction()).thenReturn(Action.HIT).thenReturn(Action.HIT).thenReturn(Action.STAND);

        game.startGame();

        verify(dealer).markBusted();
        verify(player, times(0)).markBusted();

    }

    @Test
    void shouldDealerWinAndPlayerBusted(){
        when(player.hasOverPoints()).thenReturn(false).thenReturn(false).thenReturn(false).thenReturn(true);
        when(dealer.hasOverPoints()).thenReturn(false);

        when(pack.drawCard())
                .thenReturn(buildCard("Jack", 10, CardPack.Type.DIAMOND)) // for player
                .thenReturn(buildCard("A", 1, CardPack.Type.CLUB)) // for dealer
                .thenReturn(buildCard("A", 1, CardPack.Type.HEARTS)) // for player
                .thenReturn(buildCard("Jack", 10, CardPack.Type.HEARTS)) // for player
                .thenReturn(buildCard("8", 8, CardPack.Type.HEARTS)) // for player
                .thenReturn(buildCard("Queen", 10, CardPack.Type.HEARTS)) // for dealer
                .thenReturn(buildCard("9", 9, CardPack.Type.HEARTS));// for dealer

        when(dealer.getAction()).thenReturn(Action.HIT).thenReturn(Action.STAND);

        game.startGame();

        verify(dealer, times(0)).markBusted();
        verify(player, times(1)).markBusted();

    }

    private CardPack.Card buildCard(String val, int pt, CardPack.Type type){
        return new CardPack.Card(val, pt, type);
    }

}