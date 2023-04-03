package day24;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day7/input.txt");
        //File inputFile = new File("./src/day7/example2.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> input = new ArrayList<>();
        String currentLine;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
        }
        scanner.close();
        int result1 = solve1(input);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(input);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<String> input) {
        return input.size();
    }

    private static int solve2(List<String> input) {
        return input.size();
    }

}
