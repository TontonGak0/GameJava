package view;

import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextField;

import Saving.ResourceManager;
import Saving.SaveData;
import attack.Displacement;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.CASTLE;
import model.CastleButton;
import model.Castles;
import model.CastlesDuke;
import model.CastlesNeutral;
import model.GameCastleStatLabel;
import model.GameInfoLabel;
import java.util.Timer;
import java.util.TimerTask;

public class GameViewManager  {
	private GridPane gamePane;
	private GridPane infoPane;
	private GridPane pane;
	private Scene gameScene;
	private Stage gameStage;
	
	private GameCastleStatLabel castlestatlabel;
	
	static GraphicsEnvironment graphicsEnvironment=GraphicsEnvironment.getLocalGraphicsEnvironment();
	static java.awt.Rectangle maximumWindowBounds=graphicsEnvironment.getMaximumWindowBounds();
    private static final double WIDTH =(int)maximumWindowBounds.getWidth();
    private static final double HEIGHT =(int)maximumWindowBounds.getHeight();

	
	private Stage menuStage;
	private CASTLE choosenCastle;
	private Castles myCastle;
	private Castles castleselected ;
	private AnimationTimer gameloop;
	private boolean isenemycastleselected;
	private boolean ismycastleselected;
	private int[][] presence;
	
