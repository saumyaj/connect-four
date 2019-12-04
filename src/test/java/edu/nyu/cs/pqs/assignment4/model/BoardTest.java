package edu.nyu.cs.pqs.assignment4.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static edu.nyu.cs.pqs.assignment4.model.TestUtils.board;

class BoardTest {

    @BeforeEach
    void resetTestBoard() {
        board.reset();
    }

    @Test
    void testFullColumnDetection() {
        TestUtils.fillTheColumnOfTheBoard(TestUtils.board, 0);
        assertTrue(TestUtils.board.isColumnFull(0));
        board.reset();
    }

    @Test
    void testNonFullColumnDetection() {
        for (int j = 0; j<=board.getHeight()-1; j++) {
            for (int i = 0; i < j; i++) {
                board.columnSelected(0, 0);
            }
            assertFalse(board.isColumnFull(0));
            board.reset();
        }
    }

    @Test
    void testColumnSelectedWithNegativeColumn() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            board.columnSelected(-1, 1);
        });
    }

    @Test
    void testColumnSelectedWithOutOfRangeColumn() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            board.columnSelected(board.getWidth(), 1);
        });
    }

    @Test
    void testColumnSelectedWithFullColumn() {
        for (int i = 0; i<board.getHeight(); i++) {
            board.columnSelected(0, 0);
        }
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            board.columnSelected(0, 0);
        });
    }

    @Test
    void testRemovingMostRecentEntryFromColumn() {

        for (int i = 0; i < board.getHeight(); i++) {
            board.columnSelected(0, 0);
        }
        assertTrue(board.isColumnFull(0));

        board.removeMostRecentEntryFromColumn(0);
        assertFalse(board.isColumnFull(0));

        board.columnSelected(0, 0);
        assertTrue(board.isColumnFull(0));

        board.reset();
    }

    @Test
    void testFullBoard() {
        for (int i = 0; i<board.getWidth(); i++) {
            for(int j = 0; j<board.getHeight(); j++) {
                board.columnSelected(i, 0);
            }
        }
        assertTrue(board.isBoardFull());

        board.removeMostRecentEntryFromColumn(0);
        assertFalse(board.isBoardFull());
    }

    @Test
    void testContiguousSymbolsInARow() {
        // Test contiguous in a row
        for (int i=1;i<=4;i++) {
            board.columnSelected(i, 1);
        }
        assertTrue(board.findContiguousSymbols(4, 1));
    }

    @Test
    void testContiguousSymbolsInAColumn() {
        for(int i=0;i<4;i++) {
            board.columnSelected(0, 1);
        }
        assertTrue(board.findContiguousSymbols(4, 1));
    }

    @Test
    void testContiguousSymbolsOnDescendingDiagonal() {
        for(int i=4;i>=1;i--) {
            for (int j=i;j>=1;j--) {
                board.columnSelected(4 - i, j);
            }
        }
        assertTrue(board.findContiguousSymbols(4, 1));
    }

    @Test
    void testContiguousSymbolsOnAscendingDiagonal() {
        for(int i=1;i<=4;i++) {
            for (int j=i;j>=1;j--) {
                board.columnSelected(i, j);
            }
        }
        assertTrue(board.findContiguousSymbols(4, 1));
    }

    @Test
    void testNoContiguousSymbols() {
        assertFalse(board.findContiguousSymbols(4, 1));
    }

    @Test
    void testBoardCloneConstruction() {
        Board board1 = new Board(5, 10);
        for (int i=0;i<10;i++) {
            for (int j=0;j<5;j++) {
                board1.columnSelected(j, i*j);
            }
        }

        Board board2 = new Board(board1);

        for (int i=0;i<10;i++) {
            for (int j=0;j<5;j++) {
                assertEquals(board1.getEntry(i, j), board2.getEntry(i, j));
            }
        }
    }

}