package com.ninetowns.library.parser;


public abstract class AbsParser<P extends Object> implements Parser<Object> {
	protected int totalPage;
	protected int getTotalPage() {
		return totalPage;
	}
	
	public AbsParser(String str) {
        getParseResult(str);
    }

    public abstract Object getParseResult(String netStr);

}
