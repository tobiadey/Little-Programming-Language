package lpl.compiler;

/**
 * Fresh label generator.
 */
public class FreshLabelGenerator {
    
    private static int count = 0;
    
    private FreshLabelGenerator(){}

    /**
     * Generate a new label name. Each call to this method is guaranteed to return a
     * name distinct from all those returned by any previous calls.
     * All fresh names end with a non-empty sequence of digits.
     * @param prefix the name will start with this string
     * @return a new name
     */
    public static String freshLab(String prefix) {
        return prefix + freshLab();
    }

    /**
     * Generate a new label name. Each call to this method is guaranteed to return a
     * name distinct from all those returned by any previous calls.
     * All fresh names end with a non-empty sequence of digits.
     * @return a new name
     */
    public static String freshLab() {
        String name = "@" + count;
        ++count;
        return name;
    }

}
