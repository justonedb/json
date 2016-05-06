/*
 * The MIT License
 *
 * Copyright 2016 JustOne Database Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.justone.json;

import junit.framework.TestCase;

/**
 *
 * @author Duncan
 */
public class ParserTest extends TestCase {
  
  public ParserTest(String testName) {
    super(testName);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
 /**
   * Test of parse method, of class Parser.
   */
  public void testParse() {
    
    System.out.println("parse");
    
    Parser instance = new Parser();
    instance.parse("{}");
    instance.parse("[]");
    instance.parse("[{}]");
    instance.parse("{\"a\":null}");
    instance.parse("{\"a\":false}");
    instance.parse("{\"a\":true}");
    instance.parse("{\"a\":1}");
    instance.parse("{\"a\":1.0}");
    instance.parse("{\"a\":-1.0e+1}");
    instance.parse("{\"a\":\"string\"}");
    instance.parse("{\"a\":null,\"b\":null}");
    
    instance.parse("[null]");
    instance.parse("[null,false,1,1.0,\"string\"]");
    
    instance.parse("[{\"a\":null},{\"a\":null}]");
        
  }//testParse()

  /**
   * Test of getRootElement method, of class Parser.
   */
  public void testGetRootElement() {
    
    System.out.println("getRootElement");
    
    Parser instance = new Parser();

    instance.parse("{}");
    assertEquals(true, instance.getRootElement().isObject());

    instance.parse("[]");
    assertEquals(true, instance.getRootElement().isArray());

  }//testGetRootElement()  
  
  /**
   * Test of toString method, of class Parser.
   */
  public void testToString() {
    
    System.out.println("toString");
        
    Parser instance = new Parser();

    String message="{}";
    instance.parse(message);
    assertEquals(message, instance.toString());
    
    message="[]";
    instance.parse(message);
    assertEquals(message, instance.toString());

    message="[{}]";
    instance.parse(message);
    assertEquals(message, instance.toString());

    message="{\"a\":null}";
    instance.parse(message);
    assertEquals(message, instance.toString());

    message="{\"a\":false}";
    instance.parse(message);
    assertEquals(message, instance.toString());

    message="{\"a\":1}";
    instance.parse(message);
    assertEquals(message, instance.toString());

    message="{\"a\":1.0}";
    instance.parse(message);
    assertEquals(message, instance.toString());

    message="{\"a\":\"string\"}";
    instance.parse(message);
    assertEquals(message, instance.toString());
    
    message="[null,false,1,1.0,\"string\"]";
    instance.parse(message);
    assertEquals(message, instance.toString());
    
    message="[{\"a\":null},{\"a\":null}]";
    instance.parse(message);
    assertEquals(message, instance.toString());

  }//testToString()

  /**
   * Test of contains method, of class Parser.
   */
  public void testContains() {
    
    System.out.println("contains");
    
    Parser instance = new Parser();
    instance.parse("{}");
    assertEquals(false, instance.contains(new Path("/@a")));
    
    instance.parse("{\"a\":null}");
    assertEquals(true, instance.contains(new Path("/@a")));
    assertEquals(false, instance.contains(new Path("/@b")));

    instance.parse("{\"a\":null,\"b\":null}");
    assertEquals(true, instance.contains(new Path("/@a")));
    assertEquals(true, instance.contains(new Path("/@b")));
    assertEquals(false, instance.contains(new Path("/@a/@b")));

    instance.parse("{\"a\":{\"b\":null}}");
    assertEquals(true, instance.contains(new Path("/@a")));
    assertEquals(false, instance.contains(new Path("/@b")));
    assertEquals(true, instance.contains(new Path("/@a/@b")));
    
    instance.parse("[]");
    assertEquals(false, instance.contains(new Path("/#0")));
     
    instance.parse("[null]");
    assertEquals(true, instance.contains(new Path("/#0")));
    assertEquals(false, instance.contains(new Path("/#1")));

    instance.parse("[{\"a\":null}]");
    assertEquals(true, instance.contains(new Path("/#0")));
    assertEquals(true, instance.contains(new Path("/#0/@a")));
    assertEquals(false, instance.contains(new Path("/#0/@b")));
    assertEquals(false, instance.contains(new Path("/#1/@a")));
    assertEquals(false, instance.contains(new Path("/@a")));
    
    instance.parse("{\"a\":[null]}");
    assertEquals(true, instance.contains(new Path("/@a")));
    assertEquals(true, instance.contains(new Path("/@a/#0")));
    assertEquals(false, instance.contains(new Path("/@a/#1")));
    assertEquals(false, instance.contains(new Path("/#1")));
    
  }//testContains()

  /**
   * Test of containsAll method, of class Parser.
   */
  public void testContainsAll() {
    
    System.out.println("containsAll");

    Parser instance = new Parser();
    instance.parse("{}");
    assertEquals(false, instance.containsAll(new Path[]{new Path("/@a")}));

    instance.parse("{\"a\":null}");
    assertEquals(true, instance.containsAll(new Path[]{new Path("/@a")}));
    assertEquals(false, instance.containsAll(new Path[]{new Path("/@a"),new Path("/@b")}));

    instance.parse("{\"a\":null,\"b\":null}");
    assertEquals(true, instance.containsAll(new Path[]{new Path("/@a")}));
    assertEquals(true, instance.containsAll(new Path[]{new Path("/@a"),new Path("/@b")}));
    assertEquals(false, instance.containsAll(new Path[]{new Path("/@a"),new Path("/@b"),new Path("/@c")}));
    
  }//testContainsAll()

  /**
   * Test of containsAny method, of class Parser.
   */
  public void testContainsAny() {
    
    System.out.println("containsAny");

    Parser instance = new Parser();
    instance.parse("{}");
    assertEquals(false, instance.containsAny(new Path[]{new Path("/@a")}));

    instance.parse("{\"a\":null}");
    assertEquals(true, instance.containsAny(new Path[]{new Path("/@a")}));
    assertEquals(true, instance.containsAny(new Path[]{new Path("/@a"),new Path("/@b")}));
    assertEquals(false, instance.containsAny(new Path[]{new Path("/@b")}));

    instance.parse("{\"a\":null,\"b\":null}");
    assertEquals(true, instance.containsAny(new Path[]{new Path("/@a")}));
    assertEquals(true, instance.containsAny(new Path[]{new Path("/@b")}));
    assertEquals(true, instance.containsAny(new Path[]{new Path("/@a"),new Path("/@b")}));
    assertEquals(true, instance.containsAny(new Path[]{new Path("/@a"),new Path("/@b"),new Path("/@c")}));
    assertEquals(false, instance.containsAny(new Path[]{new Path("/@c")}));
    
  }//testContainsAny()  
  
  /**
   * Test of extract method, of class Parser.
   */
  public void testGetElement() {
    
    System.out.println("getElement");
    
    Parser instance = new Parser();
    instance.parse("{}");
    assertNull(instance.getElement(new Path("/@a")));

    instance.parse("{\"a\":null}");
    assertEquals("null", instance.getElement(new Path("/@a")).toString());
    assertNull(instance.getElement(new Path("/@b")));
    
    instance.parse("{\"a\":false}");
    assertEquals("false", instance.getElement(new Path("/@a")).toString());
    assertNull(instance.getElement(new Path("/@b")));
    
    instance.parse("{\"a\":1}");
    assertEquals("1", instance.getElement(new Path("/@a")).toString());
    assertNull(instance.getElement(new Path("/@b")));
    
    instance.parse("{\"a\":1.0}");
    assertEquals("1.0", instance.getElement(new Path("/@a")).toString());
    assertNull(instance.getElement(new Path("/@b")));

    instance.parse("{\"a\":\"string\"}");
    assertEquals("\"string\"", instance.getElement(new Path("/@a")).toString());
    assertNull(instance.getElement(new Path("/@b")));
 
    instance.parse("[{\"a\":null}]");
    assertEquals("{\"a\":null}", instance.getElement(new Path("/#0")).toString());
    assertEquals("null", instance.getElement(new Path("/#0/@a")).toString());
    assertNull(instance.getElement(new Path("/#1/@a")));
    
    instance.parse("{\"a\":[1,2]}");
    assertEquals("[1,2]", instance.getElement(new Path("/@a")).toString());
    assertEquals("1", instance.getElement(new Path("/@a/#0")).toString());
    assertEquals("2", instance.getElement(new Path("/@a/#1")).toString());
    assertNull(instance.getElement(new Path("/@a/#2")));
    assertNull(instance.getElement(new Path("/@b/#0")));
    
    instance.parse("[null,false,{\"key\":\"value\"}]");
    assertNull(instance.getElement(new Path("/@key1")));
    assertNull(instance.getElement(new Path("/@key2")));

    
  }//testGetElement()
    
}//ParserTest{}
