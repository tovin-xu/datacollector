/**
 * Copyright 2016 StreamSets Inc.
 * <p>
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.stage.processor.spark;

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.lib.el.VaultEL;

import java.util.ArrayList;
import java.util.List;

@GenerateResourceBundle
public class SparkProcessorConfigBean {

  public static final String DEFAULT_THREAD_COUNT = "4";
  public static final String DEFAULT_APP_NAME = "SDC Spark App";

  @ConfigDef(
      required = false,
      type = ConfigDef.Type.NUMBER,
      defaultValue = DEFAULT_THREAD_COUNT,
      label = "Parallelism (Standalone Mode Only)",
      description = "Number of partitions to create per batch of records. Ignored in Cluster Mode.",
      group = "SPARK",
      displayPosition = 10
  )
  public int threadCount;

  @ConfigDef(
      required = false,
      type = ConfigDef.Type.STRING,
      defaultValue = DEFAULT_APP_NAME,
      label = "Application Name  (Standalone Mode Only)",
      description = "Name of the Application submitted to Spark. Ignored in Cluster Mode.",
      group = "SPARK",
      displayPosition = 20
  )
  public String appName;

  @ConfigDef(
      required = true,
      type = ConfigDef.Type.STRING,
      label = "Spark Transformer Class",
      description = "Class that implements SparkTransformer API.",
      group = "SPARK",
      displayPosition = 30
  )
  public String transformerClass;

  @ConfigDef(
      required = false,
      type = ConfigDef.Type.LIST,
      label = "Init Method Arguments",
      description = "Arguments to pass to the init method of the Transformer. " +
          "Use to make external connections or to read configuration or pre-existing data from external systems.",
      elDefs = VaultEL.class,
      group = "SPARK",
      displayPosition = 40
  )
  public List<String> preprocessMethodArgs = new ArrayList<>();

}
