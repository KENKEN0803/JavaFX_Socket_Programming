package SocketProgramming;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

class ClientThread extends Thread {
    Socket socket;
    LinkedList<Socket> chat = new LinkedList<Socket>();

    ClientThread(Socket socket) {
        this.socket = socket;
        chat.add(socket);

    }

    @Override
    public void run() {
        try {
            InputStream inputstream = socket.getInputStream();
            while (true) {
                //블로킹
                byte[] data = new byte[512];

                int size = inputstream.read(data);
                String s = new String(data, 0, size, StandardCharsets.UTF_8);
                System.out.println(s + " 데이터 받음");
                new ChatThread(chat, s).start();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

class ChatThread extends Thread {
    Socket socket;
    String s;
    LinkedList<Socket> chat2 = new LinkedList<Socket>();

    ChatThread(LinkedList<Socket> chat2, String s) {
        this.chat2 = chat2;
        this.s = s;
    }

    @Override
    public void run() {

        for (Socket socket2 : chat2) {
            try {
                System.out.println(socket2.getInetAddress() + " 접속함");
                OutputStream outputStream = socket2.getOutputStream();
                byte[] data = s.getBytes();
                outputStream.write(data);
                System.out.println("데이터 보냄");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

class ConnectThread extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket mss = new ServerSocket();
            System.out.println("메인 서버 소켓 생성");
            //mss.bind(new InetSocketAddress("localhost", 5001));
            mss.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5001));
            System.out.println("바인딩 완료");

            while (true) {
                // 블로킹
                Socket ss = mss.accept();
                System.out.println(ss.getInetAddress() + " 접속함");
                new ClientThread(ss).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class Server2 extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage arg0) throws Exception {
        VBox root = new VBox();
        root.setPrefSize(400, 300); // 창 크기
        root.setSpacing(15);
        //-------------------------------------
        // 이 영역에 모든 코드가 들어감
        // 방법 2
        Button btn1 = new Button("서버 오픈");
        Button btn2 = new Button("버튼2");

        root.getChildren().addAll(btn1, btn2);

        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                new ConnectThread().start();
            }
        });

        //-------------------------------------
        Scene scene = new Scene(root);
        arg0.setScene(scene); // 위의 설정값들을 적용
        arg0.setTitle("Server"); // 제목
        arg0.show(); // 창 띄움

    }
}