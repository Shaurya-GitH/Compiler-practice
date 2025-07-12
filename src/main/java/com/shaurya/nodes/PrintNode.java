package com.shaurya.nodes;

import com.shaurya.Token;
import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PrintNode implements Node{
    Token print;
    Token id;

    @Override
    public NodeType type(){
        return NodeType.PRINT;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this) ;
    }
}
