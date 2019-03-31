#########     IMPORTANTLY, the parameter <dictionary-file> have to be ***.json, because only Json file can be accept by the program    ##########

A sample command to start the server is:

> java ¨Cjar DictionaryServer.jar <port> <dictionary-file>

(For example: java -jar DictionaryServer.jar 4444 dic.json)

Where <port> is the port number where the server will listen for incoming client connections and <dictionary-file> is the path to the file containing the initial dictionary data.

When the client is launched, it creates a TCP socket bound to the server address and port number. This socket remains open for the duration of the client-server interaction.
All messages are sent/received through this socket.

A sample command to start the client is:

> java ¨Cjar DictionaryClient.jar <server-address> <server-port>

(For example: java -jar DictionaryClient.jar localhost 4444)

#########     IMPORTANTLY, the parameter <dictionary-file> have to be ***.json, because only Json file can be accept by the program    ##########