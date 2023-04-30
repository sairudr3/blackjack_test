package test.blackjack.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Stack;

public class CardPack {

    private final Stack<Card> remainingCards = new Stack<>();

    public CardPack(){
        for(int i=1; i<=10 ; i++){
            for(Type t : Type.values()){
                String val = i == 1? "A" : String.valueOf(i);
                remainingCards.push(new Card( val, i, t));
            }
        }
        for(Type t : Type.values()){
            remainingCards.push(new Card( "Jack", 10, t));
            remainingCards.push(new Card( "Queen", 10, t));
            remainingCards.push(new Card( "King", 10, t));
        }

    }

    public Card drawCard(){
        return remainingCards.pop();
    }

    public void shuffle() {
        Collections.shuffle(remainingCards);
    }

    @RequiredArgsConstructor
    public static class Card {
        private final String val;
        @Getter
        private final int point;
        private final Type type;

        public String toString(){
            return  val + " of " + type.name();
        }

        public boolean isAce() {
            return val.equalsIgnoreCase("A");
        }

    }

    enum Type {
        CLUB, DIAMOND, HEARTS, SPADES;
    }
}
