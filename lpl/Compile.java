package lpl;


import lpl.ast.Program;
import lpl.staticanalysis.TypeChecker;
import lpl.staticanalysis.StaticAnalysisException;

import cloptions.CLOptions;
import lpl.parser.TokenMgrError;
import lpl.parser.ParseException;
import lpl.parser.LPLParser;
import static cloptions.CLOptions.basePath;
import lpl.ast.util.PrettyPrinter;
import lpl.compiler.Compiler;
import ir.ast.util.IRPrettyPrinter;
import ir.ast.IRProgram;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A harness to run the compiler.
 */
public class Compile {

    private static boolean
            source = false,
            type = true,
            quiet = false;
    
    public static void main(String[] args) throws IOException {
        compile(args);
    }

    /**
     * Compile LPL source code to IR code.
     * <p>
     * Command line arguments: LPL source file name
     * <p>
     * Options: <ul>
     * <li> -notype (disable type checking)
     * <li> -source (pretty-print parsed input)
     * <li> -quiet (suppress progress messages)
     * </ul>
     *
     * @param args command line arguments
     */
    public static void compile(String[] args) throws IOException {
        Compiler compiler = new Compiler();
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        Set<String> options = CLOptions.options(argList, "notype", "source", "quiet").keySet();
        type = !options.contains("notype");
        source = options.contains("source");
        quiet = options.contains("quiet");
        Program root;
        try {
            String inputFileName = argList.get(0);
            report("Parsing...");
            System.out.flush();
            // Read program to be parsed from file
            try {
                root = new LPLParser(new java.io.FileInputStream(inputFileName), "UTF-8").nt_Program();
            } catch (java.io.FileNotFoundException e) {
                System.err.println("Unable to read file " + inputFileName);
                return;
            }
            reportln("...parsed OK.");
            if (type) {
                TypeChecker typeChecker = new TypeChecker();
                report("Type checking...");
                System.out.flush();
                root.accept(typeChecker);
                reportln("...type checked OK.");
            }
            if (source) {
                root.accept(new PrettyPrinter());
            }
            
            report("Compiling...");
            IRProgram p = compiler.compile(root);
            String outputFileName = "out";
            if (!"-".equals(inputFileName)) {
                outputFileName = basePath(inputFileName);
            }
            File irFile = new File(outputFileName + ".ir");
            reportln("Writing IR code to " + irFile.getPath());
            output(irFile, p);
        } catch (ParseException | TokenMgrError e) {
            System.out.println("\nSyntax error: " + e.getMessage());
        } catch (StaticAnalysisException e) {
            System.out.println("\nStatic semantics error: " + e.getMessage());
        }
    }

    private static void output(File f, IRProgram p) {
        try (PrintStream out = new PrintStream(new FileOutputStream(f), false, "UTF-8")) {
            IRPrettyPrinter.prettyPrint(out, p);
        } catch (IOException e) {
            System.err.println(e);
            throw new Error("Errror writing to file: " + f.getName());
        }
    }

    private static void report(String msg) {
        if (!quiet) {
            System.out.print(msg);
            System.out.flush();
        }
    }

    private static void reportln(String msg) {
        report(msg + "\n");
    }
}
