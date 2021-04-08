import java.io.IOException;

public final class Peer {

    public static void main(String[] args) throws IOException {
        int myPort = Integer.parseInt(args[0]);
        // Always start server handler first. 
        new ServerHandler(myPort).start();
        
        if (args.length != 1) {
            GUI gui = new GUI();
            gui.loadGUI();
            int otherPort = Integer.parseInt(args[1]);
            new ClientHandler(gui, otherPort).start();
        }
    }
}