package lpl.staticanalysis;

import java.util.ArrayList;
import java.util.List;
import lpl.ast.Type;

/**
 * LPL function signatures.
 */
public class FunSignature {

    public final String funName;
    public final Type returnType;
    private final List<Type> paramTypes;

    public FunSignature(String funName, Type returnType, List<Type> paramTypes) {
        this.funName = funName;
        this.returnType = returnType;
        this.paramTypes = new ArrayList<>(paramTypes);
    }

    /**
     * The name of this function.
     *
     * @return the function name
     */
    public String getName() {
        return funName;
    }

    /**
     * The return type of this function.
     *
     * @return the return type
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * The type of a given parameter.
     *
     * @param i the index of the parameter in this function's parameter list
     * (indexes start at zero)
     * @return the type for the specified parameter
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Type getParamType(int i) {
        return paramTypes.get(i);
    }

    /**
     * The arity (number of parameters) of this function.
     *
     * @return the arity of this function
     */
    public int getArity() {
        return paramTypes.size();
    }
}
