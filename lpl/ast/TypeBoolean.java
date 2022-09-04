package lpl.ast;

import lpl.ast.util.Visitor;

public class TypeBoolean extends Type {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean equals(Object obj) {
        return (obj instanceof TypeBoolean);
    }

    public int hashCode() {
        return 100;
    }
    
    public String toString() {
        return "bool";
    }
}
