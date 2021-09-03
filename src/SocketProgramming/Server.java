package SocketProgramming;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class SubmitThread extends Thread {
    Server server;
    String line;

    SubmitThread(Server server, String line) {
        this.server = server;
        this.line = line;
    }

    @Override
    public void run() {
        try {
            Socket cs = new Socket();

            String ServerIp = "61.83.118.69";
            int port = 5002;
            cs.connect(new InetSocketAddress(ServerIp, port));
            OutputStream outputStream = cs.getOutputStream();
            byte[] data = line.getBytes(StandardCharsets.UTF_8); // 가져온 데이터를 배열에 담음
            outputStream.write(data); // 배열을 아웃풋스트림으로 전송
            System.out.println(line + "전송 완료"); // 콘솔에 출력
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    Socket ss;
    Server server;

    ServerThread(Socket ss, Server server) {
        this.ss = ss;
        this.server = server;
    }

    @Override
    public void run() {
        InputStream inputStream = null;

        while (true) {
            try {
//                ArrayList<String> list = new ArrayList<String>();

                inputStream = ss.getInputStream();
                byte[] data = new byte[512];
                int size = inputStream.read(data);//블로킹
                String inputData = new String(data, 0, size, StandardCharsets.UTF_8);
                String clientAddress = ss.getInetAddress().getHostName();
                String line = clientAddress + " : " + inputData + "\n";
                server.textArea.appendText(line);
                new SubmitThread(server, line).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ConnectThread extends Thread {

    Server server;

    ConnectThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ServerSocket mss = new ServerSocket();
            server.textArea.appendText("소캣 생성 완료\n");
            mss.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5001));
            server.textArea.appendText("바인딩 완료\n");

            while (true) {
                Socket ss = mss.accept(); // 블로킹
                new ServerThread(ss, server).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Server extends Application {
    Button btn1 = new Button("서버 오픈");
    Button btn2 = new Button("테스트2");
    TextArea textArea = new TextArea();

    @Override
    public void start(Stage arg0) {
        VBox root = new VBox(); // root 최상단
        root.setPrefSize(400, 300); // 창의 가로길이 세로길이 a설정.
        root.setSpacing(15); // 사이 간격을 띄운다.
        //-----------------------------------------------------------------------

        btn1.setOnAction(actionEvent -> {
            new ConnectThread(this).start();
        });


        root.getChildren().addAll(btn1, btn2, textArea); // 한꺼번에 등록시키는 addAll()
        Scene scene = new Scene(root); // scene : 한장면을 그리기위한 바탕.
        arg0.setScene(scene);
        arg0.setTitle("Server");// 제목
        arg0.show();
        //-----------------------------------------------------------------------
        arg0.setTitle("서버");// 제목
        arg0.show();
    }

    public static void main(String[] args) {
        System.out.println("서버 스타트");
        launch();
    }
}


//public class Server {
//    public static void main(String[] args) {
//        System.out.println("서버 스타트");
//        try {
//            ServerSocket mss = new ServerSocket();
//            System.out.println("메인 서버 소캣 생성!");
//            mss.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5001));
//            System.out.println("바인딩 완료");
//            Socket ss = mss.accept(); // 블로킹
//            System.out.println("누군가 접속 합니다...");
//            InputStream inputStream = ss.getInputStream();
//            byte[] data = new byte[512];
//
//            int size = inputStream.read(data);//블로킹
//            String s = new String(data, 0, size, StandardCharsets.UTF_8);
//            System.out.println(s + " 데이터 받음");
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        new Scanner(System.in).nextInt();
//        System.out.println("서버 엔드");
//    }
//}