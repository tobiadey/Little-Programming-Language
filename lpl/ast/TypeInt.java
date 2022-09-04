package lpl.ast;

import lpl.ast.util.Visitor;

public class TypeInt extends Type {

    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

    public boolean equals(Object obj) {
        return (obj instanceof TypeInt);
    }

    public int hashCode() {
        return 200;
    }
    
    public String toString() {
        return "int";
    }
}
