import java.util.Comparator;

public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    public Term(String query, long weight) {
        if (query == null) {
            throw new IllegalArgumentException("query cannot be null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("weight cannot be negative");
        }
        this.query = query;
        this.weight = weight;
    }

    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("r cannot be negative");
        }
        return new PrefixOrder(r);
    }

    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    public String toString() {
        return this.weight + "\t" + this.query;
    }

    public static void main(String[] args) {
        Term a = new Term("apple", 100);
        Term b = new Term("app", 200);
        Term c = new Term("banana", 50);

        System.out.println("Natural order (query):");
        System.out.println(a.compareTo(c) < 0);

        System.out.println("Reverse weight order:");
        System.out.println(Term.byReverseWeightOrder().compare(a, b) > 0);

        System.out.println("Prefix order r=3:");
        System.out.println(Term.byPrefixOrder(3).compare(a, b) == 0);

        System.out.println("toString format:");
        System.out.println(a);
    }

    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term a, Term b) {
            return Long.compare(b.weight, a.weight);
        }
    }

    private static class PrefixOrder implements Comparator<Term> {
        private final int r;

        PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term a, Term b) {
            int limit = Math.min(r, Math.min(a.query.length(), b.query.length()));
            for (int i = 0; i < limit; i++) {
                char c1 = a.query.charAt(i);
                char c2 = b.query.charAt(i);
                if (c1 != c2) {
                    return c1 - c2;
                }
            }

            if (limit == r) {
                return 0;
            }

            if (a.query.length() < b.query.length()) {
                return -1;
            }
            if (a.query.length() > b.query.length()) {
                return 1;
            }
            return 0;
        }
    }
}
