package day9;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day9/input.txt");
        //File inputFile = new File("./src/day9/example.txt");
        //File inputFile = new File("./src/day9/example2.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<Instruction> input = new ArrayList<>();
        String currentLine;
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            input.add(new Instruction(currentLine.split(" ")[0], Integer.parseInt(currentLine.split(" ")[1])));
        }
        scanner.close();
        // int result1 = solve1(input);
        // System.out.println("Result 1:" + result1);
        int result2 = solve2(input);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<Instruction> input) {
        Set<Coord> visitedByTail = new HashSet<>();
        RopeEnd head = new RopeEnd(0, 0);
        RopeEnd tail = new RopeEnd(0, 0);
        for (Instruction instr : input) {
            for (int i = 0; i < instr.number; i++) {
                head.move(instr.dir);
                tail.follow(head);
                visitedByTail.add(new Coord(tail.x, tail.y));
                System.out.println("---");
                System.out.println("(" + head.x + ", " + head.y + ") - (" + tail.x + ", " + tail.y + ")");
                print(visitedByTail);
            }
        }
        print(visitedByTail);
        return visitedByTail.size();

    }

    private static int solve2(List<Instruction> input) {
        Set<Coord> visitedByTail = new HashSet<>();
        RopeEnd head = new RopeEnd(0, 0);
        RopeEnd rope1 = new RopeEnd(0, 0);
        RopeEnd rope2 = new RopeEnd(0, 0);
        RopeEnd rope3 = new RopeEnd(0, 0);
        RopeEnd rope4 = new RopeEnd(0, 0);
        RopeEnd rope5 = new RopeEnd(0, 0);
        RopeEnd rope6 = new RopeEnd(0, 0);
        RopeEnd rope7 = new RopeEnd(0, 0);
        RopeEnd rope8 = new RopeEnd(0, 0);
        RopeEnd tail = new RopeEnd(0, 0);
        for (Instruction instr : input) {
            //System.out.println(instr.dir + " : " + instr.number);
            int sizeBefore = visitedByTail.size();
            for (int i = 0; i < instr.number; i++) {
                head.move(instr.dir);
                rope1.follow(head);
                rope2.follow(rope1);
                rope3.follow(rope2);
                rope4.follow(rope3);
                rope5.follow(rope4);
                rope6.follow(rope5);
                rope7.follow(rope6);
                rope8.follow(rope7);
                tail.follow(rope8);
                visitedByTail.add(new Coord(tail.x, tail.y));
                //System.out.println("---");
                //System.out.println("(" + head.x + ", " + head.y + ") - (" + tail.x + ", " + tail.y + ")");
            }
            /*
            if(visitedByTail.size()> sizeBefore) {
                printWithSize(visitedByTail, 30);
            }
            */
        }
        print(visitedByTail);
        return visitedByTail.size();
    }

    private static class Instruction {
        String dir;
        int number;

        public Instruction(String d, int n) {
            this.dir = d;
            this.number = n;
        }
    }

    private static class RopeEnd {
        int x;
        int y;

        public RopeEnd(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move(String dir) {
            switch (dir) {
                case "R":
                    this.x += 1;
                    break;
                case "L":
                    this.x -= 1;
                    break;
                case "U":
                    this.y += 1;
                    break;
                case "D":
                    this.y -= 1;
            }
        }

        public void follow(RopeEnd head) {
            int headX = head.x;
            int headY = head.y;

            if (headX == this.x || headY == this.y) {
                if (headX - 2 == this.x) {
                    this.x += 1;
                } else if (headX + 2 == this.x) {
                    this.x -= 1;
                }
                if (headY - 2 == this.y) {
                    this.y += 1;
                } else if (headY + 2 == this.y) {
                    this.y -= 1;
                }
            } else {
                if (Math.abs(headX - this.x) + Math.abs(headY - this.y) > 2) {
                    if (headX > this.x) {
                        this.x += 1;
                    } else if (headX < this.x) {
                        this.x -= 1;
                    }

                    if (headY > this.y) {
                        this.y += 1;
                    } else if (headY < this.y) {
                        this.y -= 1;
                    }
                }
            }

        }
    }

    private static class Coord {
        int x;
        int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coord) {
                Coord other = (Coord) obj;
                return other.x == this.x && other.y == this.y;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void print(Set<Coord> coords) {
        int size = 0;
        for (Coord next : coords) {
            if (next.x > size) {
                size = next.x;
            }
            if (next.y > size) {
                size = next.y;
            }
        }
        size = (size + 1) * 2;
        printWithSize(coords, size);
    }

    public static void printWithSize(Set<Coord> coords, int size) {
        List<Coord> cList = new ArrayList<>(coords);
        String[][] array = new String[size][size];
        for (Coord c : cList) {
            array[c.x + size / 2][c.y + size / 2] = " X ";
        }
        for (String[] strings : array) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(strings[j] != null ? strings[j] : " . ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
