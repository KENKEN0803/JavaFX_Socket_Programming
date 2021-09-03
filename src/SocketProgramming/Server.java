package SocketProgramming;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class ServerThread extends Thread {
    Socket ss;

    ServerThread(Socket ss) {
        this.ss = ss;
    }

    @Override
    public void run() {
        InputStream inputStream = null;

        while (true) {
            try {
                inputStream = ss.getInputStream();
                byte[] data = new byte[512];
                int size = inputStream.read(data);//블로킹
                String s = new String(data, 0, size, StandardCharsets.UTF_8);
                System.out.println(s + " 데이터 받음");

            } catch (IOException e) {
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
            System.out.println("메인 서버 소캣 생성!");
            mss.bind(new InetSocketAddress(InetAddress.getLocalHost(), 5001));
            System.out.println("바인딩 완료");

            while (true) {
                Socket ss = mss.accept(); // 블로킹
                System.out.println("누군가 접속 합니다...");
                new ServerThread(ss).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Server extends Application {
    Button btn1 = new Button("서버 오픈");
    Button btn2 = new Button("테스트2");
    @Override
    public void start(Stage arg0) {
        VBox root = new VBox(); // root 최상단
        root.setPrefSize(400, 300); // 창의 가로길이 세로길이 a설정.
        root.setSpacing(15); // 사이 간격을 띄운다.
        // -----------------------------------------------------------------------

        btn1.setOnAction(actionEvent -> {
            new ConnectThread().start();
        });

        root.getChildren().addAll(btn1, btn2); // 한꺼번에 등록시키는 addAll()
        Scene scene = new Scene(root); // scene : 한장면을 그리기위한 바탕.
        arg0.setScene(scene);
        arg0.setTitle("Server");// 제목
        arg0.show();
//		---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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