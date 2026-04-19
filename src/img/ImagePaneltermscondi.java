/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package img;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePaneltermscondi extends JPanel{
  private Image bg;

    public ImagePaneltermscondi() {
        bg = new ImageIcon(getClass().getResource("/img/TERMS.png")).getImage();
        setOpaque(false);
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g); 

   
    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
}  
}
