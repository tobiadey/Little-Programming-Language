package lpl.staticanalysis;

import java.util.ArrayList;
import lpl.ast.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lpl.ast.util.VisitorAdapter;

/**
 * Visitors for type checking LPL programs.
 */
public class TypeChecker extends VisitorAdapter<Type> {

    private static final Type TYPE_BOOLEAN = new TypeBoolean();
    private static final Type TYPE_INT = new TypeInt();

    /**
     * A symbol table of function signatures.
     */
    private SymbolTable symbolTable;

    /**
     * A table mapping the local variables/parameters currently in scope to
     * their types. Note: for LPL we don't need a stack since LPL does not
     * support nested declaration of local variables within blocks; all local
     * variables are declared at the start of a function declaration and are at
     * the same scope level as the formal parameters.
     */
    private Map<String, Type> locals;

    /**
     * The name of the function currently being checked.
     */
    private String functionName;
    
    /**
     * The return type of the function currently being checked.
     */
    private Type returnType;

    /**********************/
    /*** Helper methods ***/
    /**********************/
    
    /**
     * The list of formal parameter types in a function definition.
     * @param fd the function definition
     * @return the list of formal parameter types
     */
    private static List<Type> parameterTypes(FunDef fd) {
        List<Type> types = new ArrayList<>();
        for (Formal f: fd.fs) types.add(f.t);
        return types;
    }
    
    /**
     * Build a symbol table of all the functions defined in an LPL program.
     * @param p the AST of the program
     * @return a symbol table of all the functions defined in the program
     */
    public static SymbolTable buildSymbolTable(Program p) {
        SymbolTable st = new SymbolTable();
        st.addFunSignature(p.main.id, p.main.t, parameterTypes(p.main));
        for (FunDef fd: p.fds) {
            st.addFunSignature(fd.id, fd.t, parameterTypes(fd));
        }
        return st;
    }

    /**
     * Add an entry to the table of local variables/parameters currently in
     * scope.
     *
     * @param name the name of the local variable/parameter
     * @param type the type
     *
     * @throws TypeCheckingException if a local variable/parameter entry with
     * the same name already exists in the current scope
     */
    private void addLocal(String name, Type type) {
        if (locals.containsKey(name)) {
            throw new TypeCheckingException("Double declaration of local var/param: " + name);
        }
        locals.put(name, type);
    }

    /**
     * Lookup the type for a variable.
     *
     * @param name the variable name
     * @return the type of the variable
     * @throws TypeCheckingException if the variable is not in scope
     */
    private Type getTypeForVar(String name) {
        Type t;
        t = locals.get(name);
        if (t != null) {
            return t;
        }
        throw new TypeCheckingException("No declaration found for variable: " + name);
    }

    private Type checkCall(String mname, List<Exp> actuals) {
        String msgName = mname;

        // check that named function exists in symbol table
        FunSignature calledFunction = symbolTable.getFunSignature(mname);
        if (calledFunction == null) {
            throw new TypeCheckingException("Function " + msgName + " is not defined");
        }

        // check that arities match
        int arity = calledFunction.getArity();
        if (arity != actuals.size()) {
            throw new TypeCheckingException("Function " + msgName
                    + " called with wrong number of parameters"
                    + " (" + actuals.size() + " instead of " + arity + ")");
        }

        // check actual parameter types against formal parameter types
        for (int i = 0; i < arity; i++) {
            Type t1 = calledFunction.getParamType(i);
            Type t2 = actuals.get(i).accept(this);
            if (!t1.equals(t2)) {
                throw new TypeCheckingException(ordinalString(i + 1) + " actual parameter has wrong type for "
                        + msgName);
            }
        }

        // if we get this far, it is a valid function call, return the callee's return type
        return calledFunction.getReturnType();
    }

    private String ordinalString(int i) {
        switch (i) {
            case 11:
            case 12:
            case 13:
                return i + "th";
            default:
                switch (i % 10) {
                    case 1:
                        return i + "st";
                    case 2:
                        return i + "nd";
                    case 3:
                        return i + "rd";
                    default:
                        return i + "th";
                }
        }
    }

    /*=================*/
    /* Visitor methods */
    /*=================*/
    
