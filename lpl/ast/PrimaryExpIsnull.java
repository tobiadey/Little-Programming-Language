package lpl.ast;

import lpl.ast.util.Visitor;

public class PrimaryExpIsnull extends PrimaryExp {

    public final PrimaryExp e;

    public PrimaryExpIsnull(PrimaryExp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
