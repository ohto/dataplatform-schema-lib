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
package jp.co.yahoo.dataplatform.schema.design;

import java.io.IOException;

import java.util.Map;
import java.util.LinkedHashMap;

import jp.co.yahoo.dataplatform.schema.utils.Properties;

public interface IField{

  String getName();

  Properties getProperties();

  FieldType getFieldType();

  default void merge( final IField target ) throws IOException{}

  default Map<Object,Object> toJavaObject() throws IOException{
    LinkedHashMap<Object,Object> schemaJavaObject = new LinkedHashMap<Object,Object>();
    schemaJavaObject.put( "name" , getName() );
    schemaJavaObject.put( "type" , getFieldType().toString() );
    schemaJavaObject.put( "properties" , getProperties().toMap() );
    return schemaJavaObject;
  }

}
