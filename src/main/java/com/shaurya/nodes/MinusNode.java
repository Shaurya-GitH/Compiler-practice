package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinusNode implements Node{
    Token minus;
   MiddleNode expression1;
   Node expression2;

    @Override
    public NodeType type(){
        return NodeType.MINUS;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this) ;
    }
}
