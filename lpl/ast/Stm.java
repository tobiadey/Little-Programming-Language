package lpl.ast;

import lpl.ast.util.Visitor;

public abstract class Stm {

    public abstract <T> T accept(Visitor<T> v);
}
