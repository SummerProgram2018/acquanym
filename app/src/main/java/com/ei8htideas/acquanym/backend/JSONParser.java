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

public class JSONParser {

    private class Token {
        private String input;

        public Token(String input) {
            this.input = input;
        }

        public boolean isMatch(String seq) {
            return input.startsWith(seq);
        }

        public boolean isNum() {
            try {
                Integer.parseInt(input.substring(0, 1));
            } catch(NumberFormatException ex) {
                return false;
            }
            return true;
        }

        public void next() throws EOFException {
            if(isEmpty()) {
                throw new EOFException("Unexpected EOF while parsing");
            }
            input = input.substring(1);
        }

        public void snext() throws EOFException {
            input.trim();
            next();
            input.trim();
        }

        public void strip() {
            input.trim();
        }

        public String first() {
            return input.substring(0, 1);
        }

        public boolean isEmpty() {
            return input.equals("");
        }
    }

    private Token token;

    public JSONParser(String input) {
        token = new Token(input);
    }

    public Object parse() throws EOFException {
        return parseExp();
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

    private double parseNum() throws EOFException {
        StringBuilder num = new StringBuilder();
        boolean start = false;
        boolean neg = false;
        boolean point = false;
        token.strip();
        while(token.isMatch("-") || token.isMatch(".") || token.isNum()) {
            if(!start) {
                if(token.isMatch("-")) {
                    neg = true;
                } else if(token.isMatch(".")) {
                    num.append("0.");
                    point = true;
                } else {
                    num.append(token.first());
                }
                start = true;
            } else if (token.isMatch(".")) {
                if(!point) {
                    num.append(".");
                    point = true;
                }
            } else if (token.isNum()) {
                num.append(token.first());
            }
            token.next();
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
        if(token.isMatch("{")) {
            return parseDict();
        } else if(token.isMatch("[")) {
            return parseList();
        } else if(token.isNum()) {
            return parseNum();
        } else if(token.isMatch("\"")) {
            return parseString();
        }
        return null;
    }

    public static void main(String[] args) throws EOFException {
        JSONParser parser = new JSONParser("[1]");
        System.out.println(parser.parse());
    }


}
