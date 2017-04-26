import java.util.HashMap;

public class ReservedWord {
	
	HashMap<String, String> reserved_words = new HashMap<String, String>();
	
	public ReservedWord()
	{
		reserved_words.put("DATA_TYPE_INT", "nume");
		reserved_words.put("MAIN", "main");
		reserved_words.put("DELIM_L_PAREN", "(");
		reserved_words.put("DELIM_R_PAREN", ")");
		reserved_words.put("DELIM_L_BLOCK", "{");
		reserved_words.put("DELIM_R_BLOCK", "}");
		reserved_words.put("ASSIGNMENT", ">>");
		reserved_words.put("TERMINATOR", ";");
		reserved_words.put("PRINT", "ipakita");
		reserved_words.put("RETURN", "ibalik");
		reserved_words.put("UNDERSCORE", "_");
		reserved_words.put("SCAN", "ilagay");
		reserved_words.put("IF", "kapag");
		reserved_words.put("ELSE", "kundi");
		reserved_words.put("THEN", "edi");
		reserved_words.put("SWITCH", "palit");
		reserved_words.put("CASE", "kung");
		reserved_words.put("FOR", "para");
		reserved_words.put("WHILE", "habang");
		reserved_words.put("DO", "gawin");
		reserved_words.put("BREAK", "tigil");
		reserved_words.put("CONTINUE", "tuloy");
		reserved_words.put("TRUE", "totoo");
		reserved_words.put("FALSE", "mali");
		reserved_words.put("ARITH_OP_ADD", "+");
		reserved_words.put("ARITH_OP_SUB", "-");
		reserved_words.put("ARITH_OP_MULTI", "*");
		reserved_words.put("ARITH_OP_DIV", "/");
		reserved_words.put("ARITH_OP_EXPO", "**");
		reserved_words.put("INCREMENT", "++");
		reserved_words.put("DECREMENT", "--");
		reserved_words.put("DOT", ".");
		reserved_words.put("END", "wakas");
	}
	
	public String getLexeme(String token)
	{
		String s = reserved_words.get(token);
		return s;
	}
}
