package com.shaurya.visitors;

import com.shaurya.nodes.*;

import java.util.Map;

public class SemanticVisitor implements Visitor<Void>{

    Map<String,String> symbolTable;

    SemanticVisitor(Map<String,String> symbolTable){
        this.symbolTable=symbolTable;
    }

    @Override
    public Void visit(AssignNode n) {
        return null;
    }

    @Override
    public Void visit(DeclarationNode n) {
        return null;
    }

    @Override
    public Void visit(BinaryOpNode n) {
        return null;
    }

    @Override
    public Void visit(NumberNode n) {
        return null;
    }

    @Override
    public Void visit(PrintNode n) {
        return null;
    }
}
