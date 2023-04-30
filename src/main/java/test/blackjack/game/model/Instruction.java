package test.blackjack.game.model;

import java.util.Scanner;

public class Instruction {
    private static final Instruction instance  = new Instruction();
    private static final Scanner scanner = new Scanner(System.in);

    private Instruction(){

    }

    public String getNextInstruction(){
        return scanner.next();
    }
    public  static Instruction get(){
        return instance;
    }
}
