package lpl.staticanalysis;

import lpl.ast.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Symbol tables containing function signatures.
 */
public class SymbolTable {
    
    private final Map<String, FunSignature> functionTable;

    /**
     * Initialise a new, empty symbol table.
     */
    public SymbolTable() {
        functionTable = new HashMap<>();
    }

    /**
     * Find a function signature in the symbol table.
     *
     * @param name the function name
     * @return the function signature for the named function, or null if the named
     * function does not exist
     */
    public FunSignature getFunSignature(String name) {
        return functionTable.get(name);
    }

    /**
     * Add a new function signature to the symbol table.
     *
     * @param sig the signature to add
     * @return true if the signature was added, false if a signature with the
     * same name was already present
     */
    public boolean addFunSignature(FunSignature sig) {
        if (functionTable.containsKey(sig.funName)) {
            return false;
        }
        functionTable.put(sig.funName, sig);
        return true;
    }

    /**
     * Add a new function signature to the symbol table.
     *
     * @param name the function name
     * @param type the function return type
     * @param formalTypes the list of parameter types
     * @return true if the signature was added, false if a signature with the
     * given name was already present
     */
    public boolean addFunSignature(String name, Type type, List<Type> formalTypes) {
        if (functionTable.containsKey(name)) {
            return false;
        }
        FunSignature sig = new FunSignature(name, type, formalTypes);
        functionTable.put(name, sig);
        return true;
    }

}
