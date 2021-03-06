/*
 * Copyright  (c) mrhuo.com 2017.
 */

package com.mrhuo.gobang.bean;

import java.awt.*;
import java.io.Serializable;

import static com.mrhuo.gobang.common.CONSTANT.*;

/**
 * 棋盘坐标
 * x: 0 - 14
 * y: 0 - 14
 * color: BLANK or WHITE
 */
public class ChessPoint implements Serializable {
    private int x, y;
    private ChessColor color;
    private int score;

    /**
     * 构造函数
     *
     * @param x
     * @param y
     */
    public ChessPoint(int x, int y) {
        this(x, y, null);
    }

    /**
     * 带有颜色棋子的构造函数
     *
     * @param x     x坐标
     * @param y     y坐标
     * @param color 棋子颜色
     */
    public ChessPoint(int x, int y, ChessColor color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * 带有颜色棋子的构造函数
     *
     * @param x     x坐标
     * @param y     y坐标
     * @param score 棋子颜色
     */
    public ChessPoint(int x, int y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    /**
     * 将鼠标点击坐标转化为棋盘坐标
     *
     * @param point
     * @return
     */
    public static ChessPoint transformPoint(Point point) {
        int x = (int) Math.round((point.getX() - offsetSizeX) / gridSize * 1.0);
        int y = (int) Math.round((point.getY() - offsetSizeY) / gridSize * 1.0);
        return new ChessPoint(x, y);
    }

    /**
     * 将鼠标点击坐标转化为棋盘坐标
     *
     * @return
     */
    public static ChessPoint transformPoint(int _x, int _y) {
        int x = Math.round((_x - offsetSizeX + 10) / gridSize);
        int y = Math.round((_y - offsetSizeY + 10) / gridSize);
        if (x > 14) {
            x = 14;
        }
        if (y > 14) {
            y = 14;
        }
        return new ChessPoint(x, y);
    }

    /**
     * 将棋盘坐标转化为界面坐标
     *
     * @param point
     * @return
     */
    public static Point transformToClientPoint(ChessPoint point) {
        return transformToClientPoint(point.getX(), point.getY());
    }

    /**
     * 将棋盘坐标转化为界面坐标
     *
     * @param px
     * @param py
     * @return
     */
    public static Point transformToClientPoint(int px, int py) {
        int x = offsetSizeX + px * gridSize - 10;
        int y = offsetSizeY + py * gridSize - 10;
        return new Point(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ChessColor getColor() {
        return color;
    }

    public void setColor(ChessColor color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ", " + color +
                ", " + score + ")";
    }
}
