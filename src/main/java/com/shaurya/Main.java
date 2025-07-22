package com.shaurya;

import com.shaurya.visitors.EvaluateVisitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length<1){
            System.err.println("No input source provided");
            System.exit(64);
        }
        String path=args[0];
        if(!path.endsWith(".erd")){
            throw new InvalidPropertiesFormatException("Invalid file format") ;
        }
        BufferedReader bufferedReader=new BufferedReader(new FileReader(path));
        String sourceCode=bufferedReader.lines().collect(Collectors.joining("\n"));
        BlockingDeque<Token> tokenStream=new LinkedBlockingDeque<>();
        Lexer lexer=new Lexer(0,sourceCode,tokenStream);
        Thread lexerThread=Thread.ofVirtual().start(lexer);
        Parser parser=new Parser(tokenStream);
        Thread parserThread=Thread.ofVirtual().start(parser);
        lexerThread.join();
        parserThread.join();
        HashMap<String,SymbolData> symbolTable=new HashMap<String,SymbolData>();
        EvaluateVisitor evaluateVisitor =new EvaluateVisitor(symbolTable);
        evaluateVisitor.run(parser) ;
    }
}