package lpl.ast.util;

import lpl.ast.*;
import java.io.PrintStream;
import java.util.List;

public class PrettyPrinter implements Visitor<Void> {

    private static final String INDENT = "  ";
    
    private int indent;
    private PrintStream ps;

    /**
     * Initialise a new pretty printer.
     */
    public PrettyPrinter() {
        this(System.out);
    }

    /**
     * Initialise a new pretty printer.
     */
    public PrettyPrinter(PrintStream ps) {
        this.ps = ps;
        indent = 0;
    }

    /** Start a new line of output. */
    private void newline() {
        ps.println();
    }

    /** Print a string prefixed by current indent whitespace. */
    private void iprint(String s) {
        indent();
        ps.print(s);
    }

    /** Print a string prefixed by current indent whitespace, followed by a newline. */
    private void iprintln(String s) {
        iprint(s);
        newline();
    }

    /** Print a string. */
    private void print(String s) {
        ps.print(s);
    }

    /** Print a string, followed by a newline. */
    private void println(String s) {
        ps.println(s);
    }

    /** Print current indent of whitespace. */
    private void indent() {
        for (int i = 0; i < indent; i++) {
            ps.print(INDENT);
        }
    }
    
    private void prettyPrintActuals(List<Exp> actuals) {
        print("(");
        for (int i = 0; i < actuals.size(); i++) {
            actuals.get(i).accept(this);
            if (i + 1 < actuals.size()) {
                print(", ");
            }
        }
        print(")");
    }
    
    private void prettyPrintMethodCall(String id, List<Exp> actuals) {
        print(id);
        prettyPrintActuals(actuals);
    }

    @Override
    public Void visit(Program n) {
        n.main.accept(this);
        for (FunDef md : n.fds) {
            newline();
            md.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(FunDef n) {
        iprint("def ");
        n.t.accept(this);
        print(" " + n.id + "(");
        for (int i = 0; i < n.fs.size(); i++) {
            Formal f = n.fs.get(i);
            f.accept(this);
            if (i + 1 < n.fs.size()) {
                print(", ");
            }
        }
        println(") {");
        indent++;
        for (VarDecl lv : n.lvs) {
            lv.accept(this);
        }
        for (Stm s : n.ss) {
            s.accept(this);
        }
        indent--;
        iprintln("}");
        return null;
    }

    @Override
    public Void visit(TypeArray n) {
        n.t.accept(this);
        print("[]");
        return null;
    }

    @Override
    public Void visit(TypeBoolean n) {
        print("bool");
        return null;
    }

    @Override
    public Void visit(TypeInt n) {
        print("int");
        return null;
    }

    @Override
    public Void visit(TypeUnit n) {
        print("unit");
        return null;
    }

    private void provisionalIndent(Stm s) {
        if (s instanceof StmBlock) {
            s.accept(this);
        } else {
            indent++;
            s.accept(this);
            indent--;
        }
    }

    @Override
    public Void visit(StmBlock n) {
        iprintln("{");
        indent++;
        for (Stm s : n.ss) {
            s.accept(this);
        }
        indent--;
        iprintln("}");
        return null;
    }
    
    @Override
    public Void visit(VarDecl n) {
        iprint("");
        n.t.accept(this);
        println(" " + n.id + ";");
        return null;
    }

    @Override
    public Void visit(Formal n) {
        n.t.accept(this);
        print(" " + n.id);
        return null;
    }

    @Override
    public Void visit(StmIf n) {
        iprint("if (");
        n.e.accept(this);
        println(") ");
        provisionalIndent(n.st);
        iprintln("else");
        provisionalIndent(n.sf);
        return null;
    }

    @Override
    public Void visit(StmWhile n) {
        iprint("while (");
        n.e.accept(this);
        println(") ");
        provisionalIndent(n.body);
        return null;
    }

    @Override
    public Void visit(StmOutput n) {
        iprint("output ");
        n.e.accept(this);
        println(";");
        return null;
    }

    @Override
    public Void visit(StmOutchar n) {
        iprint("outchar ");
        n.e.accept(this);
        println(";");
        return null;
    }

    @Override
    public Void visit(StmReturn n) {
        iprint("return ");
        n.e.accept(this);
        println(";");
        return null;
    }

    @Override
    public Void visit(StmAssign n) {
        iprint(n.id + " = ");
        n.e.accept(this);
        println(";");
        return null;
    }

    @Override
    public Void visit(StmArrayAssign n) {
        iprint("");
        n.e1.accept(this);
        print("[");
        n.e2.accept(this);
        print("] = ");
        n.e3.accept(this);
        println(";");
        return null;
    }

    @Override
    public Void visit(StmCall n) {
        iprint("");
        prettyPrintMethodCall(n.id, n.es);
        println(";");
        return null;
    }

    @Override
    public Void visit(ExpCall n) {
        prettyPrintMethodCall(n.id, n.es);
        return null;
    }

    @Override
    public Void visit(PrimaryExpInteger n) {
        print("" + n.i);
        return null;
    }

    @Override
    public Void visit(PrimaryExpTrue n) {
        print("true");
        return null;
    }

    @Override
    public Void visit(PrimaryExpFalse n) {
        print("false");
        return null;
    }

    @Override
    public Void visit(PrimaryExpVar n) {
        print(n.id);
        return null;
    }

    @Override
    public Void visit(PrimaryExpNot n) {
        print("!");
        n.e.accept(this);
        return null;
    }

    @Override
    public Void visit(ExpOp n) {
        n.e1.accept(this);
        print(" " + n.op + " ");
        n.e2.accept(this);
        return null;
    }

    @Override
    public Void visit(ExpArrayLookup n) {
        n.e1.accept(this);
        print("[");
        n.e2.accept(this);
        print("]");
        return null;
    }

    @Override
    public Void visit(ExpArrayLength n) {
        n.e.accept(this);
        print(".length");
        return null;
    }

    @Override
    public Void visit(PrimaryExpNewArray n) {
        print("new ");
        n.t.accept(this);
        print("[");
        n.e.accept(this);
        print("]");
        return null;
    }

    @Override
    public Void visit(PrimaryExpIsnull n) {
        print("isnull ");
        n.e.accept(this);
        return null;
    }

    @Override
    public Void visit(ExpPrimaryExp n) {
        n.e.accept(this);
        return null;
    }

    @Override
    public Void visit(PrimaryExpExp n) {
        print("(");
        n.e.accept(this);
        print(")");
        return null;
    }
}
