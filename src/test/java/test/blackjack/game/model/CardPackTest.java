package test.blackjack.game.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.beans.BeanProperty;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardPackTest {
    @InjectMocks
    CardPack pack;


    @Test
    void shouldHaveAllCardsForThePackToBeDrawn(){
        List<CardPack.Card> cards = IntStream.range(1, 53).mapToObj(i -> pack.drawCard())
                .collect(Collectors.toList());
        assertEquals(52, cards.size());
        assertThrows(RuntimeException.class, () -> pack.drawCard());
        Set<String> cardNames = cards.stream().map(CardPack.Card::toString).collect(Collectors.toSet());
        assertTrue(cardNames.containsAll(
                Set.of(
                        "A of SPADES", "A of HEARTS","A of DIAMOND", "A of CLUB",
                        "2 of SPADES", "2 of HEARTS","2 of DIAMOND", "2 of CLUB",
                        "3 of SPADES", "3 of HEARTS","3 of DIAMOND", "3 of CLUB",
                        "4 of SPADES", "4 of HEARTS","4 of DIAMOND", "4 of CLUB",
                        "5 of SPADES", "5 of HEARTS","5 of DIAMOND", "5 of CLUB",
                        "6 of SPADES", "6 of HEARTS","6 of DIAMOND", "6 of CLUB",
                        "7 of SPADES", "7 of HEARTS","7 of DIAMOND", "7 of CLUB",
                        "8 of SPADES", "8 of HEARTS","8 of DIAMOND", "8 of CLUB",
                        "9 of SPADES", "9 of HEARTS","9 of DIAMOND", "9 of CLUB",
                        "Jack of SPADES", "Jack of HEARTS","Jack of DIAMOND", "Jack of CLUB",
                        "Queen of SPADES", "Queen of HEARTS","Queen of DIAMOND", "Queen of CLUB",
                        "King of SPADES", "King of HEARTS","King of DIAMOND", "King of CLUB"
                        )
        ));
    }

    @Test
    void shouldShuffleAllTheCards(){
        List<String> cards = IntStream.range(1, 53)
                .mapToObj(i -> pack.drawCard())
                .map(CardPack.Card::toString)
                .collect(Collectors.toList());

        pack  = new CardPack();
        pack.shuffle();
        List<String> afterShuffle = IntStream.range(1, 53)
                .mapToObj(i -> pack.drawCard())
                .map(CardPack.Card::toString)
                .collect(Collectors.toList());
        assertNotEquals(String.join("," , afterShuffle), String.join(",", cards));
    }
}