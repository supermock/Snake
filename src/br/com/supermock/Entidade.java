package br.com.supermock;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Entidade {
	private JPanel[] bloco;
	private Thread thread;
	private int pos, direcao = 1, tamanho, dificuldade = 200;
	private JPanel mundo;
	private JLabel lbGameOver,lbVoceGanhou, lbParar,lbStatus;
	private int[] x, y;
	private boolean reiniciar, aviso, ganhou, pleaseWait;
	private final int DIREITA = 1;
	private final int ESQUERDA = 2;
	private final int CIMA = 3;
	private final int BAIXO = 4;
	private Comida comida;
	
	public Entidade(JPanel mundo) {
		this.mundo = mundo;
	    comida = new Comida(mundo);
	    tamanho = 100;
		bloco = new JPanel[tamanho];
		x = new int[tamanho];
		y = new int[tamanho];
		mensagem();
	}
	private void mensagem(){
		lbGameOver = new JLabel("<html>GAME OVER!<br><hr><font face=Arial size=4 color=yellow>Pressione enter, para reiniciar.</font></html>");
		lbGameOver.setFont(new Font("Dungeon",Font.BOLD,30));
		lbGameOver.setBorder(BorderFactory.createLineBorder(Color.red, 2));
		lbGameOver.setForeground(Color.white);
		lbGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		lbGameOver.setVisible(false);
		mundo.add(lbGameOver);
		
		lbVoceGanhou = new JLabel("<html>Você ganhou!<br><hr><font face=Arial size=4 color=yellow>Pressione enter, para reiniciar.</font></html>");
		lbVoceGanhou.setFont(new Font("Dungeon",Font.BOLD,30));
		lbVoceGanhou.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		lbVoceGanhou.setForeground(Color.white);
		lbVoceGanhou.setHorizontalAlignment(SwingConstants.CENTER);
		lbVoceGanhou.setVisible(false);
		mundo.add(lbVoceGanhou);
		
		lbParar = new JLabel("<html>Parado!<br><hr><font face=Arial size=4 color=yellow>Pressione 'P', para continuar.</font></html>");
		lbParar.setFont(new Font("Dungeon",Font.BOLD,30));
		lbParar.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		lbParar.setForeground(Color.white);
		lbParar.setHorizontalAlignment(SwingConstants.CENTER);
		lbParar.setVisible(false);
		mundo.add(lbParar);
		
		lbStatus = new JLabel("Score: 1");
		lbStatus.setBounds(0, 0, 160, 40);
		lbStatus.setFont(new Font("Dungeon",Font.BOLD,27));
		lbStatus.setBorder(BorderFactory.createLineBorder(Color.green, 2));
		lbStatus.setForeground(Color.white);
		lbStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lbStatus.setVisible(true);
		mundo.add(lbStatus);
		
		comida.geraPosicao();
		mundo.add(comida.obterComida()); //Adicionando comida
	}
	public JPanel getInicial() {
		montaCorpo(200, 200);
		movimentacao();
		return retornaCorpo();
	}
	private void montaCorpo(int x, int y){
		if (pos < tamanho) {
			bloco[pos] = new JPanel();
			bloco[pos].setBounds(x, y, 20, 20);
			bloco[pos].setBackground(Color.BLUE);
			bloco[pos].setBorder(BorderFactory.createLineBorder(Color.black, 2));
			if (pos == 0) {
				bloco[0].setBackground(Color.green);
			}
		} else {
			direcao = 0;
			ganhou = true;
		}
	}
	private JPanel retornaCorpo(){
		pos++;
		switch (pos) {
		case 10:
			dificuldade = 150;
			break;
		case 20:
			dificuldade = 125;
			break;
		case 30:
			dificuldade = 100;
			break;
		case 40:
			dificuldade = 85;
			break;
		case 50:
			dificuldade = 65;
			break;			
		}
		montaCorpo(x[pos-1], y[pos-1]);
		return bloco[pos-1];
	}
	private void movimentacao(){
		thread = new Thread(){
			public void run(){
				while (true) {
					for (int i = 0; i < pos; i++) {
						x[i] = bloco[i].getX();
						y[i] = bloco[i].getY();
					}
					try {
						Thread.sleep(dificuldade);
						switch (direcao) {
						case DIREITA:
								bloco[0].setBounds(bloco[0].getX()+bloco[0].getWidth(), bloco[0].getY(), 20, 20);
							break;
						case ESQUERDA:
								bloco[0].setBounds(bloco[0].getX()-bloco[0].getWidth(), bloco[0].getY(), 20, 20);
							break;
						case CIMA:
								bloco[0].setBounds(bloco[0].getX(), bloco[0].getY()-bloco[0].getHeight(), 20, 20);
							break;
						case BAIXO:
								bloco[0].setBounds(bloco[0].getX(), bloco[0].getY()+bloco[0].getHeight(), 20, 20);
							break;
							default:
								Thread.sleep(400);
								if (aviso == false) {
									if (ganhou != true) {
										lbGameOver.setBounds(mundo.getWidth()/2-130, mundo.getHeight()/2-40, 260, 80);
										lbGameOver.setVisible(true);
									} else if (ganhou == true) {
										lbVoceGanhou.setBounds(mundo.getWidth()/2-130, mundo.getHeight()/2-40, 260, 80);
										lbVoceGanhou.setVisible(true);
										ganhou = false;
									}
									aviso = true;
								} 
								if (reiniciar == true) {
									lbGameOver.setVisible(false); //Tira da tela a mensagem;
									lbVoceGanhou.setVisible(false);
									aviso = false;
									//Mata todos os blocos;
									Arrays.fill(bloco, null);
									pos = 0;
									//Remove tudo do painel;
									mundo.removeAll();
									mundo.repaint();
									//Monta e adiciona tudo;
									montaCorpo(200, 200);
									mundo.add(lbGameOver);
									mundo.add(lbVoceGanhou);
									mundo.add(lbParar);
									mundo.add(lbStatus);
									lbStatus.setText("Score: 1");
									comida.geraPosicao();
									mundo.add(comida.obterComida());
									mundo.add(retornaCorpo());
									mundo.validate();
									dificuldade = 200;
									direcao = 1; //Manda o bloco para direita;
									reiniciar = false;
								}
								break;
						}
						for (int j = 1; j < pos; j++) {
							if (bloco[0].getX() == bloco[j].getX() && bloco[0].getY() == bloco[j].getY()) {
								direcao = 0;
							} 
						}
						if ((bloco[0].getX() + bloco[0].getWidth())-10 >= mundo.getWidth() || bloco[0].getX()+10 < mundo.getX()) {
							direcao = 0;
						} else if (bloco[0].getY() + bloco[0].getHeight() >= mundo.getHeight()|| bloco[0].getY()+10 < mundo.getY()) {
							direcao = 0;
						}
						if (comida.obterComida().getX() == bloco[0].getX() && comida.obterComida().getY() == bloco[0].getY()) {
							comida.geraPosicao();
							mundo.add(retornaCorpo());
							lbStatus.setText("Score: " + pos);
						}	
					} catch (Exception e) {
						// TODO: handle exception
					}
					for (int a = 0; a < pos-1; a++) {
						bloco[a+1].setBounds(x[a],y[a], 20, 20);
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
	public void parar(){
		if (direcao != 0) {
			if (!pleaseWait) {
				lbParar.setBounds(mundo.getWidth()/2-130, mundo.getHeight()/2-40, 260, 80);
				lbParar.setVisible(true);
				pleaseWait = true;
			} else {
				lbParar.setVisible(false);
				synchronized (thread) {
		            pleaseWait = false;
		            thread.notify();
		        }
			}
		}
	}
	public void reiniciar(){
		if (direcao == 0) {
			reiniciar = true;
		}
	}
	public void orientador(int direcao){
		if(this.direcao != 0){
			if (pos != 1) {
				if (this.direcao == CIMA && direcao != BAIXO || this.direcao == BAIXO && direcao != CIMA) {
					this.direcao = direcao;
				} else if (this.direcao == DIREITA && direcao != ESQUERDA || this.direcao == ESQUERDA && direcao != DIREITA) {
					this.direcao = direcao;
				}
			} else {
				this.direcao = direcao;
			}
		}
	}
}

