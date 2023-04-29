package test.blackjack.game.model;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Game {
    private final List<Player> players;
    private final Dealer dealer;
    private final CardPack pack;

    public void startGame(){
        System.out.println("Starting game with "+ players.size()+" players..");
        System.out.println("Shuffling...");
        pack.shuffle();
        initialDeal();
        for (Player activePlayer : players) {
            handFor(activePlayer, true);
        }
        handFor(dealer, true);
        summariseGame();

    }

    private void handFor(Player player, boolean askForHitStand){
        System.out.println();
        if(checkHasBusted(player)){
            return;
        }
        boolean shouldMaskCard = player == dealer && !askForHitStand;
        dealFor(player, shouldMaskCard );
        if(checkHasBusted(player)){
            return;
        }
        if(askForHitStand){
            Action action = player.getAction();
            if(player == dealer)
                System.out.print(" > " +action.name());
            if(action == Action.HIT){
                handFor(player, true);
            }
        }
        System.out.println();
    }

    private void summariseGame(){
        System.out.println("************************************");
        boolean hasDealerBusted = dealer.isBusted();
        System.out.println(hasDealerBusted? "Dealer busted" : "Dealer has " + dealer.getPoints());
        players.forEach(player -> {
            boolean busted = player.isBusted();
            int point = player.getPoints();
            boolean hasWon = point > dealer.getPoints();
            String playerWon = hasDealerBusted || hasWon ? player.toString() : "Dealer";
            System.out.println("Scoring "+ player +
                    (
                            busted?
                                    " busted. Dealer Wins"  :
                            ( " has "+ point+". " + playerWon + " wins.")
            ));
        });
    }

    private boolean checkHasBusted(Player player){
        if(player.hasOverPoints()){
            System.out.print(" Busted over 21");
            player.markBusted();
            return true;
        }
        return false;
    }

    private void initialDeal(){
        players.forEach(player -> handFor(player, false));
        handFor(dealer, false);
    }

    private void dealFor(Player player, boolean maskCard) {
        CardPack.Card drawnCard = pack.drawCard();
        player.drawnCard(drawnCard);
        System.out.print(" Dealing to " + player + ", card : " + ( maskCard ? "face down" : player.getCards()));
    }

}
