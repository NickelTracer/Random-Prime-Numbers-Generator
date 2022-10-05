package PrimeNumberGenerator;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Main {
    private int numbersAmount;
    private int lowerBound;
    private int upperBound;

    private void obtainArraySize() {
        Scanner scan1 = new Scanner(System.in);
        boolean continueInput = true;

        //Integer is only available input
        do{
            try{
                System.out.print("Enter amount of generated random prime numbers: ");
                numbersAmount = scan1.nextInt();

                while(numbersAmount < 1){
                    System.out.print("\nYour array have to have at least one position! Try again...");
                    numbersAmount = scan1.nextInt();
                }

                continueInput = false;
            }
            catch(InputMismatchException ex){
                System.out.print("Incorrect input: an integer is required!\n\n");
                scan1.nextLine();
            }
        }
        while(continueInput);
    }

    private void obtainBoundaries() {
        Scanner scan2 = new Scanner(System.in);
        boolean continueInput = true;

        //Only integers available and lower boundary cannot be greater than upper boundary
        do{
            try{
                System.out.print("Lower boundary: ");
                lowerBound = scan2.nextInt();
                System.out.print("Upper boundary: ");
                upperBound = scan2.nextInt();

                while(lowerBound > upperBound){
                    System.out.print("Your upper bound have to be bigger or equal than lower bound! Try again...");
                    upperBound = scan2.nextInt();
                }

                //Check if it is necessary to perform code if boundaries are the same (no sense in checking if random number always will not be prime)
                if(lowerBound == upperBound && lowerBound != 2) checkIfBoundariesMakesSense();
                //Prime numbers cannot be negative
                if(lowerBound <=0 && upperBound <=0){
                    infiniteLoopException();
                }

                continueInput = false;
            }catch(InputMismatchException ex){
                System.out.print("Incorrect input: an integer is required!\n\n");
                scan2.nextLine();
            }
        }
        while(continueInput);
    }

    void infiniteLoopException(){
        System.out.println("\n\n\n########################################");
        System.out.println("\t\t\t!!!ERROR!!!\nEntered infinite loop! Exiting program!");
        System.out.println("########################################");

        System.exit(0);
    }

    //Only activates if boundaries are the same
    void checkIfBoundariesMakesSense(){
        int result;
        result = upperBound % 2;
            if (result == 0){
                infiniteLoopException();
            }
    }

    public static void main(String[] args) {

        //Invoking objects and methods
        Main obj1 = new Main();
        obj1.obtainArraySize();

        Main obj2 = new Main();
        obj2.obtainBoundaries();

        Main except = new Main();

        RNG randomNumber_obj = new RNG();

        //Fetch starting time, program will force exit if it enters infinite loop
        long startTime = System.currentTimeMillis();
        System.out.println("+----------------------------------------------+");

        //Filling array list with prime numbers until amount of numbers will match user input
        while (obj1.numbersAmount != randomNumber_obj.list.size()) {
            randomNumber_obj.generateRN(obj2.lowerBound, obj2.upperBound);
            randomNumber_obj.isPrime();

            System.out.println("\nSize of list: "+randomNumber_obj.list.size());

            //Force exit condition
            if(System.currentTimeMillis()-startTime > 3000){
                except.infiniteLoopException();
            }
        }

        //Printing final array list of results
        System.out.println("\n+----------------------------------------------+");
        System.out.println("Final List output: ");
        for(int i = 0; i < randomNumber_obj.list.size(); i++){
            System.out.print(randomNumber_obj.list.get(i));
            System.out.print(" ");
        }
    }
}

class RNG {
    private int randomInt;
    ArrayList<Integer> list = new ArrayList<>();

    //Generating random number
    public void generateRN(int min, int max) {


        randomInt = (int) (Math.random() * ((max+1)-min) + min);
        System.out.println("+----------------------------------------------+");
        System.out.println("\nRandom number generated: " + randomInt);

    }

    //Checking if generated random number is prime
    void isPrime() {
        boolean prime = true;
        int remainder;
        System.out.println("\nChecking if " + randomInt + " is prime:\n");

        //Prime number cannot be negative, although user is completely free to set negative bounds
        if (randomInt < 0){
            System.out.println(randomInt+" is not positive!");
            prime = false;
        }

        if(prime) {
            for (int i = 2; i <= Math.abs(randomInt) / 2; i++) {
                remainder = randomInt % i;
                System.out.println(randomInt + " divided by " + i + " gives remainder: " + remainder);

                //Exception for 0, 1 and not prime number
                if (remainder == 0 || randomInt == 0 || Math.abs(randomInt) == 1) {
                    prime = false;
                    break;
                }
            }
        }
        if (prime && randomInt != 0 && Math.abs(randomInt) != 1 && randomInt > 0) {
            System.out.println(randomInt + " is prime!");
            list.add(randomInt);

        } else System.out.println(randomInt + " is not matching requirements!");
    }
}