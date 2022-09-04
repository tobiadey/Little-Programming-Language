package lpl.ast;

import lpl.ast.util.Visitor;

public class PrimaryExpExp extends PrimaryExp {

    public final Exp e;

    public PrimaryExpExp(Exp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
