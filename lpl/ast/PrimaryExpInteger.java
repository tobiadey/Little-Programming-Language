package lpl.ast;

import lpl.ast.util.Visitor;

public class PrimaryExpInteger extends PrimaryExp {

    public final int i;

    public PrimaryExpInteger(int i) {
        this.i = i;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
