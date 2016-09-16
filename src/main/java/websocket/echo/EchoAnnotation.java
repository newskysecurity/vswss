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

import org.w3c.connectedcars.Action;
import org.w3c.connectedcars.ActionItem;
import org.w3c.connectedcars.Reminder;
import org.w3c.connectedcars.SpeedServer;
import org.w3c.connectedcars.Request;
import org.w3c.connectedcars.Response;
import org.w3c.connectedcars.RequestResponseParser;

@ServerEndpoint("/websocket/echoAnnotation")
public class EchoAnnotation {
	public static final String auth = "AuthMagic";
	public static final Response notAuthedResp = new Response(403, null, null);
	public static final Response successResp = new Response(200, null, null);
	public static final String notAuthed = RequestResponseParser.toJson(notAuthedResp);
	public static final String success = RequestResponseParser.toJson(successResp);
	public static Map<Session, Reminder> speedSubscribers = new HashMap<Session, Reminder>();
	private String processMessage(String message, Session session) {
		String response = success;
		Request request = null;
		try {
			request = RequestResponseParser.fromJsonToRequest(message);
			System.out.println(request);
		} catch (Exception e) {
			//TODO: log the exception
		}
		
		if (null == request || !auth.equals(request.getAuth())) {
			return notAuthed;
		}
		
		ActionItem item = request.getItem();
		
		if (null != item.getSignal() && "root.engine.speed".equals(item.getSignal().getPath())) {
			if (Action.SUBSCRIBE.equals(item.getAction())) {
				Reminder reminder = speedSubscribers.get(session);
				if (reminder != null) {
					// already subscribed, TODO: provide feedback
				} else {
					SpeedServer speed = new SpeedServer(session);
					reminder = new Reminder(3, speed);
					speedSubscribers.put(session, reminder);
				}
			} else if (Action.UNSUBSCRIBE.equals(item.getAction())) {
				Reminder reminder = speedSubscribers.get(session);
				if (reminder == null) {
					// already unsubscribed or not subscribed, ignore
				} else {
					reminder.cancel();
					speedSubscribers.remove(session);
				}
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
