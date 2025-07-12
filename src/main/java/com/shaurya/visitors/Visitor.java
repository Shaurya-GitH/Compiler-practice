package com.shaurya.visitors;

import com.shaurya.nodes.*;

public interface Visitor {
    void visit(AssignNode n);
    void visit(DeclarationNode n);
    void visit(ExpressionNode n);
    void visit(MiddleNode n);
    void visit(MinusNode n);
    void visit(MultiplicationNode n);
    void visit(PlusNode n);
    void visit(PrintNode n);
}
