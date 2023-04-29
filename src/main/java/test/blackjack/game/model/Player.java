package test.blackjack.game.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Player {
    private final String id;
    private State state;


    private final List<CardPack.Card> drawnCards= new ArrayList<>();

    public Player(String id) {
        this.id = id;
        this.state = State.PLAYING;
    }

    public String getCards(){
        return String.join(", ", drawnCards.stream().map(CardPack.Card::toString).collect(Collectors.toList()));
    }

    public void drawnCard(CardPack.Card card){
        drawnCards.add(card);
    }

    public int getPoints(){
        int points = 0;
        List<CardPack.Card> cards = new ArrayList<>(drawnCards);
        Collections.sort(cards, (a,b)-> {
            if(a.isAce() && b.isAce()) return 0;
            else if(a.isAce()) return Integer.MAX_VALUE;
            else if(b.isAce()) return Integer.MIN_VALUE;
            else return Integer.compare(a.getPoint(), b.getPoint());
        });
        for(CardPack.Card c : cards){
            if(c.isAce()){
                if(points+11>21){
                    points+=1;
                }else {
                    points+=11;
                }
            } else
                points+= c.getPoint();
        }
        return points;
    }


    public boolean hasOverPoints(){
        return getPoints() > 21;
    }

    public boolean isBusted(){
        return state == State.BUSTED;
    }

    public void markBusted(){
        state = State.BUSTED;
    }

    public Action getAction() {
        System.out.print(" Hit(H) or Stand(S) ??");
        String input = Instruction.getInstruction();
        if("H".equalsIgnoreCase(input) || "hit".equalsIgnoreCase(input)){
            return Action.HIT;
        } else {
            return Action.STAND;
        }
    }

    public String toString(){
        return id;
    }


    static enum State {
        PLAYING,
        BUSTED
    }
}
