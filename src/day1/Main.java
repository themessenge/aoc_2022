package day1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day1/input.txt");
        //File inputFile = new File("./src/day1/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Integer> allBags = new ArrayList<>();
        int sum = 0;
        while (scanner.hasNext()) {
            String currentLine = scanner.nextLine();
            if (currentLine.equals("")) {
                allBags.add(sum);
                sum = 0;
            } else {
               sum += Integer.parseInt(currentLine);
            }
        }
        allBags.add(sum);
        scanner.close();
        Collections.sort(allBags);

        int result1 = solve1(allBags);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(allBags);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<Integer> input) {
        return input.get(input.size()-1);
    }

    private static int solve2(List<Integer> input) {
        int size = input.size();
        //System.out.println(input);
        return input.get(size -1)+input.get(size -2)+input.get(size -3);
    }
}
