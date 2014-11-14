/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package omni.visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import omni.controller.APPController;
import omni.controller.ClipboardController;
import omni.controller.OSController;
import omni.controller.URLController;
import omni.controller.User;
import omni.model.GestionAppModel;
import omni.model.GestionWebModel;
import omni.model.ReadFromJSON;
import omni.model.WriteToJSON;

/**
 *
 * @author Campus
 */
public class MainWindow extends javax.swing.JFrame {

    private static final String currentVersion = "0.30";
    private static final String runningOs = OSController.getOS();

    private static final ImageIcon icon = new ImageIcon(MainWindow.class.getResource("/res/icon.png"));
    private JFileChooser jf = new JFileChooser();

    private final GestionWebModel webModel;

    private static boolean isImageWebEnabled = false;
    private static int lastWebRowSelected;
    private static boolean isModifyingWeb = false;
    private static boolean isAddingWeb = false;

    private final GestionAppModel appModel;

    private static boolean isImageAppEnabled = false;
    private static int lastAppRowSelected;
    private static boolean isModifyingApp = false;
    private static boolean isAddingApp = false;

    private static final Color BG_GENERAL_BLUE = new Color(202, 238, 255);
    private static final Color BG_RESOURCE_LIGHT_BLUE = new Color(235, 248, 255);

    private User actualUser;
    private String nombre;
    private String pass;

    private WriteToJSON wtjson = new WriteToJSON();

    /**
     * Creates new form MainWindow
     *
     * @param nombre
     * @param pass
     */
    public MainWindow(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;

        initComponents();

        ReadFromJSON rfjson = new ReadFromJSON();
        actualUser = rfjson.readUser(nombre, pass);

        this.setIconImage(icon.getImage());

        this.setLocationRelativeTo(null);
        this.setTitle("Omni | Panel de " + nombre);

        /*WEB*/
        this.versionLabelWeb.setText("Versión " + currentVersion);

        this.labelRunningOsWeb.setToolTipText("Corriendo en " + runningOs);

        if (runningOs.equals("Windows")) {
            this.labelRunningOsWeb.setIcon(new ImageIcon(MainWindow.class.getResource("/res/windows.png")));
        } else if (runningOs.equals("Linux")) {
            this.labelRunningOsWeb.setIcon(new ImageIcon(MainWindow.class.getResource("/res/linux.png")));
        } else if (runningOs.equals("OSx")) {
            this.labelRunningOsWeb.setIcon(new ImageIcon(MainWindow.class.getResource("/res/osx.png")));
        } else {
            this.labelRunningOsWeb.setText("Unknown");
        }

        this.tablaWeb.setModel(new GestionWebModel());

        this.webModel = ((GestionWebModel) MainWindow.this.tablaWeb.getModel());
        this.tablaWeb.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.panelPrincipal.setBackground(BG_GENERAL_BLUE);
        this.panelWeb.setBackground(BG_GENERAL_BLUE);
        this.panelApp.setBackground(BG_GENERAL_BLUE);
        this.panelBotonesWeb.setBackground(BG_GENERAL_BLUE);
        this.panelEditorWeb.setBackground(BG_GENERAL_BLUE);

        this.panelImagenWeb.setBackground(BG_RESOURCE_LIGHT_BLUE);

        this.textFieldNombreWeb.setEditable(false);
        this.textFieldURLWeb.setEditable(false);
        this.btnOkWeb.setEnabled(false);
        this.btnCancelWeb.setEnabled(false);
        this.btnClipboardWeb.setEnabled(false);
        this.panelImagenWeb.setEnabled(false);

        this.scrollPaneTablaWeb.getViewport().setBackground(BG_GENERAL_BLUE);

        for (int i = 0; i < this.tablaWeb.getColumnCount(); i++) {
            TableColumn cell = this.tablaWeb.getColumnModel().getColumn(i);
            cell.setCellRenderer(new ColorRenderer());
        }

        TableRowSorter<GestionWebModel> webSorter
                = new TableRowSorter<>((GestionWebModel) this.tablaWeb.getModel());

        this.tablaWeb.setRowSorter(webSorter);

        this.btnIniciarWeb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaWeb.getSelectedRow() != -1) {

                    URLController.openUrl((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 1));
                    actualUser.setAccesosCount((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 0),
                            (String) webModel.getValueAt(tablaWeb.getSelectedRow(), 1));
                    wtjson.updateElement(actualUser, false);
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar algo.",
                            "Error al iniciar", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        this.tablaWeb.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    URLController.openUrl((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 1));
                    actualUser.setAccesosCount((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 0),
                            (String) webModel.getValueAt(tablaWeb.getSelectedRow(), 1));
                    wtjson.updateElement(actualUser, false);
                }

                btnClipboardWeb.setEnabled(true);

                textFieldNombreWeb.setText((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 0));
                textFieldURLWeb.setText((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 1));

                File rutaAbs = new File((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 2));

                rutaAbs = rutaAbs.getAbsoluteFile();

