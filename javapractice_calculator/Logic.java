package javapractice_calculator;

import java.util.Stack;
import java.util.ArrayList;


public class Logic {

    Stack<String> stack = new Stack<String>();//for postfix notation result
    ArrayList<String> postFixed = new ArrayList<String>();//for postfix notation process
    String [] strArrayPre;
    ArrayList<String> strArrayPost = new ArrayList<String>();
    Stack<Double> dStack = new Stack<Double>();

    public boolean checker(String strToCalculate) {//check if the string to calculate is valid #1
        int level = 0;
        strArrayPre = strToCalculate.split("");
        for(int i=0 ; i<strArrayPre.length ; i++) {
            if(strArrayPre[i].equals("(")) {
                level++;
            }
            else if(strArrayPre[i].equals(")")) {
                level--;
            }
        }
        if(level!=0) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isNum(String str) {
        if(str.equals("(")||str.equals(")")||str.equals("/")||
        str.equals("*")||str.equals("-")||str.equals("+")||str.equals("^")) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean isNegativeCondition(String pre) {
        if(pre.equals("(")||pre.equals("+")||pre.equals("-")||pre.equals("*")||pre.equals("/")||pre.equals("^")||pre.equals("")) {
            return true;
        }
        return false;
    }

    public void preprocess() {//#2
        stack.clear();
        postFixed.clear();
        strArrayPost.clear();
        dStack.clear();

        int startIndex = 0;
        int lastIndex = 0;
        String numericString = "";
        String pre = "";
        boolean nextIsNegative=false;

        boolean canBeNegative=true;//this is flag for negative handling ("-"" is negative when first of str, after the "(", "+", "*", "/", "-" )

        for(int i=0 ; i<strArrayPre.length ;) {
            while(i<strArrayPre.length&&!isNum(strArrayPre[i])) {//not number
                pre = strArrayPre[i];
                if(canBeNegative&&pre.equals("-")) {//checking next is negative
                    nextIsNegative=true;
                    canBeNegative=false;
                }
                else {
                    strArrayPost.add(strArrayPre[i]);//if not number and not negative -> add to strArr directly
                }
                canBeNegative = isNegativeCondition(pre);//checking next can be negative
                i++;
            }
            startIndex=i;
            while(i<strArrayPre.length&&isNum(strArrayPre[i])) {
                lastIndex = i;//if number -> find first and last index of them
                i++;
            }
            for(int k=startIndex ; k<=lastIndex ; k++) {
                numericString += strArrayPre[k];//make number by concatenating numeric unit string
            }
            if(nextIsNegative) {
                numericString = "-"+numericString;//make it negative
                nextIsNegative = false;
            }
            canBeNegative = false;//after number, not can be negative
            strArrayPost.add(numericString);//after preprocessing -> number is in string as a whole
            numericString="";//clear -> this fills rest indexes with ""
        }
    }

    public int precOf(String op) {
        if(op.equals("^")) {
            return 3;
        }
        else if(op.equals("*")||op.equals("/")) {
            return 2;
        }
        else if(op.equals("+")||op.equals("-")) {
            return 1;
        }
        else if(op.equals("(")) {
            return 0;
        }
        else {
            return 0;
        }
    }

    public boolean isPrec2Greater(String inStack, String now) {//check if precedence of second argument is greater than first
        return precOf(inStack)<precOf(now);    
    }

    public double simpleOperation(String op, double n1, double n2) {
        if (op.equals("+")) {
            return (n1+n2);
        }            
        else if (op.equals("-")) {
            return (n1-n2);
        }            
        else if (op.equals("*")) {
            return (n1*n2);
        }            
        else if (op.equals("/")) {
            return (n1/n2);
        }
        else if (op.equals("^")) {
            return (Math.pow(n1, n2));
        }
        return 0;//error
    }

    public void stringToPostfix() {//#3
        String currentVal;
        for(int i=0 ; i<strArrayPost.size() ; i++) {
            currentVal=strArrayPost.get(i);
            if(currentVal.equals("")) {//the dummy produced by number stringify (rest index produce ""s )
                break;
            }
            else if(isNum(currentVal)) {//number -> add to postfixed
                postFixed.add(currentVal);
            }
            else if(currentVal.equals("(")) {//"(" -> push to stack
                stack.push(currentVal);
            }
            else if(currentVal.equals(")")) {//)
                while(!(stack.peek().equals("("))) {
                    postFixed.add(stack.pop());//pop from stack and add to postfixed until pop "(" 
                }
                stack.pop();
            }
            else{
                if(stack.empty()||isPrec2Greater(stack.peek(),currentVal)) {//stack push condition
                    stack.push(currentVal);
                }
                else {
                    while(!stack.empty() && !stack.peek().equals("(") && !isPrec2Greater(stack.peek(),currentVal) ) {//stack pop until top is '(' or prec of now is greater
                        postFixed.add(stack.pop());
                    }
                    stack.push(currentVal);
                }
            }
        }
        while(!stack.empty()) {
            postFixed.add(stack.pop());
        }
    }

    public double postfixToResult() {//#4
        double num1, num2;
        for (int i = 0; i < postFixed.size(); i++) {//postfix -> calculated result
            if (isNum(postFixed.get(i))) {//operand
                dStack.push(Double.parseDouble(postFixed.get(i)));
            }
            else {
            num2 = dStack.pop();
            num1 = dStack.pop();
            dStack.push( simpleOperation(postFixed.get(i), num1, num2) );
            }
        }

        return dStack.pop();
    }


}