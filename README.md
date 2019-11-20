# *THIS REPOSITORY IS NOT SUPPORTED* 

# json

## Description

Fast parser for streaming JSON messages.

## Overview

This package is for use in parsing streamed JSON messages which contain some expected structure. A single parser instance is able to parse multiple messages and the navigation paths 
to reach elements of interest need only be defined once prior to parsing any messages.

Methods are also provided for the run-time exploration of the message structure after it has been parsed. 

## Elements

A JSON message is parsed into a hierarchy of elements where each element is 
a scalar, array or object element. Use the parse() method to parse one or more messages.

Use getRootElement() to return the root element and use getElement() to return an element found 
at a specified path from the parsed message. The hierarchy can also be navigated using element methods to 
navigate to child elements.

## Paths

A path represents a hierarchy of tags for navigating a JSON message. Paths are constructed from strings of the form /tag/tag/ where the leading character is a tag separator and hierarchies are navigated from left to right. 

A tag may be an object key, an array index, a regex key or a root tag. An object key identifies the key within an object; an array index identifies an element within an array; a regex key is a regex expression for a matching object key; and a root tag indicates the top of the json hierarchy. 

An object key is the at character (@) followed by the key name without quotation marks; an array index is the hash character (#) followed by an integer, starting at zero for the first element; a regex key is the question mark character (?) followed by a key regex expression; a root tag is a just the tag separator.

For a JSON message of {"a":1,"b":{"c":2}}, the key tags "/@a" and "/@b/@c" represent the paths to the elements 1 and 2 respectively. 

For a JSON message of {"a":[1,2,3]} the index tags "/@a/#0" and "/@a/#2" represent the paths for elements 1 and 3 respectively. 

For a JSON message of {"a123":1,"b":3}, the regex tag "/?a.*" represents the path to the element 1; while the root tag "/" is the path to the whole message - i.e. {"a123":1,"b":3}  


## Usage Example

    /*
    construct parser
    */
    Parser parser = new Parser();
    /* 
    construct paths for expected elements 
    */
    Path identityPath=new Path("/@identity");
    Path latitudePath=new Path("/@location/@latitude");
    Path longitudePath=new Path("/@location/@longitude");
    /*
    parse a JSON message
    */
    parser.parse("{\"identity\":12345,\"location\":{\"latitude\":51.5047650,\"longitude\":-2.4841220}}");
    /*
    get elements via paths
    */
    Element identityElement=parser.getElement(identityPath);
    Element latitudeElement=parser.getElement(latitudePath);
    Element longitudeElement=parser.getElement(longitudePath);
    /*
    output elements as strings
    */
    System.out.println("id="+identityElement.toString()+" loc="+latitudeElement.toString()+","+longitudeElement.toString());
    
## Dependencies

None

## Support

This repository is not supported

## Author

Duncan Pauly

