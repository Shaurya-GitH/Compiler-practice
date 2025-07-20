package com.shaurya.visitors;

import com.shaurya.nodes.*;

public interface Visitor<T> {
    T visit(AssignNode n);
    T visit(DeclarationNode n);
    T visit(ExpressionNode n);
    T visit(MiddleNode n);
    T visit(MinusNode n);
    T visit(MultiplicationNode n);
    T visit(PlusNode n);
    T visit(PrintNode n);
}
