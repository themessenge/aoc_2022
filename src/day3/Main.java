
package day3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day3/input.txt");
        //File inputFile = new File("./src/day3/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Rucksack> input = new ArrayList<>();
        String currentLine;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            int halfwayPoint = currentLine.length() / 2;
            Rucksack r = new Rucksack(currentLine.substring(0, halfwayPoint), currentLine.substring(halfwayPoint), currentLine);
            input.add(r);
        }
        scanner.close();
        int result1 = solve1(input);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(input);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<Rucksack> input) {
        int sum = 0;
        for(Rucksack r : input){
            sum += charToInt(r.compareCompartments());
        }
        return sum;
    }

    private static int solve2(List<Rucksack> input) {
        int sum = 0;
        for(int i = 0; i < input.size()-2; i+=3){
            sum += charToInt(compareStrings(input.get(i).all, input.get(i + 1).all, input.get(i + 2).all));
        }
        return sum;
    }

    public static class Rucksack {
        String compOne;
        String compTwo;

        String all;

        public Rucksack(String one, String two, String all) {
            this.compOne = one;
            this.compTwo = two;
            this.all = all;
        }

        public char compareCompartments(){
            for(char c : compOne.toCharArray()){
                if(compTwo.contains("" + c)){
                    return c;
                }
            }
            throw new InputMismatchException("Couldnt find same char: " + compOne + " -- " + compTwo);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Rucksack){
                Rucksack r = (Rucksack) obj;
                return r.compOne.equals(this.compOne)&& r.compTwo.equals(this.compTwo);
            }
            return false;
        }
    }

    public static int charToInt(char c){
        int index = "0ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(c) + 26;
        if(index < 26){
            index = "0abcdefghijklmnopqrstuvwxyz".indexOf(c);
        }
        return index;
    }

    public static char compareStrings(String one, String two, String three){
        for(char c : one.toCharArray()){
            if(two.contains("" + c) && three.contains("" + c)){
                return c;
            }
        }
       throw new InputMismatchException("Couldnt find same char: " + one + " -- " + two + " -- " + three);
    }

}
