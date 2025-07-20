package com.shaurya.visitors;

import com.shaurya.nodes.*;

public class PrintVisitor implements Visitor<String>{

    @Override
    public String visit(AssignNode n) {
        return "";
    }

    @Override
    public String visit(DeclarationNode n) {
        return "";
    }

    @Override
    public String visit(ExpressionNode n) {
        return "";
    }

    @Override
    public String visit(MiddleNode n) {
        return "";
    }

    @Override
    public String visit(MinusNode n) {
        return "";
    }

    @Override
    public String visit(MultiplicationNode n) {
        return "";
    }

    @Override
    public String visit(PlusNode n) {
        return "";
    }

    @Override
    public String visit(PrintNode n) {
        return "";
    }
}
