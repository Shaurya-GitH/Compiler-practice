package com.shaurya.visitors;

import com.shaurya.nodes.*;

public interface Visitor<T> {
    T visit(AssignNode n);
    T visit(DeclarationNode n);
    T visit(BinaryOpNode n);
    T visit(NumberNode n);
    T visit(PrintNode n);
}
