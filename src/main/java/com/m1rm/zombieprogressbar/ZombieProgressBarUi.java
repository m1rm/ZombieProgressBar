package com.m1rm.zombieprogressbar;

import com.intellij.openapi.ui.GraphicsConfig;
import com.intellij.ui.Gray;
import com.intellij.ui.scale.JBUIScale;
import com.intellij.util.ui.GraphicsUtil;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.geom.*;
import java.net.URL;

public class ZombieProgressBarUi extends BasicProgressBarUI {
    private static final Color BLOODRED = new Color(128, 47, 47);
    private static final Icon ICON_FORWARD = loadIcon("/20x20_zombie.gif");
    private static final Icon ICON_REVERSED = loadIcon("/20x20_rzombie.gif");

    // Position and walking direction of the zombie in indeterminate mode
    private int zombieX = 0;
    private int direction = 1;

    @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
    public static ComponentUI createUI(JComponent c) {
        c.setBorder(JBUI.Borders.empty().asUIResource());
        return new ZombieProgressBarUi();
    }

    private static Icon loadIcon(final String path) {
        final URL url = ZombieProgressBarUi.class.getResource(path);
        return url != null ? new ImageIcon(url) : new ImageIcon();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(super.getPreferredSize(c).width, JBUIScale.scale(20));
    }

