package SocketProgramming;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


class ChatThread2 extends Thread {
    Socket socket;

    ChatThread2(Socket socket) {
        this.socket = socket;
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
                System.out.println(s + " 서버에서 데이터 받음");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}

public class Client2 extends Application {
    Socket cs;
    VBox root = new VBox();
    HBox hBox1 = new HBox();
    HBox hBox2 = new HBox();
    HBox hBox3 = new HBox();
    HBox hBox4 = new HBox();
    TextArea textArea = new TextArea();
    TextField textField = new TextField();
    TextField textField1 = new TextField();
    TextField textField2 = new TextField();
    TextField textField3 = new TextField();
    Button btn1 = new Button("서버 연결!");
    Button btn2 = new Button("방 나가기");
    Button btn3 = new Button("전송");
    Button btn4 = new Button("창닫기");
    Label label1 = new Label();
    Label label2 = new Label();
    Label label3 = new Label();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage arg0) throws Exception {
        root.setPrefSize(500, 400); // 창 크기
        root.setSpacing(10);
        root.setPadding(new Insets(15));
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));

        btn1.setPrefSize(100, 30);
        btn2.setPrefSize(100, 30);
        btn4.setPrefSize(100, 30);
        btn3.setPrefSize(50, 30);

        textField.setPrefWidth(400);
        textField.setPrefHeight(30);
        textField1.setPrefWidth(200);
        textField1.setText("115.22.10.42");
        textField2.setPrefWidth(200);
        textField2.setText("5001");
//        textField3.setText();

        textArea.setPadding(new Insets(10));

        label1.setText("서버 주소 : ");
        label2.setText("포트 번호 : ");
        label3.setText("닉네임을 입력 해 주세요 : ");
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(10);
        hBox2.setSpacing(15);
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setSpacing(15);
        hBox4.setSpacing(10);
        hBox4.setAlignment(Pos.CENTER);
        //-------------------------------------
//        textArea.set
        // 이 영역에 모든 코드가 들어감
        // 방법 2
        hBox4.getChildren().addAll(label3, textField3);
        hBox1.getChildren().addAll(btn1, btn2, btn4);
        hBox2.getChildren().addAll(textField, btn3);
        hBox3.getChildren().addAll(label1, textField1, label2, textField2);
        root.getChildren().addAll(hBox3, hBox1, hBox4, textArea, hBox2);


        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                cs = new Socket();
                try {
                    //cs.connect(new InetSocketAddress("localhost", 5001));
//                    cs.connect(new InetSocketAddress("220.119.22.165", 5001));
                    //cs.connect(new InetSocketAddress("61.83.118.69", 5001));
                    cs.connect(new InetSocketAddress("115.22.10.42", 5001));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("서버에 접속함");
                new ChatThread2(cs).start();
            }
        });

//        btn2.setOnAction(new EventHandler<ActionEvent>() {
//            int count = 0;
//
//            @Override
//            public void handle(ActionEvent arg0) {
//                try {
//                    OutputStream outputStream = cs.getOutputStream();
//                    String s = "apple" + count++;
//                    byte[] data = s.getBytes(StandardCharsets.UTF_8);
//                    outputStream.write(data);
//                    System.out.println("데이터 보냄");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        //-------------------------------------
        Scene scene = new Scene(root);
        arg0.setScene(scene); // 위의 설정값들을 적용
        arg0.setTitle("Client"); // 제목
        arg0.show(); // 창 띄움

    }
}



/*
//ex75 UI 에서 통신
class ConnectThread2 extends Thread{
	@Override
	public void run() {
		try {
			Socket cs = new Socket();

			//cs.connect(new InetSocketAddress("localhost", 5001));
			cs.connect(new InetSocketAddress("220.119.22.165", 5001));
			System.out.println("접속 완료");


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
public class Client extends Application{
	Socket cs;
	@Override
	public void start(Stage arg0) throws Exception {
		VBox root = new VBox();
		root.setPrefSize(400, 300); // 창 크기
		root.setSpacing(15);
		//-------------------------------------
		// 이 영역에 모든 코드가 들어감
		// 방법 2
		Button btn1 = new Button("서버에 연결");
		Button btn2 = new Button("버튼2");

		root.getChildren().addAll(btn1, btn2);

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				cs = new Socket();

				try {
					//cs.connect(new InetSocketAddress("localhost", 5001));
					cs.connect(new InetSocketAddress("220.119.22.165", 5001));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		btn2.setOnAction(new EventHandler<ActionEvent>() {
			int count = 0;
			@Override
			public void handle(ActionEvent arg0) {
				try {
					OutputStream outputStream = cs.getOutputStream();
					String s ="apple" + count++;
					byte[] data = s.getBytes();
					outputStream.write(data);
					System.out.println("데이터 보냄");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		//-------------------------------------
		Scene scene = new Scene(root);
		arg0.setScene(scene); // 위의 설정값들을 적용
		arg0.setTitle("Client"); // 제목
		arg0.show(); // 창 띄움

	}

	public static void main(String[] args) {
		launch();
	}
}
*/


/*
//ex 74 server - client 통신
public class Client {

	public static void main(String[] args) {
		System.out.println("Client Start");


		try {
			Socket cs = new Socket();

			System.out.println("숫자를 입력하면 접속을 시도합니다.");
			new Scanner(System.in).nextInt();
			// cs.connect(new InetSocketAddress("localhost", 5001));
			cs.connect(new InetSocketAddress("220.119.22.165", 5001));
			System.out.println("접속 완료");
			System.out.println("숫자를 입력하면 데이터를 전송합니다.");
			new Scanner(System.in).nextInt();

			OutputStream outputStream = cs.getOutputStream();
			String s ="apple";
			byte[] data = s.getBytes();
			outputStream.write(data);
			System.out.println("데이터 보냄");

		} catch (Exception e) {
			e.printStackTrace();
		}



		new Scanner(System.in).nextInt();
		System.out.println("Client End");
	}

}
*/