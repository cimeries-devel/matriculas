package com.devel.custom;

import com.devel.ForResources;
import com.devel.utilities.Utilidades;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DnDTabbedPane extends JTabbedPane {
    private DnDTabbedPane jtabedpane=this;
    public static final long serialVersionUID = 1L;
    private static final int LINEWIDTH = 3;
    private static final String NAME = "TabTransferData";
    private final DataFlavor FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType, NAME);
    private static GhostGlassPane s_glassPane = new GhostGlassPane();
    private boolean m_isDrawRect = false;
    private final Rectangle2D m_lineRect = new Rectangle2D.Double();
    private final Color m_lineColor = new Color(0, 100, 255);
    private TabAcceptor m_acceptor = null;
    private Double maxX=0.0;
    private Double maxY=0.0;
    private Double minX=0.0;
    private Double minY=0.0;

    @Override
    public void addTab(String title, Icon icon,Component component) {
        super.addTab(title, icon,component);
        setTabComponentAt(indexOfTab(title), new Cross(this, title,icon));
    }


    public DnDTabbedPane() {
        super();
        getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                for (Component component : jtabedpane.getComponents()) {
                    if(indexOfComponent(component)!=-1){
                        ((TabPanel) component).getOption().setBackground(new JButton().getBackground());
                    }
                }
                if(getSelectedIndex()!=-1){
                    TabPanel tabPanel=(TabPanel) getComponentAt(getSelectedIndex());
//                    tabPanel.getOption().requestFocus();
                    Utilidades.buttonSelectedOrEntered(tabPanel.getOption());
                }
            }
        });
        //creacion de pop_up
        JPopupMenu pop_up = new JPopupMenu();
        JMenuItem cerrarPestaña = new JMenuItem("Cerrar Pestaña");
        JMenuItem cerrarOtras = new JMenuItem("Cerrar Otras Pestañas");
        JMenuItem cerrarTodas = new JMenuItem("Cerrar Todas Las Pestañas");
        cerrarPestaña.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(getSelectedIndex()!=-1){
                    removeTabAt(getSelectedIndex());
                }
            }
        });
        cerrarOtras.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(getSelectedIndex()!=-1){
                    TabPanel tab= (TabPanel) getComponentAt(getSelectedIndex());
                    String titulo= getTitleAt(getSelectedIndex());
                    removeAll();
                    add(tab,titulo);
                    setTabComponentAt(indexOfTab(titulo), new Cross(jtabedpane,titulo,tab.getIcon()));
                }
            }
        });

        cerrarTodas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                removeAll();
            }
        });
        pop_up.add(cerrarPestaña);
        pop_up.addSeparator();
        pop_up.add(cerrarOtras);
        pop_up.add(cerrarTodas);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton()==3){
                    maxX=0.0;
                    maxY=0.0;
                    minX=10000.0;
                    minY=10000.0;
                    for (Component component : getComponents()) {
                        if(indexOfComponent(component)!=-1){

                            if(maxX<getBoundsAt(indexOfComponent(component)).getMaxX()){
                                maxX=getBoundsAt(indexOfComponent(component)).getMaxX();
                            }
                            if(maxY<getBoundsAt(indexOfComponent(component)).getMaxY()){
                                maxY=getBoundsAt(indexOfComponent(component)).getMaxY();
                            }

                            if(minX>getBoundsAt(indexOfComponent(component)).getLocation().getLocation().getX()){
                                minX=getBoundsAt(indexOfComponent(component)).getLocation().getLocation().getX();
                            }
                            if(minY>getBoundsAt(indexOfComponent(component)).getLocation().getLocation().getY()){
                                minY=getBoundsAt(indexOfComponent(component)).getLocation().getLocation().getY();
                            }
                        }
                    }
                    if(e.getY()<=maxY&&e.getY()>=minY&&e.getX()<=maxX&&e.getX()>=minX){
                        if(tabPlacement==3||tabPlacement==4){
                            pop_up.show(getComponentAt(getSelectedIndex()),e.getX(),e.getY());
                        }else{
                            pop_up.show(getComponentAt(getMousePosition()),e.getX(),e.getY());
                        }

                    }

                }
            }
        });
        //Modificación tabPane
        final DragSourceListener dsl = new DragSourceListener() {
            public void dragEnter(DragSourceDragEvent e) {
                e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
            }

            public void dragExit(DragSourceEvent e) {
                e.getDragSourceContext()
                        .setCursor(DragSource.DefaultMoveNoDrop);
                m_lineRect.setRect(0, 0, 0, 0);
                m_isDrawRect = false;
                s_glassPane.setPoint(new Point(-1000, -1000));
                s_glassPane.repaint();
            }

            public void dragOver(DragSourceDragEvent e) {
                TabTransferData data = getTabTransferData(e);
                if (data == null) {
                    e.getDragSourceContext().setCursor(
                            DragSource.DefaultMoveNoDrop);
                    return;
                }
                e.getDragSourceContext().setCursor(
                        DragSource.DefaultMoveDrop);
            }

            public void dragDropEnd(DragSourceDropEvent e) {
                m_isDrawRect = false;
                m_lineRect.setRect(0, 0, 0, 0);
                // m_dragTabIndex = -1;

                if (hasGhost()) {
                    s_glassPane.setVisible(false);
                    s_glassPane.setImage(null);
                }
            }

            public void dropActionChanged(DragSourceDragEvent e) {
            }
        };

        final DragGestureListener dgl = new DragGestureListener() {
            public void dragGestureRecognized(DragGestureEvent e) {
                Point tabPt = e.getDragOrigin();
                int dragTabIndex = indexAtLocation(tabPt.x, tabPt.y);
                if (dragTabIndex < 0) {
                    return;
                } // if

                initGlassPane(e.getComponent(), e.getDragOrigin(), dragTabIndex);
                try {
                    e.startDrag(DragSource.DefaultMoveDrop,
                            new TabTransferable(DnDTabbedPane.this, dragTabIndex), dsl);
                } catch (InvalidDnDOperationException idoe) {
                    idoe.printStackTrace();
                }
            }
        };

        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE,
                new CDropTargetListener(), true);
        new DragSource().createDefaultDragGestureRecognizer(this,
                DnDConstants.ACTION_COPY_OR_MOVE, dgl);
        m_acceptor = new TabAcceptor() {
            public boolean isDropAcceptable(DnDTabbedPane a_component, int a_index) {
                return true;
            }
        };
    }

    public TabAcceptor getAcceptor() {
        return m_acceptor;
    }

    public void setAcceptor(TabAcceptor a_value) {
        m_acceptor = a_value;
    }

    private TabTransferData getTabTransferData(DropTargetDropEvent a_event) {
        try {
            TabTransferData data = (TabTransferData) a_event.getTransferable().getTransferData(FLAVOR);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private TabTransferData getTabTransferData(DropTargetDragEvent a_event) {
        try {
            TabTransferData data = (TabTransferData) a_event.getTransferable().getTransferData(FLAVOR);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private TabTransferData getTabTransferData(DragSourceDragEvent a_event) {
        try {
            TabTransferData data = (TabTransferData) a_event.getDragSourceContext().getTransferable().getTransferData(FLAVOR);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    class TabTransferable implements Transferable {
        private TabTransferData m_data = null;

        public TabTransferable(DnDTabbedPane a_tabbedPane, int a_tabIndex) {
            m_data = new TabTransferData(DnDTabbedPane.this, a_tabIndex);
        }

        public Object getTransferData(DataFlavor flavor) {
            return m_data;
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] f = new DataFlavor[1];
            f[0] = FLAVOR;
            return f;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.getHumanPresentableName().equals(NAME);
        }
    }

    class TabTransferData {
        private DnDTabbedPane m_tabbedPane = null;
        private int m_tabIndex = -1;

        public TabTransferData() {
        }

        public TabTransferData(DnDTabbedPane a_tabbedPane, int a_tabIndex) {
            m_tabbedPane = a_tabbedPane;
            m_tabIndex = a_tabIndex;
        }

        public DnDTabbedPane getTabbedPane() {
            return m_tabbedPane;
        }

        public void setTabbedPane(DnDTabbedPane pane) {
            m_tabbedPane = pane;
        }

        public int getTabIndex() {
            return m_tabIndex;
        }

        public void setTabIndex(int index) {
            m_tabIndex = index;
        }
    }

    private Point buildGhostLocation(Point a_location) {
        Point retval = new Point(a_location);

        switch (getTabPlacement()) {
            case JTabbedPane.TOP: {
                retval.y = 1;
                retval.x -= s_glassPane.getGhostWidth() / 2;
            } break;

            case JTabbedPane.BOTTOM: {
                retval.y = getHeight() - 1 - s_glassPane.getGhostHeight();
                retval.x -= s_glassPane.getGhostWidth() / 2;
            } break;

            case JTabbedPane.LEFT: {
                retval.x = 1;
                retval.y -= s_glassPane.getGhostHeight() / 2;
            } break;

            case JTabbedPane.RIGHT: {
                retval.x = getWidth() - 1 - s_glassPane.getGhostWidth();
                retval.y -= s_glassPane.getGhostHeight() / 2;
            } break;
        } // switch

        retval = SwingUtilities.convertPoint(DnDTabbedPane.this,
                retval, s_glassPane);
        return retval;
    }

    class CDropTargetListener implements DropTargetListener {
        public void dragEnter(DropTargetDragEvent e) {
            if (isDragAcceptable(e)) {
                e.acceptDrag(e.getDropAction());
            } else {
                e.rejectDrag();
            } // if
        }

        public void dragExit(DropTargetEvent e) {
            m_isDrawRect = false;
        }

        public void dropActionChanged(DropTargetDragEvent e) {
        }

        public void dragOver(final DropTargetDragEvent e) {
            TabTransferData data = getTabTransferData(e);

            if (getTabPlacement() == JTabbedPane.TOP
                    || getTabPlacement() == JTabbedPane.BOTTOM) {
                initTargetLeftRightLine(getTargetTabIndex(e.getLocation()), data);
            } else {
                initTargetTopBottomLine(getTargetTabIndex(e.getLocation()), data);
            } // if-else

            repaint();
            if (hasGhost()) {
                s_glassPane.setPoint(buildGhostLocation(e.getLocation()));
                s_glassPane.repaint();
            }
        }

        public void drop(DropTargetDropEvent a_event) {
            if (isDropAcceptable(a_event)) {
                convertTab(getTabTransferData(a_event),
                        getTargetTabIndex(a_event.getLocation()));
                a_event.dropComplete(true);
            } else {
                a_event.dropComplete(false);
            }

            m_isDrawRect = false;
            repaint();
        }

        public boolean isDragAcceptable(DropTargetDragEvent e) {
            Transferable t = e.getTransferable();
            if (t == null) {
                return false;
            } // if

            DataFlavor[] flavor = e.getCurrentDataFlavors();
            if (!t.isDataFlavorSupported(flavor[0])) {
                return false;
            }
            TabTransferData data = getTabTransferData(e);

            if (DnDTabbedPane.this == data.getTabbedPane()
                    && data.getTabIndex() >= 0) {
                return true;
            }

            if (DnDTabbedPane.this != data.getTabbedPane()) {
                if (m_acceptor != null) {
                    return m_acceptor.isDropAcceptable(data.getTabbedPane(), data.getTabIndex());
                }
            }

            return false;
        }

        public boolean isDropAcceptable(DropTargetDropEvent e) {
            Transferable t = e.getTransferable();
            if (t == null) {
                return false;
            }

            DataFlavor[] flavor = e.getCurrentDataFlavors();
            if (!t.isDataFlavorSupported(flavor[0])) {
                return false;
            }

            TabTransferData data = getTabTransferData(e);

            if (DnDTabbedPane.this == data.getTabbedPane()
                    && data.getTabIndex() >= 0) {
                return true;
            }

            if (DnDTabbedPane.this != data.getTabbedPane()) {
                if (m_acceptor != null) {
                    return m_acceptor.isDropAcceptable(data.getTabbedPane(), data.getTabIndex());
                }
            }

            return false;
        }
    }

    private boolean m_hasGhost = true;

    public void setPaintGhost(boolean flag) {
        m_hasGhost = flag;
    }

    public boolean hasGhost() {
        return m_hasGhost;
    }

    /**
     * returns potential index for drop.
     * @param a_point point given in the drop site component's coordinate
     * @return returns potential index for drop.
     */
    private int getTargetTabIndex(Point a_point) {
        boolean isTopOrBottom = getTabPlacement() == JTabbedPane.TOP
                || getTabPlacement() == JTabbedPane.BOTTOM;

        // if the pane is empty, the target index is always zero.
        if (getTabCount() == 0) {
            return 0;
        } // if

        for (int i = 0; i < getTabCount(); i++) {
            Rectangle r = getBoundsAt(i);
            if (isTopOrBottom) {
                r.setRect(r.x - r.width / 2, r.y, r.width, r.height);
            } else {
                r.setRect(r.x, r.y - r.height / 2, r.width, r.height);
            } // if-else

            if (r.contains(a_point)) {
                return i;
            } // if
        } // for

        Rectangle r = getBoundsAt(getTabCount() - 1);
        if (isTopOrBottom) {
            int x = r.x + r.width / 2;
            r.setRect(x, r.y, getWidth() - x, r.height);
        } else {
            int y = r.y + r.height / 2;
            r.setRect(r.x, y, r.width, getHeight() - y);
        } // if-else

        return r.contains(a_point) ? getTabCount() : -1;
    }

    private void convertTab(TabTransferData a_data, int a_targetIndex) {
        DnDTabbedPane source = a_data.getTabbedPane();
        int sourceIndex = a_data.getTabIndex();
        if (sourceIndex < 0) {
            return;
        }

        Component cmp = source.getComponentAt(sourceIndex);
        String str = source.getTitleAt(sourceIndex);
        Icon icon=source.getIconAt(sourceIndex);
        if (this != source) {
            source.remove(sourceIndex);
            if (a_targetIndex == getTabCount()) {
                addTab(str, cmp);
            } else {
                if (a_targetIndex < 0) {
                    a_targetIndex = 0;
                }
                insertTab(str, icon, cmp, null, a_targetIndex);
                setTabComponentAt(sourceIndex, new Cross(this, str,icon));
            }

            setSelectedComponent(cmp);
            return;
        }

        if (a_targetIndex < 0 || sourceIndex == a_targetIndex) {
            return;
        }
        if (a_targetIndex == getTabCount()) {
            source.remove(sourceIndex);
            addTab(str, icon,cmp);
            setSelectedIndex(getTabCount() - 1);
//            setTabComponentAt(getTabCount() - 1, new Cross(this, str,icon));
        } else if (sourceIndex > a_targetIndex) {
            source.remove(sourceIndex);
            insertTab(str, icon, cmp, null, a_targetIndex);
            setSelectedIndex(a_targetIndex);
            setTabComponentAt(a_targetIndex, new Cross(this, str,icon));
        } else {
            source.remove(sourceIndex);
            insertTab(str, icon, cmp, null, a_targetIndex - 1);
            setSelectedIndex(a_targetIndex - 1);
            setTabComponentAt(a_targetIndex - 1, new Cross(this, str,icon));
        }
    }

    private void initTargetLeftRightLine(int next, TabTransferData a_data) {
        if (next < 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } // if

        if ((a_data.getTabbedPane() == this)
                && (a_data.getTabIndex() == next
                || next - a_data.getTabIndex() == 1)) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
        } else if (getTabCount() == 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } else if (next == 0) {
            Rectangle rect = getBoundsAt(0);
            m_lineRect.setRect(-LINEWIDTH / 2, rect.y, LINEWIDTH, rect.height);
            m_isDrawRect = true;
        } else if (next == getTabCount()) {
            Rectangle rect = getBoundsAt(getTabCount() - 1);
            m_lineRect.setRect(rect.x + rect.width - LINEWIDTH / 2, rect.y,
                    LINEWIDTH, rect.height);
            m_isDrawRect = true;
        } else {
            Rectangle rect = getBoundsAt(next - 1);
            m_lineRect.setRect(rect.x + rect.width - LINEWIDTH / 2, rect.y,
                    LINEWIDTH, rect.height);
            m_isDrawRect = true;
        }
    }

    private void initTargetTopBottomLine(int next, TabTransferData a_data) {
        if (next < 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } // if

        if ((a_data.getTabbedPane() == this)
                && (a_data.getTabIndex() == next
                || next - a_data.getTabIndex() == 1)) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
        } else if (getTabCount() == 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } else if (next == getTabCount()) {
            Rectangle rect = getBoundsAt(getTabCount() - 1);
            m_lineRect.setRect(rect.x, rect.y + rect.height - LINEWIDTH / 2,
                    rect.width, LINEWIDTH);
            m_isDrawRect = true;
        } else if (next == 0) {
            Rectangle rect = getBoundsAt(0);
            m_lineRect.setRect(rect.x, -LINEWIDTH / 2, rect.width, LINEWIDTH);
            m_isDrawRect = true;
        } else {
            Rectangle rect = getBoundsAt(next - 1);
            m_lineRect.setRect(rect.x, rect.y + rect.height - LINEWIDTH / 2,
                    rect.width, LINEWIDTH);
            m_isDrawRect = true;
        }
    }

    private void initGlassPane(Component c, Point tabPt, int a_tabIndex) {
        //Point p = (Point) pt.clone();
        getRootPane().setGlassPane(s_glassPane);
        if (hasGhost()) {
            Rectangle rect = getBoundsAt(a_tabIndex);
            BufferedImage image = new BufferedImage(c.getWidth(),
                    c.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();
            c.paint(g);
            image = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
            s_glassPane.setImage(image);
        } // if

        s_glassPane.setPoint(buildGhostLocation(tabPt));
        s_glassPane.setVisible(true);
    }

    private Rectangle getTabAreaBound() {
        Rectangle lastTab = getUI().getTabBounds(this, getTabCount() - 1);
        return new Rectangle(0, 0, getWidth(), lastTab.y + lastTab.height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (m_isDrawRect) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(m_lineColor);
            g2.fill(m_lineRect);
        }
    }

    public interface TabAcceptor {
        boolean isDropAcceptable(DnDTabbedPane a_component, int a_index);
    }
}
class Cross extends JPanel {
    private JLabel L;
    private JLabel B;
    private int size = 22;
    private JTabbedPane jTabbedPane;
    private String title;

    public Cross(final JTabbedPane jTabbedPane, String title,Icon icon) {
        this.jTabbedPane = jTabbedPane;
        this.title = title;
        setOpaque(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        L = new JLabel(title + " ");
        L.setIcon(icon);
        L.setIconTextGap(5);
        Dimension d = new Dimension(22, 22);
        B = new JLabel();
        B.setPreferredSize(d);
        B.setMaximumSize(d);
        B.setMinimumSize(d);
        ImageIcon iconoNormal = getImage("cerrar.png");
        ImageIcon iconoSegundo = getImage("cerrar2.png");
        ImageIcon iconoTercero = getImage("cerrar3.png");
        B.setToolTipText("Cerrar Pestaña " + title);
        B.setIcon(iconoNormal);
        //Listener para cierre de tabs con acceso estatico al `JTabbedPane`
        B.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                jTabbedPane.removeTabAt(jTabbedPane.indexOfTab(title));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                B.setIcon(iconoSegundo);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                B.setIcon(iconoNormal);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                B.setIcon(iconoNormal);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                B.setIcon(iconoTercero);
            }
        });
        add(L, gbc);
        gbc.gridx++;
        gbc.weightx = 0;
        add(B, gbc);
    }

    private ImageIcon getImage(String icono) {
        Image IMG = null;
        try {
            IMG = new ImageIcon(ForResources.class.getResource(String.format("Icons/x24/" + icono))).getImage();
            IMG = IMG.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageIcon(IMG);
    }
}
class GhostGlassPane extends JPanel {
    public static final long serialVersionUID = 1L;
    private final AlphaComposite m_composite;

    private Point m_location = new Point(0, 0);

    private BufferedImage m_draggingGhost = null;

    public GhostGlassPane() {
        setOpaque(false);
        m_composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
    }

    public void setImage(BufferedImage draggingGhost) {
        m_draggingGhost = draggingGhost;
    }

    public void setPoint(Point a_location) {
        m_location.x = a_location.x;
        m_location.y = a_location.y;
    }

    public int getGhostWidth() {
        if (m_draggingGhost == null) {
            return 0;
        } // if

        return m_draggingGhost.getWidth(this);
    }

    public int getGhostHeight() {
        if (m_draggingGhost == null) {
            return 0;
        } // if

        return m_draggingGhost.getHeight(this);
    }

    public void paintComponent(Graphics g) {
        if (m_draggingGhost == null) {
            return;
        } // if

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(m_composite);

        g2.drawImage(m_draggingGhost, (int) m_location.getX(), (int) m_location.getY(), null);
    }

}
