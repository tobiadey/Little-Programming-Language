package lpl.ast;

import lpl.ast.util.Visitor;

public class ExpPrimaryExp extends Exp {

    public final PrimaryExp e;

    public ExpPrimaryExp(PrimaryExp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
