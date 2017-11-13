/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.co.yahoo.dataplatform.schema.parser;

import java.io.IOException;
import java.io.UncheckedIOException;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.IntStream;

import jp.co.yahoo.dataplatform.schema.design.ArrayContainerField;
import jp.co.yahoo.dataplatform.schema.design.IContainerField;
import jp.co.yahoo.dataplatform.schema.design.IField;
import jp.co.yahoo.dataplatform.schema.objects.BytesObj;
import jp.co.yahoo.dataplatform.schema.objects.PrimitiveObject;
import jp.co.yahoo.dataplatform.schema.utils.Properties;

public class TextArrayParser implements IParser {

  private final byte[] buffer;
  private final int start;
  private final int length;

  private final List<PrimitiveObject> container;

  private final byte delimiter;
  private final IField childSchema;

  private int readOffset;
  private int endOffset;

  public TextArrayParser( final byte[] buffer , final int start , final int length , final ArrayContainerField schema ) throws IOException{
    this.buffer = buffer;
    this.start = start;
    this.length = length;

    readOffset = start;
    endOffset = start + length;

    container = new ArrayList<PrimitiveObject>();

    Properties properties = schema.getProperties();

    if( ! properties.containsKey( "delimiter" ) ){
      throw new IOException( "Delimiter property is not found. Please set array delimiter. Example 0x2c" );
    }

    delimiter = (byte)( Integer.decode( properties.get( "delimiter" ) ).intValue() );

    childSchema = schema.getField();
  }

  private boolean parse() throws IOException{
    if( endOffset <= readOffset ){
      return false;
    }

    for( int i = readOffset ; i < endOffset ; i++ ){
      if( buffer[i] == delimiter ){
        container.add( PrimitiveConverter.textObjToPrimitiveObj( childSchema , new BytesObj( buffer , readOffset , ( i - readOffset ) ) ) );
        readOffset = i + 1;
        return true;
      }
    }
    container.add( PrimitiveConverter.textObjToPrimitiveObj( childSchema , new BytesObj( buffer , readOffset , ( length - ( readOffset - start ) ) ) ) );
    readOffset = endOffset;

    return true;
  }

  private void parseAll() throws IOException{
    while( parse() );
  }

  @Override
  public PrimitiveObject get( final String key ) throws IOException{
    return get( Integer.parseInt( key ) );
  }

  @Override
  public PrimitiveObject get( final int index ) throws IOException{
    while( parse() && ! ( index < container.size() ) );

    if( index < container.size() ){
      return container.get( index );
    }

    return null;
  }

  @Override
  public IParser getParser( final String key ) throws IOException{
    return getParser( Integer.parseInt( key ) );
  }

  @Override
  public IParser getParser( final int index ) throws IOException{
    PrimitiveObject obj = get( index );
    if( obj == null ){
      return new TextNullParser();
    }

    byte[] parseTarget = obj.getBytes();
    return TextParserFactory.get( parseTarget , 0 , parseTarget.length , childSchema );
  }

  @Override
  public String[] getAllKey() throws IOException{
    parseAll();
    String[] keys = new String[size()];
    IntStream.range( 0 , size() )
      .forEach( i -> keys[i] = Integer.toString(i) );
    return keys;
  }

  @Override
  public boolean containsKey( final String key ) throws IOException{
    return false;
  }

  @Override
  public int size() throws IOException{
    parseAll();
    return container.size();
  }

  @Override
  public boolean isArray() throws IOException{
    return true;
  }

  @Override
  public boolean isMap() throws IOException{
    return false;
  }

  @Override
  public boolean isStruct() throws IOException{
    return false;
  }

  @Override
  public boolean hasParser( final int index ) throws IOException{
    return ( childSchema instanceof IContainerField);
  }

  @Override
  public boolean hasParser( final String key ) throws IOException{
    return hasParser(0);
  }

  @Override
  public Object toJavaObject() throws IOException{
    List<Object> result = new ArrayList<Object>();
    if( hasParser(0) ){
      IntStream.range( 0 , size() )
        .forEach( i -> {
          try{
            result.add( getParser(i).toJavaObject() ); 
          }catch( IOException e ){
            throw new UncheckedIOException( "IOException in lambda." , e );
          }
        } );   
    }
    else{
      IntStream.range( 0 , size() )
        .forEach( i -> {
          try{
            result.add( get(i) );
          }catch( IOException e ){
            throw new UncheckedIOException( "IOException in lambda." , e );
          }
        } );
    }

    return result;
  }

}
