/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inside;

import static inside.Inicio.nombre;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import login.login1;

/**
 *
 * @author ASUS
 */
public class Noticia extends javax.swing.JFrame {

    public JLabel titulos[], descripciones[], fechas[], autores[], facultades[], lblimagenes[];
    public Object imagenes[];
    /**
     * Creates new form Noticia
     */
    public Noticia() {
        initComponents();
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.white);
        verNoticia("general");
    }

    public int contarDatos(){
        int aux=0;
        login1 lg1 = new login1();
        lg1.Conexion();
        try{
            Statement stmt = login1.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM noticias");
            while(rs.next()){
                aux+=1;
            }
        }catch(Exception ex){
            System.out.println("Error->"+ex);
        }
        System.out.println(aux);
        return aux;
    }
    
    public ResultSet visualizar(){
        login1 lg1 = new login1();
        Connection Conexion;
        Conexion = lg1.Conexion();
        ResultSet rs = null;    
        try{
            PreparedStatement ps = login1.con.prepareStatement("SELECT * FROM noticias");
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error de consulta -> "+ ex.getMessage());
        }
        return rs;
    }
    
    public void verNoticia(String facultad){
        int numDatos = contarDatos();
        if(numDatos==0){
            JLabel lblno = new JLabel();
            lblno.setText("No hay noticias por mostrar.");
            lblno.setFont(new java.awt.Font("Trebuchet MS", 1, 32));
            lblno.setBounds(120, 60, 600, 50);
            jPanel2.add(lblno);
        }
        jPanel2.setPreferredSize(new Dimension(688,numDatos*160));
        String titulos3[], descripciones3[], fechas3[], autores3[], facultades3[];
        login1 lg1 = new login1();
        lg1.Conexion();
        if(facultad=="general"){
            String query = "SELECT * FROM noticias";
            try {
                Statement stmt = login1.con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                titulos3 = new String[numDatos];
                descripciones3 = new String[numDatos];
                fechas3 = new String[numDatos];
                autores3 = new String[numDatos];
                facultades3 = new String[numDatos];
                imagenes = new Object[numDatos];
                rs.next();
                for(int i=0; i<numDatos; i++){
                    titulos3[i]=rs.getString("titulo");
                    descripciones3[i]=rs.getString("descripc_breve");
                    fechas3[i]=rs.getString("fecha");
                    autores3[i]=rs.getString("autor");
                    facultades3[i]=rs.getString("facultad");
                    Blob blob = rs.getBlob("img");
                    byte[] data = blob.getBytes(1, (int)blob.length());
                    BufferedImage img = null;
                    try{
                        img = ImageIO.read(new ByteArrayInputStream(data));
                    }catch(IOException ex){
                        System.out.println("Error. "+ex.getMessage());
                    }
                    ImageIcon icono = new ImageIcon(img.getScaledInstance(500, 500, img.SCALE_DEFAULT));
                    imagenes[i] = new JLabel(icono);
                    rs.next();
                    System.out.println("1");
                }
                titulos = new JLabel[numDatos];
                descripciones = new JLabel[numDatos];
                fechas = new JLabel[numDatos];
                autores = new JLabel[numDatos];
                facultades = new JLabel[numDatos];
                lblimagenes = new JLabel[numDatos];
                
                for(int i=0; i<numDatos; i++){
                    System.out.println("2");
                    titulos[i] = new JLabel();
                    titulos[i].setText(titulos3[i]);
                    titulos[i].setFont(new java.awt.Font("Trebuchet MS", 1, 25));
                    titulos[i].setBounds(150, 40+i*140, 530, 30);
                    jPanel2.add(titulos[i]);
                    descripciones[i] = new JLabel();
                    descripciones[i].setText("<html>"+descripciones3[i]+"</html>");
                    descripciones[i].setBounds(150, 240+i*140, 530, 80);
                    descripciones[i].setVerticalAlignment(javax.swing.SwingConstants.TOP);
                    jPanel2.add(descripciones[i]);
                    fechas[i] = new JLabel();
                    fechas[i].setText(fechas3[i]+" - ");
                    fechas[i].setFont(new java.awt.Font("Trebuchet MS", 1, 14));
                    fechas[i].setBounds(150, 65+i*140, 100, 20);
                    jPanel2.add(fechas[i]);
                    autores[i] = new JLabel();
                    autores[i].setText(autores3[i]);
                    autores[i].setFont(new java.awt.Font("Trebuchet MS", 1, 14));
                    autores[i].setBounds(223, 65+i*140, 250, 20);
                    jPanel2.add(autores[i]);
                    facultades[i] = new JLabel();
                    facultades[i].setText(facultades3[i]);
                    facultades[i].setFont(new java.awt.Font("Trebuchet MS", 2, 14));
                    facultades[i].setForeground(new java.awt.Color(0,0,102));
                    facultades[i].setBounds(150, 140+i*140, 250, 20);
                    jPanel2.add(facultades[i]);
                    lblimagenes[i] = (JLabel) imagenes[i];
                    lblimagenes[i].setBounds(150, 100+i*140, 500, 120);
                    jPanel2.add(lblimagenes[i]);
                    
                }    
            } catch (SQLException ex) {
                System.out.println("Error. ->" + ex);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnInicio = new javax.swing.JLabel();
        btnInformatica = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnContaduria = new javax.swing.JLabel();
        btnDecoracion = new javax.swing.JLabel();
        btnCocina = new javax.swing.JLabel();
        btnExit = new javax.swing.JLabel();
        btnPanel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.setAutoscrolls(true);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo.PNG"))); // NOI18N

        btnInicio.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setText("Inicio");
        btnInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInicioMouseClicked(evt);
            }
        });

        btnInformatica.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnInformatica.setForeground(new java.awt.Color(255, 255, 255));
        btnInformatica.setText("Informática");
        btnInformatica.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnContaduria.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnContaduria.setForeground(new java.awt.Color(255, 255, 255));
        btnContaduria.setText("Contaduria");
        btnContaduria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnDecoracion.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnDecoracion.setForeground(new java.awt.Color(255, 255, 255));
        btnDecoracion.setText("Decoración");
        btnDecoracion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnCocina.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnCocina.setForeground(new java.awt.Color(255, 255, 255));
        btnCocina.setText("Cocina ");
        btnCocina.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnExit.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        btnExit.setForeground(new java.awt.Color(255, 255, 255));
        btnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar-sesion.png"))); // NOI18N
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        btnPanel.setFont(new java.awt.Font("Dialog", 1, 25)); // NOI18N
        btnPanel.setForeground(new java.awt.Color(255, 255, 255));
        btnPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iniciar-sesion.png"))); // NOI18N
        btnPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnInicio)
                        .addGap(32, 32, 32)
                        .addComponent(btnInformatica)))
                .addGap(32, 32, 32)
                .addComponent(btnContaduria)
                .addGap(32, 32, 32)
                .addComponent(btnDecoracion)
                .addGap(32, 32, 32)
                .addComponent(btnCocina)
                .addGap(31, 31, 31)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnInicio)
                        .addComponent(btnInformatica, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(btnContaduria)
                        .addComponent(btnDecoracion)
                        .addComponent(btnCocina)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setAutoscrolls(true);
        jPanel2.setPreferredSize(new java.awt.Dimension(688, 400));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel11.setText(" ENTRADAS REL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(715, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(112, 112, 112))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(525, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInicioMouseClicked
        Inicio ventana = new Inicio();
        ventana.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInicioMouseClicked

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        login1 lg1 = new login1();
        lg1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPanelMouseClicked
        String [] botones = {"Crear noticia", "Añadir administrador"};

        int opcion = JOptionPane.showOptionDialog(this, "¿Qué quiere hacer?","Administrador",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,botones,botones[0]);
        switch (opcion) {
            case 0: {
                Admin.nombre = nombre;
                Admin ventana = new Admin();
                ventana.setVisible(true);
                this.dispose();
                break;
            }

            case 1: {
                AdministrarAdmin ventana = new AdministrarAdmin();
                ventana.setVisible(true);
                this.dispose();
                break;
            }
        }

    }//GEN-LAST:event_btnPanelMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Noticia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Noticia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Noticia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Noticia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Noticia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnCocina;
    private javax.swing.JLabel btnContaduria;
    private javax.swing.JLabel btnDecoracion;
    private javax.swing.JLabel btnExit;
    private javax.swing.JLabel btnInformatica;
    private javax.swing.JLabel btnInicio;
    private javax.swing.JLabel btnPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
