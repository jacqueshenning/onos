/*
 * Copyright 2018-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.openstackvtap.web;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.onlab.junit.TestUtils;
import org.onosproject.codec.CodecService;
import org.onosproject.codec.JsonCodec;
import org.onosproject.openstackvtap.api.OpenstackVtapNetwork;
import org.onosproject.openstackvtap.codec.OpenstackVtapNetworkCodec;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for openstack vtap network codec register.
 */
public final class OpenstackVtapNetworkCodecRegisterTest {

    private OpenstackVtapNetworkCodecRegister register;

    /**
     * Tests codec register activation and deactivation.
     */
    @Test
    public void testActivateDeactivate() {
        register = new OpenstackVtapNetworkCodecRegister();
        CodecService codecService = new TestCodecService();

        TestUtils.setField(register, "codecService", codecService);
        register.activate();

        assertEquals(OpenstackVtapNetworkCodec.class.getName(),
                codecService.getCodec(OpenstackVtapNetwork.class).getClass().getName());

        register.deactivate();

        assertNull(codecService.getCodec(OpenstackVtapNetwork.class));
    }

    private static class TestCodecService implements CodecService {

        private Map<String, JsonCodec> codecMap = Maps.newConcurrentMap();

        @Override
        public Set<Class<?>> getCodecs() {
            return ImmutableSet.of();
        }

        @Override
        public <T> JsonCodec<T> getCodec(Class<T> entityClass) {
            return codecMap.get(entityClass.getName());
        }

        @Override
        public <T> void registerCodec(Class<T> entityClass, JsonCodec<T> codec) {
            codecMap.put(entityClass.getName(), codec);
        }

        @Override
        public void unregisterCodec(Class<?> entityClass) {
            codecMap.remove(entityClass.getName());
        }
    }
}
