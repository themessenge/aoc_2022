package day10;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day10/input.txt");
        //File inputFile = new File("./src/day10/example2.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        String currentLine;
        List<Instruction> instructions = new ArrayList<>();
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            instructions.add(new Instruction(currentLine.split(" ")[0], currentLine.split(" ").length > 1 ? Integer.parseInt(currentLine.split(" ")[1]) : 0));
        }
        scanner.close();
        int result1 = solve1(instructions);
        System.out.println("Result 1:" + result1);
        solve2(instructions);
    }

    private static int solve1(List<Instruction> input) {
        State state = new State();
        for (Instruction i : input) {
            Instruction.doInstruction(state, i);
        }
        return state.signalStrength;
    }

    private static void solve2(List<Instruction> input) {
        State state = new State();
        for (Instruction i : input) {
            Instruction.doInstruction(state, i);
        }
        state.printCrt();
    }

    private static class Instruction {

        private enum Instr {
            ADD,
            NOOP
        }

        private final Instr instr;
        private final int value;

        private Instruction(String instruction, int value) {
            this.instr = instruction.equals("addx") ? Instr.ADD : Instr.NOOP;
            this.value = value;
        }

        private static State doInstruction(State state, Instruction instr) {
            System.out.println("Instruction = " + instr.instr + " " + instr.value);
            state.cycle += 1;
            updateCrt(state);
            if (instr.instr == Instr.ADD) {
                if (isRelevantCycle(state)) {
                    updateSignalStrength(state);
                    state.cycle += 1;
                } else {
                    state.cycle += 1;
                    if (isRelevantCycle(state)) {
                        updateSignalStrength(state);
                    }
                }
                updateCrt(state);
                state.registerX += instr.value;
            } else if (instr.instr == Instr.NOOP) {
                if (isRelevantCycle(state)) {
                    ;
                    updateSignalStrength(state);
                }
            }
            return state;
        }

        private static void updateCrt(State state) {
            int position = (state.cycle - 1) % 40;
            System.out.print("Position = " + position + " Register = " + state.registerX);
            if (position == state.registerX - 1 ||
                position == state.registerX ||
                position == state.registerX + 1) {
                state.crt[state.cycle - 1] = '#';
                System.out.println(" Result = #");
            } else {
                state.crt[state.cycle - 1] = '.';
                System.out.println(" Result = .");
            }
        }

        private static void updateSignalStrength(State state) {
            state.signalStrength += state.cycle * state.registerX;
        }

        private static boolean isRelevantCycle(State state) {
            return state.cycle == 20 || state.cycle == 60 || state.cycle == 100 || state.cycle == 140 || state.cycle == 180 || state.cycle == 220;
        }
    }

    private static class State {

        private int cycle = 0;
        private int registerX = 1;
        private int signalStrength = 0;
        private Character[] crt = new Character[240];

        public State() {
        }

        private void printCrt() {
            for (int i = 0; i < crt.length; i++) {
                System.out.print(crt[i] + " ");
                if (i == 39 || i == 79 || i == 119 || i == 159 || i == 199) {
                    System.out.println();
                }
            }
        }
    }

}
