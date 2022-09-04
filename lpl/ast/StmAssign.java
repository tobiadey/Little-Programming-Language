package lpl.ast;

import lpl.ast.util.Visitor;

public class StmAssign extends Stm {

    public final String id;
    public final Exp e;

    public StmAssign(String id, Exp e) {
        this.id = id;
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
