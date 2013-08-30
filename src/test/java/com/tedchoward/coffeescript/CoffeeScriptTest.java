/*
Copyright (c) 2013, Ted C. Howard
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

 - Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
 - Redistributions in binary form must reproduce the above copyright notice, 
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.
   
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.tedchoward.coffeescript;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mozilla.javascript.JavaScriptException;

public class CoffeeScriptTest {
	
	private static final String COFFEESCRIPT_VERSION = "1.6.3";
	private static final String ONE_PLUS_ONE = "(function() {\n  1 + 1;\n\n}).call(this);\n";
	private static final String BARE_ONE_PLUS_ONE = "1 + 1;\n";
	
	private CoffeeScript coffee = new CoffeeScript();

	@Test
	public void testCompile() {
		String result = coffee.compile("1 + 1", null);
		assertEquals(ONE_PLUS_ONE, result);
	}
	
	@Test
	public void testCompileWithOptions() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("bare", true);
		String result = coffee.compile("1 + 1", options);
		assertEquals(BARE_ONE_PLUS_ONE, result);
	}
	
	@Test
	public void testCompilerError() {
		try {
			coffee.compile("for ->", null);
			assertTrue(false); // previous line should throw exception, this should never execute
		} catch (JavaScriptException e) { }
	}
	
	@Test
	public void testEval() {
		Object result = coffee.eval("1 + 1", null);
		assertEquals(2.0, result);
	}
		
	@Test
	public void testVersion() {
		String version = coffee.getVersion();
		assertEquals(COFFEESCRIPT_VERSION, version);
	}

}
