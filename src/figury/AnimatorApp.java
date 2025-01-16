package figury;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AnimatorApp extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public int score = 0; // Wynik
	private JLabel scoreLabel; // Etykieta do wyświetlania wyniku

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final AnimatorApp frame = new AnimatorApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AnimatorApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Kliknij na kwadrat i zacznij go przesuwać strzałkami");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int ww = 450, wh = 300;
		setBounds((screen.width - ww) / 2, (screen.height - wh) / 2, ww, wh);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(Color.decode("#DC667C"));
		contentPane.setLayout(null);

		AnimPanel kanwa = new AnimPanel();
		kanwa.setBounds(10, 11, 422, 219);
		contentPane.add(kanwa);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				kanwa.initialize();
			}
		});

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kanwa.addFig();
			}
		});
		btnAdd.setBounds(10, 239, 80, 23);
		contentPane.add(btnAdd);

		JButton btnAnimate = new JButton("Animate");
		btnAnimate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				kanwa.animate();
			}
		});
		btnAnimate.setBounds(100, 239, 80, 23);
		contentPane.add(btnAnimate);

		// Dodaj gracza do AnimPanel
		kanwa.addPlayer();

		// Inicjalizacja etykiety wyniku
		scoreLabel = new JLabel("Wynik: " + score);
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
		scoreLabel.setBounds(200, 239, 120, 23); // Pozycjonowanie
		contentPane.add(scoreLabel);

		// Przekazanie odwołania do AnimatorApp
		kanwa.setAnimatorApp(this);

	}

	public void updateScore(int newScore) {
		this.score = newScore;
		scoreLabel.setText("Wynik: " + score);
	}
}
