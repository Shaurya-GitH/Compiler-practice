package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BinaryOpNode implements Node{

    Node left;
    Token operator;
    Node right;

    @Override
    public NodeType type() {
        return NodeType.BINARY;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
