package lpl.ast;

import lpl.ast.util.Visitor;

public class PrimaryExpNewArray extends PrimaryExp {

    public final Type t;
    public final Exp e;

    public PrimaryExpNewArray(Type t, Exp e) {
        this.t = t;
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
