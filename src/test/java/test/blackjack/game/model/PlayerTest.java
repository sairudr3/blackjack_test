package test.blackjack.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerTest {

    Player player;
    @Mock
    Instruction instruction;

    @BeforeEach
    public void setup() {
        player = new Player("John Doe", instruction);
    }

    @Test
    void shouldMarkPlayerBusted(){
        assertFalse(player.isBusted());
        player.markBusted();
        assertTrue(player.isBusted());
    }

    @Test
    void shouldAskForActionByInstructionForHitAction(){
        when(instruction.getNextInstruction()).thenReturn("h");
        assertEquals(Action.HIT, player.getAction());
    }

    @Test
    void shouldAskForActionByInstructionForStandAction(){
        when(instruction.getNextInstruction()).thenReturn("s").thenReturn("asdsad");
        assertEquals(Action.STAND, player.getAction());
        assertEquals(Action.STAND, player.getAction());
    }

    @Test
    void shouldReturnCorrectDrawnCards(){
        player.drawnCard(buildCard("A", 1, CardPack.Type.CLUB));
        player.drawnCard(buildCard("Jack", 10, CardPack.Type.CLUB));
        player.drawnCard(buildCard("King", 10, CardPack.Type.CLUB));

        assertEquals("A of CLUB, Jack of CLUB, King of CLUB", player.getCards());
    }

    @Test
    void shouldCalculatePointsCorrectlyForAceDrawnCard(){
        player.drawnCard(buildCard("A", 1, CardPack.Type.CLUB));
        player.drawnCard(buildCard("Jack", 10, CardPack.Type.CLUB));
        player.drawnCard(buildCard("King", 10, CardPack.Type.CLUB));

        assertEquals(21, player.getPoints());
    }

    @Test
    void shouldCalculatePointsCorrectlyForAceDrawnCardWithSmallerCards(){
        player.drawnCard(buildCard("3", 3, CardPack.Type.DIAMOND));
        player.drawnCard(buildCard("A", 1, CardPack.Type.CLUB));
        player.drawnCard(buildCard("4", 4, CardPack.Type.CLUB));

        assertEquals(18, player.getPoints());
    }

    @Test
    void shouldCalculatePointsCorrectlyForAceDrawnCardWithAlreadyOverPoints(){
        player.drawnCard(buildCard("9", 9, CardPack.Type.DIAMOND));
        player.drawnCard(buildCard("A", 1, CardPack.Type.CLUB));
        player.drawnCard(buildCard("4", 4, CardPack.Type.CLUB));
        player.drawnCard(buildCard("Jack", 10, CardPack.Type.CLUB));

        assertEquals(24, player.getPoints());
    }

    @Test
    void shouldReturnTrueIfCardsHasOverPoints(){

        player.drawnCard(buildCard("Jack", 10, CardPack.Type.DIAMOND));
        player.drawnCard(buildCard("Queen", 10, CardPack.Type.CLUB));
        player.drawnCard(buildCard("4", 4, CardPack.Type.CLUB));

        assertTrue( player.hasOverPoints());
    }
    @Test
    void shouldReturnFalseIfCardsDoesNotHasOverPoints(){

        player.drawnCard(buildCard("A", 1, CardPack.Type.CLUB));
        player.drawnCard(buildCard("Jack", 10, CardPack.Type.CLUB));
        player.drawnCard(buildCard("King", 10, CardPack.Type.CLUB));

        assertFalse(player.hasOverPoints());
    }

    private CardPack.Card buildCard(String val, int pt, CardPack.Type type){
        return new CardPack.Card(val, pt, type);
    }
}