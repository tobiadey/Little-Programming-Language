package lpl.ast;

import lpl.ast.util.Visitor;

public class ExpOp extends Exp {

    public final PrimaryExp e1, e2;
    public final Op op;

    public ExpOp(PrimaryExp e1, Op op, PrimaryExp e2) {
        this.e1 = e1;
        this.op = op;
        this.e2 = e2;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
