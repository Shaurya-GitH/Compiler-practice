package com.shaurya.nodes;

import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ExpressionNode implements Node{
    MiddleNode expression1;
    Node expression2;

    @Override
    public NodeType type(){
        return NodeType.EXPRESSION;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this) ;
    }

}
