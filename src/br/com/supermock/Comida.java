package br.com.supermock;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Comida {
	private JPanel comida;
	private Thread thread;
	private boolean pleaseWait;
	private int x,y;
	public Comida(JPanel world) {
		comida = new JPanel();
		comida.setBackground(Color.red);
		comida.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		thread = new Thread(){
			public void run(){
				while (true) {
					x = ((int) (Math.random() * 50)+1) * 20;
					y = ((int) (Math.random() * 50)+1) * 20;
					if (x < world.getWidth()-20 && y < world.getHeight()-20) {
						comida.setBounds(x, y, 20, 20);
						pleaseWait = true;
					}
					synchronized (thread) {
	                    while (pleaseWait) {
	                        try {
	                            thread.wait();
	                        } catch (Exception e) {
	                        }
	                    }
	                }
				}
			}
		};
		thread.start();
	}
	public void geraPosicao(){
		synchronized (thread) {
            pleaseWait = false;
            thread.notify();
        }
	}
	public JPanel obterComida(){
		return comida;
	}
}
