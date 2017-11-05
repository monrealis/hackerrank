import java.util.Scanner;

public class Solution {
    private static int primeMinisterNumber;
    private static int p;
    private static int misdials;

    public static void main(String[] args) {
        load();
        iterate();
    }

    private static void load() {
        try (Scanner s = new Scanner(System.in)) {
            primeMinisterNumber = s.nextInt();
            p = s.nextInt();
        }
    }

    private static void iterate() {
        for (int i = 1;; ++i)
            try {
                iterate(i);
            } catch (Finished e) {
                return;
            }
    }

    private static void iterate(int i) throws Finished {
        int from = S.s(2 * i - 1);
        int to = S.s(2 * i);
        if (from == to) {
            ++misdials;
            return;
        }
        Group oldGroup = Groups.get(from);
        Group newGroup = Groups.get(to);
        if (oldGroup == newGroup)
            return;
        newGroup.mergeFrom(oldGroup);
        mergeGroups(i, newGroup);
    }

    private static void mergeGroups(int i, Group newGroup) throws Finished {
        Group pmGroup = Groups.get(primeMinisterNumber);
        if (pmGroup != newGroup)
            return;
        double percentage = 100.0 * pmGroup.count / Groups.groups.length;
        if (percentage > p) {
            System.out.println(i - misdials);
            throw new Finished();
        }
    }

    public static class Groups {
        private static Group[] groups = new Group[1_000_000];

        static {
            for (int i = 0; i < groups.length; ++i)
                groups[i] = new Group(i);
        }

        private static Group get(int index) {
            while (groups[index].count == null)
                groups[index] = groups[groups[index].first];
            return groups[index];
        }
    }

    private static class S {
        private static Integer[] cache = new Integer[10_000_000];

        public static int s(int k) {
            if (cache[k] == null)
                cache[k] = getS(k);
            return cache[k];
        }

        private static int getS(int k) {
            if (k >= 1 && k <= 55)
                return (int) ((100_003L - 200_003L * k + 300_007L * k * k * k) % 1_000_000);
            else
                return (s(k - 24) + s(k - 55)) % 1_000_000;
        }
    }

    private static class Group {
        public int first;
        public Integer count;

        public Group(int first) {
            this.first = first;
            this.count = 1;
        }

        public void mergeFrom(Group from) {
            this.count += from.count;
            from.count = null;
            from.first = this.first;
        }
    }

    private static class Finished extends Exception {
    }
}