/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanelcreate extends JPanel{
  private Image bg;

    public ImagePanelcreate() {
        bg = new ImageIcon(getClass().getResource("/gui/create.png")).getImage();
        setOpaque(false);
    }

    @Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g); // keep this for safety

    // draw image AFTER background
    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
}  
}
