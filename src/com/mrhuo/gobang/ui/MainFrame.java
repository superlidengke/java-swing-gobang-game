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
    private JMenuBar menuBar;
    private JMenu systemFunction;
    private JMenuItem loginMenu;
    private JMenu peopleWithRobotFight;
    private JMenuItem peopleWithRobotFightPeopleHoldBlack;
    private JMenuItem peopleWithRobotFightPeopleHoldWhite;
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

        this.buildMenuBar();
        this.buildMainContent();
        this.addEventListener();

        this.setVisible(true);

        /**
         * 设置用户登录监听
         */
        gameLogic.setLoginListener(this);
    }

    /**
     * 增加事件
     */
    private void addEventListener() {
        this.loginMenu.addActionListener(e -> {
            if (loginMenu.getText().equals("登录")) {
                gameLogic.login();
            } else {
                gameLogic.logout();
            }
        });

        //人机对弈，人执黑
        this.peopleWithRobotFightPeopleHoldBlack.addActionListener(e -> {
            if (!gameLogic.isUserLogined()) {
                CONSTANT.alertUser("您还没有登录！");
                return;
            }
            gameLogic.startNewGame(GameMode.WITH_ROBOT, ChessColor.BLACK);
            this.gameInfo.updateUserChess(ChessColor.BLACK);
            this.gameInfo.updateGameStatus("轮到黑方下棋");
            repaint();
        });

        //人机对弈，人执白
        this.peopleWithRobotFightPeopleHoldWhite.addActionListener(e -> {
            if (!gameLogic.isUserLogined()) {
                CONSTANT.alertUser("您还没有登录！");
                return;
            }
            gameLogic.startNewGame(GameMode.WITH_ROBOT, ChessColor.WHITE);
            this.gameInfo.updateUserChess(ChessColor.WHITE);
            this.gameInfo.updateGameStatus("轮到黑方下棋");
            repaint();
        });
    }

    /**
     * 构造菜单条
     */
    private void buildMenuBar() {
        this.menuBar = new JMenuBar();
        this.systemFunction = new JMenu("系统功能");
        this.loginMenu = new JMenuItem("登录");

        this.peopleWithRobotFight = new JMenu("人机对弈");
        this.peopleWithRobotFightPeopleHoldBlack = new JMenuItem("人执黑");
        this.peopleWithRobotFightPeopleHoldWhite = new JMenuItem("人执白");

        //人人对弈下有两个子菜单
        //  人人对弈：
        //      |---人执黑
        //      |---人执白
        this.systemFunction.add(this.loginMenu);

        //人机对弈下有两个子菜单
        //  人机对弈：
        //  |---人执黑
        //  |---人执白
        this.peopleWithRobotFight.add(this.peopleWithRobotFightPeopleHoldBlack);
        this.peopleWithRobotFight.add(this.peopleWithRobotFightPeopleHoldWhite);

        //最终菜单条将是：
        //  |---系统功能
        //      |---登录/退出
        //  |---人人对弈
        //      |---人执黑
        //      |---人执白
        //  |---人机对弈：
        //      |---人执黑
        //      |---人执白
        this.menuBar.add(this.systemFunction);
        this.menuBar.add(this.peopleWithRobotFight);

        this.setJMenuBar(this.menuBar);
    }

    /**
     * 构造主界面内容
     */
    private void buildMainContent() {
        this.gameInfo = new GameInfo();
        this.chessBoard = new ChessBoard(this.gameInfo);

        this.add(this.chessBoard, BorderLayout.CENTER);
        this.add(this.gameInfo, BorderLayout.NORTH);
    }

    /**
     * 登录成功事件，启动服务器发送登录信息
     *
     * @param user
     */
    @Override
    public void onLoginSuccess(User user) {
        this.gameInfo.updateUserInfo("欢迎您，" + user.getName());
        this.gameInfo.updateGameStatus("已登录，请选择对弈方式");
        this.loginMenu.setText("退出");
    }

    @Override
    public void onLoginFailed(String msg) {
        this.gameInfo.updateUserInfo("未登录");
        this.gameInfo.updateGameStatus("请登录");
        this.loginMenu.setText("登录");
        this.gameInfo.updateUserChess(null);
    }
}
