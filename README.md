# JAVA swing 五子棋项目
人机/人人对弈五子棋项目

### 一、部分截图：

人人对弈时服务器日志：
-----------------------------------
![server running](http://res.mrhuo.com/github/gobang-simple-server.png)

人人对弈时客户端运行截图：
-----------------------------------
![gobang-fight-with-people](http://res.mrhuo.com/github/gobang-fight-with-people.png)

人机对战截图：
-----------------------------------
![gobang-fight-with-robot](http://res.mrhuo.com/github/gobang-fight-with-robot.png)

游戏结束截图：
-----------------------------------
![gobang-game-over](http://res.mrhuo.com/github/gobang-game-over.png)

极端情况，和局截图：
-----------------------------------
![gobang-balance](http://res.mrhuo.com/github/gobang-balance.png)

### 二、运行

##### 1.启动服务器 （人人对弈时需要）
切换到 `build/` 目录下，执行 `java -jar gobang-server.jar` 即可。

##### 2.启动客户端
切换到 `build/` 目录下，双击 `gobang.jar` 即可。


#接口设计
##1.AI算法接口
Point getBestPoint(ChessColors,myColor,mySteps,competitorSteps)
ChessColors表示棋盘和棋局，为一个二维数组，元素为白子，黑子或空。数组大小对应棋盘大小。
myColor,mySteps,competitorSteps这三个参数可选，根据算法不一定用到。
mySteps,competitorSteps分别记录自己和对方所走的每一步，推测对应的下棋模式。
###算法1. 只需当前棋局
每一种情况如已连4两端无子必赢，已连4一端有字，已连3等赋一分值。扫描每一点，符合情况的分值累加，
分别计算白子和黑子的得分，再相加，如果分值高，对方高则应阻止，自己高就应在此落子，所以无论是哪方
在此落子分值高自己都应在此落子。