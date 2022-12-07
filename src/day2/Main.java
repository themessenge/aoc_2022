package day2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day2/input.txt");
        //File inputFile = new File("./src/day2/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<RPS> allInput = new ArrayList<>();
        while (scanner.hasNext()) {
            String currentLine = scanner.nextLine();
            String oppString = currentLine.split(" ")[0];
            String meString = currentLine.split(" ")[1];
            int opp,me;
            opp = switch (oppString) {
                case "A" -> 1;
                case "B" -> 2;
                default -> 3;
            };
            me = switch (meString) {
                case "X" -> 1;
                case "Y" -> 2;
                default -> 3;
            };
            allInput.add(new RPS(opp, me));
        }
        scanner.close();

        int result1 = solve1(allInput);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(allInput);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<RPS> input) {
        int sum = 0;
        for(RPS game : input){
            sum+=game.computeResult();
        }
        return sum;
    }

    private static int solve2(List<RPS> input) {
        int sum = 0;
        for(RPS game : input){
            sum+=game.computeStrategy();
        }
        return sum;
    }

    private static class RPS{
        int opp;
        int me;

        public RPS(int myOpp, int myMe){
            this.opp = myOpp;
            this.me = myMe;
        }

        public int computeResult(){
            if(opp==1 && me==2 || opp==2&&me==3 || opp==3&&me==1){
                return 6 + me;
            } else if (opp==me){
                return 3 + me;
            } else {
                return me;
            }
        }

        public int computeStrategy(){
            int sum=0;
            if(me == 2){ //Draw
               sum += opp + 3;
            } else if(me == 1) { //lose
                if(opp == 1){
                    sum += 3;
                } else if (opp == 2){
                    sum+= 1;
                } else {
                    sum+= 2;
                }
            } else {// win
                sum+=6;
                if(opp == 1){
                    sum+= 2;
                } else if (opp == 2){
                    sum+= 3;
                } else {
                    sum+= 1;
                }
            }
            return sum;
        }
    }
}
