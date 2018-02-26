package serverjsh.Network;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewSessionLauncher implements Runnable{

    private int mPort;

    private static final Logger log = Logger.getLogger(NewSessionLauncher.class);

    public NewSessionLauncher(int mPort) {
        this.mPort = mPort;
    }

    @Override
    public void run() {

        //готовим фабрику потоков
        ExecutorService service = Executors.newCachedThreadPool();

        try {

            // привинтить сокет на локалхост
            ServerSocket server = new ServerSocket(mPort);

            log.info("Thread of accepting connect is started");

            // слушаем порт
            while (true) {

                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток и увеличиваем счётчик на единицу
                Socket socket = server.accept();
                try {
                    service.submit(new NewSessionThread(socket));
                    log.info(socket.getInetAddress() + ":" + socket.getPort() + " New connect");
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }

            }

        } catch (Exception e) {
            log.error(e);
        }
    }
}
