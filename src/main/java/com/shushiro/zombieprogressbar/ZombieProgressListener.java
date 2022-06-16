package com.shushiro.zombieprogressbar;

import javax.swing.*;
import java.util.Objects;

import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.ui.LafManager;
import com.intellij.ide.ui.LafManagerListener;
import com.intellij.openapi.extensions.PluginId;
import org.jetbrains.annotations.NotNull;

public class ZombieProgressListener implements LafManagerListener, DynamicPluginListener {
    private volatile static Object previousProgressBar = null;
    private volatile static PluginId pluginId = null;

    public ZombieProgressListener() {
        updateProgressBarUi();
        pluginId = PluginId.getId("shushiro.zombieprogressbar");
    }

    static void updateProgressBarUi() {
        final Object prev = UIManager.get("ProgressBarUI");
        if (!Objects.equals(ZombieProgressBarUi.class.getName(), prev)) {
            previousProgressBar = prev;
        }
        UIManager.put("ProgressBarUI", ZombieProgressBarUi.class.getName());
        UIManager.getDefaults().put(ZombieProgressBarUi.class.getName(), ZombieProgressBarUi.class);
    }

    static void resetProgressBarUi() {
        UIManager.put("ProgressBarUI", previousProgressBar);
    }

    @Override
    public void lookAndFeelChanged(@NotNull final LafManager lafManager) {
        updateProgressBarUi();
    }

    @Override
    public void pluginLoaded(@NotNull final IdeaPluginDescriptor pluginDescriptor) {
        if (Objects.equals(pluginId, pluginDescriptor.getPluginId())) {
            updateProgressBarUi();
        }
    }

    @Override
    public void beforePluginUnload(@NotNull final IdeaPluginDescriptor pluginDescriptor, final boolean isUpdate) {
        if (Objects.equals(pluginId, pluginDescriptor.getPluginId())) {
            resetProgressBarUi();
        }
    }
}