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
    Node expression;

    @Override
    public NodeType type(){
        return NodeType.DECLARATION;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this) ;
    }
}
