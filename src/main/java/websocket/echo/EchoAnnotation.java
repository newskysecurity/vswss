/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package websocket.echo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/echoAnnotation")
public class EchoAnnotation {
	public static final String auth = "AuthMagic";
	public static final String notAuthed = "{'status':403}";
	public static final String success = "{'status':200}";
	public static Map<Session, Reminder> speedSubscribers = new HashMap<Session, Reminder>();
	private String processMessage(String message, Session session) {
		String response = success;
		if (!message.contains("'auth':'" + auth + "'")) {
			return notAuthed;
		}
		
		if (message.contains("'subscribe':'root.engine.speed'")) {
			Reminder reminder = speedSubscribers.get(session);
			if (reminder != null) {
				// already subscribed, TODO: provide feedback
			} else {
				SpeedServer speed = new SpeedServer(session);
				reminder = new Reminder(3, speed);
				speedSubscribers.put(session, reminder);
			}
		} else if (message.contains("'unsubscribe':'root.engine.speed'")) {
			Reminder reminder = speedSubscribers.get(session);
			if (reminder == null) {
				// already unsubscribed or not subscribed, ignore
			} else {
				reminder.cancel();
				speedSubscribers.remove(session);
			}
		}
		
		return response;
	}
	
    @OnMessage
    public void replyTextMessage(Session session, String msg, boolean last) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(processMessage(msg, session), last);
            }
        } catch (IOException e) {
            try {
                session.close();
            } catch (IOException e1) {
                // Ignore
            }
        }
    }

    public void echoTextMessage(Session session, String msg, boolean last) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(msg, last);
            }
        } catch (IOException e) {
            try {
                session.close();
            } catch (IOException e1) {
                // Ignore
            }
        }
    }

    @OnMessage
    public void echoBinaryMessage(Session session, ByteBuffer bb,
            boolean last) {
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendBinary(bb, last);
            }
        } catch (IOException e) {
            try {
                session.close();
            } catch (IOException e1) {
                // Ignore
            }
        }
    }

    /**
     * Process a received pong. This is a NO-OP.
     *
     * @param pm    Ignored.
     */
    @OnMessage
    public void echoPongMessage(PongMessage pm) {
        // NO-OP
    }
}
