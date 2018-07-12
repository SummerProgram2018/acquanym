package com.ei8htideas.acquanym.backend;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;

/**
 * Created by Henry on 5/07/2018.
 */

public class JSONParser extends Thread {

    private class Token {
        private String input;

        public Token(String input) {
            this.input = input;
        }

        public void extend(String seq) {
            input += seq;
        }

        public boolean isMatch(String seq) {
            return input.startsWith(seq);
        }

        public boolean isNum() {
            return "1234567890".contains(input.substring(0, 1));
        }

        public void next() throws EOFException {
            input = input.substring(1);
        }

        public void snext() throws EOFException {
            strip();
            next();
            strip();
        }

        public void strip() {
            input = input.trim();
        }

        public String first() {
            return input.substring(0, 1);
        }

        public boolean isEmpty() {
            return input.length() == 0;
        }

        public String toString() {
            return input;
        }
    }

    private Token token;
    private Object result;

    public JSONParser(String input) {
        token = new Token(input);
    }

    public JSONParser() {
        token = new Token("");
    }

    public Object parse() throws EOFException {
        return parseExp();
    }

    public void feed(char c) {
        String s = String.valueOf(c);
        token.extend(s);
    }

    public void run() {
        try {
            result = parseExp();
        } catch(EOFException ex) {
            ex.printStackTrace();
        }
    }

    public Object getResult() {
        return result;
    }

    private String parseString() throws EOFException {
        //starts with "
        token.strip();
        token.next();
        StringBuilder sb = new StringBuilder();
        while(!token.isMatch("\"")) {
            sb.append(token.first());
            token.next();
        }
        token.next(); //closing "
        token.strip();
        return sb.toString();
    }

    private Double parseNum() throws EOFException {
        StringBuilder num = new StringBuilder();
        boolean start = false;
        boolean point = false;
        token.strip();
        while(token.isMatch("-") || token.isMatch(".") || token.isMatch("e") || token.isNum()) {
            if(!start) {
                if(token.isMatch("-")) {
                    num.append("-");
                } else if(token.isMatch(".")) {
                    num.append("0.");
                    point = true;
                } else {
                    num.append(token.first());
                }
                start = true;
                token.next();
            } else if (token.isMatch(".")) {
                if (!point) {
                    num.append(".");
                    point = true;
                }
                token.next();
            } else if (token.isMatch("e")) {
                num.append("e");
                token.next();
                Integer post = parseNum().intValue();
                num.append(String.valueOf(post));
            } else if (token.isNum()) {
                num.append(token.first());
                token.next();
            } else {
                token.next();
            }
        }
        token.strip();
        return Double.parseDouble(num.toString());
    }

    private List<Object> parseList() throws EOFException {
        //Starts with [
        token.snext();
        List<Object> res = new ArrayList<>();
        while(!token.isMatch("]")) {
            res.add(parseExp());
            if(token.isMatch(",")) {
                token.snext(); //match ,
            }
        }
        token.snext(); //closing ]
        return res;
    }

    private Map<Object, Object> parseDict() throws EOFException {
        //Starts with {
        token.snext();
        Map<Object, Object> res = new HashMap<>();
        while(!token.isMatch("}")) {
            Object key = parseExp();
            token.snext(); //match :
            Object val = parseExp();
            res.put(key, val);
            if(token.isMatch(",")) {
                token.snext(); //match ,
            }
        }
        token.snext(); //closing }
        return res;
    }

    private Object parseExp() throws EOFException {
        token.strip();
        if(token.isMatch("{")) {
            return parseDict();
        } else if(token.isMatch("[")) {
            return parseList();
        } else if(token.isNum() || token.isMatch("-") || token.isMatch(".") || token.isMatch("e")) {
            return parseNum();
        } else if(token.isMatch("\"")) {
            return parseString();
        }
        return null;
    }

    public static void main(String[] args) throws EOFException {
    }

}
