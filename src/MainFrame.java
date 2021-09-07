import javax.swing.*;
import java.awt.event.*;
public class MainFrame extends JFrame{
    private final int RECTANGLE_WIDTH = 20;
    private final int RECTANGLE_HEIGHT = 20;
    private final int RECTANGLE_COUNT_X = 30;
    private final int RECTANGLE_COUNT_Y = 30;

    private final int width = RECTANGLE_COUNT_X*RECTANGLE_WIDTH;
    private final int height = RECTANGLE_COUNT_Y*RECTANGLE_HEIGHT;

    private MainPanel main;
    private JMenuBar menuBar;
    private JMenuItem newMenu;
    private JMenuItem clearLines;
    private JMenuItem solveMenu;

    public MainFrame(){

        main = new MainPanel(width,height);
        menuBar = new MenuBar();

        setJMenuBar(menuBar);
        add(main);
        setSize(width,height);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private class MenuBar extends JMenuBar{
        public MenuBar(){
            JMenu menu = new JMenu("Menu");
            add(menu);

            newMenu = new JMenuItem("New Game");
            clearLines = new JMenuItem("Clear Lines");
            solveMenu = new JMenuItem("Solve");

            menu.add(newMenu);
            menu.add(clearLines);
            menu.add(solveMenu);

            menuListeners();
        }
        private void menuListeners(){
            newMenu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    main.refreshBoard();
                }
            });
            clearLines.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    main.removeLines();
                }
            });
            solveMenu.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    //solve
                    System.out.println("Clicked solve");
                    main.Solve();
                }
            });
        }
    }
    public static void main(String[] args) {
        new MainFrame();
    }


}
