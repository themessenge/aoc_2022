package day23;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {

    public static List<Elf> input = new ArrayList<>();
    public static List<Elf> input2 = new ArrayList<>();
    public static int xSize;
    public static int ySize;

    public static int printSize = 20;

    public static Queue<Integer> orderOfDirections = new ArrayBlockingQueue<>(4);


    public static void main(String[] args) throws IOException {
        //File inputFile = new File("./src/day23/input.txt");
        File inputFile = new File("./src/day23/example2.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> tempInput = new ArrayList<>();
        while (scanner.hasNext()) {
            tempInput.add(scanner.nextLine());
        }
        scanner.close();
        ySize = tempInput.size();
        xSize = tempInput.get(0).length();
        for (int y = 0; y < ySize; y++) {
            char[] line = tempInput.get(y).toCharArray();
            for (int x = 0; x < xSize; x++) {
                if (line[x] == '#') {
                    input.add(new Elf(x, y));
                    input2.add(new Elf(x, y));
                }
            }
        }

        //initialize directions
        orderOfDirections.add(1);
        orderOfDirections.add(2);
        orderOfDirections.add(3);
        orderOfDirections.add(4);
        int result1 = solve1();
        System.out.println("Result 1:" + result1);
        input = input2; //Reset
        orderOfDirections.clear();
        orderOfDirections.add(1);
        orderOfDirections.add(2);
        orderOfDirections.add(3);
        orderOfDirections.add(4);
        int result2 = solve2();
        System.out.println("Result 2:" + result2);
    }

    private static int solve1() {
        print(input);
        for (int round = 0; round < 10; round++) {
            System.out.println("=========== ROUND " + (round + 1) + " ===========");
            System.out.println("First direction: " + orderOfDirections.peek());
            boolean moved = false;
            for (Elf elf : input) {
                elf.findDirOfNextMove();
            }
            //       printMoves(input);
            for (Elf elf : input) {
                moved |= elf.moveElf();
            }
            if (!moved) {
                break;
            }
            //       print(input);
            int direction = orderOfDirections.remove();
            orderOfDirections.add(direction);
        }
        print(input);
        int yMin = Integer.MAX_VALUE;
        int yMax = Integer.MIN_VALUE;
        int xMin = Integer.MAX_VALUE;
        int xMax = Integer.MIN_VALUE;
        for (Elf e : input) {
            yMin = min(yMin, e.y);
            xMin = min(xMin, e.x);
            yMax = max(yMax, e.y);
            xMax = max(xMax, e.x);
        }
        return (Math.abs(yMax - yMin) + 1) * (Math.abs(xMax - xMin) + 1) - input.size();
    }

    private static int solve2() {
        print(input);
        boolean moved = true;
        int round = 0;
        while (moved) {
            round++;
            System.out.println("=========== ROUND " + round + " ===========");
            System.out.println("First direction: " + orderOfDirections.peek());
            moved = false;
            for (Elf elf : input) {
                elf.findDirOfNextMove();
            }
            //      printMoves(input);
            for (Elf elf : input) {
                moved |= elf.moveElf();
            }
            //      print(input);
            int direction = orderOfDirections.remove();
            orderOfDirections.add(direction);
        }
        print(input);
        return round;
    }


    private static void print(List<Elf> inputs) {
        char[][] printArray = new char[printSize][printSize];
        for (int y = 0; y < printSize; y++) {
            for (int x = 0; x < printSize; x++) {
                printArray[y][x] = '.';
            }
        }
        for (Elf e : inputs) {
            printArray[e.y + printSize / 4][e.x + printSize / 4] = '#';
        }
        printArray(printArray);
    }

    private static void printMoves(List<Elf> inputs) {
        char[][] printArray = new char[printSize][printSize];
        for (int y = 0; y < printSize; y++) {
            for (int x = 0; x < printSize; x++) {
                printArray[y][x] = '.';
            }
        }
        for (Elf e : inputs) {
            char c = 'X';
            switch (e.dirOfNextMove) {
                case 1:
                    c = '^';
                    break;
                case 2:
                    c = 'v';
                    break;
                case 3:
                    c = '<';
                    break;
                case 4:
                    c = '>';
            }
            printArray[e.y + printSize / 4][e.x + printSize / 4] = c;
        }
        printArray(printArray);
    }

    private static void printArray(char[][] printArray) {
        for (char[] chars : printArray) {
            for (int x = 0; x < printArray[0].length; x++) {
                System.out.print(chars[x] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static Elf getValueInGrid(int y, int x) {
        for (Elf e : input) {
            if (e.x == x && e.y == y) {
                return e;
            }
        }
        return null;
    }

    private static class Elf {
        int x;
        int y;
        int dirOfNextMove;
        int xOfNextMove;
        int yOfNextMove;

        public Elf(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean moveElf() {
            if (this.dirOfNextMove == 0) { //stay
                return false;
            }
            for (Elf e : input) {
                if (e != this
                    && e.xOfNextMove == this.xOfNextMove
                    && e.yOfNextMove == this.yOfNextMove) {
                    return false; //there is another elf who wants to move there
                }
            }
            x = xOfNextMove;
            y = yOfNextMove;
            return true;
        }

        public void findDirOfNextMove() {
            xOfNextMove = x;
            yOfNextMove = y;
            dirOfNextMove = 0;
            boolean hasNeighbours = false;
            for (int dir : orderOfDirections) {
                hasNeighbours |= hasNeighborsInDirection(dir);
            }
            if (!hasNeighbours) {
                return;
            }
            for (int dir : orderOfDirections) {
                if (!hasNeighborsInDirection(dir)) {
                    dirOfNextMove = dir;
                    break;
                }
            }
            if (dirOfNextMove == 0) { //cannot move
                return;
            }
            switch (dirOfNextMove) {
                case 1: //north
                    yOfNextMove = y - 1;
                    break;
                case 2: //south
                    yOfNextMove = y + 1;
                    break;
                case 3: //west
                    xOfNextMove = x - 1;
                    break;
                case 4: //east
                    xOfNextMove = x + 1;
                    break;
            }
        }

        private boolean hasNeighborsInDirection(int direction) {
            switch (direction) {
                case 1:
                    return hasNeighborsNorth();
                case 2:
                    return hasNeighborsSouth();
                case 3:
                    return hasNeighborsWest();
                case 4:
                    return hasNeighborsEast();
            }
            return false;
        }

        private boolean hasNeighborsNorth() {
            return getValueInGrid(y - 1, x - 1) != null
                || getValueInGrid(y - 1, x) != null
                || getValueInGrid(y - 1, x + 1) != null;
        }

        private boolean hasNeighborsSouth() {
            return getValueInGrid(y + 1, x - 1) != null
                || getValueInGrid(y + 1, x) != null
                || getValueInGrid(y + 1, x + 1) != null;
        }

        private boolean hasNeighborsWest() {
            return getValueInGrid(y - 1, x - 1) != null
                || getValueInGrid(y, x - 1) != null
                || getValueInGrid(y + 1, x - 1) != null;
        }

        private boolean hasNeighborsEast() {
            return getValueInGrid(y - 1, x + 1) != null
                || getValueInGrid(y, x + 1) != null
                || getValueInGrid(y + 1, x + 1) != null;
        }

    }


}
