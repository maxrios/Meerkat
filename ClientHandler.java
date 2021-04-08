import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.Socket;


public class ClientHandler extends Thread {
    private GUI gui;
    private int port;

    public ClientHandler(GUI gui, int port) {
        this.gui = gui;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            initClient();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void initClient() throws Exception {
        Socket socket = new Socket("localhost", port);
        
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        
        gui.setOutputStream(out);
        gui.setTitle("P2P APP to " + port);


        String message = "";
        while(true) {
            
            // if (gui.hasIncomingFile()) {
            //     System.out.println("file seen");
            //     receiveFile(gui.getRequestedFile(), in);
            //     gui.resetHasIncomingFile();
            // } else 
            if((message = in.readLine()) != null && !message.isEmpty()) {
                gui.addToScreen("peer@" + port + ": " + message, true); 
            }
        }
    }

    public static void receiveFile(String fileName, DataInputStream in, long size) throws Exception {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream("./downloads/" + fileName);

        byte[] buffer = new byte[4*1024];
        while (size > 0 && (bytes = in.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer,0,bytes);
            size -= bytes;      // read upto file size
        }
        fileOutputStream.close();
    }
}
