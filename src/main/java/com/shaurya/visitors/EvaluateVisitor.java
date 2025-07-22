package com.shaurya.visitors;

import com.shaurya.Parser;
import com.shaurya.SymbolData;
import com.shaurya.enums.SymbolType;
import com.shaurya.enums.TokenType;
import com.shaurya.nodes.*;

import java.util.List;
import java.util.Map;

public class EvaluateVisitor implements Visitor<String>{

    Map<String,SymbolData> symbolTable;

    public EvaluateVisitor(Map<String,SymbolData> symbolTable){
        this.symbolTable=symbolTable;
    }

    public void run(Parser parser){
        List<Node> ast=parser.getAst();
        for(Node n:ast){
           n.accept(this);
        }
    }

    @Override
    public String visit(AssignNode n) {
        if(symbolTable.containsKey(n.getId().getLexeme())){
            String expression= n.getExpression().accept(this);
            symbolTable.put(n.getId().getLexeme(),new SymbolData(SymbolType.INT,Integer.parseInt(expression)));
            return null;
        }
        else{
            throw new RuntimeException("Variable not initialized");
        }
    }

    @Override
    public String visit(DeclarationNode n) {
        String expression=n.getExpression().accept(this);
        symbolTable.put(n.getId().getLexeme(),new SymbolData(SymbolType.INT,Integer.parseInt(expression)));
        return null;
    }

    @Override
    public String visit(BinaryOpNode n) {
        String left=n.getLeft().accept(this);
        String right=n.getRight().accept(this);
        switch (n.getOperator().getType()){
            case TokenType.PLUS:
                int sum= Integer.parseInt(left)+ Integer.parseInt(right);
                return sum+"";
            case TokenType.MINUS:
                int difference= Integer.parseInt(left)- Integer.parseInt(right);
                return difference+"";
            case TokenType.MULTIPLY:
                int product= Integer.parseInt(left)* Integer.parseInt(right);
                return product+"";
            default:
                break;
        }
        return null;
    }

    @Override
    public String visit(NumberNode n) {
        if(n.getId().getType()== TokenType.IDENTIFIER){
            int value= (int) symbolTable.get(n.getId().getLexeme()).getData();
            return Integer.toString(value);
        }
        else{
            return n.getId().getLexeme();
        }
    }

    @Override
    public String visit(PrintNode n) {
        if(symbolTable.containsKey(n.getId().getLexeme())){
            System.out.println(symbolTable.get(n.getId().getLexeme()).getData()) ;
            return null;
        }
        throw new RuntimeException("Variable not initialized");
    }
}
