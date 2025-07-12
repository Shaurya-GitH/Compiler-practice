package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeclarationNode implements Node {
    Token type;
    Token id;
    Token equals;
    ExpressionNode expression;

    @Override
    public NodeType type(){
        return NodeType.DECLARATION;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this) ;
    }
}
