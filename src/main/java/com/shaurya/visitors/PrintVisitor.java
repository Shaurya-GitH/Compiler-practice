package com.shaurya.visitors;

import com.shaurya.Parser;
import com.shaurya.nodes.*;

import java.util.List;

public class PrintVisitor implements Visitor<String>{

    public void print(Parser parser){
        StringBuilder builder=new StringBuilder();
        List<Node> ast=parser.getAst();
        for(Node n:ast){
           builder.append(n.accept(this)) ;
        }
        System.out.println(builder.toString()) ;
    }

    @Override
    public String visit(AssignNode n) {
        String exp= n.getExpression().accept(this);
        return "{"+n.getId().getLexeme()+" = "+exp+"}";
    }

    @Override
    public String visit(DeclarationNode n) {
        return "";
    }

    @Override
    public String visit(ExpressionNode n) {
        String middleNode=n.getExpression1().accept(this);
        if(n.getExpression2()!=null){
            String addSub=n.getExpression2().accept(this);
            return "{ "+middleNode+" "+addSub+" }";
        }
        return "{ "+middleNode+" }";
    }

    @Override
    public String visit(MiddleNode n) {
        String id= n.getId().getLexeme();
        if(n.getExpression()!=null){
            String expression= n.getExpression().accept(this);
            return "{ "+id+" "+expression+" }";
        }
        return "{ "+id+" }";
    }

    @Override
    public String visit(MinusNode n) {
        String middleNode=n.getExpression1().accept(this);
        if(n.getExpression2()!=null){
            String addSub=n.getExpression2().accept(this);
            return "{ -"+middleNode+" "+addSub+" }";
        }
        return "{ -"+middleNode+" }";
    }

    @Override
    public String visit(MultiplicationNode n) {
        if(n.getExpression()!=null){
            String expression= n.getExpression().accept(this);
            return "{ * "+n.getId().getLexeme()+" "+expression+" }";
        }
        return "{ * "+n.getId().getLexeme()+" }";
    }

    @Override
    public String visit(PlusNode n) {
        String middleNode=n.getExpression1().accept(this);
        if(n.getExpression2()!=null){
            String addSub=n.getExpression2().accept(this);
            return "{ +"+middleNode+" "+addSub+" }";
        }
        return "{ +"+middleNode+" }";
    }

    @Override
    public String visit(PrintNode n) {
        return "";
    }
}
