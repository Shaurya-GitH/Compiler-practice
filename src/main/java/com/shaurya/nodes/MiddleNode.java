package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MiddleNode implements Node{
    Token id;
    MultiplicationNode expression;

    @Override
    public NodeType type(){
        return NodeType.MIDDLE;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this) ;
    }
}
