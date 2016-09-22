# vswss
Vehicle Signal through WebSocket Secure

Demo serving vehicle signal through WebSocket secure. This is based on apache tomcat's echo websocket project.

Steps:
1.Open https://auto.newskysecurity.com/speed.xhtml
2.Click Connect to open websocket secure connection with server
3.Once connection is establishes, click 'Send message' to subscribe to signal 'root.engine.speed' using auth token 'AuthMagic'
4.Simulated vehicle speed signal will then keep flowing back to client, and speedometer will be updated
5.To unsubscribe, modify the message and change 'SUBSCRIBE' to 'UNSUBSCRIBE', click 'Send message', and vehicle speed signal will stop
6.To verify invalid auth token, change auth token from AuthMagic to something else, click 'Send message', and server will return status 403
