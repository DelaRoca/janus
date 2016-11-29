package br.com.janus.view;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.janus.controller.OrdemServicoController;

public class GerenciadorDeInterface {
	private static Frame frame;

	public static void run() {
		open("Janus");
	}
	
	private static void open(String title) {
		String iconPath = "resources" + File.separator + "icone.png";
		LookAndFeel.aparenciaNimbus();
		frame = new Frame();
		setPanel(new Principal());
		frame.setTitle(title);
		frame.pack();
		frame.setIconImage(new ImageIcon(iconPath).getImage());
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(1000, 650);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void setPanel(JPanel panel) {
		JPanel panelAtual = frame.getPanel();
		panelAtual.removeAll();
		for (Component comp : panel.getComponents())
			panelAtual.add(comp);
		String backPath = "resources" + File.separator + "background.jpg";
		panelAtual.add(new JLabel(new ImageIcon(backPath))).setBounds(0, 0, 1000, 600);
		panelAtual.repaint();
	}

}
