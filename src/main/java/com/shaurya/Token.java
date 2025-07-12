package com.shaurya;

import com.shaurya.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    TokenType type;
    String lexeme;
    int start;
    int end;
}
