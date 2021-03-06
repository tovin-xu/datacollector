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
package com.streamsets.pipeline.lib.httpsource;

import com.streamsets.pipeline.api.PushSource;
import com.streamsets.pipeline.lib.http.HttpConfigs;
import com.streamsets.pipeline.lib.http.HttpReceiver;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class TestAbstractHttpServerPushSource {

  static class ForTestAbstractHttpServerPushSource extends AbstractHttpServerPushSource<HttpReceiver> {
    public ForTestAbstractHttpServerPushSource(HttpConfigs httpConfigs, HttpReceiver receiver) {
      super(httpConfigs, receiver);
    }

    public PushSource.Context getContext() {
      return super.getContext();
    }

  }

  @Test
  public void testSource() throws Exception {
    HttpConfigs httpConfigs = Mockito.mock(HttpConfigs.class);
    Mockito.when(httpConfigs.getMaxConcurrentRequests()).thenReturn(1);
    HttpReceiver receiver = Mockito.mock(HttpReceiver.class);

    // we need this subclass for mockito to be able to spy getContex()
    ForTestAbstractHttpServerPushSource source = new ForTestAbstractHttpServerPushSource(httpConfigs, receiver);

    source = Mockito.spy(source);
    PushSource.Context context = Mockito.mock(PushSource.Context.class);
    Mockito.doReturn(context).when(source).getContext();

    Mockito.doNothing().when(source).dispatchHttpReceiverErrors(Mockito.anyLong());

    Assert.assertEquals(1, source.getNumberOfThreads());
    Mockito.verify(httpConfigs, Mockito.times(1)).getMaxConcurrentRequests();

    //stopped
    Mockito.doReturn(true).when(context).isStopped();
    source.produce(null, 0);
    Mockito.verify(source, Mockito.never()).dispatchHttpReceiverErrors(Mockito.eq(100L));

    //running
    Mockito.doReturn(false).doReturn(true).when(context).isStopped();
    source.produce(null, 0);
    Mockito.verify(source, Mockito.times(1)).dispatchHttpReceiverErrors(Mockito.eq(100L));

  }
}
