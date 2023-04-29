package test.blackjack.game;

import test.blackjack.game.model.*;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Application {

    public static void main(String[] args) {
        System.out.println("####  Welcome to Black jack 21 game ####");
        Game game = buildGame(Integer.parseInt(args[0]));
        //Game game = buildGame(3);
        game.startGame();

    }

    private static Game buildGame(int numbers){
        List<Player> players = IntStream.range(1, numbers+1).mapToObj(i-> new Player("Player "+ i)).collect(Collectors.toList());
        Function<Integer, Action> actionStrategyForDealer = (pts)->{
            if( (pts+4 )<21){
                return Action.HIT;
            }
            return Action.STAND;
        };
        Dealer dealer = new Dealer(actionStrategyForDealer);
        CardPack pack = new CardPack();
        return  new Game(players, dealer, pack);
    }
}
