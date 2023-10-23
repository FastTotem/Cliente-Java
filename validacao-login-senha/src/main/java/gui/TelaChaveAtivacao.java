package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class TelaChaveAtivacao {

    public interface ActivationListener {
        void onActivation(String key);
    }
    private String chaveAtivacao;
    private JButton buttonAtivar;
    private JFrame telaChaveAtivacaoFrame;

    private JTextField inputChaveAtivacao;

    public void desenharTela(ActivationListener listener) {

        Color roxoMedio = new Color(189, 6, 221);
        Color roxoEscuro = new Color(103, 4, 120);

        Font titulo = new Font("Montserrat", Font.BOLD, 30);
        Font label = new Font("Montserrat", Font.BOLD, 15);

        Image icon = new ImageIcon("D:\\FastTotem\\Cliente-Java\\validacao-login-senha\\src\\main\\java\\assets\\img\\IconFastTotem.png").getImage();

        telaChaveAtivacaoFrame = new JFrame();
        telaChaveAtivacaoFrame.setSize(520, 300);
        telaChaveAtivacaoFrame.setTitle("Fast Totem");
        telaChaveAtivacaoFrame.setIconImage(icon);
        telaChaveAtivacaoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        telaChaveAtivacaoFrame.getContentPane().setBackground(roxoMedio);
        telaChaveAtivacaoFrame.setLayout(null);
        telaChaveAtivacaoFrame.setResizable(false);

        JLabel labelTituloTela = new JLabel();
        labelTituloTela.setText("Fast Totem - Ativação de Totem");
        labelTituloTela.setBounds(25, 30, 450, 30);
        labelTituloTela.setForeground(Color.WHITE);
        labelTituloTela.setFont(titulo);

        JLabel labelChaveAtivacao = new JLabel();
        labelChaveAtivacao.setText("Insira aqui a chave de ativação do totem:");
        labelChaveAtivacao.setBounds(100, 90, 300, 30);
        labelChaveAtivacao.setForeground(Color.WHITE);
        labelChaveAtivacao.setFont(label);

        inputChaveAtivacao = new JTextField();
        inputChaveAtivacao.setBounds(100, 120, 300, 30);
        inputChaveAtivacao.setHorizontalAlignment(JTextField.LEFT);

        buttonAtivar = new JButton();
        buttonAtivar.setText("Ativar");
        buttonAtivar.setBounds(200, 170, 100, 30);
        buttonAtivar.setBackground(roxoEscuro);
        buttonAtivar.setForeground(Color.WHITE);
        buttonAtivar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        telaChaveAtivacaoFrame.getContentPane().add(labelTituloTela);
        telaChaveAtivacaoFrame.getContentPane().add(labelChaveAtivacao);
        telaChaveAtivacaoFrame.getContentPane().add(inputChaveAtivacao);
        telaChaveAtivacaoFrame.getContentPane().add(buttonAtivar);

        telaChaveAtivacaoFrame.setVisible(true);

        // Ouvintes de eventos
        // Muda o cursor para pointer quando passar no botão
        buttonAtivar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonAtivar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                buttonAtivar.setCursor(Cursor.getDefaultCursor());
            }
        });
        CountDownLatch latch = new CountDownLatch(1);

        buttonAtivar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String chaveAtivacaoText = inputChaveAtivacao.getText();
                listener.onActivation(chaveAtivacaoText);
                latch.countDown();
            }
        });

        // permitir avançar para o botão com backspace
        inputChaveAtivacao.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == 10) {
                    buttonAtivar.requestFocus();
                }

            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void fechar(JFrame tela){
        tela.dispose();
    }

    public String getChaveAtivacao() {
        return chaveAtivacao;
    }

    public JButton getButtonAtivar() {
        return buttonAtivar;
    }

    public JTextField getInputChaveAtivacao() {
        return inputChaveAtivacao;
    }

    public JFrame getTelaChaveAtivacaoFrame() {
        return telaChaveAtivacaoFrame;
    }
}
