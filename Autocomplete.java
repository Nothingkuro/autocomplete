import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Autocomplete {
    private final Term[] terms;

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("terms array cannot be null");
        }
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException("entry cannot be null");
            }
        }
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix,
    // in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix cannot be null");
        }
        Term dummy = new Term(prefix, 0);
        int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, dummy, Term.byPrefixOrder(prefix.length()));
        if (firstIndex == -1) {
            return new Term[0];
        }
        int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, dummy, Term.byPrefixOrder(prefix.length()));
        int count = lastIndex - firstIndex + 1;
        Term[] matches = new Term[count];
        for (int i = 0; i < count; i++) {
            matches[i] = terms[firstIndex + i];
        }
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("prefix cannot be null");
        }
        Term dummy = new Term(prefix, 0);
        int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, dummy, Term.byPrefixOrder(prefix.length()));
        if (firstIndex == -1) {
            return 0;
        }
        int lastIndex = BinarySearchDeluxe.lastIndexOf(terms, dummy, Term.byPrefixOrder(prefix.length()));
        return lastIndex - firstIndex + 1;
    }

    // unit testing
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong(); // read the next weight
            in.readChar(); // scan past the tab
            String query = in.readLine(); // read the next query
            terms[i] = new Term(query, weight);// construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
