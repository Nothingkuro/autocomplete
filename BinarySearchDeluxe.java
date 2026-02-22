import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        int low = 0;
        int high = a.length - 1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparator.compare(a[mid], key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                result = mid;
                high = mid - 1; // Look for an earlier occurrence
            }
        }
        return result;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        int low = 0;
        int high = a.length - 1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparator.compare(a[mid], key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                result = mid;
                low = mid + 1;  // Look for a later occurrence
            }
        }
        return result;
    }

    // unit testing
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java BinarySearchDeluxe <n>");
            return;
        }
        
        int n = Integer.parseInt(args[0]);
        if (n < 0) n = 0;
        
        Integer[] testArray = new Integer[5 * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 5; j++) {
                testArray[i * 5 + j] = i + 1;
            }
        }
        
        // Pick a uniformly random integer between 0 (inclusive) and n + 1 (exclusive) and call it key.
        int key = (int) (Math.random() * (n + 1));
        
        Comparator<Integer> comparator = Integer::compare;
        
        int firstActual = firstIndexOf(testArray, key, comparator);
        int lastActual = lastIndexOf(testArray, key, comparator);
        
        int firstExpected = -1;
        int lastExpected = -1;
        
        for (int i = 0; i < testArray.length; i++) {
            if (testArray[i] == key) {
                if (firstExpected == -1) {
                    firstExpected = i;
                }
                lastExpected = i;
            }
        }
        
        if (firstActual != firstExpected) {
            System.err.println("firstIndexOf failed for key " + key + ". Expected: " + firstExpected + ", Actual: " + firstActual);
        } else {
            System.out.println("firstIndexOf passed for key " + key + " (index: " + firstActual + ")");
        }
        
        if (lastActual != lastExpected) {
            System.err.println("lastIndexOf failed for key " + key + ". Expected: " + lastExpected + ", Actual: " + lastActual);
        } else {
            System.out.println("lastIndexOf passed for key " + key + " (index: " + lastActual + ")");
        }
    }
}
