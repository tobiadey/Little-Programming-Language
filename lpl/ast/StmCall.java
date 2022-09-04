package lpl.ast;

import java.util.List;
import java.util.Collections;
import lpl.ast.util.Visitor;

public class StmCall extends Stm {

    public final String id;
    public final List<Exp> es;

    public StmCall(String id, List<Exp> es) {
        this.id = id;
        this.es = Collections.unmodifiableList(es);
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
