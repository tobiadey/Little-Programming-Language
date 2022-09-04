package lpl.ast;

import lpl.ast.util.Visitor;

public class PrimaryExpFalse extends PrimaryExp {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
