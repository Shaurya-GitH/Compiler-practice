package com.shaurya;

import com.shaurya.enums.SymbolType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SymbolData {
    SymbolType type;
    Object data;
}
