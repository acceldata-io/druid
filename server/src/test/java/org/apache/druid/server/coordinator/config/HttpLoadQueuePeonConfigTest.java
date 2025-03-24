/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.server.coordinator.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.matchers.ThrowableMessageMatcher;

public class HttpLoadQueuePeonConfigTest
{
  @Test
  public void testValidateBatchSize() throws JsonProcessingException
  {
    ObjectMapper jsonMapper = new ObjectMapper();

    MatcherAssert.assertThat(
        Assert.assertThrows(ValueInstantiationException.class, () ->
            jsonMapper.readValue("{\"batchSize\":0}", HttpLoadQueuePeonConfig.class)
        ),
        CoreMatchers.allOf(
            CoreMatchers.instanceOf(ValueInstantiationException.class),
            ThrowableMessageMatcher.hasMessage(
                CoreMatchers.containsString("Batch size must be greater than 0.")
            )
        )
    );

    HttpLoadQueuePeonConfig emptyConfig = jsonMapper.readValue(
        "{}",
        HttpLoadQueuePeonConfig.class
    );
    Assert.assertNull(emptyConfig.getBatchSize());

    HttpLoadQueuePeonConfig config = jsonMapper.readValue(
        "{\"batchSize\":2}",
        HttpLoadQueuePeonConfig.class
    );
    Assert.assertEquals(2, config.getBatchSize().intValue());
  }
}
