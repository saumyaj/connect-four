package model;

public abstract class Player {

    public enum Symbol {
        CROSS, CIRCLE
    }

    private String name;
    ConnectFourModel gameModel;

    private Symbol symbol;

    Player(String name, ConnectFourModel gameModel, Symbol symbol) {
        this.name = name;
        this.gameModel = gameModel;
        this.symbol = symbol;
    }

    abstract void move();

    public String getName() {
        return name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

}
