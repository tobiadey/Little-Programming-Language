package lpl.ast;

import lpl.ast.util.Visitor;

public class Formal {

    public final Type t;
    public final String id;

    public Formal(Type t, String id) {
        this.t = t;
        this.id = id;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
