package tcp;

// saleem, Nov1998

// saleem, Jan 2015, update and compile check for CS1006 with javac 1.8.0_31

// A simple TCP text chat program.

// Note that this program is written to demonstrate the use of the
// various i/o calls and does not necessarily have the best structure
// for extending to a full application!

import java.io.*;
import java.net.*;

enum ChatState {
  idle,     // wait for session to be established
  chatting  // chat session in progress
}

class Numbers {
  final static int chatPort   =  4242; // the port number to be used
  final static int soTimeout  =    10; // ms to wait for socket read
  final static int readRetry  =    10; // # re-try of handshake
  final static int sleepTime  =   200; // ms to sleep - 200 is fine
  final static int bufferSize =   128; // # chars in line 
}

class TcpTextChat
{
  static String         _quit   = "quit"; // user command
  static String         _prompt = ">";
  static String         _busy   = "::busy::";

  static String         _me;          // identity to be used for chat
  static String         _otherPerson; // the other person

  static ServerSocket   _listen;      // to receive from network
  static BufferedReader _keyboard;    // to receive from keyboard

  static ChatState      _state;
  static Socket         _call;        // for chat

  InputStream           _in;  // network input stream
  OutputStream          _out; // network output stream 

  public static void main(String[] argv)
    throws InterruptedException, IOException
  {
    if (argv.length != 0) {
      report("usage: TcpTextChat");
      System.exit(0);
    }

    startup();

    TcpTextChat chat = null; // a chat session
    boolean     quit = false;

    while(!quit) {

      String lineKeyboard = null;
 
      // input from user?
      lineKeyboard = readKeyboard();

      try {
        switch (_state) {

        case idle:
        // wait for either:
        // - user typing the name for a remote host
        // - an incoming connection

        // assume user has given the name of a remote host
        if (checkQuit(lineKeyboard)) {
          quit = true;
          shutdown();
          System.exit(0);            
        }
        else chat = makeCall(lineKeyboard);

        if (chat == null) chat = checkIncomingCall();

        if (chat != null) _state = ChatState.chatting;
        break;

        case chatting:
        // check for other chat requests from network
        checkIncomingCall();

        // anything to print?
        String lineNetwork = chat.recv();
        chat.print(lineNetwork);

        // anything to send?
        chat.send(lineKeyboard);

        if (checkQuit(lineKeyboard) || checkQuit(lineNetwork)) {
          chat.endCall();
          chat = null;
          _state = ChatState.idle;
        }
        break;
        } // switch

      } // try

      catch (java.io.IOException e) {
        String eName = e.getClass().getName();
        if (eName != "java.net.SocketTimeoutException") {
          error("main() problem: " + eName);
          throw e;
        }
      }

      // avoid CPU overhead of continuous looping here
      Thread.sleep(Numbers.sleepTime);
 
    } // while

    System.exit(0);
  }


  TcpTextChat(Socket call, String otherPerson)
    throws SocketException
  {
    _call = call;
    _otherPerson = otherPerson;

    try {
      _in = call.getInputStream();
      _out = call.getOutputStream();
    }
    catch (java.io.IOException e) {
      error("TcpTextChat(): io problem " + e.getClass().getName());
      System.exit(-1);
    }

    report("new call");
    report("local: " +
            _call.getLocalAddress().getHostName() + " " +
            _call.getLocalAddress().getHostAddress() +
            " port: " + _call.getLocalPort());
    report("remote: " +
            _call.getInetAddress().getHostName() + " " +
            _call.getInetAddress().getHostAddress() +
            " port: " + _call.getPort());
    report("chatting to: " + _otherPerson);
  }
 
  static boolean checkQuit(String s)
  { return s != null ? s.compareTo(_quit) == 0 : false; }

  static boolean checkBusy(String s)
  { return s != null ? s.compareTo(_busy) == 0 : false; }

  static void error(String s) { System.err.println("-!- " + s); }
  static void report(String s) { System.out.println("-*- " + s); }
  static void chatting(String s) { System.out.println("--> " + s); }

  static void startup()
  {
    // create listening socket
    try {
      _listen = new ServerSocket(Numbers.chatPort);
      _listen.setSoTimeout(Numbers.soTimeout);
    }
    catch (java.io.IOException e) {
      error("server failed! " + e.getClass().getName());
      System.exit(-1);
    }
    // local input
    _keyboard = new BufferedReader(new InputStreamReader(System.in),
                                  Numbers.bufferSize); // restrict line length

    _me = System.getProperty("user.name");
    report("greetings " + _me);
    report("started server");
    InetAddress h;
    String s = "(unknown)";
    try {
      h = InetAddress.getLocalHost();
      s = h.getCanonicalHostName();
      report("host: " + h.getByName(s));
    }
    catch (java.net.UnknownHostException e) {
      s = "(unknown)";
      error("startup(): cannot get hostname!");
    }
    report("port: " + _listen.getLocalPort());
    report("ready ...");

    _state = ChatState.idle;
  }

