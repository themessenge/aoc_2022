
package day8;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.max;

public class Main {

    private static  int[][] inputs;
    private static int xLength, yLength;

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day8/input.txt");
        //File inputFile = new File("./src/day8/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        List<String> temps = new ArrayList<>();
        while (scanner.hasNext()) {
            temps.add(scanner.nextLine());
        }
        scanner.close();

        inputs = new int[temps.size()][temps.get(0).length()];
        for (int i = 0; i < temps.size(); i++) {
            for (int j = 0; j < temps.get(0).length(); j++) {
                inputs[i][j] = Integer.parseInt(temps.get(i).charAt(j) + "");
            }
        }
        scanner.close();
        print(inputs);
        yLength= inputs.length;
        xLength=inputs[0].length;

        int result1 = solve1();
        System.out.println("Result 1:" + result1);
        int result2 = solve2();
        System.out.println("Result 2:" + result2);
    }

    private static int solve1() {
        int[][] visible = new int[yLength][xLength];
        int count = 0;
        for(int i= 0; i < inputs.length; i++){
            for(int j =0; j < inputs[0].length; j++) {
                int vis = isVisible(i, j, inputs[i][j]);
                count += vis;
                visible[i][j] = vis;
            }
        }
        print(visible);
        return count;
    }

    private static int solve2() {
        int[][] scenics = new int[yLength][xLength];
        int maxVis = 0;
        for(int i= 0; i < inputs.length; i++){
            for(int j =0; j < inputs[0].length; j++) {
                int vis = computeScenics(i, j, inputs[i][j]);
                maxVis  = max(vis, maxVis);
                scenics[i][j] = vis;
            }
        }
        print(scenics);
        return maxVis;
    }

    private static int isVisible(int yCoord, int xCoord, int height){
        if(xCoord == 0 || yCoord == 0 || xCoord == xLength-1  || yCoord==yLength-1){
            return 1;
        }
        boolean top = true;
        boolean bottom = true;
        boolean left = true;
        boolean right = true;
        for(int i = 0; i < inputs.length; i++){
            for(int j =0; j < inputs[0].length; j++) {
                if(i == yCoord && j < xCoord){
                    left &= inputs[i][j] < height;
                } else if (i == yCoord && j > xCoord){
                    right &= inputs[i][j] < height;
                } else if(j == xCoord && i < yCoord){
                    top &= inputs[i][j] < height;
                } else if(j == xCoord && i > yCoord){
                    bottom &= inputs[i][j] < height;
                }
            }
        }
        return (left || right || top || bottom) ? 1 : 0;
    }

    private static int computeScenics(int yCoord, int xCoord, int height) {
        if(xCoord == 0 || yCoord == 0 || xCoord == xLength-1  || yCoord==yLength-1){
            return 0;
        }
        boolean topBlocked = false;
        boolean bottomBlocked = false;
        boolean leftBlocked = false;
        boolean rightBlocked = false;
        int top = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;
        for(int i = yCoord - 1; i >= 0; i--) {
            boolean topLeftBigger = inputs[i][xCoord] >= height;
            if (!topBlocked) {
                topBlocked = topLeftBigger;
                top += topBlocked ? 0 : 1;
            }
            if (!leftBlocked) {
                leftBlocked = topLeftBigger;
                left += leftBlocked ? 0 : 1;
            }
        }
        for(int i = yCoord + 1; i < yLength; i++) {
            boolean bottomRightBigger = inputs[yCoord][i] >= height;
            if (!bottomBlocked){
                bottomBlocked = bottomRightBigger;
                bottom += bottomBlocked ? 0 : 1;
            }
            if (!rightBlocked) {
                rightBlocked = bottomRightBigger;
                right += rightBlocked ? 0 : 1;
            }
        }
        top += topBlocked ? 1 : 0;
        bottom += bottomBlocked ? 1 : 0;
        left += leftBlocked ? 1 : 0;
        right += rightBlocked ? 1 : 0;
        return top * bottom * left * right;
    }

    private static void print(int[][] inputs){
        for (int[] input : inputs) {
            for (int j = 0; j < inputs[0].length; j++) {
                System.out.print(input[j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
