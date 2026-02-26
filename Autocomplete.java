import java.util.Arrays;
<<<<<<< HEAD
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
=======
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
>>>>>>> f6fa772bee7b18f41c2084714f39c57224b6eb38

public class Autocomplete {
    private final Term[] terms;

<<<<<<< HEAD
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
=======
    // constructor
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new IllegalArgumentException("terms array is null");
        
        // check for nulls
        for (Term t : terms) 
            if (t == null) throw new IllegalArgumentException("null term detected");
        
        // copy
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) this.terms[i] = terms[i];
        
        // sort by query
        Arrays.sort(this.terms);
    }

    // get all matches
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("prefix is null");
        
        int first = firstIdx(prefix);
        int last = lastIdx(prefix);
        
        if (first == -1) return new Term[0];
        
        // collect matches
        Term[] matches = new Term[last - first + 1];
        for (int i = 0; i < matches.length; i++) matches[i] = terms[first + i];
        
        // sort by weight
>>>>>>> f6fa772bee7b18f41c2084714f39c57224b6eb38
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

<<<<<<< HEAD
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
=======
    // count matches
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("prefix is null");
        
        int first = firstIdx(prefix);
        int last = lastIdx(prefix);
        
        return (first == -1) ? 0 : last - first + 1;
    }
    
    // binary search helpers
    private int firstIdx(String prefix) {
        Term dummy = new Term(prefix, 0);
        Comparator<Term> comp = Term.byPrefixOrder(prefix.length());
        return BinarySearchDeluxe.firstIndexOf(terms, dummy, comp);
    }
    
    private int lastIdx(String prefix) {
        Term dummy = new Term(prefix, 0);
        Comparator<Term> comp = Term.byPrefixOrder(prefix.length());
        return BinarySearchDeluxe.lastIndexOf(terms, dummy, comp);
    }

    // test client - using standard Java I/O only
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Autocomplete <filename> <k>");
            return;
        }
        
        try {
            String filename = args[0];
            Scanner fileScanner = new Scanner(new File(filename), "UTF-8");
            
            int n = fileScanner.nextInt();
            Term[] terms = new Term[n];
            
            for (int i = 0; i < n; i++) {
                long weight = fileScanner.nextLong();
                // Read the rest of the line (includes tab and query)
                String line = fileScanner.nextLine();
                // Remove leading tab if present
                if (line.startsWith("\t")) {
                    line = line.substring(1);
                }
                terms[i] = new Term(line, weight);
            }
            fileScanner.close();

            int k = Integer.parseInt(args[1]);
            Autocomplete ac = new Autocomplete(terms);
            
            System.out.println("Enter prefixes (Ctrl+Z to exit on Windows, Ctrl+D on Mac/Linux):");
            Scanner consoleScanner = new Scanner(System.in, "UTF-8");
            
            while (consoleScanner.hasNextLine()) {
                String prefix = consoleScanner.nextLine();
                Term[] results = ac.allMatches(prefix);
                System.out.printf("%d matches\n", ac.numberOfMatches(prefix));
                for (int i = 0; i < Math.min(k, results.length); i++)
                    System.out.println(results[i]);
                System.out.println();
            }
            consoleScanner.close();
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + args[0]);
        }
    }
}
>>>>>>> f6fa772bee7b18f41c2084714f39c57224b6eb38
