/**
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
package com.streamsets.pipeline.stage.processor.jdbctee;

import com.streamsets.pipeline.api.Config;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.impl.Utils;
import com.streamsets.pipeline.lib.jdbc.JdbcBaseUpgrader;

import java.util.ArrayList;
import java.util.List;

public class JdbcTeeUpgrader extends JdbcBaseUpgrader{

  @Override
  public List<Config> upgrade(String library, String stageName, String stageInstance, int fromVersion, int toVersion, List<Config> configs) throws StageException {
    switch(fromVersion) {
      case 1:
        upgradeV1toV2(configs);
        // fall through
        break;
      default:
        throw new IllegalStateException(Utils.format("Unexpected fromVersion {}", fromVersion));
    }
    return configs;
  }

  private void upgradeV1toV2(List<Config> configs) {
    List<Config> configsToRemove = new ArrayList<>();
    List<Config> configsToAdd = new ArrayList<>();

    for (Config config : configs) {
      if (config.getName().equals("useMultiRowInsert")){
        boolean val = (Boolean)config.getValue();
        configsToAdd.add(new Config("useMultiRowOp", val? true : false));
        configsToRemove.add(config);
        break;
      }
    }
    configs.addAll(configsToAdd);
    configs.removeAll(configsToRemove);

    // added default operation, unsupported operation action, and maxPrepStmtCache
    configs.add(new Config("defaultOperation", "INSERT"));
    configs.add(new Config("unsupportedAction", "DISCARD"));
    configs.add(new Config("maxPrepStmtCache", -1));
  }

}
