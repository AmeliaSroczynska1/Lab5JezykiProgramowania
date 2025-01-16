package figury;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AnimPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Image image;
	private Graphics2D device;
	private Graphics2D buffer;
	private int delay = 70;
	private Timer timer;

	private List<Figura> figures; // Lista figur
	private List<Figura> staticFigures; // Lista statycznych figur
	private PlayerButton player;
	private AnimatorApp animatorApp; // Referencja do AnimatorApp

	public AnimPanel() {
		super();
		timer = new Timer(delay, this);
		figures = new ArrayList<>();
		staticFigures = new ArrayList<>();
		setLayout(null);
	}

	public void setAnimatorApp(AnimatorApp app) {
		this.animatorApp = app; // Przechowywanie referencji
	}

	public void addPlayer() {
		player = new PlayerButton("");
		this.add(player);
		player.setBounds(0, 0, 30, 30);
		Thread thread = new Thread(player);
		thread.start();
		this.revalidate();
		this.repaint();

		// Start a collision detection thread
		Thread collisionThread = collisionDetectionThread();
		collisionThread.start();

		Thread scoreThread = scoreCollisionThread();
		scoreThread.start();
	}

	public void initialize() {
		int width = getWidth();
		int height = getHeight();

		image = createImage(width, height);
		buffer = (Graphics2D) image.getGraphics();
		buffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		device = (Graphics2D) getGraphics();
		device.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Start the circle spawner thread
		startCircleSpawner();
	}

	void addFig() {
		Figura fig = (figures.size() % 2 == 0)
				? new Kwadrat(buffer, delay, getWidth(), getHeight())
				: new Elipsa(buffer, delay, getWidth(), getHeight());
		figures.add(fig);
		timer.addActionListener(fig);
		new Thread(fig).start();
	}

	void animate() {
		if (timer.isRunning()) {
			timer.stop();
		} else {
			timer.start();
		}
	}

	private Thread collisionDetectionThread() {
		return new Thread(() -> {
			try {
				while (true) {
					synchronized (this) {
						Rectangle playerBounds = player.getBounds();
						for (Figura fig : figures) {
							Shape figShape = fig.shape;
							if (figShape != null && figShape.getBounds().intersects(playerBounds)) {
								showDialog();
								timer.stop();
								return;
							}
						}
					}
					Thread.sleep(20);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}

	private Thread scoreCollisionThread() {
		return new Thread(() -> {
			try {
				while (true) {
					boolean collisionDetected = false;
					synchronized (this) {
						Rectangle playerBounds = player.getBounds();
						List<Figura> collidedFigures = new ArrayList<>();
						for (Figura fig : staticFigures) {
							Shape figShape = fig.shape;
							if (figShape != null && figShape.getBounds().intersects(playerBounds)) {
								collidedFigures.add(fig);
							}
						}
						if (!collidedFigures.isEmpty()) {
							// Aktualizacja wyniku i usuwanie zaliczonych figur
							for (Figura fig : collidedFigures) {
								removeCircle(fig);
							}
							collisionDetected = true;
						}
					}
					if (collisionDetected && animatorApp != null) {
						SwingUtilities.invokeLater(() -> animatorApp.updateScore(animatorApp.score + 1));
					}
					Thread.sleep(50);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}

	private void removeCircle(Figura circle) {
		synchronized (this) {
			staticFigures.remove(circle);
			timer.removeActionListener(circle);
		}
	}
	private void showDialog() {
		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(this, "Przegrałeś! \nZdobyłeś " + animatorApp.score + " punktów", "Przegrana", JOptionPane.INFORMATION_MESSAGE);
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, null);
			buffer.clearRect(0, 0, getWidth(), getHeight());
		}
	}

	private void startCircleSpawner() {
		Thread spawnerThread = new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(5000); // Czeka 5 sekund
					SwingUtilities.invokeLater(() -> {
						synchronized (this) {
							Figura circle = new StaticCircle(buffer, delay, getWidth(), getHeight());
							staticFigures.add(circle);
							timer.addActionListener(circle);
							new Thread(circle).start();
						}
					});
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
		spawnerThread.start();
	}

}

