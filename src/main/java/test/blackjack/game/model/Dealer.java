package test.blackjack.game.model;

import java.util.function.Function;

public class Dealer extends Player{

    private final Function<Integer, Action> actionStrategy;

    public Dealer(Function<Integer, Action> actionStrategy) {
        super("Computer", null);
        this.actionStrategy =actionStrategy;
    }

    public Action getAction(){
        return actionStrategy.apply(getPoints());
    }
}
