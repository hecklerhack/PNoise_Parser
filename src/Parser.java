import java.util.Stack;

public class Parser
{
	Stack<String> parse_stack;
	public Parser()
	{
		parse_stack = new Stack<String>();
	}

	//gets each token from scanner
	public void getToken(Token t)
	{
		parse_stack.push(t.getToken());
	}

//just used to test if all tokens go to parser
/*	public void printParserTokens()
	{
		while(!parse_stack.empty())
			System.out.print(parse_stack.pop());
	}*/
}