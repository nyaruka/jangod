package net.asfun.jangod.lib.filter;

import java.util.HashMap;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;

public class GetFilter implements Filter{

	@Override
	public Object filter(Object object, JangodInterpreter interpreter, String... arg) throws InterpretException {
		if ( arg.length != 1) {
			throw new InterpretException("filter get expects 1 arg >>> " + arg.length);
		}
		Object key = interpreter.resolveObject(arg[0]);
		HashMap map = (HashMap) object;

		System.out.println("GET FILTER");
		
		return map.get(key);
	}

	@Override
	public String getName() {
		return "get";
	}

}
