package lpl.ast;

import java.util.Objects;
import lpl.ast.util.Visitor;

public class TypeArray extends Type {

    public final Type t;

    public TypeArray(Type t) {
        this.t = t;
    }

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean equals(Object obj) {
        if (obj instanceof TypeArray) {
            TypeArray other = (TypeArray) obj;
            return other.t.equals(t);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return 1 + t.hashCode();
    }

    public String toString() {
        return t + "[]";
    }
}
