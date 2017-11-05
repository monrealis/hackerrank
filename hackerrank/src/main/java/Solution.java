import java.util.Scanner;

public class Solution {
    private static int primeMinisterNumber;
    private static int p;
    private static int misdials;
    private static Group[] groups = new Group[1_000_000];

    static {
        for (int i = 0; i < groups.length; ++i)
            groups[i] = new Group(i);
    }

    public static void main(String[] args) {
        try (Scanner s = new Scanner(System.in)) {
            primeMinisterNumber = s.nextInt();
            p = s.nextInt();
        }

        for (int i = 1;; ++i) {
            int from = s(2 * i - 1);
            int to = s(2 * i);
            if (from == to) {
                ++misdials;
                continue;
            }
            while (groups[from].count == null)
                groups[from] = groups[groups[from].first];
            while (groups[to].count == null)
                groups[to] = groups[groups[to].first];
            while (groups[primeMinisterNumber].count == null)
                groups[primeMinisterNumber] = groups[groups[primeMinisterNumber].first];

            Group oldGroup = groups[from];
            Group newGroup = groups[to];
            if (oldGroup == newGroup) {
                continue;
            }
            newGroup.count += oldGroup.count;
            oldGroup.count = null;
            oldGroup.first = newGroup.first;
            groups[from] = newGroup;

            if (groups[primeMinisterNumber] == newGroup || groups[primeMinisterNumber] == oldGroup) {
                double percentage = 100.0 * groups[primeMinisterNumber].count / groups.length;
                if (percentage > p) {
                    System.out.println(i - misdials);
                    break;
                }

            }

        }

    }

    private static Integer[] cache = new Integer[10_000_000];

    private static int s(int k) {
        if (cache[k] != null)
            return cache[k];
        if (k >= 1 && k <= 55) {
            int r = (int) ((100_003L - 200_003L * k + 300_007L * k * k * k) % 1_000_000);
            cache[k] = r;
            return r;
        } else if (k >= 56) {
            int r = (s(k - 24) + s(k - 55)) % 1_000_000;
            cache[k] = r;
            return r;
        }
        throw new IllegalArgumentException();
    }

    private static class Group {
        public int first;
        public Integer count;

        public Group(int first) {
            this.first = first;
            this.count = 1;
        }
    }
}