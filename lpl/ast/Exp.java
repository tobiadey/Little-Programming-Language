package lpl.ast;

import lpl.ast.util.Visitor;

public abstract class Exp {

    public abstract <T> T accept(Visitor<T> v);
}
