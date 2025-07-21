package com.shaurya;

import com.shaurya.enums.TokenType;
import com.shaurya.nodes.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

@Data
public class Parser implements Runnable {
    BlockingDeque<Token> tokenStream;
    List<Node> ast=new ArrayList<>();

    Parser(BlockingDeque<Token> tokenStream){
        this.tokenStream=tokenStream;
    }

    @Override
    public void run(){
       while(true) {
          try {
               Token token=tokenStream.take();
               if(token.getType()== TokenType.END){
                   break;
               }
               Node node=switch(token.getType()){
                   case TokenType.INT -> intParser(token) ;
                   case TokenType.PRINT -> printParser(token);
                   case TokenType.IDENTIFIER -> idParser(token);
                   default-> throw new RuntimeException("Invalid Syntax");
               };
               ast.add(node);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       }
    }

    DeclarationNode intParser(Token token) throws InterruptedException{
        Token id=expect(TokenType.IDENTIFIER);
        Token equals=expect(TokenType.EQUALS);
        Node expression= expressionParser();
        expect(TokenType.SEMICOLON);
        return new DeclarationNode(token,id,equals,expression);
    }

    Node expressionParser() throws InterruptedException{
        Node left= termParser();
        while(true){
            Token curr= tokenStream.peek();
            if(curr.getType()==TokenType.PLUS || curr.getType()==TokenType.MINUS){
                expect(TokenType.PLUS,TokenType.MINUS);
                Node right= termParser();
                left= new BinaryOpNode(left,curr,right) ;
            }
            else break;
        }
        return left;
    }

    PrintNode printParser(Token token) throws InterruptedException {
        Token id=expect(TokenType.IDENTIFIER);
        expect(TokenType.SEMICOLON);
        return new PrintNode(token,id);
    }

    AssignNode idParser(Token token) throws InterruptedException {
        Token equals=expect(TokenType.EQUALS);
        Node expression=expressionParser();
        expect(TokenType.SEMICOLON);
        return new AssignNode(token,equals,expression);
    }

    Node termParser() throws InterruptedException {
       Token id=expect(TokenType.IDENTIFIER,TokenType.NUMBER) ;
       Node left=new NumberNode(id);
       while(true){
           Token curr= tokenStream.peek();
           if(curr.getType()==TokenType.MULTIPLY){
               expect(TokenType.MULTIPLY);
               Node right=new NumberNode(expect(TokenType.IDENTIFIER,TokenType.NUMBER));
               left=new BinaryOpNode(left,curr,right);
           }
           else break;
       }
       return left;
    }

    //helper methods
    Token expect(TokenType type) throws InterruptedException{
        Token token= tokenStream.take();
        if(token.getType()!=type){
            throw new RuntimeException("Invalid Syntax "+token.getLexeme());
        }
        return token;
    }

    Token expect(TokenType type1,TokenType type2) throws InterruptedException{
        Token token= tokenStream.take();
        if(token.getType()!=type1 && token.getType()!=type2){
            throw new RuntimeException("Invalid Syntax "+token.getLexeme()+" "+token.getType());
        }
        return token;
    }
}