                try {
                    labelShowImagenWeb.setIcon(new ImageIcon(rutaAbs.toURI().toURL()));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void mousePressed(MouseEvent me) {

            }

            @Override
            public void mouseReleased(MouseEvent me) {

            }

            @Override
            public void mouseEntered(MouseEvent me) {

            }

            @Override
            public void mouseExited(MouseEvent me) {

            }

        });

        this.btnAnyadirWeb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                isAddingWeb = true;

                textFieldNombreWeb.setText("");
                textFieldURLWeb.setText("");
                labelShowImagenWeb.setIcon(
                        new ImageIcon(MainWindow.class.getResource("/res/icon.png")));

                textFieldNombreWeb.setEditable(true);
                textFieldURLWeb.setEditable(true);
                panelImagenWeb.setEnabled(true);
                btnOkWeb.setEnabled(true);
                btnCancelWeb.setEnabled(true);
                isImageWebEnabled = true;

                btnAnyadirWeb.setEnabled(false);
                btnIniciarWeb.setEnabled(false);
                btnModificarWeb.setEnabled(false);
                btnEliminarWeb.setEnabled(false);
                btnClipboardWeb.setEnabled(false);
                tablaWeb.setEnabled(false);
                tabbedPanePrincipal.setEnabled(false);

            }

        });

        this.btnEliminarWeb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tablaWeb.getSelectedRow() != -1) {

                    if (JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar "
                            + webModel.getValueAt(tablaWeb.getSelectedRow(), 0) + "?",
                            "Eliminar", JOptionPane.YES_NO_OPTION) == 0) {

                        actualUser.removeAcceso("web", webModel.getValueAt(tablaWeb.getSelectedRow(), 0).toString(),
                                webModel.getValueAt(tablaWeb.getSelectedRow(), 1).toString(),
                                webModel.getValueAt(tablaWeb.getSelectedRow(), 2).toString());

                        webModel.removeRow(tablaWeb.getSelectedRow());

                        wtjson.updateElement(actualUser, false);

                        textFieldNombreWeb.setText("");
                        textFieldURLWeb.setText("");
                        labelShowImagenWeb.setIcon(null);
                        tablaWeb.clearSelection();

                        textFieldNombreWeb.setEditable(false);
                        textFieldURLWeb.setEditable(false);
                        panelImagenWeb.setEnabled(false);
                        btnOkWeb.setEnabled(false);
                        btnCancelWeb.setEnabled(false);
                        btnClipboardWeb.setEnabled(false);
                        isImageWebEnabled = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar algo.",
                            "Error al eliminar", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        this.btnCancelWeb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                textFieldNombreWeb.setText("");
                textFieldURLWeb.setText("");
                labelShowImagenWeb.setIcon(null);
                tablaWeb.clearSelection();

                textFieldNombreWeb.setEditable(false);
                textFieldURLWeb.setEditable(false);
                panelImagenWeb.setEnabled(false);
                btnOkWeb.setEnabled(false);
                btnCancelWeb.setEnabled(false);
                btnClipboardWeb.setEnabled(false);
                isImageWebEnabled = false;

                btnAnyadirWeb.setEnabled(true);
                btnIniciarWeb.setEnabled(true);
                btnModificarWeb.setEnabled(true);
                btnEliminarWeb.setEnabled(true);
                tablaWeb.setEnabled(true);
                tabbedPanePrincipal.setEnabled(true);

                isModifyingWeb = false;
            }

        });

        this.panelImagenWeb.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (isImageWebEnabled) {
                    if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                        File rutaAbs = jf.getSelectedFile().getAbsoluteFile();

                        try {

                            labelShowImagenWeb.setIcon(new ImageIcon(rutaAbs.toURI().toURL()));

                        } catch (MalformedURLException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (isImageWebEnabled) {
                    MainWindow.this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isImageWebEnabled) {
                    MainWindow.this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }

        });

        this.btnModificarWeb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tablaWeb.getSelectedRow() != -1) {

                    panelImagenWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                    textFieldNombreWeb.setEditable(true);
                    textFieldURLWeb.setEditable(true);
                    panelImagenWeb.setEnabled(true);
                    btnOkWeb.setEnabled(true);
                    btnCancelWeb.setEnabled(true);
                    isImageWebEnabled = true;

                    btnAnyadirWeb.setEnabled(false);
                    btnIniciarWeb.setEnabled(false);
                    btnModificarWeb.setEnabled(false);
                    btnEliminarWeb.setEnabled(false);
                    lastWebRowSelected = tablaWeb.getSelectedRow();
                    tablaWeb.setEnabled(false);
                    tabbedPanePrincipal.setEnabled(false);

                    textFieldNombreWeb.setText((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 0));
                    textFieldURLWeb.setText((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 1));

                    File rutaAbs = new File((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 2));

                    rutaAbs = rutaAbs.getAbsoluteFile();

                    try {
                        labelShowImagenWeb.setIcon(new ImageIcon(rutaAbs.toURI().toURL()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    isModifyingWeb = true;

                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar algo.",
                            "Error al modificar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.btnOkWeb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                String nombre, url, imagePath;

                if (!textFieldURLWeb.getText().equals("")
                        && !textFieldNombreWeb.getText().equals("")
                        && labelShowImagenWeb.getIcon() != null) {

                    textFieldNombreWeb.setEditable(false);
                    textFieldURLWeb.setEditable(false);
                    panelImagenWeb.setEnabled(false);
                    btnOkWeb.setEnabled(false);
                    btnCancelWeb.setEnabled(false);
                    isImageWebEnabled = false;

                    if (!textFieldURLWeb.getText().contains("://")) {
                        textFieldURLWeb.setText("http://" + textFieldURLWeb.getText());
                    }

                    if (isModifyingWeb) {

                        actualUser.removeAcceso("web", webModel.getValueAt(lastWebRowSelected, 0).toString(),
                                webModel.getValueAt(lastWebRowSelected, 1).toString(),
                                webModel.getValueAt(lastWebRowSelected, 2).toString());

                        wtjson.updateElement(actualUser, false);

                        webModel.removeRow(lastWebRowSelected);
                    }

                    if (OSController.isUnix() || OSController.isMac()) {
                        nombre = textFieldNombreWeb.getText();
                        url = textFieldURLWeb.getText();
                        imagePath = labelShowImagenWeb.getIcon().toString()
                                .substring(5, labelShowImagenWeb.getIcon().toString().length());

                        webModel.addRow(nombre, url, imagePath);
                        actualUser.setAcceso("web", nombre, url, imagePath);
                        wtjson.updateElement(actualUser, false);
                        
                    } else if (OSController.isWindows()) {
                        nombre = textFieldNombreWeb.getText();
                        url = textFieldURLWeb.getText();
                        imagePath = labelShowImagenWeb.getIcon().toString()
                                .substring(6, labelShowImagenWeb.getIcon().toString().length());

                        webModel.addRow(nombre, url, imagePath);
                        actualUser.setAcceso("web", nombre, url, imagePath);
                        wtjson.updateElement(actualUser, false);

                    } else {
                        nombre = textFieldNombreWeb.getText();
                        url = textFieldURLWeb.getText();
                        imagePath = labelShowImagenWeb.getIcon().toString();

                        webModel.addRow(nombre, url, imagePath);
                        actualUser.setAcceso("web", nombre, url, imagePath);
                        wtjson.updateElement(actualUser, false);

                    }

                    btnAnyadirWeb.setEnabled(true);
                    btnIniciarWeb.setEnabled(true);
                    btnModificarWeb.setEnabled(true);
                    btnEliminarWeb.setEnabled(true);
                    tablaWeb.setEnabled(true);
                    tabbedPanePrincipal.setEnabled(true);

                    textFieldNombreWeb.setText("");
                    textFieldURLWeb.setText("");
                    labelShowImagenWeb.setIcon(null);
                    tablaWeb.clearSelection();

                    isModifyingWeb = false;
                    isAddingWeb = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos.",
                            "Imposible añadir web", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.btnClipboardWeb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (tablaWeb.getSelectedRow() != -1) {
                    new ClipboardController()
                            .copyToClipboard((String) webModel.getValueAt(tablaWeb.getSelectedRow(), 2));
                }
            }

        });

        /*APP*/
        this.versionLabelApp.setText("Versión " + currentVersion);

        this.tablaApp.setModel(new GestionAppModel());

        this.labelRunningOsApp.setToolTipText("Corriendo en " + runningOs);

        if (runningOs.equals("Windows")) {
            this.labelRunningOsApp.setIcon(new ImageIcon(MainWindow.class.getResource("/res/windows.png")));
        } else if (runningOs.equals("Linux")) {
            this.labelRunningOsApp.setIcon(new ImageIcon(MainWindow.class.getResource("/res/linux.png")));
        } else if (runningOs.equals("OSx")) {
            this.labelRunningOsApp.setIcon(new ImageIcon(MainWindow.class.getResource("/res/osx.png")));
        } else {
            this.labelRunningOsApp.setText("Unknown");
        }

        this.appModel = ((GestionAppModel) MainWindow.this.tablaApp.getModel());
        this.tablaApp.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.panelApp.setBackground(BG_GENERAL_BLUE);
        this.panelBotonesApp.setBackground(BG_GENERAL_BLUE);
        this.panelEditorApp.setBackground(BG_GENERAL_BLUE);

        this.panelImagenApp.setBackground(BG_RESOURCE_LIGHT_BLUE);

        this.textFieldNombreApp.setEditable(false);
        this.textFieldRutaApp.setEditable(false);
        this.btnRutaApp.setEnabled(false);
        this.btnOkApp.setEnabled(false);
        this.btnCancelApp.setEnabled(false);
        this.btnClipboardApp.setEnabled(false);
        this.panelImagenApp.setEnabled(false);

        this.scrollPaneTablaApp.getViewport().setBackground(BG_GENERAL_BLUE);

        setUserConfig();

        for (int i = 0; i < this.tablaApp.getColumnCount(); i++) {
            TableColumn cell = this.tablaApp.getColumnModel().getColumn(i);
            cell.setCellRenderer(new ColorRenderer());
        }

        TableRowSorter<GestionAppModel> appSorter
                = new TableRowSorter<>((GestionAppModel) this.tablaApp.getModel());

        this.tablaApp.setRowSorter(appSorter);

        this.btnRutaApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                    textFieldRutaApp.setText(jf.getSelectedFile().getAbsoluteFile().getAbsolutePath());

                }
            }

        });

        this.btnIniciarApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaApp.getSelectedRow() != -1) {

                    APPController.openApp((String) appModel.getValueAt(tablaApp.getSelectedRow(), 1));
                    actualUser.setAccesosCount((String) appModel.getValueAt(tablaApp.getSelectedRow(), 0),
                            (String) appModel.getValueAt(tablaApp.getSelectedRow(), 1));
                    wtjson.updateElement(actualUser, false);

                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar algo.",
                            "Error al iniciar", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        this.tablaApp.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {

                if (me.getClickCount() == 2) {
                    APPController.openApp((String) appModel.getValueAt(tablaApp.getSelectedRow(), 1));
                    actualUser.setAccesosCount((String) appModel.getValueAt(tablaApp.getSelectedRow(), 0),
                            (String) appModel.getValueAt(tablaApp.getSelectedRow(), 1));
                    wtjson.updateElement(actualUser, false);
                }

                btnClipboardApp.setEnabled(true);

                textFieldNombreApp.setText((String) appModel.getValueAt(tablaApp.getSelectedRow(), 0));
                textFieldRutaApp.setText((String) appModel.getValueAt(tablaApp.getSelectedRow(), 1));

                File rutaAbs = new File((String) appModel.getValueAt(tablaApp.getSelectedRow(), 2));

                rutaAbs = rutaAbs.getAbsoluteFile();

                try {
                    labelShowImagenApp.setIcon(new ImageIcon(rutaAbs.toURI().toURL()));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void mousePressed(MouseEvent me) {

            }

            @Override
            public void mouseReleased(MouseEvent me) {

            }

            @Override
            public void mouseEntered(MouseEvent me) {

            }

            @Override
            public void mouseExited(MouseEvent me) {

            }

        });

        this.btnAnyadirApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                isAddingApp = true;

                textFieldNombreApp.setText("");
                textFieldRutaApp.setText("");
                labelShowImagenApp.setIcon(
                        new ImageIcon(MainWindow.class.getResource("/res/icon.png")));

                textFieldNombreApp.setEditable(true);
                textFieldRutaApp.setEditable(true);
                panelImagenApp.setEnabled(true);
                btnRutaApp.setEnabled(true);
                btnOkApp.setEnabled(true);
                btnCancelApp.setEnabled(true);
                isImageAppEnabled = true;

                btnAnyadirApp.setEnabled(false);
                btnIniciarApp.setEnabled(false);
                btnModificarApp.setEnabled(false);
                btnEliminarApp.setEnabled(false);
                btnClipboardApp.setEnabled(false);
                tablaApp.setEnabled(false);
                tabbedPanePrincipal.setEnabled(false);

            }

        });

        this.btnEliminarApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tablaApp.getSelectedRow() != -1) {

                    if (JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar "
                            + appModel.getValueAt(tablaApp.getSelectedRow(), 0) + "?",
                            "Eliminar", JOptionPane.YES_NO_OPTION) == 0) {

                        actualUser.removeAcceso("app", appModel.getValueAt(tablaApp.getSelectedRow(), 0).toString(),
                                appModel.getValueAt(tablaApp.getSelectedRow(), 1).toString(),
                                appModel.getValueAt(tablaApp.getSelectedRow(), 2).toString());

                        wtjson.updateElement(actualUser, false);

                        appModel.removeRow(tablaApp.getSelectedRow());

                        textFieldNombreApp.setText("");
                        textFieldRutaApp.setText("");
                        labelShowImagenApp.setIcon(null);
                        tablaApp.clearSelection();

                        textFieldNombreApp.setEditable(false);
                        textFieldRutaApp.setEditable(false);
                        panelImagenApp.setEnabled(false);
                        btnRutaApp.setEnabled(false);
                        btnOkApp.setEnabled(false);
                        btnCancelApp.setEnabled(false);
                        btnClipboardApp.setEnabled(false);
                        isImageAppEnabled = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar algo.",
                            "Error al eliminar", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        this.btnCancelApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                textFieldNombreApp.setText("");
                textFieldRutaApp.setText("");
                labelShowImagenApp.setIcon(null);
                tablaApp.clearSelection();

                textFieldNombreApp.setEditable(false);
                textFieldRutaApp.setEditable(false);
                panelImagenApp.setEnabled(false);
                btnRutaApp.setEnabled(false);
                btnOkApp.setEnabled(false);
                btnCancelApp.setEnabled(false);
                btnClipboardApp.setEnabled(false);
                isImageAppEnabled = false;

                btnAnyadirApp.setEnabled(true);
                btnIniciarApp.setEnabled(true);
                btnModificarApp.setEnabled(true);
                btnEliminarApp.setEnabled(true);
                tablaApp.setEnabled(true);
                tabbedPanePrincipal.setEnabled(true);

                isModifyingApp = false;
            }

        });

        this.panelImagenApp.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (isImageAppEnabled) {
                    if (jf.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                        File rutaAbs = jf.getSelectedFile().getAbsoluteFile();

                        try {

                            labelShowImagenApp.setIcon(new ImageIcon(rutaAbs.toURI().toURL()));

                        } catch (MalformedURLException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (isImageAppEnabled) {
                    MainWindow.this.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isImageAppEnabled) {
                    MainWindow.this.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            }

        });

        this.btnModificarApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                if (tablaApp.getSelectedRow() != -1) {

                    panelImagenApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                    textFieldNombreApp.setEditable(true);
                    textFieldRutaApp.setEditable(true);
                    panelImagenApp.setEnabled(true);
                    btnRutaApp.setEnabled(true);
                    btnOkApp.setEnabled(true);
                    btnCancelApp.setEnabled(true);
                    isImageAppEnabled = true;

                    btnAnyadirApp.setEnabled(false);
                    btnIniciarApp.setEnabled(false);
                    btnModificarApp.setEnabled(false);
                    btnEliminarApp.setEnabled(false);
                    lastAppRowSelected = tablaApp.getSelectedRow();
                    tablaApp.setEnabled(false);
                    tabbedPanePrincipal.setEnabled(false);

                    textFieldNombreApp.setText((String) appModel.getValueAt(tablaApp.getSelectedRow(), 0));
                    textFieldRutaApp.setText((String) appModel.getValueAt(tablaApp.getSelectedRow(), 1));

                    File rutaAbs = new File((String) appModel.getValueAt(tablaApp.getSelectedRow(), 2));

                    rutaAbs = rutaAbs.getAbsoluteFile();

                    try {
                        labelShowImagenApp.setIcon(new ImageIcon(rutaAbs.toURI().toURL()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    isModifyingApp = true;

                } else {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar algo.",
                            "Error al modificar", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.btnOkApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                String nombre, action, imagePath;

                if (!textFieldRutaApp.getText().equals("")
                        && !textFieldNombreApp.getText().equals("")
                        && labelShowImagenApp.getIcon() != null) {

                    textFieldNombreApp.setEditable(false);
                    textFieldRutaApp.setEditable(false);
                    panelImagenApp.setEnabled(false);
                    btnRutaApp.setEnabled(false);
                    btnOkApp.setEnabled(false);
                    btnCancelApp.setEnabled(false);
                    isImageAppEnabled = false;

                    if (isModifyingApp) {

                        actualUser.removeAcceso("app", appModel.getValueAt(lastAppRowSelected, 0).toString(),
                                appModel.getValueAt(lastAppRowSelected, 1).toString(),
                                appModel.getValueAt(lastAppRowSelected, 2).toString());

                        wtjson.updateElement(actualUser, false);

                        appModel.removeRow(lastAppRowSelected);
                    }

                    if (OSController.isUnix() || OSController.isMac()) {
                        nombre = textFieldNombreApp.getText();
                        action = textFieldRutaApp.getText();
                        imagePath = labelShowImagenApp.getIcon().toString()
                                .substring(5, labelShowImagenApp.getIcon().toString().length());

                        appModel.addRow(nombre, action, imagePath);
                        actualUser.setAcceso("app", nombre, action, imagePath);
                        wtjson.updateElement(actualUser, false);

                    } else if (OSController.isWindows()) {
                        nombre = textFieldNombreApp.getText();
                        action = textFieldRutaApp.getText();
                        imagePath = labelShowImagenApp.getIcon().toString()
                                .substring(6, labelShowImagenApp.getIcon().toString().length());
                        
                        appModel.addRow(nombre, action, imagePath);
                        actualUser.setAcceso("app", nombre, action, imagePath);
                        wtjson.updateElement(actualUser, false);

                    } else {
                        nombre = textFieldNombreApp.getText();
                        action = textFieldRutaApp.getText();
                        imagePath = labelShowImagenApp.getIcon().toString();

                        appModel.addRow(nombre, action, imagePath);
                        actualUser.setAcceso("web", nombre, action, imagePath);
                        wtjson.updateElement(actualUser, false);

                    }

                    btnAnyadirApp.setEnabled(true);
                    btnIniciarApp.setEnabled(true);
                    btnModificarApp.setEnabled(true);
                    btnEliminarApp.setEnabled(true);
                    tablaApp.setEnabled(true);
                    tabbedPanePrincipal.setEnabled(true);

                    textFieldNombreApp.setText("");
                    textFieldRutaApp.setText("");
                    labelShowImagenApp.setIcon(null);
                    tablaApp.clearSelection();

                    isModifyingApp = false;
                    isAddingApp = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos.",
                            "Imposible añadir web", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.btnClipboardApp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (tablaApp.getSelectedRow() != -1) {
                    new ClipboardController()
                            .copyToClipboard((String) appModel.getValueAt(tablaApp.getSelectedRow(), 2));
                }
            }

        });

    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUserConfig() {

        for (int i = 0; i < actualUser.getAcceso().size(); i++) {

            String nombre_acc = actualUser.getAcceso().get(i)[1];
            String action = actualUser.getAcceso().get(i)[2];
            String imagePath = actualUser.getAcceso().get(i)[3];

            if (actualUser.getAcceso().get(i)[0].equals("web")) {
                webModel.addRow(nombre_acc, action, imagePath);
            } else if (actualUser.getAcceso().get(i)[0].equals("app")) {
                appModel.addRow(nombre_acc, action, imagePath);
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

        panelPrincipal = new javax.swing.JPanel();
        tabbedPanePrincipal = new javax.swing.JTabbedPane();
        panelWeb = new javax.swing.JPanel();
        panelBotonesWeb = new javax.swing.JPanel();
        btnAnyadirWeb = new javax.swing.JButton();
        btnModificarWeb = new javax.swing.JButton();
        btnEliminarWeb = new javax.swing.JButton();
        btnIniciarWeb = new javax.swing.JButton();
        versionLabelWeb = new javax.swing.JLabel();
        labelRunningOsWeb = new javax.swing.JLabel();
        panelEditorWeb = new javax.swing.JPanel();
        textFieldNombreWeb = new javax.swing.JTextField();
        textFieldURLWeb = new javax.swing.JTextField();
        labelNombreWeb = new javax.swing.JLabel();
        labelURLWeb = new javax.swing.JLabel();
        labelImagenWeb = new javax.swing.JLabel();
        panelImagenWeb = new javax.swing.JPanel();
        labelShowImagenWeb = new javax.swing.JLabel();
        btnOkWeb = new javax.swing.JButton();
        btnCancelWeb = new javax.swing.JButton();
        btnClipboardWeb = new javax.swing.JButton();
        scrollPaneTablaWeb = new javax.swing.JScrollPane();
        tablaWeb = new javax.swing.JTable();
        panelApp = new javax.swing.JPanel();
        panelBotonesApp = new javax.swing.JPanel();
        btnAnyadirApp = new javax.swing.JButton();
        btnModificarApp = new javax.swing.JButton();
        btnEliminarApp = new javax.swing.JButton();
        btnIniciarApp = new javax.swing.JButton();
        versionLabelApp = new javax.swing.JLabel();
        labelRunningOsApp = new javax.swing.JLabel();
        panelEditorApp = new javax.swing.JPanel();
        textFieldNombreApp = new javax.swing.JTextField();
        textFieldRutaApp = new javax.swing.JTextField();
        labelNombreApp = new javax.swing.JLabel();
        labelRutaApp = new javax.swing.JLabel();
        labelImagenApp = new javax.swing.JLabel();
        panelImagenApp = new javax.swing.JPanel();
        labelShowImagenApp = new javax.swing.JLabel();
        btnOkApp = new javax.swing.JButton();
        btnCancelApp = new javax.swing.JButton();
        btnClipboardApp = new javax.swing.JButton();
        btnRutaApp = new javax.swing.JButton();
        scrollPaneTablaApp = new javax.swing.JScrollPane();
        tablaApp = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemSalir = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItemCerrarSesion = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(714, 612));
        setMinimumSize(new java.awt.Dimension(714, 612));
        setPreferredSize(new java.awt.Dimension(714, 612));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        tabbedPanePrincipal.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPanePrincipal.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        panelBotonesWeb.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnAnyadirWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/add_file-26.png"))); // NOI18N
        btnAnyadirWeb.setText("Añadir");
        btnAnyadirWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnModificarWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/edit_file-26.png"))); // NOI18N
        btnModificarWeb.setText("Modificar");
        btnModificarWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnEliminarWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/delete-26.png"))); // NOI18N
        btnEliminarWeb.setText("Eliminar");
        btnEliminarWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnIniciarWeb.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnIniciarWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/external_link-64.png"))); // NOI18N
        btnIniciarWeb.setText("¡ INICIAR !");
        btnIniciarWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        versionLabelWeb.setFont(new java.awt.Font("DejaVu Sans", 2, 10)); // NOI18N

        labelRunningOsWeb.setMaximumSize(new java.awt.Dimension(64, 64));
        labelRunningOsWeb.setMinimumSize(new java.awt.Dimension(64, 64));
        labelRunningOsWeb.setPreferredSize(new java.awt.Dimension(64, 64));

        javax.swing.GroupLayout panelBotonesWebLayout = new javax.swing.GroupLayout(panelBotonesWeb);
        panelBotonesWeb.setLayout(panelBotonesWebLayout);
        panelBotonesWebLayout.setHorizontalGroup(
            panelBotonesWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesWebLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotonesWebLayout.createSequentialGroup()
                        .addComponent(btnAnyadirWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 93, Short.MAX_VALUE))
                    .addComponent(versionLabelWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotonesWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIniciarWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panelBotonesWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotonesWebLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(btnEliminarWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesWebLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelRunningOsWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelBotonesWebLayout.setVerticalGroup(
            panelBotonesWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesWebLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnyadirWeb)
                    .addComponent(btnModificarWeb)
                    .addComponent(btnEliminarWeb))
                .addGap(18, 18, 18)
                .addGroup(panelBotonesWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnIniciarWeb)
                    .addComponent(versionLabelWeb)
                    .addComponent(labelRunningOsWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEditorWeb.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textFieldNombreWeb.setMaximumSize(new java.awt.Dimension(212, 27));
        textFieldNombreWeb.setMinimumSize(new java.awt.Dimension(212, 27));
        textFieldNombreWeb.setPreferredSize(new java.awt.Dimension(212, 27));

        textFieldURLWeb.setMaximumSize(new java.awt.Dimension(212, 27));
        textFieldURLWeb.setMinimumSize(new java.awt.Dimension(212, 27));
        textFieldURLWeb.setPreferredSize(new java.awt.Dimension(212, 27));

        labelNombreWeb.setText("Nombre");

        labelURLWeb.setText("URL");

        labelImagenWeb.setText("Imagen");

        panelImagenWeb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelImagenWeb.setToolTipText("Se recomienda que la imagen sea de 128x128 px");
        panelImagenWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelImagenWeb.setMaximumSize(new java.awt.Dimension(128, 128));
        panelImagenWeb.setMinimumSize(new java.awt.Dimension(128, 128));
        panelImagenWeb.setPreferredSize(new java.awt.Dimension(128, 128));

        labelShowImagenWeb.setMaximumSize(new java.awt.Dimension(128, 128));
        labelShowImagenWeb.setMinimumSize(new java.awt.Dimension(128, 128));
        labelShowImagenWeb.setPreferredSize(new java.awt.Dimension(128, 128));

        javax.swing.GroupLayout panelImagenWebLayout = new javax.swing.GroupLayout(panelImagenWeb);
        panelImagenWeb.setLayout(panelImagenWebLayout);
        panelImagenWebLayout.setHorizontalGroup(
            panelImagenWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
            .addGroup(panelImagenWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImagenWebLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelShowImagenWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelImagenWebLayout.setVerticalGroup(
            panelImagenWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
            .addGroup(panelImagenWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImagenWebLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelShowImagenWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnOkWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/checkmark-26.png"))); // NOI18N
        btnOkWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOkWeb.setIconTextGap(0);
        btnOkWeb.setMaximumSize(new java.awt.Dimension(38, 36));
        btnOkWeb.setMinimumSize(new java.awt.Dimension(38, 36));
        btnOkWeb.setPreferredSize(new java.awt.Dimension(38, 36));

        btnCancelWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/cancel-2-24.png"))); // NOI18N
        btnCancelWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelWeb.setIconTextGap(0);

        btnClipboardWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/clipboard-26.png"))); // NOI18N
        btnClipboardWeb.setToolTipText("Copiar ruta al portapapeles");

        javax.swing.GroupLayout panelEditorWebLayout = new javax.swing.GroupLayout(panelEditorWeb);
        panelEditorWeb.setLayout(panelEditorWebLayout);
        panelEditorWebLayout.setHorizontalGroup(
            panelEditorWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorWebLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorWebLayout.createSequentialGroup()
                        .addGroup(panelEditorWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelNombreWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelURLWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelImagenWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelEditorWebLayout.createSequentialGroup()
                                .addComponent(btnOkWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btnCancelWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(textFieldURLWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditorWebLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelEditorWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldNombreWeb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditorWebLayout.createSequentialGroup()
                                .addComponent(panelImagenWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClipboardWeb)
                                .addGap(10, 10, 10)))))
                .addContainerGap())
        );
        panelEditorWebLayout.setVerticalGroup(
            panelEditorWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorWebLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNombreWeb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textFieldNombreWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelURLWeb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textFieldURLWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelImagenWeb)
                .addGap(12, 12, 12)
                .addGroup(panelEditorWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClipboardWeb)
                    .addComponent(panelImagenWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEditorWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOkWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelWeb))
                .addContainerGap())
        );

        scrollPaneTablaWeb.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablaWeb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPaneTablaWeb.setViewportView(tablaWeb);

        javax.swing.GroupLayout panelWebLayout = new javax.swing.GroupLayout(panelWeb);
        panelWeb.setLayout(panelWebLayout);
        panelWebLayout.setHorizontalGroup(
            panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelWebLayout.createSequentialGroup()
                .addGroup(panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelWebLayout.createSequentialGroup()
                        .addComponent(scrollPaneTablaWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelEditorWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelBotonesWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelWebLayout.setVerticalGroup(
            panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelWebLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelWebLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPaneTablaWeb, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelEditorWeb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotonesWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        tabbedPanePrincipal.addTab("Web", panelWeb);

        panelBotonesApp.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnAnyadirApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/add_file-26.png"))); // NOI18N
        btnAnyadirApp.setText("Añadir");
        btnAnyadirApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnModificarApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/edit_file-26.png"))); // NOI18N
        btnModificarApp.setText("Modificar");
        btnModificarApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnEliminarApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/delete-26.png"))); // NOI18N
        btnEliminarApp.setText("Eliminar");
        btnEliminarApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnIniciarApp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnIniciarApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/external_link-64.png"))); // NOI18N
        btnIniciarApp.setText("¡ INICIAR !");
        btnIniciarApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        versionLabelApp.setFont(new java.awt.Font("DejaVu Sans", 2, 10)); // NOI18N

        labelRunningOsApp.setMaximumSize(new java.awt.Dimension(64, 64));
        labelRunningOsApp.setMinimumSize(new java.awt.Dimension(64, 64));
        labelRunningOsApp.setPreferredSize(new java.awt.Dimension(64, 64));

        javax.swing.GroupLayout panelBotonesAppLayout = new javax.swing.GroupLayout(panelBotonesApp);
        panelBotonesApp.setLayout(panelBotonesAppLayout);
        panelBotonesAppLayout.setHorizontalGroup(
            panelBotonesAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesAppLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotonesAppLayout.createSequentialGroup()
                        .addComponent(btnAnyadirApp, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 93, Short.MAX_VALUE))
                    .addComponent(versionLabelApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBotonesAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnIniciarApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificarApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panelBotonesAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBotonesAppLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(btnEliminarApp, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBotonesAppLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelRunningOsApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelBotonesAppLayout.setVerticalGroup(
            panelBotonesAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBotonesAppLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBotonesAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnyadirApp)
                    .addComponent(btnModificarApp)
                    .addComponent(btnEliminarApp))
                .addGap(18, 18, 18)
                .addGroup(panelBotonesAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnIniciarApp)
                    .addComponent(versionLabelApp)
                    .addComponent(labelRunningOsApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelEditorApp.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        textFieldNombreApp.setMaximumSize(new java.awt.Dimension(212, 27));
        textFieldNombreApp.setMinimumSize(new java.awt.Dimension(212, 27));
        textFieldNombreApp.setPreferredSize(new java.awt.Dimension(212, 27));

        textFieldRutaApp.setMaximumSize(new java.awt.Dimension(212, 27));
        textFieldRutaApp.setMinimumSize(new java.awt.Dimension(212, 27));
        textFieldRutaApp.setPreferredSize(new java.awt.Dimension(212, 27));

        labelNombreApp.setText("Nombre");

        labelRutaApp.setText("Comando o ruta aplicación.");

        labelImagenApp.setText("Imagen");

        panelImagenApp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelImagenApp.setToolTipText("Se recomienda que la imagen sea de 128x128 px");
        panelImagenApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelImagenApp.setMaximumSize(new java.awt.Dimension(128, 128));
        panelImagenApp.setMinimumSize(new java.awt.Dimension(128, 128));
        panelImagenApp.setPreferredSize(new java.awt.Dimension(128, 128));

        labelShowImagenApp.setMaximumSize(new java.awt.Dimension(128, 128));
        labelShowImagenApp.setMinimumSize(new java.awt.Dimension(128, 128));
        labelShowImagenApp.setPreferredSize(new java.awt.Dimension(128, 128));

        javax.swing.GroupLayout panelImagenAppLayout = new javax.swing.GroupLayout(panelImagenApp);
        panelImagenApp.setLayout(panelImagenAppLayout);
        panelImagenAppLayout.setHorizontalGroup(
            panelImagenAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
            .addGroup(panelImagenAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImagenAppLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelShowImagenApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelImagenAppLayout.setVerticalGroup(
            panelImagenAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 128, Short.MAX_VALUE)
            .addGroup(panelImagenAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelImagenAppLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(labelShowImagenApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        btnOkApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/checkmark-26.png"))); // NOI18N
        btnOkApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOkApp.setIconTextGap(0);
        btnOkApp.setMaximumSize(new java.awt.Dimension(38, 36));
        btnOkApp.setMinimumSize(new java.awt.Dimension(38, 36));
        btnOkApp.setPreferredSize(new java.awt.Dimension(38, 36));

        btnCancelApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/cancel-2-24.png"))); // NOI18N
        btnCancelApp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelApp.setIconTextGap(0);

        btnClipboardApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/clipboard-26.png"))); // NOI18N
        btnClipboardApp.setToolTipText("Copiar ruta al portapapeles");

        btnRutaApp.setText("...");

        javax.swing.GroupLayout panelEditorAppLayout = new javax.swing.GroupLayout(panelEditorApp);
        panelEditorApp.setLayout(panelEditorAppLayout);
        panelEditorAppLayout.setHorizontalGroup(
            panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorAppLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEditorAppLayout.createSequentialGroup()
                        .addGroup(panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelEditorAppLayout.createSequentialGroup()
                                .addComponent(textFieldRutaApp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRutaApp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelNombreApp, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelImagenApp, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelEditorAppLayout.createSequentialGroup()
                                .addComponent(btnOkApp, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btnCancelApp, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(labelRutaApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditorAppLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldNombreApp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditorAppLayout.createSequentialGroup()
                                .addComponent(panelImagenApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClipboardApp)
                                .addGap(10, 10, 10)))))
                .addContainerGap())
        );
        panelEditorAppLayout.setVerticalGroup(
            panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEditorAppLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelNombreApp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textFieldNombreApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelRutaApp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFieldRutaApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRutaApp))
                .addGap(18, 18, 18)
                .addComponent(labelImagenApp)
                .addGap(12, 12, 12)
                .addGroup(panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClipboardApp)
                    .addComponent(panelImagenApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelEditorAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOkApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelApp))
                .addContainerGap())
        );

        scrollPaneTablaApp.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tablaApp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollPaneTablaApp.setViewportView(tablaApp);

        javax.swing.GroupLayout panelAppLayout = new javax.swing.GroupLayout(panelApp);
        panelApp.setLayout(panelAppLayout);
        panelAppLayout.setHorizontalGroup(
            panelAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAppLayout.createSequentialGroup()
                .addGroup(panelAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAppLayout.createSequentialGroup()
                        .addComponent(scrollPaneTablaApp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelEditorApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelBotonesApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelAppLayout.setVerticalGroup(
            panelAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAppLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(scrollPaneTablaApp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(panelEditorApp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelBotonesApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        tabbedPanePrincipal.addTab("App", panelApp);

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPanePrincipal)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPanePrincipal, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jMenu1.setText("Inicio");

        menuItemSalir.setText("Salir");
        menuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSalirActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemSalir);
        jMenu1.add(jSeparator1);

        menuItemCerrarSesion.setText("Cerrar sesión");
        menuItemCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCerrarSesionActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemCerrarSesion);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Opciones");

        jMenuItem2.setText("Ver perfil");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ayuda");

        jMenuItem3.setText("Guia rápida");
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Acerca de...");
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItemSalirActionPerformed

    private void menuItemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCerrarSesionActionPerformed
        JFrame login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_menuItemCerrarSesionActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (isModifyingWeb || isModifyingApp
                || isAddingWeb || isAddingApp) {
            if (JOptionPane.showConfirmDialog(null, "¿Seguro que deseas salir ahora?",
                    "Salir", JOptionPane.YES_NO_OPTION) == 0) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame viewProfile = new ViewProfile(actualUser);
        viewProfile.setVisible(true);
        viewProfile.requestFocus();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnyadirApp;
    private javax.swing.JButton btnAnyadirWeb;
    private javax.swing.JButton btnCancelApp;
    private javax.swing.JButton btnCancelWeb;
    private javax.swing.JButton btnClipboardApp;
    private javax.swing.JButton btnClipboardWeb;
    private javax.swing.JButton btnEliminarApp;
    private javax.swing.JButton btnEliminarWeb;
    private javax.swing.JButton btnIniciarApp;
    private javax.swing.JButton btnIniciarWeb;
    private javax.swing.JButton btnModificarApp;
    private javax.swing.JButton btnModificarWeb;
    private javax.swing.JButton btnOkApp;
    private javax.swing.JButton btnOkWeb;
    private javax.swing.JButton btnRutaApp;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel labelImagenApp;
    private javax.swing.JLabel labelImagenWeb;
    private javax.swing.JLabel labelNombreApp;
    private javax.swing.JLabel labelNombreWeb;
    private javax.swing.JLabel labelRunningOsApp;
    private javax.swing.JLabel labelRunningOsWeb;
    private javax.swing.JLabel labelRutaApp;
    private javax.swing.JLabel labelShowImagenApp;
    private javax.swing.JLabel labelShowImagenWeb;
    private javax.swing.JLabel labelURLWeb;
    private javax.swing.JMenuItem menuItemCerrarSesion;
    private javax.swing.JMenuItem menuItemSalir;
    private javax.swing.JPanel panelApp;
    private javax.swing.JPanel panelBotonesApp;
    private javax.swing.JPanel panelBotonesWeb;
    private javax.swing.JPanel panelEditorApp;
    private javax.swing.JPanel panelEditorWeb;
    private javax.swing.JPanel panelImagenApp;
    private javax.swing.JPanel panelImagenWeb;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel panelWeb;
    private javax.swing.JScrollPane scrollPaneTablaApp;
    private javax.swing.JScrollPane scrollPaneTablaWeb;
    private javax.swing.JTabbedPane tabbedPanePrincipal;
    private javax.swing.JTable tablaApp;
    private javax.swing.JTable tablaWeb;
    private javax.swing.JTextField textFieldNombreApp;
    private javax.swing.JTextField textFieldNombreWeb;
    private javax.swing.JTextField textFieldRutaApp;
    private javax.swing.JTextField textFieldURLWeb;
    private javax.swing.JLabel versionLabelApp;
    private javax.swing.JLabel versionLabelWeb;
    // End of variables declaration//GEN-END:variables
}
