
package day6;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int totalDiskSpace = 70000000;
    static int neededFreeDiskSpace = 30000000;

    public static void main(String[] args) throws IOException {
        File inputFile = new File("./src/day7/input.txt");
        //File inputFile = new File("./src/day7/example.txt");
        Scanner scanner = new Scanner(inputFile).useDelimiter("\r\n");
        Directory baseDir = new Directory(null, "/");
        Directory currentDir = baseDir;
        String currentLine = scanner.nextLine(); //throw away first line
        while (scanner.hasNext()) {
            currentLine = scanner.nextLine();
            if (currentLine.startsWith("$ ")) { //instruction
                currentLine = currentLine.substring(2);
                if (currentLine.startsWith("cd ..")) { //change directory out
                    currentDir = currentDir.parent;
                } else if (currentLine.startsWith("cd ")) { //change directory in
                    currentDir = currentDir.getDir(currentLine.substring(3));
                } else if (!currentLine.startsWith("ls")) {
                    throw new InputMismatchException(currentLine);
                }
            } else if (currentLine.startsWith("dir ")) { //dir entry
                currentDir.add(currentLine.substring(4));
            } else { //file entry
                currentDir.add(Integer.parseInt(currentLine.split(" ")[0]), currentLine.split(" ")[1]);
            }
        }
        scanner.close();
        baseDir.calculateSizeOfChildrenRecursive(0);
        baseDir.print(0);
        int result1 = solve1(baseDir);
        System.out.println("Result 1:" + result1);
        int result2 = solve2(baseDir);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(Directory input) {
        return input.findAllDirSmallerThan(100000, 0);
    }

    private static int solve2(Directory input) {
        int usedDiskSpace = input.sizeOfChildren;
        int freeDiskSpace = totalDiskSpace-usedDiskSpace;
        if(neededFreeDiskSpace < freeDiskSpace){
            System.out.println("Already enough space!");
            return 0;
        }

        return input.findSmallestDirGreaterThan(neededFreeDiskSpace-freeDiskSpace, null).sizeOfChildren;
    }

    private static class Directory {

        Directory parent;
        List<Directory> childDirectories;
        List<Main.MyFile> childFiles;

        int sizeOfChildren=0;

        String name;

        public Directory(Directory myParent, String myName) {
            this.parent = myParent;
            this.name = myName;
            childDirectories = new ArrayList<>();
            childFiles = new ArrayList<>();
        }

        public void add(String dirName) {
            childDirectories.add(new Directory(this, dirName));
        }

        public void add(int fileSize, String fileName) {
            childFiles.add(new MyFile(this, fileName, fileSize));
        }

        public Directory getDir(String dirName) {
            for (Directory d : childDirectories) {
                if (d.name.equals(dirName)) {
                    return d;
                }
            }
            throw new InputMismatchException("Did not find Directory " + dirName);
        }

        public int calculateSizeOfChildrenRecursive(int currentSize){
            if(this.sizeOfChildren==0){
                int size = 0;
                for(MyFile f : this.childFiles){
                    size+= f.size;
                }
                for(Directory d: this.childDirectories) {
                     size = d.calculateSizeOfChildrenRecursive(size);
                }
                this.sizeOfChildren = size;
            }
            return currentSize + sizeOfChildren;
        }

        public int findAllDirSmallerThan(int maxSize, int currentSizeSum){
            for(Directory d : this.childDirectories){
                currentSizeSum = d.findAllDirSmallerThan(maxSize, currentSizeSum);
            }
            if(this.sizeOfChildren < maxSize){
                currentSizeSum+= this.sizeOfChildren;
            }
            return currentSizeSum;
        }

        public Directory findSmallestDirGreaterThan(int minSize, Directory currentSmallest){
            if(currentSmallest == null || this.sizeOfChildren > minSize && this.sizeOfChildren < currentSmallest.sizeOfChildren){
                currentSmallest = this;
            }
            for(Directory d : this.childDirectories){
               currentSmallest = d.findSmallestDirGreaterThan(minSize, currentSmallest);
            }
            return currentSmallest;
        }

        public void print(int level){
            for(int i = 0; i < level; i++){
                System.out.print("  ");
            }
            System.out.println(this.name + " : " + this.sizeOfChildren);
            for (Directory d : this.childDirectories){
                d.print(level + 1);
            }
        }


    }

    private static class MyFile {
        Directory parent;
        int size;
        String name;

        public MyFile(Directory myParent, String myName, int mySize) {
            this.parent = myParent;
            this.name = myName;
            this.size = mySize;
        }

    }
}
