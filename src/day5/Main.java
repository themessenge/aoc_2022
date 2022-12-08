package day5;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day5/input.txt");
        //File inputFile = new File("./src/day5/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Stack<Character>> input1 = new ArrayList<>();
        List<Stack<Character>> input2 = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        String currentLine;

        int numberOfStacks = 9;
        for (int i = 0; i < numberOfStacks; i++) {
            input1.add(new Stack<>());
            input2.add(new Stack<>());
        }
        int stackNumber;

        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            stackNumber = 0;
            if (currentLine.startsWith(" 1 ")) {
            } else if (currentLine.startsWith("move ")) {
                instructions.add(new Instruction(
                    Integer.parseInt(currentLine.split(" from ")[0].substring(5)),
                    Integer.parseInt(currentLine.split(" from ")[1].split(" to ")[0]),
                    Integer.parseInt(currentLine.split(" from ")[1].split(" to ")[1])));
            } else {
                for (int i = 1; i < currentLine.length(); i += 4) {
                    char stackEntry = currentLine.charAt(i);
                    if (stackEntry != ' ') {
                        input1.get(stackNumber).add(stackEntry);
                        input2.get(stackNumber).add(stackEntry);
                    }
                    stackNumber++;
                }
            }
        }

        for (Stack<Character> stack : input1) {
            Collections.reverse(stack);
        }
        for (Stack<Character> stack : input2) {
            Collections.reverse(stack);
        }

        System.out.println("Original");
        print(input1);
        scanner.close();
        String result1 = solve1(input1, instructions);
        System.out.println("Result 1:" + result1);
        String result2 = solve2(input2, instructions);
        System.out.println("Result 2:" + result2);
    }

    private static String solve1(List<Stack<Character>> input, List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.numberOfBoxes; i++) {
                input.get(instruction.toStack - 1).add(input.get(instruction.fromStack - 1).pop());
            }
            //print(input);
        }
        StringBuilder result = new StringBuilder();
        for (Stack<Character> stack : input) {
            if (!stack.empty()) {
                result.append(stack.pop());
            }
        }
        print(input);
        return result.toString();
    }

    private static String solve2(List<Stack<Character>> input, List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            Stack<Character> boxStack = new Stack<>();
            for (int i = 0; i < instruction.numberOfBoxes; i++) {
                boxStack.add(input.get(instruction.fromStack - 1).pop());
            }
            while (!boxStack.empty()) {
                input.get(instruction.toStack - 1).add(boxStack.pop());
            }
            //print(input);
        }
        StringBuilder result = new StringBuilder();
        for (Stack<Character> stack : input) {
            if (!stack.empty()) {
                result.append(stack.pop());
            }
        }
        print(input);
        return result.toString();
    }


    private static class Instruction {
        int numberOfBoxes;
        int fromStack;
        int toStack;

        public Instruction(int num, int from, int to) {
            this.numberOfBoxes = num;
            this.fromStack = from;
            this.toStack = to;
        }
    }

    private static void print(List<Stack<Character>> inputs) {
        for (Stack<Character> input : inputs) {
            Stack<Character> copy = (Stack<Character>) input.clone();
            while (!copy.empty()) {
                System.out.print(copy.pop() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static Stack<Character> reverseStack(Stack<Character> stack) {
        Stack<Character> reversed = new Stack<>();
        while (!stack.empty()) {
            reversed.add(stack.pop());
        }
        return reversed;
    }
}
