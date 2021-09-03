package SocketProgramming;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class ClientThread extends Thread {
    @Override
    public void run() {
        try {
            Socket cs = new Socket();
            System.out.println("문자 입력...");
            String message = new Scanner(System.in).nextLine();
            cs.connect(new InetSocketAddress("61.83.118.69", 5001));
            OutputStream outputStream = cs.getOutputStream();
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            outputStream.write(data);
            System.out.println("전송 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Client extends Application {
    Button btn1 = new Button("테스트");
    Button btn2 = new Button("서버에 접속하기");

    @Override
    public void start(Stage arg0) throws Exception {
        VBox root = new VBox(); // root 최상단
        root.setPrefSize(400, 300); // 창의 가로길이 세로길이 a설정.
        root.setSpacing(15); // 사이 간격을 띄운다.
        // -----------------------------------------------------------------------

        btn2.setOnAction(actionEvent -> {
            new ClientThread().start();
        });

        // -----------------------------------------------------------------------
        root.getChildren().addAll(btn1, btn2); // 한꺼번에 등록시키는 addAll()
        Scene scene = new Scene(root); // scene : 한장면을 그리기위한 바탕.
        arg0.setScene(scene);
        arg0.setTitle("클라이언트");// 제목
        arg0.show();
    }

    public static void main(String[] args) {
        System.out.println("클라이언트 스타트");
        launch();
    }
}


//public class Client {Client
//    public static void main(String[] args) {
//        System.out.println("클라이언트 스타트");
//        try {
//            Socket cs = new Socket();
//            System.out.println("문자 입력...");
//            String message = new Scanner(System.in).nextLine();
//            cs.connect(new InetSocketAddress("61.83.118.69", 5001));
//            OutputStream outputStream = cs.getOutputStream();
//            byte[] data = message.getBytes(StandardCharsets.UTF_8);
//            outputStream.write(data);
//            System.out.println("전송 완료");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("클라이언트 엔드");
//    }
//}