  static void shutdown()
  {
    try {
      if (_call != null) { _call.close(); }
      _listen.close();
      report("shutdown ... bye ...");
    }
    catch (IOException e) {
      error("io problem() " + e.getClass().getName());
      System.exit(-1);
    }
  }

  static TcpTextChat checkIncomingCall()
    throws IOException
  {
    TcpTextChat chat = null;
    Socket connection = null;

    try {
      // wait for a connection request
      connection = _listen.accept();
      connection.setSoTimeout(Numbers.soTimeout);
      connection.setTcpNoDelay(true);
      if (!handshake(connection)) return null;

      switch (_state) {

      case idle:
        chat = new TcpTextChat(connection, _otherPerson);
        _state = ChatState.chatting;
        break; // idle

      // already chatting so send "busy" signal
      case chatting:
        // handshake() does the following:
        // - sends a "busy" signal
        // closes the connection
        break;

      } // switch
    } // try

    catch (java.net.SocketTimeoutException e) {
      // ignore
    }
    catch (java.net.UnknownHostException e) {
      report("checkIncomingCall(): cannot get hostname of remote host");
    }
    catch (java.io.IOException e) {
      error("checkIncomingCall() problem: " + e.getClass().getName());
      throw e;
    }

    return chat;
  }


  static TcpTextChat makeCall(String host)
  {
    TcpTextChat chat = null;
    Socket call = null;
    String callee = null;

    if (host != null) {

      try {
        call = new Socket(host, Numbers.chatPort);
        call.setSoTimeout(Numbers.soTimeout);
        call.setTcpNoDelay(true);
        if (!handshake(call)) return null;
        chat = new TcpTextChat(call, _otherPerson);
      } // try

      catch (java.net.UnknownHostException e) {
        error("makeCall(): unknown host " + host);
        return null;
      }
      catch (java.io.IOException e) {
        error("makeCall(): i/o problem " + e.getClass().getName());
        return null;
      }

    } // if

    if (chat != null) {
      report("new chat!");
    }

    return chat;
  }


  static String readNetwork(Socket connection)
    throws IOException
  { return connection == null ? null : recvLine(connection.getInputStream()); }


  static void writeNetwork(Socket connection, String line)
    throws IOException
  { sendLine(connection.getOutputStream(), line); }


  static String readKeyboard()
  {
    String line = null;

    try {
      if (_keyboard.ready()) line = _keyboard.readLine();
    }
    catch (java.io.IOException e) {
      error("readKeyboard(): problem reading! " + e.getClass().getName());
      System.exit(-1);
    }

    return line;
  }


  static void sendLine(OutputStream out, String line)
  {
    if (out == null || line == null || line.length() < 0) return;

    try {
      int l = line.length();
      if (l > Numbers.bufferSize) {
        report("line too long (" + l + ") truncated to " + Numbers.bufferSize);
        l = Numbers.bufferSize;
      }
      out.write(line.getBytes(), 0, l);
    }
    catch (java.io.IOException e) {
      error("sendLine() problem " + e.getClass().getName());
    }
  }


  static String recvLine(InputStream in)
    throws IOException
  {
    if (in == null) { return null; }

    String line = null;

    try {
      byte buffer[] = new byte[Numbers.bufferSize];
      int l = in.read(buffer);
      if (l > 0) line = new String(buffer, 0, l);
    }
    catch (java.net.SocketTimeoutException e) {
      // ignore
    }
    catch (java.io.IOException e) {
      String eName = e.getClass().getName();
      if (eName != "java.io.InterruptedIOException") {
        error("recvLine() problem " + eName);
        throw e;
      }
    }

    return line;
  }


  static boolean handshake(Socket connection)
  {
    boolean receivedName = false;

    try {
      // already chatting ...
      if (_state == ChatState.chatting) {
        writeNetwork(connection, _busy);
        connection.close();
        return false;
      }

      writeNetwork(connection, _me);

      for(int i = 0; (i < Numbers.readRetry) && !receivedName; ++i) {
        String line = readNetwork(connection);
        if (line != null) {
          if (checkBusy(line)) {
            report("other end is busy - try again later.");
            break;
          }
          else {
            _otherPerson = line;
            receivedName = true;
          }
        }
        else Thread.sleep(Numbers.sleepTime);
      }

    } // try

    catch (java.lang.InterruptedException e) {
      // ignore - just means that we are still waiting
    }
    catch (java.io.IOException e) {
      error("handshake(): io exception '" + e.getClass().getName());
    }

    return receivedName;
  }

  void print(String line)
  { if (line != null) chatting(_otherPerson + " " + _prompt + " " + line); }

  void send(String line)
  { sendLine(_out, line); }

  String recv()
    throws IOException
  { return recvLine(_in); }

  void endCall()
  {
    try {
      if (_state == ChatState.chatting) {
        report("closing call with " + _otherPerson);
        _call.close();
        _call = null;
        _state = ChatState.idle;
      }
    }
    catch (java.io.IOException e) {
      error("endCall(): io problem " + e.getClass().getName());
      System.exit(-1);
    }
  }

} // TcpTextChat
