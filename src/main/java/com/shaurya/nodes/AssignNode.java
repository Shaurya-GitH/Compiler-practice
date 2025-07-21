package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AssignNode implements Node{
    Token id;
    Token equals;
    Node expression;

    @Override
    public NodeType type(){
       return NodeType.ASSIGN;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
       return v.visit(this) ;
    }
}
