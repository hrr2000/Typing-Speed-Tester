
import java.util.*;  
import javafx.scene.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.application.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.beans.property.*;

public class speed extends Application{
	
	private static SimpleStringProperty wordsNum = new SimpleStringProperty("0");
	private static int timeNum = 60;
	private static boolean start = false;
	
	@Override
	public void start(Stage primaryStage){
		
		Label time = new Label();
		Label words = new Label();
	
	
		Pane react1 = new Pane();
		Pane react2 = new Pane();
		Pane react3 = new Pane();
		Pane react4 = new Pane();
		react1.getStyleClass().add("img1");
		react2.getStyleClass().add("img2");
		react3.getStyleClass().add("img3");
		react4.getStyleClass().add("img4");
		react1.setPrefSize(170,400);
		react2.setPrefSize(170,400);
		react3.setPrefSize(170,400);
		react4.setPrefSize(170,400);
		
		StackPane reactText = new StackPane();
		Label reactTextLabel = new Label();
		reactTextLabel.getStyleClass().add("react-text");
		reactText.getChildren().add(reactTextLabel);
		reactTextLabel.setText("Let's Start !");

		

		
		time.getStyleClass().add("num-box");
		words.getStyleClass().add("num-box");

		time.setText("60");
		words.textProperty().bind(wordsNum);

		BorderPane topBar = new BorderPane();
		HBox stats = new HBox();
		
		stats.getChildren().add(words);
		stats.getChildren().add(time);
		
		
		String data = "play large round come music follow above try end right what among table can write language gold hand far has some boat these order answer up close sleep ground most learn down life better fill and road wheel field stand right family sit check distant press never stood second close work white all food again place sun usual produce set find school it face men simple size week horse international wondering  while true old know mountain check special father cause when stable play large round come music follow above try end right what among table can write language gold hand far has some boat these order answer up close sleep ground most learn down life better fill and road wheel field stand right family sit check distant press never stood second close work white all food again place sun usual produce set find school it face men simple size week horse international wondering  while true old know mountain check special father cause when stable ";
		VBox mainPane = new VBox();
		mainPane.getChildren().add(topBar);
		mainPane.getChildren().add(stats);
		
		ArrayList<String> dataSet = new ArrayList<String>(Arrays.asList(data.split(" ")));
		ArrayList<TextField> stmt = new ArrayList<TextField>();
		ArrayList<Pane> panes = new ArrayList<Pane>();
		
		
		Timer timer = new Timer();
		TimerTask ts = new TimerTask(){
			@Override
			public void run(){
				Platform.runLater(new Runnable() {
					public void run() {
						timeNum--;
						time.setText(timeNum+"");
						if(timeNum == 0) {
							timer.cancel();
							reactTextLabel.setText(reactTextLabel.getText()+" : "+wordsNum.getValue()+" wpm ");
							for(int i=0;i<stmt.size();i++){
								stmt.get(i).setDisable(true);
							}
						}
					}
				});
			}
		};
		
		int containerSize = 100;
		FlowPane tile = new FlowPane();
		for(int i=0;i<dataSet.size();i++){
			Label placeholder = new Label(dataSet.get(i));
			placeholder.setLayoutX(0);
			placeholder.setLayoutY(0);
			placeholder.getStyleClass().add("word-label");
			
			stmt.add(new TextField());
			stmt.get(i).setPrefWidth(dataSet.get(i).length()*15);
			containerSize += stmt.get(i).getPrefWidth();
			stmt.get(i).getStyleClass().add("word-field");
			stmt.get(i).setLayoutX(0);
			stmt.get(i).setLayoutY(0);
			
			if(i!=0) stmt.get(i).setDisable(true);
			final int here = i;
			final int next = i+1;
			
			
			stmt.get(i).textProperty().addListener((obs, oldText, newText) -> {
				if(!start) {
					timer.scheduleAtFixedRate(ts,0,1000);
					start = true;
				}
				int sz = stmt.get(here).getText().length();
				boolean state = true;
				for(int j=0;j<sz;j++){
					if(sz > dataSet.get(here).length() || stmt.get(here).getText().charAt(j)!=dataSet.get(here).charAt(j)){
						stmt.get(here).setStyle("-fx-text-fill:red;");
						state = false;
					}
				}
				if(state) stmt.get(here).setStyle("-fx-text-fill:white;");
			});
			stmt.get(i).addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
				if(key.getCode()==KeyCode.SPACE) {
					if(stmt.get(here).getText().equals(dataSet.get(here))){
						stmt.get(here).setStyle("-fx-text-fill:#9df734;");
						int num = Integer.parseInt(wordsNum.getValue());
						num++;
						wordsNum.setValue(num+"");
						if(num>40){	
							reactTextLabel.setText("you are Octupus :O");
							react3.setVisible(false);
						}else if(num>20){
							reactTextLabel.setText("you are T-Rex :)");
							react2.setVisible(false);
						}else{
							reactTextLabel.setText("you are Turtle :(");
							react1.setVisible(false);
						}
					}else{
						stmt.get(here).setStyle("-fx-text-fill:red;");
					}
					if(next < stmt.size()) {
						stmt.get(here).setDisable(true);
						stmt.get(next).requestFocus();
						stmt.get(next).setDisable(false);
					}
					else stmt.get(here).setDisable(true);
				}
			});
			
			panes.add(new Pane());
			panes.get(i).getStyleClass().add("word-box");
			panes.get(i).getChildren().add(placeholder);
			panes.get(i).getChildren().add(stmt.get(i));
		}
		for(int i=0;i<panes.size();i++){
			tile.getChildren().add(panes.get(i));
		}
		
		tile.setPrefWidth(containerSize);
		ScrollPane scroll = new ScrollPane();
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);		
		scroll.setContent(tile);
		scroll.getStyleClass().add("scroll");
		
		stmt.get(0).requestFocus();
		
		mainPane.getChildren().add(scroll);
		
		mainPane.getChildren().add(reactText);
		
		StackPane reactContainer = new StackPane();
		
		reactContainer.getChildren().add(react4);
		reactContainer.getChildren().add(react3);
		reactContainer.getChildren().add(react2);
		reactContainer.getChildren().add(react1);
		
		mainPane.getChildren().add(reactContainer);
		
		mainPane.getStyleClass().add("body");
		Scene scene = new Scene(mainPane,400,500);
		scene.getStylesheets().add("style.css");
        scene.setFill(Color.TRANSPARENT);
		
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args){
		Application.launch(args);
	}
}