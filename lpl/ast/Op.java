package lpl.ast;

public enum Op {

    AND("&&"), OR("||"), LESSTHAN("<"), EQUALS("=="), DIV("/"), PLUS("+"), MINUS("-"), TIMES("*");

    private final String name;

    private Op(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
