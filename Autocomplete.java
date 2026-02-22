import java.util.Arrays;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Autocomplete {
    private final Term[] terms;

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
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

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