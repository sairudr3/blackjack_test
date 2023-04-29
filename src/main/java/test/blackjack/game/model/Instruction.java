package test.blackjack.game.model;

import java.util.Scanner;

public class Instruction {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getInstruction(){
        return scanner.next();
    }
}
