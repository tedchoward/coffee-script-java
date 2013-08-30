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

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * <h1>CoffeeScript Compiler for Java.</h1> 
 * 
 * This library wraps the official CoffeeScript compiler (written in
 * JavaScript) using Mozilla's (Java-based) Rhino JavaScript engine.
 * 
 * @author Ted C. Howard
 * 
 */
public class CoffeeScript {
	
	static final Scriptable SHARED_GLOBAL_SCOPE;
	
	static {
		try {
			Context ctx = Context.enter();
			SHARED_GLOBAL_SCOPE = ctx.initStandardObjects();
		} finally {
			Context.exit();
		}
	}
	
	private Scriptable coffee;
	private Scriptable scope;
	
	public CoffeeScript() {
		Context context = Context.enter();
		
		try {
			scope = Util.createNewScope(context);
			Reader r = new InputStreamReader(CoffeeScript.class.getResourceAsStream("coffee-script.js"), "UTF-8");
			context.evaluateReader(scope, r, "coffee-script.js", 0, null);
			coffee = (Scriptable) scope.get("CoffeeScript", null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			Context.exit();
		}
	}
	
	/**
	 * Compiles CoffeeScript source code into JavaScript source code.
	 * @param code the CoffeeScript source code
	 * @param options an optional Map of configuration options to pass to the compiler
	 * @return the compiled JavaScript source code
	 */
	public String compile(String code, Map<String, Object> options) {
		Context context = Context.enter();
		
		try {
			Scriptable optionsObj = Util.toJSObject(options, context, scope);
			Object retVal = ScriptableObject.callMethod(context, coffee, "compile", new Object[] {code, optionsObj});
			return (String) retVal;
		} finally {
			Context.exit();
		}
	}
	
	/**
	 * Evaluates CoffeeScript code and returns the result of the expression
	 * @param code the CoffeeScript expression
	 * @param options an optional Map of configuration options to pass to the compiler
	 * @return the result of the CoffeeScript expression
	 */
	public Object eval(String code, Map<String, Object> options) {
		Context context = Context.enter();
		
		try {
			Object result = ScriptableObject.callMethod(context, coffee, "eval", new Object[] {code, options});
			return result;
		} finally {
			Context.exit();
		}
	}
	
	/**
	 * @return the version of the CoffeeScript compiler (equivalent to CoffeeScript.VERSION)
	 */
	public String getVersion() {
		Context.enter();
		
		try {
			return ScriptableObject.getTypedProperty(coffee, "VERSION", String.class);
		} finally {
			Context.exit();
		}
	}
}
