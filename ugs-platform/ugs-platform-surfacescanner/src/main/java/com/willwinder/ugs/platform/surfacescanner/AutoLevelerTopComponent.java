/*
    Copyright 2017 Will Winder

    This file is part of Universal Gcode Sender (UGS).

    UGS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UGS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UGS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.willwinder.ugs.platform.surfacescanner;

import com.willwinder.ugs.nbm.visualizer.shared.IRendererNotifier;
import com.willwinder.ugs.nbm.visualizer.shared.RenderableUtils;
import com.willwinder.ugs.nbp.lib.lookup.CentralLookup;
import com.willwinder.universalgcodesender.listeners.UGSEventListener;
import com.willwinder.universalgcodesender.model.BackendAPI;
import com.willwinder.universalgcodesender.model.Position;
import com.willwinder.universalgcodesender.model.UGSEvent;
import com.willwinder.universalgcodesender.model.UnitUtils.Units;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Point3d;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.willwinder.ugs.nbm.autoleveler//AutoLeveler//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "AutoLevelerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "com.willwinder.ugs.nbm.autoleveler.AutoLevelerTopComponent")
@ActionReference(path = "Menu/Window/Experimental" , position = 999)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AutoLevelerAction",
        preferredID = "AutoLevelerTopComponent"
)
@Messages({
    "CTL_AutoLevelerAction=AutoLeveler",
    "CTL_AutoLevelerTopComponent=AutoLeveler Window",
    "HINT_AutoLevelerTopComponent=This is a AutoLeveler window"
})
public final class AutoLevelerTopComponent extends TopComponent implements ChangeListener, UGSEventListener {
    private AutoLevelPreview r;
    private SurfaceScanner scanner;
    private BackendAPI backend;
    private Position surface[][] = null;

    public AutoLevelerTopComponent() {
        initComponents();
        setName(Bundle.CTL_AutoLevelerTopComponent());
        setToolTipText(Bundle.HINT_AutoLevelerTopComponent());

        backend = CentralLookup.getDefault().lookup(BackendAPI.class);
        backend.addUGSEventListener(this);

        ChangeListener cl = this;
        stepResolution.addChangeListener(cl);
        xMin.addChangeListener(cl);
        xMax.addChangeListener(cl);
        yMin.addChangeListener(cl);
        yMax.addChangeListener(cl);
        zMin.addChangeListener(cl);
        zMax.addChangeListener(cl);
        xOffset.addChangeListener(cl);
        yOffset.addChangeListener(cl);
        unitInch.addChangeListener(cl);
        unitMM.addChangeListener(cl);
    }

    @Override
    public void UGSEvent(UGSEvent evt) {
        if (evt.isProbeEvent()) {
            scanner.probeEvent(evt.getProbePosition());
        }
    }

    private double getValue(JSpinner spinner) {
        Object o = spinner.getValue();
        try {
            return Double.parseDouble(o.toString());
        } catch(Exception ignored) {
        }
        return 0.0f;
    }

    /**
     * The preview parameters were changed.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        double xOff = getValue(xOffset);
        double yOff = getValue(yOffset);

        Units units = this.unitInch.isSelected() ? Units.INCH : Units.MM;
        Position corner1 = new Position(getValue(xMin) + xOff, getValue(yMin) + yOff, getValue(zMin), units);
        Position corner2 = new Position(getValue(xMax) + xOff, getValue(yMax) + yOff, getValue(zMax), units);

        double resolution = getValue(stepResolution);
        scanner.update(corner1, corner2, resolution);
        
        if (r != null) {
            // The visualizer works in MM, so convert units before providing to renderer.
            Position corner1mm = corner1.getPositionIn(Units.MM);
            Position corner2mm = corner2.getPositionIn(Units.MM);

            Point3d lowerLeft = new Point3d(corner1mm.x, corner1mm.y, corner1mm.z);
            Point3d upperRight = new Point3d(corner2mm.x, corner2mm.y, corner2mm.z);

            r.updateSettings(scanner.getProbePositions(), scanner.getProbePositionGrid());
            //r.updateSettings(lowerLeft, upperRight, resolution * UnitUtils.scaleUnits(units, Units.MM));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        unitGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        zMin = new javax.swing.JSpinner();
        yMin = new javax.swing.JSpinner();
        xMin = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        xMax = new javax.swing.JSpinner();
        yMax = new javax.swing.JSpinner();
        zMax = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        xOffset = new javax.swing.JSpinner();
        yOffset = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        stepResolution = new javax.swing.JSpinner();
        probeSpeed = new javax.swing.JSpinner();
        jPanel3 = new javax.swing.JPanel();
        scanSurfaceButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        unitMM = new javax.swing.JRadioButton();
        unitInch = new javax.swing.JRadioButton();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel7.text")); // NOI18N

        zMin.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        yMin.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        xMin.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel6.text")); // NOI18N

        xMax.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        yMax.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        zMax.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel8.text")); // NOI18N

        xOffset.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        yOffset.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 1.0d));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(xMin)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(zMin))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(yMin, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6)
                    .addComponent(yMax, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(xMax)
                    .addComponent(zMax))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(yOffset, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(xOffset))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(zMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(xMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(yMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(xMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(yMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(zMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(9, 9, 9)
                        .addComponent(xOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(yOffset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel3.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jLabel4.text")); // NOI18N

        stepResolution.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 1.0d));

        probeSpeed.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stepResolution, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(probeSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(stepResolution, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(probeSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(scanSurfaceButton, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.scanSurfaceButton.text")); // NOI18N
        scanSurfaceButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanSurfaceButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.jButton2.text")); // NOI18N

        unitGroup.add(unitMM);
        unitMM.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(unitMM, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.unitMM.text")); // NOI18N

        unitGroup.add(unitInch);
        org.openide.awt.Mnemonics.setLocalizedText(unitInch, org.openide.util.NbBundle.getMessage(AutoLevelerTopComponent.class, "AutoLevelerTopComponent.unitInch.text")); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scanSurfaceButton)
                    .addComponent(jButton2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(unitMM)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(unitInch)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitMM)
                    .addComponent(unitInch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scanSurfaceButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 80, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void scanSurfaceButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanSurfaceButtonActionPerformed
        if (scanner == null || scanner.getProbePositions() == null || scanner.getProbePositions().isEmpty()) {
            return;
        }

        try {
            for (Position p : scanner.getProbePositions()) {
                Position pMM = p.getPositionIn(Units.MM);
                backend.sendGcodeCommand(true, String.format("G90 G21 G0 X%f Y%f Z%f", p.x, p.y, p.z));
                backend.probe("Z", getValue(this.probeSpeed), this.scanner.getProbeDistance(), p.getUnits());
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }//GEN-LAST:event_scanSurfaceButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSpinner probeSpeed;
    private javax.swing.JButton scanSurfaceButton;
    private javax.swing.JSpinner stepResolution;
    private javax.swing.ButtonGroup unitGroup;
    private javax.swing.JRadioButton unitInch;
    private javax.swing.JRadioButton unitMM;
    private javax.swing.JSpinner xMax;
    private javax.swing.JSpinner xMin;
    private javax.swing.JSpinner xOffset;
    private javax.swing.JSpinner yMax;
    private javax.swing.JSpinner yMin;
    private javax.swing.JSpinner yOffset;
    private javax.swing.JSpinner zMax;
    private javax.swing.JSpinner zMin;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        scanner = new SurfaceScanner();
        if (r == null) {
            IRendererNotifier notifier = Lookup.getDefault().lookup(IRendererNotifier.class);
            r = new AutoLevelPreview(0, notifier);
        }

        RenderableUtils.registerRenderable(r);
    }

    @Override
    public void componentClosed() {
        RenderableUtils.removeRenderable(r);
        r = null;
    }

    void writeProperties(java.util.Properties p) {
    }

    void readProperties(java.util.Properties p) {
    }
}