    @Override
    public Type visit(Program p) {
        symbolTable = buildSymbolTable(p);
        locals = new HashMap<>();
        p.main.accept(this);
        for (FunDef md : p.fds) {
            md.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(FunDef fd) {
        // set the current function name and return type
        functionName = fd.id;
        returnType = fd.t;
        // clear any junk from the locals map (only the locals of *this* function are in scope)
        locals.clear();
        // add type mappings for all the locals
        for (Formal f : fd.fs) {
            addLocal(f.id, f.t);
        }
        for (VarDecl lv : fd.lvs) {
            addLocal(lv.id, lv.t);
        }
        // check the body statements
        for (Stm s : fd.ss) {
            s.accept(this);
        }
        return null;
    }

    /*======================================*/
    /* Statement visitors (all return null) */
    /*======================================*/
    
    @Override
    public Type visit(StmReturn s) {
        if (!returnType.equals(s.e.accept(this))) {
            throw new TypeCheckingException("Return expression has wrong type for function " + functionName);
        }
        return null;
    }

    @Override
    public Type visit(StmIf s) {
        if (!(s.e.accept(this).equals(TYPE_BOOLEAN))) {
            throw new TypeCheckingException("The condition of if must be of type boolean");
        }
        s.st.accept(this);
        s.sf.accept(this);
        return null;
    }

    @Override
    public Type visit(StmWhile s) {
        if (!(s.e.accept(this).equals(TYPE_BOOLEAN))) {
            throw new TypeCheckingException("The condition of while must be of type boolean");
        }
        s.body.accept(this);
        return null;
    }

    @Override
    public Type visit(StmOutput s) {
        if (!(s.e.accept(this).equals(TYPE_INT))) {
            throw new TypeCheckingException("The argument of output must be of type int");
        }
        return null;
    }

    @Override
    public Type visit(StmOutchar s) {
        if (!(s.e.accept(this).equals(TYPE_INT))) {
            throw new TypeCheckingException("The argument of outchar must be of type int");
        }
        return null;
    }

    @Override
    public Type visit(StmAssign s) {
        Type t1 = getTypeForVar(s.id);
        Type t2 = s.e.accept(this);
        if (!t1.equals(t2)) {
            throw new TypeCheckingException("Type error in assignment to " + s.id);
        }
        return null;
    }

    @Override
    public Type visit(StmArrayAssign s) {
        Type t1 = s.e1.accept(this);
        Type t2 = s.e2.accept(this);
        Type t3 = s.e3.accept(this);
        if (t1 instanceof TypeArray) {
            Type elementType = ((TypeArray) t1).t;
            if (!(t2 instanceof TypeInt)) {
                throw new TypeCheckingException("Type error in array assignment: index is not an int");
            }
            if (!elementType.equals(t3)) {
                throw new TypeCheckingException("Type error in array assignment: RHS type does not match array element type");
            }
        } else {
            throw new TypeCheckingException("Type error in array assignment: not an array");
        }
        return null;
    }

    @Override
    public Type visit(StmBlock s) {
        for (Stm innerS : s.ss) {
            innerS.accept(this);
        }
        return null;
    }

    @Override
    public Type visit(StmCall s) {
        checkCall(s.id, s.es);
        return null;
    }

    /*=========================================*/
    /* Expression visitors (all return a Type) */
    /*=========================================*/
    
    @Override
    public Type visit(ExpOp e) {
        Type resType;
        switch (e.op) {
            case DIV:
            case PLUS:
            case MINUS:
            case TIMES:
                resType = TYPE_INT;
                break;
            case AND:
            case LESSTHAN:
            case EQUALS:
                resType = TYPE_BOOLEAN;
                break;
            default:
                throw new Error("Unknown operator: " + e.op);
        }
        String errmsg = null;
        switch (e.op) {
            case DIV:
            case PLUS:
            case MINUS:
            case TIMES:
            case LESSTHAN:
                if (!(e.e1.accept(this).equals(TYPE_INT))) {
                    errmsg = "First argument of " + e.op + " must be of type int";
                } else if (!(e.e2.accept(this).equals(TYPE_INT))) {
                    errmsg = "Second argument of " + e.op + " must be of type int";
                }
                break;
            case AND:
                if (!(e.e1.accept(this).equals(TYPE_BOOLEAN))) {
                    errmsg = "First argument of " + e.op + " must be of type boolean";
                } else if (!(e.e2.accept(this).equals(TYPE_BOOLEAN))) {
                    errmsg = "Second argument of " + e.op + " must be of type boolean";
                }
                break;
            case EQUALS:
                Type t1 = e.e1.accept(this);
                Type t2 = e.e2.accept(this);
                if (!t1.equals(t2)) {
                    errmsg = "Arguments to == must have the same type";
                }
                break;
            default:
                throw new Error("unknown operator: " + e.op);
        }
        if (errmsg == null) {
            return resType;
        } else {
            throw new TypeCheckingException(errmsg);
        }
    }

    @Override
    public Type visit(ExpCall e) {
        return checkCall(e.id, e.es);
    }

    @Override
    public Type visit(ExpPrimaryExp e) {
        return e.e.accept(this);
    }

    @Override
    public Type visit(PrimaryExpInteger e) {
        return TYPE_INT;
    }

    @Override
    public Type visit(PrimaryExpTrue e) {
        return TYPE_BOOLEAN;
    }

    @Override
    public Type visit(PrimaryExpFalse e) {
        return TYPE_BOOLEAN;
    }

    @Override
    public Type visit(PrimaryExpVar e) {
        return getTypeForVar(e.id);
    }

    @Override
    public Type visit(PrimaryExpNot e) {
        if (!(e.e.accept(this).equals(TYPE_BOOLEAN))) {
            throw new TypeCheckingException("Negated expression must be of type boolean");
        }
        return TYPE_BOOLEAN;
    }

    @Override
    public Type visit(ExpArrayLookup e) {
        Type t1 = e.e1.accept(this);
        Type t2 = e.e2.accept(this);
        if (t1 instanceof TypeArray) {
            Type elementType = ((TypeArray) t1).t;
            if (!(t2 instanceof TypeInt)) {
                throw new TypeCheckingException("Type error in array lookup: index is not an int");
            }
            return elementType;
        } else {
            throw new TypeCheckingException("Type error in array lookup: not an array");
        }
    }

    @Override
    public Type visit(ExpArrayLength e) {
        Type t = e.e.accept(this);
        if (t instanceof TypeArray) {
            return TYPE_INT;
        } else {
            throw new TypeCheckingException("Type error in array length: not an array");
        }
    }

    @Override
    public Type visit(PrimaryExpNewArray e) {
        Type sizeType = e.e.accept(this);
        if (!(sizeType instanceof TypeInt)) {
            throw new TypeCheckingException("Type error in array creation: size is not an int");
        }
        return new TypeArray(e.t);
    }

    @Override
    public Type visit(PrimaryExpIsnull e) {
        Type t = e.e.accept(this);
        if (t instanceof TypeArray) {
            return TYPE_BOOLEAN;
        } else {
            throw new TypeCheckingException("Type error in null test: not an array");
        }
    }

    @Override
    public Type visit(PrimaryExpExp e) {
        return e.e.accept(this);
    }
}
