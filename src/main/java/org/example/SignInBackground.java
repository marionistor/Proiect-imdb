package org.example;

import javax.swing.*;
import java.awt.*;

public class SignInBackground extends JPanel {

        private Image backgroundImage;

        public SignInBackground(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(),  null);
        }
}
