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

import jp.co.yahoo.dataplatform.schema.design.IField;
import jp.co.yahoo.dataplatform.schema.design.FieldType;
import jp.co.yahoo.dataplatform.schema.objects.*;

public final class PrimitiveConverter{

  private PrimitiveConverter(){}

  public static PrimitiveObject textObjToPrimitiveObj( final IField type , final PrimitiveObject fromObj ) throws IOException{
    try{
      switch( type.getFieldType() ){
        case UNION:
        case ARRAY:
        case MAP:
        case STRUCT:
        case BYTES:
        case NULL:
          return fromObj;

        case BOOLEAN:
          return new BooleanObj( fromObj.getBoolean() );
        case BYTE:
          return new ByteObj( fromObj.getByte() );
        case DOUBLE:
          return new DoubleObj( fromObj.getDouble() );
        case FLOAT:
          return new FloatObj( fromObj.getFloat() );
        case INTEGER:
          return new IntegerObj( fromObj.getInt() );
        case LONG:
          return new LongObj( fromObj.getLong() );
        case SHORT:
          return new ShortObj( fromObj.getShort() );
        case STRING:
          return new StringObj( fromObj.getString() );

        default:
          return fromObj;
      }
    }catch( Exception e ){
      return NullObj.getInstance();
    }
  }

}
