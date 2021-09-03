//package SocketProgramming;
//
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//
//
//class ServerThreadC extends Thread {
//    Socket ss;
//    Client client;
//
//    ServerThreadC(Socket ss, Client client) {
//        this.ss = ss;
//        this.client = client;
//    }
//
//    @Override
//    public void run() {
//        InputStream inputStream = null;
//
//        while (true) {
//            try {
//                ArrayList<String> list = new ArrayList<String>();
//
//                inputStream = ss.getInputStream();
//                byte[] data = new byte[512];
//                int size = inputStream.read(data);//블로킹
//                String inputData = new String(data, 0, size, StandardCharsets.UTF_8);
//                String clientAddress = ss.getInetAddress().getHostName();
//                client.textArea.appendText(inputData);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
//
//class ConnectThreadC extends Thread {
//    Client client;
//
//    ConnectThreadC(Client client) {
//        this.client = client;
//    }
//
//    @Override
//    public void run() {
//        try {
//            ServerSocket mss = new ServerSocket();
//            int port = 5001;
//            client.textArea.appendText("소캣 생성 완료\n");
//            mss.bind(new InetSocketAddress("115.22.10.42", port));
//            client.textArea.appendText("바인딩 완료\n" + port + "번 포트에서 서버 응답 대기중...\n");
//            while (true) {
//                Socket ss = mss.accept(); // 블로킹
//                new ServerThreadC(ss, client).start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//
//class ClientThread extends Thread {
//    Client client;
//
//    ClientThread(Client client) {
//        this.client = client;
//    }
//
//    @Override
//    public void run() {
//        try {
//            Socket cs = new Socket();
//
//            String ServerIp = "115.22.10.42";
//            int port = 5001;
//            cs.connect(new InetSocketAddress(ServerIp, port));
//            OutputStream outputStream = cs.getOutputStream();
//
//            String input = client.textField.getText(); // 텍스트필드에서 텍스트 가져옴
//            byte[] data = input.getBytes(StandardCharsets.UTF_8); // 가져온 데이터를 배열에 담음
//            outputStream.write(data); // 배열을 아웃풋스트림으로 전송
//            System.out.println(input + "전송 완료"); // 콘솔에 출력
//            client.textField.setText(""); // 텍스트필드를 공백으로 돌림
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//public class Client extends Application {
//    Button btn1 = new Button("서버에 접속하기");
//    Button btn2 = new Button("---");
//    TextArea textArea = new TextArea();
//    TextField textField = new TextField();
//
//    @Override
//    public void start(Stage arg0) throws Exception {
//        VBox root = new VBox(); // root 최상단
//        root.setPrefSize(400, 300); // 창의 가로길이 세로길이 a설정.
//        root.setSpacing(15); // 사이 간격을 띄운다.
//
//        // -----------------------------------------------------------------------
//
//        btn1.setOnAction(actionEvent -> {
//            new ConnectThreadC(this).start();
//        });
//
//        textField.setOnAction(actionEvent -> {
//            new ClientThread(this).start();
//        });
//
//        // -----------------------------------------------------------------------
//        root.getChildren().addAll(btn1, btn2, textArea, textField); // 한꺼번에 등록시키는 addAll()
//        Scene scene = new Scene(root); // scene : 한장면을 그리기위한 바탕.
//        arg0.setScene(scene);
//        arg0.setTitle("클라이언트");// 제목
//        arg0.show();
//    }
//
//    public static void main(String[] args) {
//        System.out.println("클라이언트 스타트");
//        launch();
//    }
//}