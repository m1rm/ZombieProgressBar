import com.intellij.ide.ui.LafManager;

import javax.swing.*;

public class ZombieApplicationComponent {
    public ZombieApplicationComponent() {
        LafManager.getInstance().addLafManagerListener(__ -> updateProgressBarUi());
        updateProgressBarUi();
    }

    private void updateProgressBarUi() {
        UIManager.put("ProgressBarUI", ZombieProgressBarUi.class.getName());
        UIManager.getDefaults().put(ZombieProgressBarUi.class.getName(), ZombieProgressBarUi.class);
    }
}
