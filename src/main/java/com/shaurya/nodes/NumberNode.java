package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NumberNode implements Node{

    Token id;

    @Override
    public NodeType type() {
        return NodeType.NUMBER;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
