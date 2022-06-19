package com.shushiro.zombieprogressbar;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public final class ZombieResourceLoader {

    private static final Cache<String, ImageIcon> CACHE = CacheBuilder.newBuilder().maximumSize(100L).build();

    private ZombieResourceLoader() {
    }

    public static ImageIcon getIcon() {
        return getIconInternal("24x24_zombie.gif");
    }

    public static ImageIcon getReversedIcon() { return getIconInternal("24x24_rzombie.gif"); }

    public static Optional<URL> getResource(final String resourceName) {
        return Optional.ofNullable(ZombieResourceLoader.class.getClassLoader().getResource(resourceName));
    }

    private static ImageIcon getIconInternal(final String resourceName) {
        try {
            return CACHE.get(resourceName, () -> getResource(resourceName).map(ImageIcon::new).orElseGet(ImageIcon::new));
        } catch (final ExecutionException e) {
            return new ImageIcon();
        }
    }
}
