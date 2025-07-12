package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlusNode implements Node{
    Token plus;
    MiddleNode expression1;
    Node expression2;

    @Override
    public NodeType type(){
        return NodeType.PLUS;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this) ;
    }
}