	public GameViewManager() {
		castleselected = null;
		myCastle = null;
		isenemycastleselected = false;
		ismycastleselected = false;
		initializeStage();
		
	/*	gameloop=new AnimationTimer() {

				@Override
				public void handle(long now) {
					createlabelinfo(now);
				}
         	
         };
         gameloop.start();
         gameloop.stop();*/
	}


	
	private void initializeStage() {
		pane = new GridPane();
		gamePane = new GridPane();
		infoPane=new GridPane();
	
		createlabelinfo();

        pane.add(gamePane, 0, 0);
        pane.add(infoPane, 1,0);
        
        gameScene = new Scene(pane, WIDTH, HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        
        gamePane.setGridLinesVisible(true);
        infoPane.setGridLinesVisible(true);
		
		
	}
	 public boolean isCastle(int[][] presence, int x,int y) {
	    	if(x==0||x==9||y==0||y==8) {
	    		return true;
	    	}
	    	for(int i=-1;i<2;i++) {
	    		for(int j=-1;j<2;j++) {
	    			if(presence[x+i][y+j]==1) {
	    				return true;
	    			}
	    		}
	    	}
	    	
	    	return false;
	    }
	    
	
	
	private void createCastle(CASTLE choosenCastle) {
		int number=0;
		boolean doit=false;
    	presence= new int[11][11];
    	Button button;
    	Random rand = new Random(); 
		InputStream input = getClass().getResourceAsStream("/view/resources/castlechooser/castle_grey.png");
		Image image = new Image(input, 70,59, true, true);
		ImageView imageView;    
    	for(int row=0;row<10;row++) {
    		for(int col=0;col<9;col++) {
    			presence[row][col]=0;
    			button=new Button();

		    	button.setMinHeight(79);
		    	button.setMinWidth(75);
    			button.setStyle("-fx-background: transparent");
    			gamePane.add(button,row,col);
    		}
    	}
    	while(number<10) {
	    	int x = rand.nextInt(10);
	    	int y = rand.nextInt(8);
	    	if(isCastle(presence,x,y)==false) {
	    		presence[x][y]=1;
	    		imageView = new ImageView(image);
		    	button=new Button();
		    	CastlesNeutral castle = new CastlesNeutral(imageView,x,y);
		    	button.setMinHeight(79);
		    	button.setMinWidth(75);
		    	button.setStyle("-fx-background:transparent");
		    	button.setGraphic(imageView);
				gamePane.add(button,x,y);
				button.setOnAction(new EventHandler<ActionEvent>() {
    				
    				@Override
    				public void handle(ActionEvent event) {
    					castlestatlabel.setText(castle.toString());
    					castleselected = castle;
    					ismycastleselected = false;
    					isenemycastleselected = true;				
    					}
    			});
				number++;		
	    				
	    	}	
    	}
    	while(doit==false) {
    	int x = rand.nextInt(10);
    	int y = rand.nextInt(8);
    	if(isCastle(presence,x,y)==false) {
    		presence[x][y]=1;
    		doit=true;
    		
    		image = new Image(choosenCastle.getUrlCastle(),70,59,true,true);
    		imageView=new ImageView(image);
	    	button=new Button();
	    	CastlesDuke castle = new CastlesDuke(imageView, "Player 1", x,y);
	    	button.setMinHeight(79);
	    	button.setMinWidth(75);
	    	button.setStyle("-fx-background:transparent");
	    	button.setGraphic(imageView);
			gamePane.add(button,x,y);
			button.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					castlestatlabel.setText(castle.toString());
					myCastle = castle;
					ismycastleselected = true;
					isenemycastleselected = false;
				}
			});
    	}
    	}
    	doit=false;
    	while(doit==false) {
        	int x = rand.nextInt(10);
        	int y = rand.nextInt(8);
        	if(isCastle(presence,x,y)==false) {
        		presence[x][y]=1;
        		doit=true;
        		image = new Image("/view/resources/castlechooser/castle_purple.png",70,59,true,true);
        		imageView=new ImageView(image);
    	    	button=new Button();
    	    	CastlesDuke castle = new CastlesDuke(imageView, "NPC", x,y);
    	    	button.setMinHeight(79);
    	    	button.setMinWidth(75);
    	    	button.setStyle("-fx-background:transparent");
    	    	button.setGraphic(imageView);
    			gamePane.add(button,x,y);
    			button.setOnAction(new EventHandler<ActionEvent>() {
    				
    				@Override
    				public void handle(ActionEvent event) {
    					castlestatlabel.setText(castle.toString());
    					castleselected = castle;
    					ismycastleselected = false;
    					isenemycastleselected = true;
    				}
    			});
        	}
        	}
    	
	}


	public void createNewGame(Stage menuStage, CASTLE choosenCastle) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		this.choosenCastle = choosenCastle;
		createCastle(choosenCastle);
		gameStage.show();
	}
	
		
	private Button createbtnSave() {
		Button btnSave = new Button("SAVE");
		btnSave.setPrefHeight(2*79+19);
		btnSave.setPrefWidth((WIDTH-10*75)/2-20);
		btnSave.setOnAction(event -> {
            SaveData data = new SaveData();
            data.castlechoosen = choosenCastle;
            data.stage = gameStage;
            try {
                ResourceManager.save(data, "1.save");
                System.out.println("hellooooooooooooooooooo");
            }
            catch (Exception e) {
                System.out.println("Couldn't save: " + e.getMessage());
            }
        });
		return btnSave;
	}
	
	private Button createbtnExit() {
		Button btnExit=new Button("EXIT");
		btnExit.setPrefHeight(2*79+19);
		btnExit.setPrefWidth((WIDTH - 10*75)/2 -20);
		btnExit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				gameStage.close();
				
			}
    		
    	});
		return btnExit;
	}
	
	private Button createPikeman() {
		Button addpikeman = new Button("PIKEMAN\n ROUNDS 5\n FLORINS 100\n HP 5 & DMG 1 \n SPEED 2");
		addpikeman.setPrefHeight(3*79+39);
		addpikeman.setPrefWidth((WIDTH - 10*75)/3 -20);
		addpikeman.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (myCastle.getTreasure() > 100) {
					myCastle.addPikeman();
				/*}
				else {
					long wait=now;
					while(now<wait+3000) {
						addpikeman.setText("NOT ENOUGHT MONEY");	
					}
					addpikeman.setText("PIKEMAN\n ROUNDS 5\n FLORINS 100\n HP 5 & DMG 1 \n SPEED 2");
					
				}
				
			}*/
				}
			}
		});
		return addpikeman;
	}
	
	private Button createKnight() {
		Button addKnight = new Button("KNIGHT\n ROUNDS 20\n FLORINS 500\n HP 3 & DMG 5\n SPEED 6");
		addKnight.setPrefHeight(3*79+39);
		addKnight.setPrefWidth((WIDTH - 10*75)/3 -20);
		addKnight.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (myCastle.getTreasure() > 500) {
						try {
							myCastle.addKnight();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
				else {
					addKnight.setText("NOT ENOUGHT MONEY");

					long nano = System.nanoTime();
					long launchtime = TimeUnit.NANOSECONDS.toSeconds(nano);
					
					if (TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()) - launchtime >= 1 ) {
						addKnight.setText("NOT ENOUGHT MONEY");
					}
					else {
						System.out.println(TimeUnit.NANOSECONDS.toSeconds(System.nanoTime()) - launchtime);
					}
				}
				addKnight.setText("KNIGHT\n ROUNDS 20\n FLORINS 500\n HP 3 & DMG 5\n SPEED 6");
				}
			});
			
		
		return addKnight;
	}
	
	private Button createOnagre()  {
		Button addOnagre = new Button("ONAGRE\n ROUNDS 50\n FLORINS 1000\n HP 5 & DMG 10 \n SPEED 1");
		addOnagre.setPrefHeight(3*79+39);
		addOnagre.setPrefWidth((WIDTH - 10*75)/3 -20);
		addOnagre.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (myCastle.getTreasure() > 1000) {
					try {
						myCastle.addOnagre();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					try {
						Thread.sleep(4000);
						addOnagre.setText("NOT ENOUGH MONEY");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}
			
		});
		return addOnagre;
	}
	
	
	
		private Button createbtnAttack() {
			Button btnAttack = new Button("ATTACK !");
			btnAttack.setPrefWidth(WIDTH-10*75-10);
	        btnAttack.setPrefHeight(2*79+39);
	        btnAttack.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (castleselected !=null && isenemycastleselected == true && myCastle !=null ) {
						//attack(castleselected,numberpikeman(),numberknight(),numberonagre());
						Displacement attack=new Displacement(gamePane,myCastle,castleselected,presence);
					}
					else {
						btnAttack.setText("CHOOSE AN ENEMY CASTLE !");
					}
				}
			});
			
			
			return btnAttack;
		}
	
	
	    private void createlabelinfo() {

			
			GameInfoLabel castleinfolabel = new GameInfoLabel("INFORMATION !");
			castleinfolabel.setTranslateX(0);
			castleinfolabel.setTranslateY(0);
			
			GameInfoLabel castletrooplabel = new GameInfoLabel("TROOPS !");
			castletrooplabel.setTranslateX(0);
			castletrooplabel.setTranslateY(0);
			
			castlestatlabel = new GameCastleStatLabel( " OWNER : \n LEVEL : \n TREASURE : \n EARNINGS : \n TROOPS : " );
			castlestatlabel.setTranslateX(0);
			castlestatlabel.setTranslateY(0);
			
		
			GameInfoLabel castleotherlabel = new GameInfoLabel("OTHERS !");
			castleotherlabel.setTranslateX(0);
			castleotherlabel.setTranslateY(0);
			
			TextField nbPikeman = new TextField();
			nbPikeman.setMinWidth(50);
			
			TextField nbKnight = new TextField();
			nbKnight.setMinWidth(50);
			
			TextField nbOnagre = new TextField();
			nbOnagre.setMinWidth(50);
			
			infoPane.add(castleinfolabel,0,0);
			VBox boxfieldtroop = new VBox();
			boxfieldtroop.getChildren().addAll(nbPikeman, nbKnight, nbOnagre);
			System.out.println(nbOnagre.getText());
			HBox boxinfos = new HBox(10,castlestatlabel,boxfieldtroop,createbtnAttack());
			infoPane.add(boxinfos, 0, 1);
			infoPane.add(castletrooplabel, 0, 2);
			HBox boxtroop = new HBox(10, createPikeman(), createKnight(), createOnagre());
			boxtroop.setAlignment(Pos.CENTER);
			infoPane.add(boxtroop,0,3);
			infoPane.add(castleotherlabel,0,4);
			HBox box = new HBox (10,createbtnSave(), createbtnExit());
			box.setAlignment(Pos.CENTER);
			infoPane.add(box, 0, 5);
			
			
			
			
		}
}
