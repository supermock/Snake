package br.com.supermock;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;


@SuppressWarnings("serial")
public class World extends JFrame implements KeyListener{
	private JPanel pnMundo;
	private Entidade entidade;
	private ImageIcon icon = new ImageIcon(getClass().getResource("/br/com/supermock/img/icone.png"));
	
	public World() {
		super("Snake");
		this.criarMundo();
		this.addKeyListener(this);
		this.setSize(400, 410);
		this.setIconImage(icon.getImage());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void criarMundo(){
		pnMundo = new JPanel();
		pnMundo.setBackground(Color.black);
		pnMundo.setLayout(null);
		pnMundo.setBorder(BorderFactory.createLineBorder(Color.white, 3));
		this.add(pnMundo);
	
		entidade = new Entidade(pnMundo);
		pnMundo.add(entidade.getInicial());
	}
	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == 40){
			entidade.orientador(4); // Baixo
		}else if (event.getKeyCode() == 38) {
			entidade.orientador(3); // Cima
        }else if (event.getKeyCode() == 39) {
			entidade.orientador(1); // Direita
        }else if (event.getKeyCode() == 37) {
			entidade.orientador(2); // Esquerda
		}else if (event.getKeyChar() == KeyEvent.VK_ENTER) {
			entidade.reiniciar();
		}else if (event.getKeyCode() == 80) {
			entidade.parar();
		}
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
	
}
