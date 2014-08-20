/*
 * Copyright 2005-2014 Red Hat, Inc.
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
package org.hornetq.tests.integration.openwire.amq;

import java.util.Arrays;
import java.util.Collection;

import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.command.ActiveMQDestination;
import org.hornetq.tests.integration.openwire.BasicOpenWireTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * adapted from: org.apache.activemq.JMSConsumerTest
 *
 * @author <a href="mailto:hgao@redhat.com">Howard Gao</a>
 *
 */
@RunWith(Parameterized.class)
public class JMSConsumer3Test extends BasicOpenWireTest
{
   @Parameterized.Parameters(name = "deliveryMode={0} ackMode={1} destinationType={2}")
   public static Collection<Object[]> getParams()
   {
      return Arrays.asList(new Object[][] {
         {DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.QUEUE_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.TOPIC_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.TEMP_QUEUE_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.TEMP_TOPIC_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.QUEUE_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.TOPIC_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.TEMP_QUEUE_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.TEMP_TOPIC_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.QUEUE_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.TOPIC_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.TEMP_QUEUE_TYPE},
         {DeliveryMode.NON_PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.TEMP_TOPIC_TYPE},
         {DeliveryMode.PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.QUEUE_TYPE},
         {DeliveryMode.PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.TOPIC_TYPE},
         {DeliveryMode.PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.TEMP_QUEUE_TYPE},
         {DeliveryMode.PERSISTENT, Session.AUTO_ACKNOWLEDGE, ActiveMQDestination.TEMP_TOPIC_TYPE},
         {DeliveryMode.PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.QUEUE_TYPE},
         {DeliveryMode.PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.TOPIC_TYPE},
         {DeliveryMode.PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.TEMP_QUEUE_TYPE},
         {DeliveryMode.PERSISTENT, Session.DUPS_OK_ACKNOWLEDGE, ActiveMQDestination.TEMP_TOPIC_TYPE},
         {DeliveryMode.PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.QUEUE_TYPE},
         {DeliveryMode.PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.TOPIC_TYPE},
         {DeliveryMode.PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.TEMP_QUEUE_TYPE},
         {DeliveryMode.PERSISTENT, Session.CLIENT_ACKNOWLEDGE, ActiveMQDestination.TEMP_TOPIC_TYPE}
      });
   }

   public ActiveMQDestination destination;
   public int deliveryMode;
   public int prefetch;
   public int ackMode;
   public byte destinationType;
   public boolean durableConsumer;

   public JMSConsumer3Test(int deliveryMode, int ackMode, byte destinationType)
   {
      this.deliveryMode = deliveryMode;
      this.ackMode = ackMode;
      this.destinationType = destinationType;
   }

   @Test
   public void testMutiReceiveWithPrefetch1() throws Exception
   {
      // Set prefetch to 1
      ((ActiveMQConnection)connection).getPrefetchPolicy().setAll(1);
      connection.start();

      // Use all the ack modes
      Session session = connection.createSession(false, ackMode);
      destination = createDestination(session, destinationType);
      MessageConsumer consumer = session.createConsumer(destination);

      // Send the messages
      sendMessages(session, destination, 4);

      System.out.println("messages are sent.");
      // Make sure 4 messages were delivered.
      Message message = null;
      for (int i = 0; i < 4; i++)
      {
         message = consumer.receive(5000);
         System.out.println("message received: " + message + " ack mode: " + ackMode);
         assertNotNull(message);
      }
      assertNull(consumer.receiveNoWait());
      message.acknowledge();
   }

}

