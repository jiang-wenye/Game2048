import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game2048 extends JFrame {
        private final int SIZE=4;
        private final int BLOCK_WIDTH=60;
        private final int BLOCK_HEIGHT=60;
        private final int WINDOW_WIDTH=1000;
        private final int WINDOW_HEIGHT=550;
        private final int BUTTON_WIDTH=60;
        private final int BUTTON_HEIGHT=60;
        private final int SPACE=20;

        private JTextField[][] fields=new JTextField[SIZE][SIZE];
        private JButton leftButton,rightButton,upButton,downButton;
        int[][]data=new int[SIZE][SIZE];

        public Game2048() {
            //窗口大小与位置
            this.setBounds(200, 200, WINDOW_WIDTH, WINDOW_HEIGHT);
            //窗口关闭，进程结束
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //设置窗口居中
            this.setLocationRelativeTo(null);
            //窗口名称
            this.setTitle("2048");
            //窗口是否可见
            this.setVisible(true);
            //设置窗口内组件为绝对布局
            setLayout(null);
            //将4*4的编辑框添加到窗口正中间
            int left, top;
            left = (WINDOW_WIDTH - BLOCK_WIDTH * SIZE) / 2;
            top = (WINDOW_HEIGHT - BLOCK_HEIGHT * SIZE) / 2;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    fields[i][j] = new JTextField();
                    fields[i][j].setBounds(left + j * BLOCK_WIDTH, top + i * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT);
                    this.add(fields[i][j]);
                }
            }

            //定义四个按钮
            leftButton = new JButton("左");
            leftButton.setBounds(left - SPACE - BUTTON_WIDTH,
                    top + (SIZE * BLOCK_HEIGHT - BUTTON_HEIGHT) / 2,
                    BUTTON_WIDTH,
                    BUTTON_HEIGHT);
            this.add(leftButton);
            //实例化匿名对象
            leftButton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //弹出对话框
                            //JOptionPane.showMessageDialog(null, "hello?");
                            //将data中每一行数据复制为一维数组，处理一维数组，然后将，一维数组复制回data
                            int[] a = new int[SIZE];
                            for (int i = 0; i < SIZE; i++) {
                                for (int j = 0; j < SIZE; j++) {
                                    a[j] = data[i][j];
                                }
                                accessOneArray(a);
                                for (int j = 0; j < SIZE; j++) {
                                    data[i][j] = a[j];
                                }
                            }
                            selectRandomPos();
                            putDataToFiles();
                        }
                    }
            );

            rightButton = new JButton("右");
            rightButton.setBounds(left + BLOCK_WIDTH * SIZE + SPACE,
                    top + (SIZE * BLOCK_HEIGHT - BUTTON_HEIGHT) / 2,
                    BUTTON_WIDTH,
                    BUTTON_HEIGHT);
            this.add(rightButton);

            upButton = new JButton("上");
            upButton.setBounds(left + (SIZE * BLOCK_WIDTH - BUTTON_WIDTH) / 2,
                    top - SPACE - BUTTON_HEIGHT,
                    BUTTON_WIDTH,
                    BUTTON_HEIGHT);
            this.add(upButton);

            downButton = new JButton("下");
            downButton.setBounds(left + (SIZE * BLOCK_WIDTH - BUTTON_WIDTH) / 2,
                    top + SPACE + SIZE * BLOCK_HEIGHT,
                    BUTTON_WIDTH,
                    BUTTON_HEIGHT);
            this.add(downButton);

            //在两个随机位置放置随机数
            selectRandomPos();
            selectRandomPos();

            //将data中的数据填充到fileds对象中
            putDataToFiles();
        }

        //在数组中随机选择一个值为0的元素，给元素赋值2、4随机数
        private void selectRandomPos(){
            int row,col;
            row=(int) (Math.random()*SIZE);
            col=(int) (Math.random()*SIZE);
            while (data[row][col]!=0){
                col++;
                if(col==SIZE){
                    col=0;
                    row++;
                    if (row==SIZE)
                        row=0;
                }
            }
            if (Math.random()>0.5)
                data[row][col]=2;
            else
                data[row][col]=4;
        }

        //将data中的数据写入fields中
        private void putDataToFiles(){
            for (int i=0;i<SIZE;i++){
                for (int j=0;j<SIZE;j++){
                    if (data[i][j]==0)
                        fields[i][j].setText("");
                    else
                        fields[i][j].setText(data[i][j]+"");
                }
            }
        }

        //将一维数组中的数据向左移动，移动时相邻的数据累加
        private void accessOneArray(int[]arr){
            int number=1;//确定是第几个非0；如值为1，则找到的非零为第一个非零
            int firstPos=0;//保存第一个非0元素的下标
            //相邻的相同数字累加
            for (int i=0;i<arr.length;i++){
                if (arr[i]!=0){
                    if (number==1){
                        firstPos=i;
                        number++;
                    }else{
                        if (arr[i]==arr[firstPos]){
                            arr[firstPos]+=arr[i];
                            arr[i]=0;
                            number=1;
                        }else{
                            firstPos=i;
                        }
                    }
                }
            }
            //将非零的数据向左移动
            int pos=0;
            for(int i=0;i<arr.length;i++){
                if(arr[i]!=0){
                    arr[pos]=arr[i];
                    pos++;
                }
            }
            for (int i=pos;i< arr.length;i++){
                arr[i]=0;
            }
        }
        private void showArr(int[]arr){
            for(int i=0;i< arr.length;i++){
                System.out.println(arr[i]+"");
            }
            System.out.println();
        }
}