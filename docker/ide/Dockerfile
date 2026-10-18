# JDK image plus the X11 client libraries the sandbox IDE needs to open a window
FROM eclipse-temurin:21-jdk
RUN apt-get update \
 && apt-get install -y --no-install-recommends \
    libxext6 libxrender1 libxtst6 libxi6 libxrandr2 libfreetype6 fontconfig fonts-dejavu-core \
 && rm -rf /var/lib/apt/lists/*
