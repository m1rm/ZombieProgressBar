package com.m1rm.zombieprogressbar;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

public class ZombieProgressListener implements LafManagerListener, DynamicPluginListener {
    private static final PluginId PLUGIN_ID = PluginId.getId("com.m1rm.zombieprogressbar");
    private static final String PROGRESS_BAR_UI = "ProgressBarUI";
    private static volatile Object previousProgressBarUi = null;

    public ZombieProgressListener() {
        updateProgressBarUi();
    }

    static void updateProgressBarUi() {
        final Object prev = UIManager.get(PROGRESS_BAR_UI);
        if (!Objects.equals(ZombieProgressBarUi.class.getName(), prev)) {
            previousProgressBarUi = prev;
        }
        UIManager.put(PROGRESS_BAR_UI, ZombieProgressBarUi.class.getName());
        UIManager.getDefaults().put(ZombieProgressBarUi.class.getName(), ZombieProgressBarUi.class);
    }

    static void resetProgressBarUi() {
        // A null value removes our override, so the look and feel's own ProgressBarUI applies again
        UIManager.put(PROGRESS_BAR_UI, previousProgressBarUi);
        // Drop all references to our classes so the plugin classloader can be unloaded:
        // the class registered by name, and the createUI method Swing caches under the class itself
        UIManager.getDefaults().remove(ZombieProgressBarUi.class.getName());
        UIManager.getDefaults().remove(ZombieProgressBarUi.class);
        refreshProgressBars();
    }

    /** Re-creates the UI of all existing progress bars, so they pick up the current ProgressBarUI. */
    private static void refreshProgressBars() {
        for (final Window window : Window.getWindows()) {
            UIUtil.uiTraverser(window).filter(JProgressBar.class).forEach(JProgressBar::updateUI);
        }
    }

    @Override
    public void lookAndFeelChanged(@NotNull final LafManager lafManager) {
        updateProgressBarUi();
    }

    @Override
    public void pluginLoaded(@NotNull final IdeaPluginDescriptor pluginDescriptor) {
        if (PLUGIN_ID.equals(pluginDescriptor.getPluginId())) {
            updateProgressBarUi();
            refreshProgressBars();
        }
    }

    @Override
    public void beforePluginUnload(@NotNull final IdeaPluginDescriptor pluginDescriptor, final boolean isUpdate) {
        if (PLUGIN_ID.equals(pluginDescriptor.getPluginId())) {
            resetProgressBarUi();
        }
    }
}
