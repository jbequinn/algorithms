/**
 *
 */
package com.dodecaedro.backtrack.sudoku;

import com.dodecaedro.backtrack.BacktrackNode;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author JM
 */
public class SudokuNode implements BacktrackNode {
  public final static int SIDE_SIZE = 9;
  private int[][] board = new int[SIDE_SIZE][SIDE_SIZE];
  public int currentNumber;

  public SudokuNode() {
    this.currentNumber = 0;
  }

  public SudokuNode(int startNumber) {
    this.currentNumber = startNumber;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.dodecaedro.backtrack.BacktrackNode#isLeaf()
   */
  public boolean isLeaf() {
    return isAllBoardFull() || isAnyNumberRepeated();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.dodecaedro.backtrack.BacktrackNode#isGoal()
   */
  public boolean isGoal() {
    return isAllBoardFull() && !isAnyNumberRepeated();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.dodecaedro.backtrack.BacktrackNode#getChildrenNodes()
   */
  public Collection<BacktrackNode> getChildrenNodes() {
    Collection<BacktrackNode> children = new ArrayList<BacktrackNode>();
    currentNumber+=1;

    if (currentNumber > 9) {
      currentNumber = 1;
    }

    for (int posY = 0; posY < SIDE_SIZE; posY++) {
      for (int posX = 0; posX < SIDE_SIZE; posX++) {
        if (!isPositionUsed(posX, posY)) {
          SudokuNode childrenNode = new SudokuNode(currentNumber);
          childrenNode.setBoard(this.getBoard());
          childrenNode.setValuePositionXY(currentNumber, posX, posY);
          children.add(childrenNode);
        }
      }
    }
    return children;
  }

  public void setValuePositionXY(int value, int positionX, int positionY) {
    this.board[positionX][positionY] = value;
  }

  public boolean isAnyNumberRepeated() {
    return isAnyNumberRepeatedRow() || isAnyNumberRepeatedColumn()
        || isAnyNumberRepeatedBlock();
  }

  public boolean isAnyNumberRepeatedBlock() {
    for (int currentBlockIndexX = 3; currentBlockIndexX < SIDE_SIZE; currentBlockIndexX += 3) {
      for (int currentBlockIndexY = 3; currentBlockIndexY < SIDE_SIZE; currentBlockIndexY += 3) {

        for (int posY = currentBlockIndexY - 3; posY < currentBlockIndexY; posY++) {
          for (int posX = currentBlockIndexX - 3; posX < currentBlockIndexX; posX++) {

            for (int comparePosY = currentBlockIndexY - 3; comparePosY < currentBlockIndexY; comparePosY++) {
              for (int comparePosX = currentBlockIndexX - 3; comparePosX < currentBlockIndexX; comparePosX++) {
                if (posX != comparePosX || posY != comparePosY) {
                  if (board[posX][posY] == board[comparePosX][comparePosY] && this.board[posX][posY] != 0) {
                    return true;
                  }
                }
              }
            }

          }
        }
      }
    }

    return false;
  }

  public boolean isAnyNumberRepeatedColumn() {
    for (int posY = 0; posY < SIDE_SIZE; posY++) {
      for (int posX = 0; posX < SIDE_SIZE; posX++) {
        for (int comparePosY = 0; comparePosY < SIDE_SIZE; comparePosY++) {
          if (posY != comparePosY) {
            if (this.board[posX][posY] == this.board[posX][comparePosY] && this.board[posX][posY] != 0) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public boolean isAnyNumberRepeatedRow() {
    for (int posY = 0; posY < SIDE_SIZE; posY++) {
      for (int posX = 0; posX < SIDE_SIZE; posX++) {
        for (int comparePosX = 0; comparePosX < SIDE_SIZE; comparePosX++) {
          if (posX != comparePosX) {
            if (this.board[posX][posY] == this.board[comparePosX][posY] && this.board[posX][posY] != 0) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  public boolean isAllBoardFull() {
    for (int posY = 0; posY < SIDE_SIZE; posY++) {
      for (int posX = 0; posX < SIDE_SIZE; posX++) {
        if (board[posX][posY] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isPositionUsed(int positionX, int positionY) {
    return this.board[positionX][positionY] != 0;
  }

  public int[][] getBoard() {
    return board;
  }

  public void setBoard(int[][] copyBoard) {
    this.board = copyUsingForLoop(copyBoard);
  }

  private static int[][] copyUsingForLoop(int[][] aArray) {
    int[][] copy = new int[aArray.length][aArray.length];
    for (int idy = 0; idy < aArray.length; ++idy) {
      for (int idx = 0; idx < aArray.length; ++idx) {
        copy[idx][idy] = aArray[idx][idy];
      }
    }
    return copy;
  }

}