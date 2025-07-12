package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MultiplicationNode implements Node {
    Token multiply;
    Token id;
    MultiplicationNode expression;

    @Override
    public NodeType type(){
        return NodeType.MULTI;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this) ;
    }
}
