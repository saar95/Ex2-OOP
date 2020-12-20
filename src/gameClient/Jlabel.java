package gameClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class Jlabel extends JFrame implements ActionListener {
        private JTextField jTextID;
        private JLabel label;
        private JButton jButton;
        private JLabel level;
        private JTextField jTextLevel;
        private JLabel success;
        private JLabel wrongID;
        private JLabel wrongLevel;
        private ImageIcon image;
        private Ex2 ex2;
        private Long gameId;
        private int gameLevel;

        public Jlabel(Ex2 ex2){
            this.ex2=ex2;
            label=new JLabel();
            label.setBounds(32,50,100,20);
            label.setText("ID:");
            jTextID=new JTextField();
            jTextID.setBounds(70,50,100,20);

            level=new JLabel();//
            level.setBounds(32,80,100,20);//
            level.setText("Level:");//
            jTextLevel=new JTextField();
            jTextLevel.setBounds(70,80,100,20);

            jButton=new JButton();
            jButton.setText("Start");
            jButton.setBounds(78,110,80,30);
            jButton.addActionListener(this);

            success=new JLabel("");
            success.setBounds(60,130,300,20);
            wrongID=new JLabel("");
            wrongID.setBounds(180,50,300,20);
            wrongLevel=new JLabel("");
            wrongLevel.setBounds(180,80,300,20);

            image=new ImageIcon("data/open.jpeg");
            setSize(490,260);

            //setIconImage();
            this.add(label);
            this.add(jTextID);
            this.add(level);
            this.add(jTextLevel);
            this.add(jButton);
            this.add(success);
            this.add(wrongID);
            this.add(wrongLevel);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);
            setVisible(true);
        }
        public Long getId(){
            return this.gameId;
        }
        public int getLevel(){
            return this.gameLevel;
        }
        public void setId(Long id){
            this.gameId=id;
        }
        public void setLevel(int level){
            this.gameLevel=level;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean flag =true;
            String textID=jTextID.getText();
            Long id = Long.valueOf(textID.length());
            this.gameId =Long.parseLong(String.valueOf(textID));
            if (id!=9) {
                flag=false;
                wrongID.setText("Wrong ID,please try again");
            }
            String textLevel=jTextLevel.getText();
            Integer level=Integer.parseInt(textLevel);
            if (flag==true) {
                success.setText("Successful login,lets start!");
                this.gameLevel=Integer.parseInt(textLevel);
                ex2.setId(getId());
                ex2.setLevel(getLevel());
                setVisible(false);
                Thread client = new Thread(ex2);
                client.start();
            }
        }
}
