package com.shaurya.visitors;

import com.shaurya.Parser;
import com.shaurya.nodes.*;

import java.util.List;

public class PrintVisitor implements Visitor<String>{

    public void print(Parser parser){
        StringBuilder builder=new StringBuilder();
        List<Node> ast= parser.getAst() ;
        for(Node n:ast){
           builder.append(n.accept(this));
        }
        System.out.println(builder.toString());
    }

    @Override
    public String visit(AssignNode n) {
        String expression= n.getExpression().accept(this);
        return "( "+n.getId().getLexeme()+" = "+expression+" )";
    }

    @Override
    public String visit(DeclarationNode n) {
        String expression= n.getExpression().accept(this);
        return "( int "+n.getId().getLexeme()+" = "+expression+" )";
    }

    @Override
    public String visit(BinaryOpNode n) {
        String left= n.getLeft().accept(this);
        String right= n.getRight().accept(this);
        return "( "+left+" "+n.getOperator().getLexeme()+" "+right+" )";
    }

    @Override
    public String visit(NumberNode n) {
        return n.getId().getLexeme();
    }

    @Override
    public String visit(PrintNode n) {
        return "( print "+ n.getId().getLexeme()+" )";
    }
}
