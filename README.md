# CoffeeScript Compiler for Java

This library wraps the official [CoffeeScript][1] compiler (written in
JavaScript) using Mozilla's (Java-based) [Rhino][2] JavaScript engine.

## Usage

To compile some CoffeeScript into JavaScript:

    String coffeeScriptSrc = ...;
    
    CoffeeScript coffee = new CoffeeScript();
    String javaScriptSrc = coffee.compile(coffeeScriptSrc, null);

To evaluate a CoffeeScript expression

    String coffeeScriptSrc = ...;
    CoffeeScript coffee = new CoffeeScript();
    Object result = coffee.eval(coffeeScriptSrc, null);

[1]: http://coffeescript.org
[2]: https://developer.mozilla.org/en-US/docs/Rhino


