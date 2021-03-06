package Model;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Joueur {


    protected int numJoueur; //Numéro du joueur
    protected Color couleur;
    private Piece pieceActive; //Piece avec laquelle le joueur peut interagir
    private ArrayList<Piece> PoolDePiece; //Liste des pièces du joueur, utilisés différemment en fonction du jeu

    protected Plateau plateau;


    public Joueur (int num, Plateau plateau, Color couleur, ArrayList<Piece> PoolDePiece) {
        this.numJoueur = num;
        this.plateau = plateau;
        this.couleur = couleur;
        this.PoolDePiece = PoolDePiece;
    }


    //Manipulation liste de Pieces.


    public void ajouterPiece(Piece piece) {
        PoolDePiece.add(piece);
    }

    public void supprimerPiece(int index) {
        PoolDePiece.remove(index);
    }

    public void supprimerPiece(Piece piece) {
        PoolDePiece.remove(piece);
    }

    public void SelectPiece(int index) {
        pieceActive = PoolDePiece.get(index);
    }


    public boolean poolIsEmpty() {
        return PoolDePiece.isEmpty();
    }
    //Accesseurs

    public ArrayList<Piece> getPoolDePiece() {
        return PoolDePiece;
    }

    public Color getCouleur() {
        return couleur;
    }

    public Piece getPieceActive() {
        return pieceActive;
    }

    public void setPieceActive(Piece pieceActive) {
        this.pieceActive = pieceActive;
    }

    public int getNumJoueur() {
        return numJoueur;
    }

    public void setNumJoueur(int numJoueur) {
        this.numJoueur = numJoueur;
    }

}
