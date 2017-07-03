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
package jp.co.yahoo.dataplatform.schema.formatter;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;

import jp.co.yahoo.dataplatform.schema.objects.ObjectToJsonNode;
import jp.co.yahoo.dataplatform.schema.objects.PrimitiveObject;
import jp.co.yahoo.dataplatform.schema.parser.IParser;

public class JacksonMessageWriter implements IMessageWriter {

  private final ObjectMapper objectMapper;

  public JacksonMessageWriter(){
    objectMapper = new ObjectMapper();
  }

  @Override
  public byte[] create( final PrimitiveObject obj ) throws IOException{
    return objectMapper.writeValueAsBytes( ObjectToJsonNode.get( obj ) );
  }

  @Override
  public byte[] create( final List<Object> array ) throws IOException{
    return objectMapper.writeValueAsBytes( JacksonContainerToJsonObject.getFromList( array ) );
  }

  @Override
  public byte[] create( final Map<Object,Object> map ) throws IOException{
    return objectMapper.writeValueAsBytes( JacksonContainerToJsonObject.getFromMap( map ) );
  }

  @Override
  public byte[] create( final IParser parser ) throws IOException{
    JsonNode jsonNode;
    if( parser.isMap() || parser.isStruct() ){
      jsonNode = JacksonParserToJsonObject.getFromObjectParser( parser );
    }
    else if( parser.isArray() ){
      jsonNode = JacksonParserToJsonObject.getFromArrayParser( parser );
    }
    else{
      jsonNode = NullNode.getInstance();
    }

    return objectMapper.writeValueAsBytes( jsonNode );
  }

  @Override
  public void close() throws IOException{
  }

}
