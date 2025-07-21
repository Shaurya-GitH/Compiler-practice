package com.shaurya;

import com.shaurya.enums.State;
import com.shaurya.enums.TokenType;
import lombok.Data;

import java.util.concurrent.BlockingDeque;

@Data
public class Lexer implements Runnable {
    private int position;
    private String input;
    private BlockingDeque<Token> tokenStream;

    Lexer(int position,String input,BlockingDeque<Token> tokenStream){
        this.position=position;
       this.input=input;
       this.tokenStream=tokenStream;
    }

    //DFA
    @Override
    public void run(){
        State state= State.START;
        while(position<input.length()){
            try{
                state=switch(state) {
                    case START->startState();
                    case NEXTSTART->nextStartState();
                    case IDENTIFIER-> identifierState();
                    case DIGIT-> digitState();
                    case OPERATORS-> operatorState();
                };
            }
            catch(InterruptedException e){
               System.err.println(e.getMessage()) ;
            }
        }
        try {
            tokenStream.put(new Token(TokenType.END,"end",position,position));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    State startState(){
       char curr=input.charAt(position) ;
       if(Character.isAlphabetic(curr)){
           return State.IDENTIFIER;
       }
       else if(Character.isDigit(curr)){
           return State.DIGIT;
       }
       else if(Character.isWhitespace(curr)){
           return State.NEXTSTART;
       }
       else{
           return State.OPERATORS;
       }
    }

    State identifierState() throws InterruptedException {
        Token token=new Token();
        token.setStart(position);
        while(position<input.length()){
            char curr=input.charAt(position);
            if(Character.isDigit(curr)){
                throw new RuntimeException("Invalid syntax");
            }
            else if(!Character.isAlphabetic(curr)){
               break;
            }
            position++;
        }
        token.setEnd(position-1);
       token.setLexeme(input.substring(token.getStart(),position)) ;
       switch(token.getLexeme()){
           case "int":{
               token.setType(TokenType.INT);
               break;
           }
           case "run":{
               token.setType(TokenType.PRINT);
               break;
           }
           default:{
               token.setType(TokenType.IDENTIFIER);
           }
       }
       tokenStream.put(token);
       return State.NEXTSTART;
    }

    State nextStartState(){
        while(position<input.length()){
            char curr=input.charAt(position);
            if(curr!='\n' && !Character.isWhitespace(curr)){
                return State.START;
            }
            position++;
        }
        return State.START;
    }

    State digitState() throws InterruptedException {
        Token token=new Token();
        token.setStart(position);
        token.setType(TokenType.NUMBER);
        while(position<input.length()){
            char curr=input.charAt(position);
            if(Character.isAlphabetic(curr)){
                throw new RuntimeException("Invalid Syntax");
            }
            else if(!Character.isDigit(curr)){
               break;
            }
            position++;
        }
        token.setEnd(position-1);
        token.setLexeme(input.substring(token.getStart(),position));
        tokenStream.put(token);
        return State.NEXTSTART;
    }

    State operatorState() throws InterruptedException {
        if(position+1<input.length()){
            char next=input.charAt(position+1);
            if(next=='+' || next=='-' || next=='=' || next==';'){
                throw new RuntimeException("Invalid Syntax");
           }
        }

        Token token=new Token();
        token.setStart(position);
        token.setEnd(position);
        char curr= input.charAt(position);
        switch(curr){
            case '+':{
                token.setType(TokenType.PLUS);
                token.setLexeme("+");
                break;
            }
            case '-':{
                token.setType(TokenType.MINUS);
                token.setLexeme("-");
                break;
            }
            case '=':{
                token.setType(TokenType.EQUALS);
                token.setLexeme("=");
                break;
            }
            case '*':{
                token.setType(TokenType.MULTIPLY);
                token.setLexeme("*");
                break;
            }
            case ';':{
                token.setType(TokenType.SEMICOLON);
                token.setLexeme(";");
                break;
            }
            default: throw new RuntimeException("Invalid Syntax");
        }
        position++;
        tokenStream.put(token);
        return State.NEXTSTART;
    }
}