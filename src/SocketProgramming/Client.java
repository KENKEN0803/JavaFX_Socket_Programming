package SocketProgramming;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


class ClientThread extends Thread {
    Client client;

    ClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            Socket cs = new Socket();

            String Serverip = "61.83.118.69";
            int port = 5001;
            cs.connect(new InetSocketAddress(Serverip, port));
            OutputStream outputStream = cs.getOutputStream();

            String input = client.textField.getText(); // 텍스트필드에서 텍스트 가져옴
            byte[] data = input.getBytes(StandardCharsets.UTF_8); // 가져온 데이터를 배열에 담음
            outputStream.write(data); // 배열을 아웃풋스트림으로 전송
            System.out.println(input + "전송 완료"); // 콘솔에 출력
            client.textField.setText(""); // 텍스트필드를 공백으로 돌림
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Client extends Application {
    Button btn1 = new Button("서버에 접속하기");
    Button btn2 = new Button("---");
    TextArea textArea = new TextArea();
    TextField textField = new TextField();

    @Override
    public void start(Stage arg0) throws Exception {
        VBox root = new VBox(); // root 최상단
        root.setPrefSize(400, 300); // 창의 가로길이 세로길이 a설정.
        root.setSpacing(15); // 사이 간격을 띄운다.

        // -----------------------------------------------------------------------

        btn1.setOnAction(actionEvent -> {
//            new ClientThread().start();
        });

        textField.setOnAction(actionEvent -> {
            new ClientThread(this).start();
        });

        // -----------------------------------------------------------------------
        root.getChildren().addAll(btn1, btn2, textArea, textField); // 한꺼번에 등록시키는 addAll()
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