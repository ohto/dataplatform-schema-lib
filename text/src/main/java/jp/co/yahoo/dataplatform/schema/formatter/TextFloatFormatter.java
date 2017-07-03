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

import jp.co.yahoo.dataplatform.schema.objects.PrimitiveObject;
import jp.co.yahoo.dataplatform.schema.parser.IParser;
import jp.co.yahoo.dataplatform.schema.utils.ByteArrayData;

public class TextFloatFormatter implements ITextFormatter{

  private byte[] convert( final float target ) throws IOException{
    return Float.valueOf( target ).toString().getBytes("UTF-8");
  }

  @Override
  public void write(final ByteArrayData buffer , final Object obj ) throws IOException{
    if( obj instanceof Short ){
      float target = ( (Short) obj ).floatValue();
      buffer.append( convert( target ) );
    }
    else if( obj instanceof Integer ){
      float target = ( (Integer) obj ).floatValue();
      buffer.append( convert( target ) );
    }
    else if( obj instanceof Long ){
      float target = ( (Long) obj ).floatValue();
      buffer.append( convert( target ) );
    }
    else if( obj instanceof Float ){
      float target = ( (Float) obj ).floatValue();
      buffer.append( convert( target ) );
    }
    else if( obj instanceof Double ){
      float target = ( (Double) obj ).floatValue();
      buffer.append( convert( target ) );
    }
    else if( obj instanceof PrimitiveObject){
      buffer.append( convert( ( (PrimitiveObject)obj ).getFloat() ) );
    }
  }

  @Override
  public void writeParser( final ByteArrayData buffer , final PrimitiveObject obj , final IParser parser ) throws IOException{
    buffer.append( convert( ( (PrimitiveObject)obj ).getFloat() ) );
  }

}
