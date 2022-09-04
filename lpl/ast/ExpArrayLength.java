package lpl.ast;

import lpl.ast.util.Visitor;

public class ExpArrayLength extends Exp {

    public final PrimaryExp e;

    public ExpArrayLength(PrimaryExp e) {
        this.e = e;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
