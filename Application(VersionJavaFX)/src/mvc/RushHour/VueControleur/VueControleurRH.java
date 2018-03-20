package mvc.RushHour.VueControleur;

import com.sun.javafx.geom.Vec2d;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mvc.RushHour.Modele.Partie;
import mvc.VueControleur.GrilleVue;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class VueControleurRH extends Application implements Observer {

    private Partie partie;
    GrilleVue grille = new GrilleVue(6,6,50);

    @Override
    public void start(Stage primaryStage){
        //TOP
        Text titre = new Text("--- Rush Hour ---");
        titre.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        titre.setFill(Color.MEDIUMPURPLE);
        grille.setAlignment(titre, Pos.CENTER);
        grille.setTop(titre);
        partie = new Partie(grille.getP());

        //RIGHT
        Button startButton = new Button();
        startButton.setPadding(new Insets(10));
        startButton.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        startButton.setText("Commencer");
        grille.setRight(startButton);

        grille.setPadding(new Insets(20));
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                partie.initialiser();
                grille.getP().setPieceCourante(null);
            }
        });



        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        // la vue observe les "update" du modèle, et réalise les mises à jour graphiques
        partie.getPlateau().addObserver(this);

        Scene scene = new Scene(grille, Color.WHITE);

        for (int i = 0; i < grille.getLargeur(); i++) {
            for (int j = 0; j < grille.getHauteur() ; j++) {
                Rectangle[][] tab = grille.getTab();
                int finalI = i;
                int finalJ = j;
                tab[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ArrayList<Vec2d> positions = grille.getP().occurrencesPiecesPlateau( grille.getP().recupererPiece(finalJ,finalI));
                        effacerBordures();
                        for (int k = 0; k < positions.size(); k++) {
                            grille.getP().recupererPiece(finalJ,finalI).setBordure(Color.BLUE);
                            tab[(int) positions.get(k).y][(int) positions.get(k).x].setStroke(Color.BLUE);
                        }
                        grille.getP().setPieceCourante(grille.getP().recupererPiece(finalJ,finalI));
                    }
                });
            }
        }


        //EVENEMENTS LIES AUX TOUCHES CLAVIER
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.UP)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versHaut(grille.getP().getPieceCourante());
                }
                if (ke.getCode().equals(KeyCode.LEFT)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versGauche(grille.getP().getPieceCourante());
                    //bouger à gauche la pièce courante du plateau si possible
                }

                if (ke.getCode().equals(KeyCode.RIGHT)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versDroite(grille.getP().getPieceCourante());
                    //bouger à droite la pièce courante du plateau si possible
                }
                if (ke.getCode().equals(KeyCode.DOWN)) {
                    if(grille.getP().getPieceCourante() != null)
                        grille.getP().versBas(grille.getP().getPieceCourante());
                }
            }
        });

        primaryStage.setTitle("Jeu Plateau");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    synchronized public void update(Observable o, Object arg) {
                effacerBordures();
                for(int a = 0; a< grille.getP().getLargeur(); a++)
                    for(int b = 0; b< grille.getP().getHauteur(); b++){

                        if(grille.getP().getTableauJeu()[b][a] != null){
                            grille.getTab()[a][b].setFill(grille.getP().getTableauJeu()[b][a].getCouleur());
                            grille.getTab()[a][b].setStroke(grille.getP().recupererPiece(b,a).getBordure());
                        }
                        else{
                            grille.getTab()[a][b].setFill(Color.WHITE);
                            grille.getTab()[a][b].setStroke(null);
                        }
                    }
            }

            public void effacerBordures(){
                for (int i = 0; i < grille.getHauteur(); i++) {
                    for (int j = 0; j < grille.getLargeur(); j++) {
                        grille.getTab()[i][j].setStroke(null);
                        if(grille.getP().recupererPiece(i,j) != null && grille.getP().recupererPiece(i,j)!=grille.getP().getPieceCourante())
                            grille.getP().recupererPiece(i,j).setBordure(Color.TRANSPARENT);
                    }
                }
            }
}