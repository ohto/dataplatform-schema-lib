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
package jp.co.yahoo.dataplatform.schema.objects;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.node.BinaryNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class ObjectToJsonNode{

  public static JsonNode get( final Object obj ) throws IOException{

    if( obj instanceof PrimitiveObject ){
      return PrimitiveObjectToJsonNode.get( (PrimitiveObject)obj );
    }
    else if( obj instanceof String ){
      return new TextNode( (String)obj );
    }
    else if( obj instanceof Boolean ){
      return BooleanNode.valueOf( (Boolean)obj );
    }
    else if( obj instanceof Short ){
      return IntNode.valueOf( ( (Short)obj ).intValue() );
    }
    else if( obj instanceof Integer ){
      return IntNode.valueOf( (Integer)obj );
    }
    else if( obj instanceof Long ){
      return new LongNode( (Long)obj );
    }
    else if( obj instanceof Float ){
      return new DoubleNode( ( (Float)obj ).doubleValue() );
    }
    else if( obj instanceof Double ){
      return new DoubleNode( (Double)obj );
    }
    else if( obj instanceof byte[] ){
      return new BinaryNode( (byte[])obj );
    }
    else if( obj == null ){
      return NullNode.getInstance();
    }
    else{
      return new TextNode( obj.toString() );
    }
  }

}
