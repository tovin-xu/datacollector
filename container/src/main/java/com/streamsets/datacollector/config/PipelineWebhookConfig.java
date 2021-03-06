/*
 * Copyright 2017 StreamSets Inc.
 *
 * Licensed under the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.streamsets.datacollector.config;

import com.streamsets.pipeline.api.ConfigDef;

public class PipelineWebhookConfig extends WebhookCommonConfig {
  @ConfigDef(
      required = false,
      type = ConfigDef.Type.TEXT,
      label = "Payload",
      defaultValue = "{\n  \"text\" : \"Pipeline '{{PIPELINE_TITLE}}' state changed to {{PIPELINE_STATE}} at " +
          "{{TIME}}. \\n <{{PIPELINE_URL}}|Click here> for details!\"\n}",
      description = "Data that should be included as a part of the Webhook request",
      displayPosition = 240,
      lines = 2,
      dependsOn = "httpMethod",
      triggeredByValue = { "POST", "PUT", "DELETE" },
      group = "NOTIFICATIONS",
      mode = ConfigDef.Mode.JSON
  )
  public String payload = "";
}
