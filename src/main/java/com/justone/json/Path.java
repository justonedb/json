/*

MIT License
 
Copyright (c) 2016 JustOne Database Inc

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/

package com.justone.json;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A JSON navigation path.
 * <P>
 * A path represents a hierarchy of tags for navigating a JSON message. Paths
 * are constructed from strings of the form /tag/tag/ where the leading character
 * is a tag separator and hierarchies are navigated from left to right. 
 * </P>
 * <P>
 * A tag may be either an object key or an array index. An object key is the at character (@)
 * followed by the key name without quotation marks; while an array index is the hash character (#) 
 * followed by an integer, starting at zero for the first element.
 * </P>
 * <P>
 * For a JSON message of {"a":1,"b":{"c":2}}, the strings "/@a" and "/@b/@c" represent the paths
 * to the elements 1 and 2 respectively. For a JSON message of {"a":[1,2,3]} the strings
 * "/@a/#0" and "/@a/#2" represent the paths for elements 1 and 3 respectively. 
 * </P>
 * @author Duncan Pauly
 * @version 1.0
 */
public class Path {
  
  /**
   * Prefix character for object key tag
   */
  public static final char KEY='@';
  /**
   * Prefix character for array index tag
   */
  public static final char INDEX='#';
  /**
   * Prefix character for regular expression tag
   */
  public static final char PATTERN='?';
  /**
   * Tag separator character
   */
  protected final char fSeparator;
  /**
   * Tag levels in the path 
   */
  protected final int fDepth;
  /**
   * Object key at each tag level
   */
  protected final String[] fKeys;
  /**
   * Array index at each tag level
   */
  protected final int[] fIndexes;
  /**
   * Regular expressions at each tag level
   */
  protected final Pattern[] fPatterns;
  
  /**
   * Path constructor   
   * @param aString canonical path string
   * 
   */
  public Path(String aString) {
    
    assert aString!=null;

    if (aString.length()==1) {//if root path
      fSeparator=aString.charAt(0);//set leading character as tag separator
      fDepth=0;//path is root
      fKeys=null;//no key tags
      fIndexes=null;//no index tags
      fPatterns=null;//no pattern tags
      return;
    }//if root path
    
    if (aString.length()<2) throw new RuntimeException("Empty path : "+aString);//empty path
    
    fSeparator=aString.charAt(0);//extract leading character as tag separator
    
    String[] tags=aString.substring(1).split("\\"+fSeparator,-1);//split path into array of tags
    fDepth=tags.length;//set tag depth
    fKeys=new String[fDepth];//allocate array for key tags
    fIndexes=new int[fDepth];//allocate array for index tags
    fPatterns=new Pattern[fDepth];//allocate array for pattern tags
    for (int i=0;i<fDepth;++i) {//for each tag
      if (tags[i].trim().length()<2) throw new RuntimeException("Bad key tag : "+aString+" ["+i+"]");//bad key tag
      if (tags[i].charAt(0)==KEY) {//if object key tag
        fKeys[i]=tags[i].substring(1);//set key tag after removing @ prefix
        assert fKeys[i]!=null;
      } else if (tags[i].charAt(0)==INDEX) {//else if array index tag
        for (int j=1;j<tags[i].length();++j) {//for each subsequent character
          if ("0123456789".indexOf(tags[i].charAt(j))<0) throw new RuntimeException("Bad index tag : "+aString+" ["+i+"]");//bad index tag
        }//for each subsequent character
        fIndexes[i]=Integer.parseInt(tags[i].substring(1));//set array index after removing # tag
        assert fIndexes[i]>=0;
      } else if (tags[i].charAt(0)==PATTERN) {//else if pattern tag
        try {
        fPatterns[i]=Pattern.compile(tags[i].substring(1));//compile pattern after removing ? tag
        } catch (PatternSyntaxException exception) {
          throw new RuntimeException("Bad pattern tag : "+aString+" ["+i+"] : "+exception.getMessage());//bad pattern tag
        }
        assert fPatterns[i]!=null;
      } else {//else bad prefix
        throw new RuntimeException("Bad tag : "+aString+" ["+i+"]");//bad tag prefix
      }//if object key tag
    }//for each tag
        
  }//Path()
      
  /**
   * Returns canonical string representation of the path
   * @return string representation of the path
   */
  @Override
  public String toString() {
    
    assert fDepth>=0;
   
    StringBuilder buffer=new StringBuilder();//create buffer for string manipulation
    
    if (fDepth==0) {//if root path
      return String.valueOf(fSeparator);
    }//if root path
    
    for (int i=0;i<fDepth;++i) {//for each tag
      
      buffer.append(fSeparator);//append label separator
      if (fKeys[i]!=null) {//if object key
        buffer.append(KEY);//append object key prefix
        buffer.append(fKeys[i]);//append key
      } else if (fPatterns[i]!=null){//if pattern
        buffer.append(PATTERN);//append pattern prefix
        buffer.append(fPatterns[i].toString());//append pattern string
      } else {//else index
        buffer.append(INDEX);//append array index prefix
        buffer.append(fIndexes[i]);//append index    
      }//if object key
    }//for each tag
    
    return buffer.toString();//return string
    
  }//toString()
 
 /**
   * Returns the tag of a given element
   * @param aElement given element to return tag for
   * @return tag of the given element
   */
  public static String getTag(Element aElement) {
    
    assert aElement!=null;

    if (aElement.iParent==null) return "";//
    if (aElement.iKey!=null) {//if object key
      return KEY+aElement.iKey;//return object key label
    } else {//else array index
      return String.valueOf(INDEX)+String.valueOf(aElement.iIndex);//return array index label     
    }//if object key
    
  }//getTag()

  /**
   * Returns the path string for a given element
   * @param aElement given element to return path string for
   * @param aSeparator character to be used as label separator
   * @return path string of the given element
   */
  public static String getPath(Element aElement,char aSeparator) {
    assert aElement!=null;
    assert aSeparator>0;
    
    StringBuilder stringBuilder=new StringBuilder();//buffer for path string result
    Element element=aElement;//start at given element
    while (element.iParent!=null) {//until root element
      stringBuilder.insert(0, getTag(element));//prepend element label
      stringBuilder.insert(0, aSeparator);//prepend label separator
      element=element.iParent;//go to parent
    }//until root element
    
    return stringBuilder.toString();//return path string
    
  }//getPath()    
  
}//Path{}
