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

public class OrcBytesFormatter implements IOrcFormatter{

  @Override
  public Object write( final Object obj ) throws IOException{
    if( obj instanceof byte[] ){
      return obj;
    }
    else if( obj instanceof String ){
      return ( (String)obj ).getBytes( "UTF-8" );
    }
    else if( obj instanceof PrimitiveObject){
      return ( (PrimitiveObject)obj ).getBytes();
    }

    return null;
  }

  @Override
  public Object writeFromParser( final PrimitiveObject obj , final IParser parser ) throws IOException{
    return obj.getBytes();
  }

  @Override
  public void clear() throws IOException{

  }

}
