package com.shaurya;

import com.shaurya.enums.TokenType;
import com.shaurya.nodes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class Parser implements Runnable {
    LinkedBlockingDeque<Token> tokenStream;
    List<Node> ast=new ArrayList<>();

    Parser(LinkedBlockingDeque<Token> tokenStream){
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
        ExpressionNode expression= expressionParser();
        expect(TokenType.SEMICOLON);
        return new DeclarationNode(token,id,equals,expression);
    }

    ExpressionNode expressionParser() throws InterruptedException{
        MiddleNode expression1= middleNodeParser();
        Node expression2= addSubParser();
        return new ExpressionNode(expression1,expression2);
    }

    PrintNode printParser(Token token) throws InterruptedException {
        Token id=expect(TokenType.IDENTIFIER);
        expect(TokenType.SEMICOLON);
        return new PrintNode(token,id);
    }

    AssignNode idParser(Token token) throws InterruptedException {
        Token equals=expect(TokenType.EQUALS);
        ExpressionNode expression=expressionParser();
        expect(TokenType.SEMICOLON);
        return new AssignNode(token,equals,expression);
    }

    MiddleNode middleNodeParser() throws InterruptedException {
       Token id=expect(TokenType.IDENTIFIER,TokenType.NUMBER) ;
       MultiplicationNode expression=multiplicationParser();
       return new MiddleNode(id,expression);
    }

    MultiplicationNode multiplicationParser() throws InterruptedException {
        Token curr=tokenStream.peek();
        if(curr.getType()==TokenType.SEMICOLON || curr.getType()==TokenType.PLUS || curr.getType()==TokenType.MINUS){
          return null;
        }
        else if(curr.getType()==TokenType.MULTIPLY){
            expect(TokenType.MULTIPLY);
            Token id=expect(TokenType.IDENTIFIER,TokenType.NUMBER);
            MultiplicationNode expression=multiplicationParser();
            return new MultiplicationNode(curr,id,expression);
        }
        else{
            throw new RuntimeException("Invalid Syntax "+curr.getLexeme());
        }
    }

    Node addSubParser() throws InterruptedException{
        Token curr=tokenStream.peek();
        return switch(curr.getType()){
            case TokenType.PLUS -> plusParser(curr);
            case TokenType.MINUS -> minusParser(curr);
            case TokenType.SEMICOLON -> null;
            default -> throw new RuntimeException("Invalid Syntax "+curr.getLexeme());
        };
    }

    PlusNode plusParser(Token token) throws InterruptedException {
        expect(TokenType.PLUS);
        MiddleNode expression1=middleNodeParser();
        Node expression2=addSubParser();
        return new PlusNode(token,expression1,expression2);
    }

    MinusNode minusParser(Token token) throws InterruptedException {
        expect(TokenType.MINUS);
        MiddleNode expression1=middleNodeParser();
        Node expression2=addSubParser();
        return new MinusNode(token,expression1,expression2);
    }

    //helper method
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