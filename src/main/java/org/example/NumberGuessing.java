package org.example;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessing {
    private static final int MAX_NUMBER = 100;
    private static final int MIN_NUMBER = 1;
    private static final int EASY_LEVEL_CHANCES = 10;
    private static final int MEDIUM_LEVEL_CHANCES = 5;
    private static final int HARD_LEVEL_CHANCES = 2;

    enum DifficultyLevel{
        EASY(EASY_LEVEL_CHANCES),
        MEDIUM(MEDIUM_LEVEL_CHANCES),
        HARD(HARD_LEVEL_CHANCES);

        private final int chances;

        DifficultyLevel(int chances){
            this.chances = chances;
        }

        public int getChances(){
            return chances;
        }
    }

    private static final Random random = new Random();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int systemGuess = generateRandomNumber();
        DifficultyLevel selectedLevel = selectedDifficulty();
        int remainingChances = selectedLevel.getChances();


        System.out.println("You have selected the " + selectedLevel.name() + " difficulty level.");
        startGame(systemGuess, remainingChances);
    }

    private static int generateRandomNumber(){
        return random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;
    }

    private static DifficultyLevel selectedDifficulty(){
        int choice = -1;

        while(choice < 1 || choice > 3){
            try{
                System.out.println("Please select the difficulty level:");
                System.out.println("1. Easy");
                System.out.println("2. Medium");
                System.out.println("3. Hard");
                choice = scanner.nextInt();
                scanner.nextLine();

                if(choice == 1){
                    return DifficultyLevel.EASY;
                } else if (choice == 2) {
                    return DifficultyLevel.MEDIUM;
                }else if(choice == 3){
                    return DifficultyLevel.HARD;
                }else{
                    System.out.println("Invalid choice! Please select a valid difficulty.");
                }
            }catch (InputMismatchException e){
                scanner.nextLine();
                System.out.println("Invalid input! Please enter a number between 1 and 3.");
            }
        }
        return DifficultyLevel.EASY;
    }

    private static void startGame(int systemGuess,int chances){
        boolean end= false;
        while( !end && chances > 0){
            System.out.println("Enter your guess (You have " + chances + " chances left):");

            int userGuess = -1;
            try {
                userGuess = scanner.nextInt();
                scanner.nextLine();

                if (userGuess == systemGuess) {
                    System.out.println("Congratulations! You guessed it right.");
                    end = true;
                } else {
                    if (userGuess < systemGuess) {
                        System.out.println("Incorrect! The number is greater than " + userGuess);
                    } else {
                        System.out.println("Incorrect! The number is less than " + userGuess);
                    }
                    chances--;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Invalid input! Please enter a valid number.");
            }
        }
    }
}
