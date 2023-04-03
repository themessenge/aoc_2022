package day11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Monkey> monkeys;
        //monkeys = createTestMonkeys(true);
        monkeys = createMonkeys(true);
        int result1 = solve1(monkeys);
        System.out.println("Result 1:" + result1);
        monkeys.clear();
        //monkeys = createTestMonkeys(false);
        monkeys = createMonkeys(false);
        long result2 = solve2(monkeys);
        System.out.println("Result 2:" + result2);
    }

    private static int solve1(List<Monkey> input) {
        for (int i = 0; i < 20; i++) {
            for (Monkey m : input) {
                Iterator<Item> itr = m.items.iterator();
                while (itr.hasNext()) {
                    Item item = itr.next();
                    doOperation(m, item);
                    item.startValue = Math.floorDiv(item.startValue, 3);
                    if (item.isDivisibleBy(m.divisibleTestValue)) {
                        m.trueMonkey.items.add(item);
                    } else {
                        m.falseMonkey.items.add(item);
                    }
                    m.handlingCounter++;
                    itr.remove();
                }
            }
            System.out.println("--------Round " + (i + 1) + "----------");
            printMonkeys(input);
        }

        List<Integer> handlingCounters = input.stream().map(m -> m.handlingCounter).sorted().collect(Collectors.toList());
        return handlingCounters.get(handlingCounters.size() - 1) * handlingCounters.get(handlingCounters.size() - 2);
    }

    private static long solve2(List<Monkey> input) {
        for (int i = 0; i < 10000; i++) {
            for (Monkey m : input) {
                Iterator<Item> itr = m.items.iterator();
                while (itr.hasNext()) {
                    Item item = itr.next();
                    doOperation(m, item);
                    if (item.isDivisibleBy(m.divisibleTestValue)) {
                        m.trueMonkey.items.add(item);
                    } else {
                        m.falseMonkey.items.add(item);
                    }
                    m.handlingCounter++;
                    itr.remove();
                }

            }
            if (i % 1000 == 0) {
                System.out.println("--------Round " + (i + 1) + "----------");
                printMonkeys(input);
            }
        }
        List<Integer> handlingCounters = input.stream().map(m -> m.handlingCounter).sorted().collect(Collectors.toList());
        return (long) handlingCounters.get(handlingCounters.size() - 1) * (long) handlingCounters.get(handlingCounters.size() - 2);
    }

    private static void doOperation(Monkey m, Item item) {
        if (m.operation == Monkey.Operation.MULT) {
            item.multiply(m.operationValue);
        } else if (m.operation == Monkey.Operation.ADD) {
            item.add(m.operationValue);
        } else if (m.operation == Monkey.Operation.MULTSELF) {
            item.square();
        }
    }

    private static void printMonkeys(List<Monkey> input) {
        for (int i = 0; i < input.size(); i++) {
            input.get(i).print(i);
        }
    }

    private static class Monkey {

        private enum Operation {
            MULT,
            MULTSELF,
            ADD
        }

        private final Queue<Item> items = new LinkedList<>();
        private final Operation operation;
        private int operationValue;
        private final int divisibleTestValue;
        private Monkey trueMonkey;
        private Monkey falseMonkey;

        private int handlingCounter = 0;

        public Monkey(List<Integer> items, Operation operation, int operationValue, int divisibleTestValue, boolean isFirst) {
            if (isFirst) {
                this.items.addAll(items.stream().map(NormalItem::new).collect(Collectors.toList()));
            } else {
                this.items.addAll(items.stream().map(ModulusItem::new).collect(Collectors.toList()));
            }
            this.operation = operation;
            this.operationValue = operationValue;
            this.divisibleTestValue = divisibleTestValue;
        }

        public Monkey(List<Integer> items, Operation operation, int divisibleTestValue, boolean isFirst) {
            if (isFirst) {
                this.items.addAll(items.stream().map(NormalItem::new).collect(Collectors.toList()));
            } else {
                this.items.addAll(items.stream().map(ModulusItem::new).collect(Collectors.toList()));
            }
            this.operation = operation;
            this.divisibleTestValue = divisibleTestValue;
        }

        public void print(int i) {
            System.out.println("Monkey " + i + " counter " + handlingCounter);
            System.out.print("Items: ");
            for (Item l : items) {
                System.out.print(l.startValue + ": ");
                if (l instanceof ModulusItem) {
                    for (Map.Entry<Integer, Integer> entry : ((ModulusItem) l).modulos.entrySet()) {
                        System.out.print("(" + entry.getKey() + ", " + entry.getValue() + ") ");
                    }
                }
            }
            System.out.println();
        }

        private void addModulos(List<Monkey> monkeys) {
            monkeys.forEach(m -> m.initItems(items));
        }

        private void initItems(Queue<Item> items) {
            items.forEach(i -> ((ModulusItem) i).addModulo(divisibleTestValue));
        }

    }

    private abstract static class Item {

        protected int startValue;

        public Item(int startValue) {
            this.startValue = startValue;
        }

        abstract void square();

        abstract void multiply(int mult);

        abstract void add(int add);

        abstract boolean isDivisibleBy(int div);
    }

    private static class NormalItem extends Item {

        public NormalItem(int startValue) {
            super(startValue);
        }

        @Override
        void square() {
            startValue = startValue * startValue;
        }

        @Override
        void multiply(int mult) {
            startValue = startValue * mult;
        }

        @Override
        void add(int add) {
            startValue = startValue + add;
        }

        @Override
        boolean isDivisibleBy(int div) {
            return startValue % div == 0;
        }
    }

    private static class ModulusItem extends Item {
        private final HashMap<Integer, Integer> modulos = new HashMap<>();

        public ModulusItem(int startValue) {
            super(startValue);
        }

        public void addModulo(int mod) {
            modulos.put(mod, startValue % mod);
        }

        @Override
        public void square() {
            for (Map.Entry<Integer, Integer> entry : modulos.entrySet()) {
                Integer mod = entry.getKey();
                Integer val = entry.getValue();
                modulos.put(mod, (val * val) % mod);
            }
        }

        @Override
        public void multiply(int mult) {
            for (Map.Entry<Integer, Integer> entry : modulos.entrySet()) {
                Integer mod = entry.getKey();
                Integer val = entry.getValue();
                modulos.put(mod, (val * mult) % mod);
            }
        }

        @Override
        public void add(int add) {
            for (Map.Entry<Integer, Integer> entry : modulos.entrySet()) {
                Integer mod = entry.getKey();
                Integer val = entry.getValue();
                modulos.put(mod, (val + add) % mod);
            }
        }

        @Override
        public boolean isDivisibleBy(int div) {
            return modulos.get(div) == 0;
        }
    }

    private static List<Monkey> createTestMonkeys(boolean isFirst) {
        List<Monkey> monkeys = new ArrayList<>();
        Monkey m0 = new Monkey(List.of(79, 98),
            Monkey.Operation.MULT, 19,
            23, isFirst);
        Monkey m1 = new Monkey(List.of(54, 65, 75, 74),
            Monkey.Operation.ADD, 6,
            19, isFirst);
        Monkey m2 = new Monkey(List.of(79, 60, 97),
            Monkey.Operation.MULTSELF,
            13, isFirst);
        Monkey m3 = new Monkey(List.of(74),
            Monkey.Operation.ADD, 3,
            17, isFirst);
        m0.trueMonkey = m2;
        m0.falseMonkey = m3;
        m1.trueMonkey = m2;
        m1.falseMonkey = m0;
        m2.trueMonkey = m1;
        m2.falseMonkey = m3;
        m3.trueMonkey = m0;
        m3.falseMonkey = m1;
        monkeys.add(m0);
        monkeys.add(m1);
        monkeys.add(m2);
        monkeys.add(m3);
        if (!isFirst) {
            monkeys.forEach(m -> m.addModulos(monkeys));
        }
        return monkeys;
    }

    private static List<Monkey> createMonkeys(boolean isFirst) {
        List<Monkey> monkeys = new ArrayList<>();
        Monkey m0 = new Monkey(List.of(73, 77),
            Monkey.Operation.MULT, 5,
            11, isFirst);
        Monkey m1 = new Monkey(List.of(57, 88, 80),
            Monkey.Operation.ADD, 5,
            19, isFirst);
        Monkey m2 = new Monkey(List.of(61, 81, 84, 69, 77, 88),
            Monkey.Operation.MULT, 19,
            5, isFirst);
        Monkey m3 = new Monkey(List.of(78, 89, 71, 60, 81, 84, 87, 75),
            Monkey.Operation.ADD, 7,
            3, isFirst);
        Monkey m4 = new Monkey(List.of(60, 76, 90, 63, 86, 87, 89),
            Monkey.Operation.ADD, 2,
            13, isFirst);
        Monkey m5 = new Monkey(List.of(88),
            Monkey.Operation.ADD, 1,
            17, isFirst);
        Monkey m6 = new Monkey(List.of(84, 98, 78, 85),
            Monkey.Operation.MULTSELF,
            7, isFirst);
        Monkey m7 = new Monkey(List.of(98, 89, 78, 73, 71),
            Monkey.Operation.ADD, 4,
            2, isFirst);
        m0.trueMonkey = m6;
        m0.falseMonkey = m5;
        m1.trueMonkey = m6;
        m1.falseMonkey = m0;
        m2.trueMonkey = m3;
        m2.falseMonkey = m1;
        m3.trueMonkey = m1;
        m3.falseMonkey = m0;
        m4.trueMonkey = m2;
        m4.falseMonkey = m7;
        m5.trueMonkey = m4;
        m5.falseMonkey = m7;
        m6.trueMonkey = m5;
        m6.falseMonkey = m4;
        m7.trueMonkey = m3;
        m7.falseMonkey = m2;
        monkeys.add(m0);
        monkeys.add(m1);
        monkeys.add(m2);
        monkeys.add(m3);
        monkeys.add(m4);
        monkeys.add(m5);
        monkeys.add(m6);
        monkeys.add(m7);
        if (!isFirst) {
            monkeys.forEach(m -> m.addModulos(monkeys));
        }
        return monkeys;
    }

}
