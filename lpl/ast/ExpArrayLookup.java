package lpl.ast;

import lpl.ast.util.Visitor;

public class ExpArrayLookup extends Exp {

    public final PrimaryExp e1;
    public final Exp e2;

    public ExpArrayLookup(PrimaryExp e1, Exp e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