    @Override
    protected void paintIndeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }
        Graphics2D g2 = (Graphics2D)g;

        Insets progressBarInsets = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (progressBarInsets.right + progressBarInsets.left);
        int barRectHeight = progressBar.getHeight() - (progressBarInsets.top + progressBarInsets.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }

        int w = c.getWidth();
        int h = c.getPreferredSize().height;
        if (isUneven(c.getHeight() - h)) h++;

        g2.setPaint(BLOODRED);
        if (c.isOpaque()) {
            g2.fillRect(0, (c.getHeight() - h)/2, w, h);
        }

        final GraphicsConfig config = GraphicsUtil.setupAAPainting(g2);
        g2.translate(0, (c.getHeight() - h) / 2);

        final float R = JBUIScale.scale(8f);
        final float R2 = JBUIScale.scale(9f);
        g2.fill(new RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R));

        moveZombie(w);

        Area area = new Area(new Rectangle2D.Float(0, 0, w, h));
        area.subtract(new Area(new RoundRectangle2D.Float(1f, 1f, w - 2f, h - 2f, R, R)));
        g2.setPaint(Gray._128);
        if (c.isOpaque()) {
            g2.fill(area);
        }

        area.subtract(new Area(new RoundRectangle2D.Float(0, 0, w, h, R2, R2)));
        g2.setPaint(getParentBackground(c));
        if (c.isOpaque()) {
            g2.fill(area);
        }

        g2.draw(new RoundRectangle2D.Float(1f, 1f, w - 2f - 1f, h - 2f -1f, R, R));
        g2.translate(0, -(c.getHeight() - h) / 2);

        Icon icon = direction > 0 ? ICON_FORWARD : ICON_REVERSED;
        int iconY = (c.getHeight() - h) / 2 + Math.max(0, h - icon.getIconHeight());
        icon.paintIcon(progressBar, g2, zombieX - JBUIScale.scale(10), iconY);

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
                paintString(g2, progressBarInsets.left, progressBarInsets.top, barRectWidth, barRectHeight, boxRect.x, boxRect.width);
            }
            else {
                paintString(g2, progressBarInsets.left, progressBarInsets.top, barRectWidth, barRectHeight, boxRect.y, boxRect.height);
            }
        }
        config.restore();
    }

    /** Advances the zombie by one pixel per frame and turns it around at both ends of the bar. */
    private void moveZombie(int barWidth) {
        zombieX += direction;
        if (zombieX <= 2) {
            zombieX = 2;
            direction = 1;
        } else if (zombieX >= barWidth - JBUIScale.scale(15)) {
            zombieX = barWidth - JBUIScale.scale(15);
            direction = -1;
        }
    }

    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        if (progressBar.getOrientation() != SwingConstants.HORIZONTAL || !c.getComponentOrientation().isLeftToRight()) {
            super.paintDeterminate(g, c);
            return;
        }
        final GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        Insets borderInsets = progressBar.getInsets(); // area for border
        int progressBarWidth = progressBar.getWidth();
        int progressBarHeight = progressBar.getPreferredSize().height;
        if (isUneven(c.getHeight() - progressBarHeight)) progressBarHeight++;

        int barRectWidth = progressBarWidth - (borderInsets.right + borderInsets.left);
        int barRectHeight = progressBarHeight - (borderInsets.top + borderInsets.bottom);

        if (barRectWidth <= 0 || barRectHeight <= 0) {
            config.restore();
            return;
        }

        int amountFull = getAmountFull(borderInsets, barRectWidth, barRectHeight);

        Color background = getParentBackground(c);

        g.setColor(background);
        Graphics2D g2 = (Graphics2D)g;
        if (c.isOpaque()) {
            g.fillRect(0, 0, progressBarWidth, progressBarHeight);
        }

        final float R = JBUIScale.scale(8f);
        final float R2 = JBUIScale.scale(9f);
        final float off = JBUIScale.scale(1f);

        g2.translate(0, (c.getHeight() - progressBarHeight)/2);
        g2.setColor(progressBar.getForeground());
        g2.fill(new RoundRectangle2D.Float(0, 0, progressBarWidth - off, progressBarHeight - off, R2, R2));
        g2.setColor(background);
        g2.fill(new RoundRectangle2D.Float(off, off, progressBarWidth - 2f*off - off, progressBarHeight - 2f*off - off, R, R));

        g2.setPaint(BLOODRED);
        g2.fill(new RoundRectangle2D.Float(2f*off,2f*off, amountFull - JBUIScale.scale(5f), progressBarHeight - JBUIScale.scale(5f), JBUIScale.scale(7f), JBUIScale.scale(7f)));

        // Paint the zombie after the bar, so it walks on top of it
        int iconY = Math.max(0, progressBarHeight - ICON_FORWARD.getIconHeight());
        ICON_FORWARD.paintIcon(progressBar, g2, amountFull - JBUIScale.scale(7), iconY);
        g2.translate(0, -(c.getHeight() - progressBarHeight)/2);

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            paintString(g, borderInsets.left, borderInsets.top,
                    barRectWidth, barRectHeight,
                    amountFull, borderInsets);
        }
        config.restore();
    }

    private void paintString(Graphics2D g2, int x, int y, int w, int h, int fillStart, int amountFull) {
        String progressString = progressBar.getString();
        g2.setFont(progressBar.getFont());
        Point renderLocation = getStringPlacement(g2, progressString,
                x, y, w, h);
        Rectangle oldClip = g2.getClipBounds();

        if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
            g2.setColor(getSelectionBackground());
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(fillStart, y, amountFull, h);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
        } else { // VERTICAL
            g2.setColor(getSelectionBackground());
            AffineTransform rotate =
                    AffineTransform.getRotateInstance(Math.PI/2);
            g2.setFont(progressBar.getFont().deriveFont(rotate));
            renderLocation = getStringPlacement(g2, progressString,
                    x, y, w, h);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
            g2.setColor(getSelectionForeground());
            g2.clipRect(x, fillStart, w, amountFull);
            BasicGraphicsUtils.drawString(progressBar, g2, progressString,
                    renderLocation.x, renderLocation.y);
        }
        g2.setClip(oldClip);
    }

    @Override
    protected int getBoxLength(int availableLength, int otherDimension) {
        return availableLength;
    }

    private static Color getParentBackground(JComponent c) {
        Container parent = c.getParent();
        return parent != null ? parent.getBackground() : UIUtil.getPanelBackground();
    }

    private static boolean isUneven(int value) {
        return value % 2 != 0;
    }
}
