package lpl.ast.util;

import lpl.ast.*;

public interface Visitor<T> {

    public T visit(Program p);

    public T visit(FunDef fd);

    public T visit(VarDecl vd);

    public T visit(Formal f);

    public T visit(TypeUnit t);

    public T visit(TypeBoolean t);

    public T visit(TypeInt t);

    public T visit(TypeArray t);

    public T visit(StmBlock s);

    public T visit(StmIf s);

    public T visit(StmWhile s);

    public T visit(StmOutput s);

    public T visit(StmOutchar s);

    public T visit(StmReturn s);

    public T visit(StmAssign s);

    public T visit(StmArrayAssign s);

    public T visit(StmCall s);
    
    public T visit(ExpOp e);

    public T visit(ExpArrayLookup e);

    public T visit(ExpArrayLength e);

    public T visit(ExpCall e);
    
    public T visit(ExpPrimaryExp e);

    public T visit(PrimaryExpInteger e);

    public T visit(PrimaryExpTrue e);

    public T visit(PrimaryExpFalse e);

    public T visit(PrimaryExpVar e);

    public T visit(PrimaryExpNewArray e);

    public T visit(PrimaryExpNot e);

    public T visit(PrimaryExpIsnull e);
    
    public T visit(PrimaryExpExp e);
}
