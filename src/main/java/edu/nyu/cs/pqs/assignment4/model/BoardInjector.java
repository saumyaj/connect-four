package edu.nyu.cs.pqs.assignment4.model;

public class BoardInjector {
    public Connect4 getModel() {
        return Connect4.getInstance(new Board(7, 6));
    }
}
