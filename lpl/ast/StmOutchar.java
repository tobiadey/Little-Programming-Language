package lpl.ast;

import lpl.ast.util.Visitor;

public class StmOutchar extends Stm {

    public final Exp e;

    public StmOutchar(Exp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
