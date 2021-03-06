package mvc.Blokus.VueControleurBlokus;

import Model.Piece;
import VueControleur.GrillePiece;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import mvc.Blokus.ModeleBlokus.JoueurBlokus;
import mvc.Blokus.ModeleBlokus.Partie;

import java.util.HashMap;

public class ListePiece extends TilePane{

    private JoueurBlokus joueur;
    private Partie partie;

    //On associe à chaque pièce sa propre GridPane la représentant.
    private HashMap<Piece, GridPane> listeGrillesPieces;

    public ListePiece(JoueurBlokus joueur, Partie partie) {
        this.joueur = joueur;
        this.partie = partie;
        this.listeGrillesPieces = new HashMap<Piece, GridPane>();

        //On remplit une TilePane avec chaque pièce du joueur.
        for (Piece piece: joueur.getPoolDePiece() ) {

            //On crée une gridPane avec la pièce.
            GridPane grillePiece = new GrillePiece( piece.getCases(), joueur.getCouleur(), true,15);
            listeGrillesPieces.put(piece, grillePiece);
            grillePiece.setPadding(new Insets(3));
            getChildren().add(grillePiece);

            //CONTROLLEURS : Quand on clique sur une pièce, elle devient la pièce active du plateau (celle qu'on peut manipuler).
            grillePiece.setOnMouseClicked(event -> {
                partie.setPieceCourante(piece);
            });
        }
    }

    public void update() {

        //On efface toutes les pièces affichés dans la liste des pièces.
        //Necessaire pour se débarraser des pièces jouées.
        listeGrillesPieces.forEach( (piece, grid) -> {
            getChildren().remove(grid);
        });

        //On réinitialise la liste des pièce/grilles.
        listeGrillesPieces.clear();

        //Pour chaque pièce, on recalcule sa grille qu'on rajoute dans la liste.
        for (Piece piece: joueur.getPoolDePiece() ) {

            //On refait une nouvelle grille à partir de l'état actuel de la pièce.
            GridPane grillePiece = new GrillePiece( piece.getCases(), joueur.getCouleur(), true,15);
            grillePiece.setPadding(new Insets(3));
            listeGrillesPieces.put(piece, grillePiece);

            //CONTROLLEURS : Quand on clique sur une pièce, elle devient la pièce active du plateau (celle qu'on peut manipuler).
            grillePiece.setOnMouseClicked(event -> {
                partie.setPieceCourante(piece);
                //DEBUG material
                //System.out.println("Piece active changée ! - " + piece.getNom());
                //partie.getPieceCourante().afficherPiece();
            });

            //On la rajoute à gridPane.
            getChildren().add(listeGrillesPieces.get(piece));
        }
    }


}
