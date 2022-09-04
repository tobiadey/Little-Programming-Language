package lpl.ast;

import java.util.List;
import java.util.Collections;
import lpl.ast.util.Visitor;

public class Program {

    public final FunDef main;
    public final List<FunDef> fds;

    public Program(FunDef main, List<FunDef> fds) {
        this.main = main;
        this.fds = Collections.unmodifiableList(fds);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
