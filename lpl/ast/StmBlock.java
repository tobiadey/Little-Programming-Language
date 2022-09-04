package lpl.ast;

import java.util.List;
import java.util.Collections;
import lpl.ast.util.Visitor;

public class StmBlock extends Stm {

    public final List<Stm> ss;

    public StmBlock(List<Stm> ss) {
        this.ss = Collections.unmodifiableList(ss);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
