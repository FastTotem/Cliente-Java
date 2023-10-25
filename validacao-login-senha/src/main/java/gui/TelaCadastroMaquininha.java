package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class TelaCadastroMaquininha {

    public interface ActivationListener {
        void onActivation();
    }
    private String chaveAtivacao;
    private JButton buttonConfirmar;
    private JFrame telaChaveAtivacaoFrame;

    private JTextField inputChaveAtivacao;

    public void desenharTelaMaquininhaConectada(TelaCadastroMaquininha.ActivationListener listener) {

        Color roxoMedio = new Color(189, 6, 221);
        Color roxoEscuro = new Color(103, 4, 120);

        Font titulo = new Font("Montserrat", Font.BOLD, 20);
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
        labelTituloTela.setText("Fast Totem - Conexão da maquininha");
        labelTituloTela.setBounds(25, 30, 450, 30);
        labelTituloTela.setForeground(Color.WHITE);
        labelTituloTela.setFont(titulo);

        JLabel labelChaveAtivacao = new JLabel();
        labelChaveAtivacao.setText("<html>Por favor, garanta que sua maquininha de cartão esteja devidamente conectada. Quando estiver certo disso, clique em OK</html>");
        labelChaveAtivacao.setBounds(20, 90, 450, 60);
        labelChaveAtivacao.setForeground(Color.WHITE);
        labelChaveAtivacao.setFont(label);

        buttonConfirmar = new JButton();
        buttonConfirmar.setText("OK");
        buttonConfirmar.setBounds(100, 180, 300, 30);
        buttonConfirmar.setBackground(roxoEscuro);
        buttonConfirmar.setForeground(Color.WHITE);
        buttonConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        telaChaveAtivacaoFrame.getContentPane().add(labelTituloTela);
        telaChaveAtivacaoFrame.getContentPane().add(labelChaveAtivacao);
        telaChaveAtivacaoFrame.getContentPane().add(buttonConfirmar);

        telaChaveAtivacaoFrame.setVisible(true);

        // Ouvintes de eventos
        // Muda o cursor para pointer quando passar no botão
        buttonConfirmar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                buttonConfirmar.setCursor(Cursor.getDefaultCursor());
            }
        });
        CountDownLatch latch = new CountDownLatch(1);

        buttonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onActivation();
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void desenharTelaMaquininhaDesconectada(TelaCadastroMaquininha.ActivationListener listener){

        Color roxoMedio = new Color(189, 6, 221);
        Color roxoEscuro = new Color(103, 4, 120);

        Font titulo = new Font("Montserrat", Font.BOLD, 20);
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
        labelTituloTela.setText("Fast Totem - Conexão da maquininha");
        labelTituloTela.setBounds(25, 30, 450, 30);
        labelTituloTela.setForeground(Color.WHITE);
        labelTituloTela.setFont(titulo);

        JLabel labelChaveAtivacao = new JLabel();
        labelChaveAtivacao.setText("<html>Por favor, remova a maquininha. Após remover, clique em OK<html>");
        labelChaveAtivacao.setBounds(20, 90, 450, 30);
        labelChaveAtivacao.setForeground(Color.WHITE);
        labelChaveAtivacao.setFont(label);

        buttonConfirmar = new JButton();
        buttonConfirmar.setText("OK");
        buttonConfirmar.setBounds(100, 120, 300, 30);
        buttonConfirmar.setBackground(roxoEscuro);
        buttonConfirmar.setForeground(Color.WHITE);
        buttonConfirmar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        telaChaveAtivacaoFrame.getContentPane().add(labelTituloTela);
        telaChaveAtivacaoFrame.getContentPane().add(labelChaveAtivacao);
        telaChaveAtivacaoFrame.getContentPane().add(buttonConfirmar);

        telaChaveAtivacaoFrame.setVisible(true);

        // Ouvintes de eventos
        // Muda o cursor para pointer quando passar no botão
        buttonConfirmar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                buttonConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                buttonConfirmar.setCursor(Cursor.getDefaultCursor());
            }
        });
        CountDownLatch latch = new CountDownLatch(1);

        buttonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onActivation();
                latch.countDown();
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

    public JButton getButtonConfirmar() {
        return buttonConfirmar;
    }

    public JTextField getInputChaveAtivacao() {
        return inputChaveAtivacao;
    }

    public JFrame getTelaChaveAtivacaoFrame() {
        return telaChaveAtivacaoFrame;
    }

}
