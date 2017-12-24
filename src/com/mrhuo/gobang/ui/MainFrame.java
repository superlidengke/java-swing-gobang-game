/*
 * Copyright  (c) mrhuo.com 2017.
 */

package com.mrhuo.gobang.ui;

import com.mrhuo.gobang.bean.ChessColor;
import com.mrhuo.gobang.bean.GameMode;
import com.mrhuo.gobang.bean.User;
import com.mrhuo.gobang.common.CONSTANT;
import com.mrhuo.gobang.events.LoginListener;
import com.mrhuo.gobang.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

/**
 * 主界面
 */
public class MainFrame extends JFrame implements LoginListener {

    private final GameLogic gameLogic = GameLogic.getInstance();
    private ChessBoard chessBoard;
    private GameInfo gameInfo;

    public MainFrame() {
        super("勉強五子棋");
        this.init();
    }

    /**
     * 初始化界面
     */
    private void init() {
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        this.buildMainContent();
        this.addEventListener();

        this.setVisible(true);


        /**
         * 设置用户登录监听
         */
        gameLogic.setLoginListener(this);
        gameLogic.login();
        selectChessAndStart();
    }

    /**
     * 增加事件
     */
    private void addEventListener() {
        //人机对弈，人执黑
        this.blackChess.addActionListener(l -> {
            selectChessAndStart();
        });
        //人机对弈，人执白
        this.whiteChess.addActionListener(l -> {
            selectChessAndStart();
        });
    }

    /**
     * Check current user chess color, and the radio button select color,
     * if user chess color is null,then the game just started,user have not select chess before, just start the game.
     * if  user chess color is not null, user tried click the button to change the select, check whether
     * the button select is different with the current user color, if it's restart the game.
     */
    private void selectChessAndStart(){
        Boolean blackSelect = this.blackChess.isSelected();
        ChessColor currentSelect = this.gameLogic.getCurrentUser().getUserChessColor();
        // at first app start,it's null, auto select black,when user change chess color,it's not null
        if(currentSelect!=null){
           Boolean currentBlack = currentSelect == ChessColor.BLACK;
           if(blackSelect == currentBlack){//if not change the color,ignore the click
               return;
           }
        }
        if(blackSelect){
            gameLogic.startNewGame(GameMode.WITH_ROBOT, ChessColor.BLACK);
            this.gameInfo.updateUserChess(ChessColor.BLACK);
            this.gameInfo.updateGameStatus("轮到黑方下棋");
            repaint();
        }else {
            gameLogic.startNewGame(GameMode.WITH_ROBOT, ChessColor.WHITE);
            this.gameInfo.updateUserChess(ChessColor.WHITE);
            this.gameInfo.updateGameStatus("轮到黑方下棋");
            repaint();
        }
    }

    /**
     * 构造主界面内容
     */
    private void buildMainContent() {
        this.gameInfo = new GameInfo();
        this.chessBoard = new ChessBoard(this.gameInfo);
        this.controlPane = getControlPane();
        this.add(this.controlPane,BorderLayout.SOUTH);
        this.add(this.chessBoard, BorderLayout.CENTER);
        this.add(this.gameInfo, BorderLayout.NORTH);
    }
    private JRadioButton blackChess;
    private JRadioButton whiteChess;
    private JPanel controlPane;
    private JPanel getControlPane(){
        blackChess = new JRadioButton();
        whiteChess = new JRadioButton();
        ButtonGroup chessSelect = new ButtonGroup();
        chessSelect.add(blackChess);
        chessSelect.add(whiteChess);
        blackChess.setText("黑子（先手）");
        whiteChess.setText("白子（后手）");
        blackChess.setSelected(true);
        controlPane =new JPanel();
        controlPane.add(blackChess);
        controlPane.add((whiteChess));
        return controlPane;
    }

    /**
     * 登录成功事件
     *
     * @param user
     */
    @Override
    public void onLoginSuccess(User user) {
        this.gameInfo.updateUserInfo("欢迎您，" + user.getName());
        this.gameInfo.updateGameStatus("已登录，请选择对弈方式");
    }


}
