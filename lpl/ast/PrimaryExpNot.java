package lpl.ast;

import lpl.ast.util.Visitor;

public class PrimaryExpNot extends PrimaryExp {

    public final PrimaryExp e;

    public PrimaryExpNot(PrimaryExp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
