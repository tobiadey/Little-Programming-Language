package lpl.ast;

import lpl.ast.util.Visitor;

public class PrimaryExpVar extends PrimaryExp {

    public final String id;

    public PrimaryExpVar(String id) {
        this.id = id;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
