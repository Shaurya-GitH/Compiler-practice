package com.shaurya.nodes;

import com.shaurya.enums.NodeType;
import com.shaurya.visitors.Visitor;

public interface Node {
    NodeType type();
    void accept(Visitor v);
}
