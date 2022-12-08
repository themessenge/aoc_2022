package day4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day4/input.txt");
        //File inputFile = new File("./src/day4/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<SectionPair> input = new ArrayList<>();
        String currentLine;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            String[] sections = currentLine.split(",");
            List<Integer> firstSection = new ArrayList<>();
            List<Integer> secondSection = new ArrayList<>();
            for (int i = Integer.parseInt(sections[0].split("-")[0]); i <= Integer.parseInt(sections[0].split("-")[1]); i++) {
                firstSection.add(i);
            }
            for (int i = Integer.parseInt(sections[1].split("-")[0]); i <= Integer.parseInt(sections[1].split("-")[1]); i++) {
                secondSection.add(i);
            }
            input.add(new SectionPair(firstSection, secondSection));
        }
        scanner.close();

        int result1 = solve1(input);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(input);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<SectionPair> input) {
        int count = 0;
        for (SectionPair sp : input) {
            count += sp.oneContainedInOther() ? 1 : 0;
        }
        return count;
    }

    private static int solve2(List<SectionPair> input) {
        int count = 0;
        for (SectionPair sp : input) {
            count += sp.oneOverlapsWithOther() ? 1 : 0;
        }
        return count;
    }


    private static class SectionPair {
        List<Integer> shorterSection, longerSection;

        public SectionPair(List<Integer> first, List<Integer> second) {
            if (first.size() <= second.size()) {
                this.shorterSection = first;
                this.longerSection = second;
            } else {
                this.shorterSection = second;
                this.longerSection = first;
            }
        }

        public boolean oneContainedInOther() {
            for (Integer i : shorterSection) {
                if (!longerSection.contains(i)) {
                    return false;
                }
            }
            return true;
        }

        public boolean oneOverlapsWithOther() {
            for (Integer i : longerSection) {
                if (shorterSection.contains(i)) {
                    return true;
                }
            }
            return false;
        }


    }

}
