package lpl.ast.util;

import lpl.ast.*;

/** Implements Visitor with trivial methods (all throw an error). */
public class VisitorAdapter<T> implements Visitor<T>  {

    @Override
    public T visit(Program n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(FunDef n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(VarDecl n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(Formal n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(TypeUnit n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(TypeBoolean n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(TypeInt n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(TypeArray n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmBlock n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmAssign n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmArrayAssign n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmIf n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmWhile n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmOutput n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmOutchar n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmReturn n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(StmCall n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }
    
    @Override
    public T visit(ExpOp n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(ExpArrayLookup n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(ExpArrayLength n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(ExpCall n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(ExpPrimaryExp n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpInteger n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpTrue n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpFalse n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpVar n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpNewArray n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpNot n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpIsnull n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }

    @Override
    public T visit(PrimaryExpExp n) {
        throw new Error("visitor called on unexpected AST node type: " + n);
    }
}

