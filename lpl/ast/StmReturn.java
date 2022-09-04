package lpl.ast;

import lpl.ast.util.Visitor;

public class StmReturn extends Stm {

    public final Exp e;

    public StmReturn(Exp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
