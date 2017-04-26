import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Hashtable;

public class Tokenizer
{
	private ArrayList<TokenData> tokenDatas;
	
	private String str;
	private Token lastToken;
	private boolean pushBack;
        public String literal;
        public Identifier id;

	public Tokenizer(String str){
		this.tokenDatas = new ArrayList<TokenData>();
		this.str = str;
	
                tokenDatas.add(new TokenData(Pattern.compile("^((-)?[0-9]+)"), TokenType.INTEGER_CONSTANT));
                tokenDatas.add(new TokenData(Pattern.compile("^(\".*\")"), TokenType.STRING_CONSTANT));
                tokenDatas.add(new TokenData(Pattern.compile("^(nume)"), TokenType.DATA_TYPE_INT));
                tokenDatas.add(new TokenData(Pattern.compile("^(lutang)"), TokenType.DATA_TYPE_FLOAT));
                tokenDatas.add(new TokenData(Pattern.compile("^(doble)"), TokenType.DATA_TYPE_DOUBLE));
                tokenDatas.add(new TokenData(Pattern.compile("^(tali)"), TokenType.DATA_TYPE_STRING));
                tokenDatas.add(new TokenData(Pattern.compile("^(tanda)"), TokenType.DATA_TYPE_CHARACTER));
                tokenDatas.add(new TokenData(Pattern.compile("^(pasya)"), TokenType.DATA_TYPE_BOOLEAN));
                tokenDatas.add(new TokenData(Pattern.compile("^(simula)"), TokenType.MAIN));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\()"), TokenType.DELIM_L_PAREN));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\))"), TokenType.DELIM_R_PAREN));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\{)"), TokenType.DELIM_L_BLOCK));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\})"), TokenType.DELIM_R_BLOCK));
                tokenDatas.add(new TokenData(Pattern.compile("^(>>)"), TokenType.ASSIGNMENT));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\;)"), TokenType.TERMINATOR));
                tokenDatas.add(new TokenData(Pattern.compile("^(tali)"), TokenType.DATA_TYPE_STRING));
                tokenDatas.add(new TokenData(Pattern.compile("^(ipakita)"), TokenType.PRINT));
                tokenDatas.add(new TokenData(Pattern.compile("^(_)"), TokenType.UNDERSCORE));
                tokenDatas.add(new TokenData(Pattern.compile("^(ibalik)"), TokenType.RETURN));
                tokenDatas.add(new TokenData(Pattern.compile("^(ilagay)"), TokenType.SCAN));
                tokenDatas.add(new TokenData(Pattern.compile("^(@, #, $, ^, \\, =, ., ?, ', :)"), TokenType.SYMBOLS));
                tokenDatas.add(new TokenData(Pattern.compile("^(kapag)"), TokenType.IF));
                tokenDatas.add(new TokenData(Pattern.compile("^(kundi)"), TokenType.ELSE));
                tokenDatas.add(new TokenData(Pattern.compile("^(edi)"), TokenType.THEN));
                tokenDatas.add(new TokenData(Pattern.compile("^(palit)"), TokenType.SWITCH));
                tokenDatas.add(new TokenData(Pattern.compile("^(kung)"), TokenType.CASE));
                tokenDatas.add(new TokenData(Pattern.compile("^(para)"), TokenType.FOR));
                tokenDatas.add(new TokenData(Pattern.compile("^(habang)"), TokenType.WHILE));
                tokenDatas.add(new TokenData(Pattern.compile("^(gawin)"), TokenType.DO));
                tokenDatas.add(new TokenData(Pattern.compile("^(tigil)"), TokenType.BREAK));
                tokenDatas.add(new TokenData(Pattern.compile("^(tuloy)"), TokenType.CONTINUE));
                tokenDatas.add(new TokenData(Pattern.compile("^(totoo)"), TokenType.TRUE));
                tokenDatas.add(new TokenData(Pattern.compile("^(mali)"), TokenType.FALSE));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\+)"), TokenType.ARITH_OP_ADD));
                tokenDatas.add(new TokenData(Pattern.compile("^(-)"), TokenType.ARITH_OP_SUB));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\*)"), TokenType.ARITH_OP_MULTI));
                tokenDatas.add(new TokenData(Pattern.compile("^(/)"), TokenType.ARITH_OP_DIV));
                tokenDatas.add(new TokenData(Pattern.compile("^('*''*')"), TokenType.ARITH_EXPO));
                tokenDatas.add(new TokenData(Pattern.compile("^(&&)"), TokenType.LOGICAL_OPE));
                tokenDatas.add(new TokenData(Pattern.compile("^(==, <, >, <=, >=, !=)"), TokenType.RELATIONAL_OPE));
                tokenDatas.add(new TokenData(Pattern.compile("^('+''+')"), TokenType.INCREMENT));
                tokenDatas.add(new TokenData(Pattern.compile("^(--)"), TokenType.DECREMENT));
                tokenDatas.add(new TokenData(Pattern.compile("^(\\.)"), TokenType.DOT));
                tokenDatas.add(new TokenData(Pattern.compile("^(wakas)"), TokenType.END));
                tokenDatas.add(new TokenData(Pattern.compile("^([a-zA-Z][a-zA-Z0-9]*)"), TokenType.IDENTIFIER));
                
                for (String t : new String[] {"\\{", "\\}", ";", "_", "\\(", "\\)", "\\.", "\\,"}){
                    tokenDatas.add(new TokenData(Pattern.compile("^(" + t + ")"), TokenType.TOKEN));
                }
	}
        
        public Token nextToken(){
            str = str.trim();
            
            if(pushBack){
                pushBack = false;
                return lastToken;
            }
            
            if(str.isEmpty()){
                return (lastToken = new Token("", TokenType.EMPTY));
            }
            
            for(TokenData data : tokenDatas){
                Matcher matcher = data.getPattern().matcher(str);
                
                if (matcher.find()){
                    String token = matcher.group().trim();
                    str = matcher.replaceFirst("");
                    
                        switch(data.getType()){
                            case DATA_TYPE_INT : return (lastToken = new Token("DATA_TYPE_INT", data.getType()));
                            case MAIN : return (lastToken = new Token("MAIN", data.getType()));
                            case DELIM_L_PAREN : return (lastToken = new Token("DELIM_L_PAREN", data.getType()));
                            case DELIM_R_PAREN : return (lastToken = new Token("DELIM_R_PAREN", data.getType()));
                            case DELIM_L_BLOCK : return (lastToken = new Token("DELIM_L_BLOCK", data.getType()));
                            case DELIM_R_BLOCK : return (lastToken = new Token("DELIM_R_BLOCK", data.getType()));
                            case ASSIGNMENT : return (lastToken = new Token("ASSIGNMENT", data.getType()));
                            case TERMINATOR : return (lastToken = new Token("TERMINATOR", data.getType()));
                            case PRINT : return (lastToken = new Token("PRINT", data.getType()));
                            case RETURN : return (lastToken = new Token("RETURN", data.getType()));
                            case STRING_CONSTANT : 
                                literal = token;
                                return (lastToken = new Token("STRING_CONSTANT," + token, data.getType()));
                            case INTEGER_CONSTANT : 
                                literal = token;
                                return (lastToken = new Token("INTEGER_CONSTANT," + token, data.getType()));
                            case UNDERSCORE : return (lastToken = new Token("UNDERSCORE", data.getType()));
                            case DATA_TYPE_FLOAT : return (lastToken = new Token("DATA_TYPE_FLOAT", data.getType()));
                            case DATA_TYPE_DOUBLE : return (lastToken = new Token("DATA_TYPE_DOUBLE", data.getType()));
                            case DATA_TYPE_STRING : return (lastToken = new Token("DATA_TYPE_STRING", data.getType()));
                            case DATA_TYPE_CHARACTER : return (lastToken = new Token("DATA_TYPE_CHARACTER", data.getType()));
                            case DATA_TYPE_BOOLEAN : return (lastToken = new Token("DATA_TYPE_BOOLEAN", data.getType()));
                            case ARITH_OP_ADD : return (lastToken = new Token("ARITH_OP_ADD", data.getType()));
                            case ARITH_OP_SUB : return (lastToken = new Token("ARITH_OP_SUB", data.getType()));
                            case ARITH_OP_MULTI : return (lastToken = new Token("ARITH_OP_MULTI", data.getType()));
                            case ARITH_OP_DIV : return (lastToken = new Token("ARITH_OP_DIV", data.getType()));
                            case ARITH_EXPO : return (lastToken = new Token("ARITH_EXPO", data.getType()));
                            case LOGICAL_OPE : return (lastToken = new Token("LOGICAL_OPE", data.getType()));
                            case RELATIONAL_OPE : return (lastToken = new Token("RELATIONAL_OPE", data.getType()));
                            case INCREMENT : return (lastToken = new Token("INCREMENT", data.getType()));
                            case DECREMENT : return (lastToken = new Token("DECREMENT", data.getType()));
                            case IF : return (lastToken = new Token("IF", data.getType()));
                            case THEN : return (lastToken = new Token("THEN", data.getType()));
                            case ELSE : return (lastToken = new Token("ELSE", data.getType()));
                            case SWITCH : return (lastToken = new Token("SWITCH", data.getType()));
                            case CASE : return (lastToken = new Token("CASE", data.getType()));
                            case WHILE : return (lastToken = new Token("WHILE", data.getType()));
                            case FOR : return (lastToken = new Token("FOR", data.getType()));
                            case DO : return (lastToken = new Token("DO", data.getType()));
                            case BREAK : return (lastToken = new Token("BREAK", data.getType()));
                            case CONTINUE : return (lastToken = new Token("CONTINUE", data.getType()));
                            case SCAN : return (lastToken = new Token("SCAN", data.getType()));
                            case SYMBOLS : return (lastToken = new Token("SYMBOLS", data.getType()));
                            case DOT : return (lastToken = new Token("DOT", data.getType()));
                            case TRUE:
                                literal = token;
                                return (lastToken = new Token("TRUE", data.getType()));
                            case FALSE:
                                literal = token;
                                return (lastToken = new Token("FALSE", data.getType()));
                            case END:
                            	return (lastToken = new Token("END", data.getType()));
                            
                            default : 
                                id = new Identifier();
                                id.setIdentifierVar(token);
                                return (lastToken = new Token("IDENTIFIER, " + token, TokenType.STRING_CONSTANT));
                                                                                     
                                  
                    }
                }
            }
            
            throw new IllegalStateException("Parse error in " + str);
        }
        
        public boolean hasNextToken(){
            return !str.isEmpty();
        }

        public Identifier getIdentifier()
        {
            return id;
        }

        public String getLiteral()
        {
            return literal;
        }
        
        public void pushBack(){
            if (lastToken != null){
                this.pushBack = true;
            }
        }
	
}//end class