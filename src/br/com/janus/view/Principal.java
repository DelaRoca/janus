package br.com.janus.view;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;

public class Principal extends JPanel {

	private static final long serialVersionUID = 1L;

	public Principal() {
		setLayout(null);
		
		JLabel lblJanus = new JLabel("Janus");
		lblJanus.setForeground(new Color(255, 215, 0));
		lblJanus.setFont(new Font("Edwardian Script ITC", Font.BOLD, 60));
		lblJanus.setHorizontalAlignment(SwingConstants.CENTER);
		lblJanus.setBounds(10, 24, 981, 124);
		add(lblJanus);
		
		JLabel lblMoldura = new JLabel("");
		lblMoldura.setBounds(10, 11, 981, 578);
		lblMoldura.setBorder(BorderFactory.createLoweredSoftBevelBorder());
		add(lblMoldura);

	}
}
