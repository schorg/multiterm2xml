package GUI;

import java.awt.Cursor;
import java.io.File;

import javax.swing.filechooser.FileFilter;

import Controller.MTF;
import Controller.MultiTermDomProcessor;
import Controller.ExcelPrettyPrinter;


public class Start extends javax.swing.JFrame {

	private static final long serialVersionUID = 8494417028271681703L;

    public Start(java.awt.Frame parent, boolean modal) {
    	initComponents();      
    }

    private void initComponents() {
    	openFileChooser = new javax.swing.JFileChooser();
        createFileChooser = new javax.swing.JFileChooser();
        filePanel = new javax.swing.JPanel();
        open = new javax.swing.JButton();
        create = new javax.swing.JButton();
        openField = new javax.swing.JTextField();
        createField = new javax.swing.JTextField();
        sourceLabel = new javax.swing.JLabel();
        destinationLabel = new javax.swing.JLabel();
        start = new javax.swing.JButton();
        close = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();

        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuClose = new javax.swing.JMenuItem();

        openFileChooser.setCurrentDirectory(new java.io.File("."));
        openFileChooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
              return f.getName().toLowerCase().endsWith(".xml")
                  || f.isDirectory();
            }

            public String getDescription() {
              return "XML Dateien";
            }
          });        
        openFileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	openFileChooserActionPerformed(evt);
            }
        });
        createFileChooser.setCurrentDirectory(new java.io.File("."));
        createFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        createFileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	createFileChooserActionPerformed(evt);
            }
        });
        createFileChooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
              return f.getName().toLowerCase().endsWith(".xls")
                  || f.isDirectory();
            }

            public String getDescription() {
              return "Excel Dateien";
            }
          });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TermXML zu Excel Konvertierer");
        setResizable(true);
        setLocation(200, 200);
        filePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Dateien wählen"));
        open.setText("Öffnen");
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	openActionPerformed(evt);
            }
        });
        create.setText("Erstellen");
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	createActionPerformed(evt);
            }
        });
        sourceLabel.setText("Quelldatei wählen");
        destinationLabel.setText("Zieldatei wählen");

        javax.swing.GroupLayout filePanelLayout = new javax.swing.GroupLayout(filePanel);
        filePanel.setLayout(filePanelLayout);
        filePanelLayout.setHorizontalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sourceLabel)
                    .addGroup(filePanelLayout.createSequentialGroup()
                        .addGroup(filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openField, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(createField, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(open, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(create, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))
                    .addComponent(destinationLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        filePanelLayout.setVerticalGroup(
            filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filePanelLayout.createSequentialGroup()
                .addComponent(sourceLabel)
                .addGap(8, 8, 8)
                .addGroup(filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(open)
                    .addComponent(openField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(destinationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(create))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        start.setText("Start");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });
        close.setText("Schließen");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	closeActionPerformed(evt);
            }
        });
        //progressBar.setStringPainted(true);
        menuFile.setText("Datei");
        menuClose.setText("Schließen");
        menuClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	closeActionPerformed(evt);
            }
        });

        setJMenuBar(menuBar);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(filePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(start)
                        .addGap(12, 12, 12)
                        .addComponent(close))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        pack();
    }    
    
    
    private void closeActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        this.dispose();
    }  
    
    
    private void openActionPerformed(java.awt.event.ActionEvent evt) {
        this.openFileChooser.showOpenDialog(this);
    }                                        

    private void createActionPerformed(java.awt.event.ActionEvent evt) {
        this.createFileChooser.showSaveDialog(this);
    }  
    
    private void openFileChooserActionPerformed(java.awt.event.ActionEvent evt) {
    	File f = this.openFileChooser.getSelectedFile();
    	if (f != null)
    		this.openField.setText(f.getAbsolutePath());
    }                                             

    private void createFileChooserActionPerformed(java.awt.event.ActionEvent evt) {
    	File f = this.createFileChooser.getSelectedFile();
    	if (f != null) {
    		if(!f.getAbsolutePath().contains(".xls"))
    			this.createField.setText(f.getAbsolutePath() + ".xls");
    		else
    			this.createField.setText(f.getAbsolutePath());
    	}
    }  
    
    private void startActionPerformed(java.awt.event.ActionEvent evt) {
    	
    	if(this.openField.getText().equals("")){
    		error = new ErrorPopup("Keine XML Datei gew‰hlt");
    		error.setVisible(true);
    	}
    	else if(!this.openField.getText().contains(".xml")){
    		error = new ErrorPopup("Keine XML Datei gew‰hlt");
    		error.setVisible(true);
    	}
    	else if(!new File(this.openField.getText()).exists()){
    		error = new ErrorPopup("Die XML Datei existiert nicht");
    		error.setVisible(true);
    	}
    	else if(this.createField.getText().equals("")){
    		error = new ErrorPopup("Keine Excel Datei gewählt");
    		error.setVisible(true);

    	}
    	else if(!this.createField.getText().contains(".xls")){
    		error = new ErrorPopup("Keine Excel Datei gewählt");
    		error.setVisible(true);
    	}
    	else{
    		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
    		this.progressBar.setIndeterminate(true);
    		
    		try {
    			MultiTermDomProcessor mtd = new MultiTermDomProcessor(this.openField.getText());
    			MTF mtf = mtd.process();
    			// free enough heap space
    			mtd = null;
    			System.gc();
    			ExcelPrettyPrinter epp = new ExcelPrettyPrinter(this.createField.getText());
    			epp.mkSheet(mtf);
    			epp.close();
    		} catch (Exception e) {
    			error = new ErrorPopup(e.getMessage());
    			error.setVisible(true);
    		}
    		this.progressBar.setIndeterminate(false);
    		this.progressBar.setValue(100);
    		setCursor(null);
    	}
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Start dialog = new Start(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    private javax.swing.JButton close;
    private javax.swing.JButton create;
    private javax.swing.JTextField createField;
    private javax.swing.JLabel destinationLabel;
    private javax.swing.JPanel filePanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuClose;
    private javax.swing.JMenu menuFile;
    private javax.swing.JButton open;
    private javax.swing.JTextField openField;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel sourceLabel;
    private javax.swing.JButton start;
    private javax.swing.JFileChooser openFileChooser;
    private javax.swing.JFileChooser createFileChooser;
    private ErrorPopup error;
}
