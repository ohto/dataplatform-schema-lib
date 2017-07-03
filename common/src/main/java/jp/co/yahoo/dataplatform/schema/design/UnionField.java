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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import jp.co.yahoo.dataplatform.schema.utils.Properties;

public class UnionField implements INamedContainerField {

  private final String name;
  private final Properties properties;
  private final List<String> keyList = new ArrayList<String>();
  private final Map<String,IField> fieldContainer = new HashMap<String,IField>();

  public UnionField( final String name ){
    this.name = name;
    properties = new Properties();
  }

  public UnionField( final String name , final Properties properties ){
    this.name = name;
    this.properties = properties;
  }

  @Override
  public String getName(){
    return name;
  }

  @Override
  public IField getField(){
    throw new UnsupportedOperationException( "UnionField does not have a default field." );
  }

  @Override
  public void set( final IField field ) throws IOException{
    String fieldName = field.getClass().getName();
    if( fieldContainer.containsKey( fieldName ) ){
      throw new IOException( fieldName + " is already set." );
    }

    keyList.add( fieldName );
    fieldContainer.put( fieldName , field );
  }

  @Override
  public IField get( final String key ) throws IOException{
    return fieldContainer.get( key );
  }

  @Override
  public boolean containsKey( final String key ) throws IOException{
    return fieldContainer.containsKey( key );
  }

  @Override
  public String[] getKeys() throws IOException{
    String[] keyArray = new String[ keyList.size() ];
    return keyList.toArray( keyArray );
  }

  @Override
  public Properties getProperties(){
    return properties;
  }

  @Override
  public FieldType getFieldType(){
    return FieldType.UNION;
  }

}
