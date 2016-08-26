package websocket.echo;

import java.io.IOException;

import javax.websocket.Session;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;

public class SpeedServer implements ContinuousTask {
	Session session;
	Async async;
	
	int mph = 0;
	int delta = 10;
	
	public SpeedServer(Session session) {
		this.session = session;
		this.async = session.getAsyncRemote();
	}
	@Override
	public void remind() {
		if (mph == 80) {
			delta = -10;
		} else if (mph == 0) {
			delta = 10;
		}
		mph += delta;
        if (session.isOpen()) {
            async.sendText("{'mph':" + mph + "}", sendHandler);
        }
	}

    /**
     * SendHandler that will continue to send buffered messages.
     */
    private final SendHandler sendHandler = new SendHandler() {
        @Override
        public void onResult(SendResult result) {
            if (!result.isOK()) {
                // Message could not be sent. In this case, we don't
                // set isSendingMessage to false because we must assume the connection
                // broke (and onClose will be called), so we don't try to send
                // other messages.
                // As a precaution, we close the session (e.g. if a send timeout occured).
                // TODO: session.close() blocks, while this handler shouldn't block.
                // Ideally, there should be some abort() method that cancels the
                // connection immediately...
                try {
                    session.close();
                } catch (IOException ex) {
                    // Ignore
                }
            }
        }
    };
}
