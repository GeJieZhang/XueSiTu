package com.live.bean.control;

public class WhiteBoradBean {
    private boolean isPen = true;
    private boolean isEraser = false;
    private boolean isRectangular = false;
    private boolean isCirle = false;
    private boolean isMove = false;


    private int cirlerIndex = 0;
    private int penSize = 5;

    public boolean isPen() {
        return isPen;
    }

    public void setPen(boolean pen) {
        isPen = pen;
    }

    public boolean isEraser() {
        return isEraser;
    }

    public void setEraser(boolean eraser) {
        isEraser = eraser;
    }

    public boolean isRectangular() {
        return isRectangular;
    }

    public void setRectangular(boolean rectangular) {
        isRectangular = rectangular;
    }

    public boolean isCirle() {
        return isCirle;
    }

    public void setCirle(boolean cirle) {
        isCirle = cirle;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    public int getCirlerIndex() {
        return cirlerIndex;
    }

    public void setCirlerIndex(int cirlerIndex) {
        this.cirlerIndex = cirlerIndex;
    }

    public int getPenSize() {
        return penSize;
    }

    public void setPenSize(int penSize) {
        this.penSize = penSize;
    }


    public void initValue() {
        this.isPen = false;
        this.isEraser = false;
        this.isRectangular = false;
        this.isCirle = false;
        this.isMove = false;
    }
}
