public enum TokenType{
	
        EMPTY,
        TOKEN, 
	IDENTIFIER, 
	INTEGER_CONSTANT,//integer 
	STRING_CONSTANT,//"string"
        TRUE, 
        FALSE,
        MAIN,//simula
        DQ,//TRY
        DELIM_L_PAREN,//(
        DELIM_R_PAREN,//)
        DELIM_L_BLOCK,//{
        DELIM_R_BLOCK,//}
        ASSIGNMENT,//>>
        TERMINATOR,//;
        PRINT,//ipakita
        SCAN,//ilagay
        UNDERSCORE,//_
        SYMBOLS,//mga symbols
        /*CONDITIONAL STATEMENTS*/
        IF,//kapag
        ELSE,//kundi
        THEN,//edi
        SWITCH,//palit
        CASE,//kung
        /*LOOP*/
        FOR,//para
        WHILE,//habang
        DO,//gawin
        /*BRANCHING STATEMENTS*/
        BREAK,//tigil
        CONTINUE,//tuloy
        RETURN,//ibalik
        /*DATA TYPES*/
        DATA_TYPE_STRING,//tali
        DATA_TYPE_DOUBLE,//doble
        DATA_TYPE_FLOAT,//lutang
        DATA_TYPE_CHARACTER,//tanda
        DATA_TYPE_BOOLEAN,//pasya
        DATA_TYPE_INT,//nume
        /*ARITHMETICS*/
        ARITH_OP_ADD,//+
        ARITH_OP_SUB,//-
        ARITH_OP_MULTI,//*
        ARITH_OP_DIV,// /
        ARITH_EXPO,// **
        LOGICAL_OPE,// logical operators
        RELATIONAL_OPE,// relational operators
        INCREMENT,// ++
        DECREMENT,// --
        DOT,
        END
        

}//end enum