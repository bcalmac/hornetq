/*
 * Copyright 2009 Red Hat, Inc.
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *    http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.hornetq.core.remoting.impl.invm;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A TransportConstants
 * 
 * @author <a href="mailto:tim.fox@jboss.com">Tim Fox</a>
 *
 */
public class TransportConstants
{
   public static final String SERVER_ID_PROP_NAME = "serverid";
   
   public static final Set<String> ALLOWABLE_CONNECTOR_KEYS;

   public static final Set<String> ALLOWABLE_ACCEPTOR_KEYS;

   static
   {
      Set<String> allowableAcceptorKeys = new HashSet<String>();
      allowableAcceptorKeys.add(SERVER_ID_PROP_NAME);   
      
      ALLOWABLE_CONNECTOR_KEYS = Collections.unmodifiableSet(allowableAcceptorKeys);
      
      Set<String> allowableConnectorKeys = new HashSet<String>();
      allowableConnectorKeys.add(SERVER_ID_PROP_NAME);
            
      ALLOWABLE_ACCEPTOR_KEYS = Collections.unmodifiableSet(allowableConnectorKeys);
   }
}
