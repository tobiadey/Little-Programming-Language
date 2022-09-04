package lpl.ast;

import lpl.ast.util.Visitor;

public class TypeUnit extends Type {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean equals(Object obj) {
        return (obj instanceof TypeUnit);
    }

    public int hashCode() {
        return 1;
    }
    
    public String toString() {
        return "unit";
    }
}
