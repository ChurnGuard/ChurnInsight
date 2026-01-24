FROM mcr.microsoft.com/devcontainers/base:ubuntu

# Install common dependencies
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends \
    git \
    curl \
    wget \
    ca-certificates \
    build-essential \
    mysql-client \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/*

# Install Node.js 20
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - \
    && apt-get install -y nodejs \
    && npm install -g npm@latest \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/*

# Install Java 17 (OpenJDK)
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends openjdk-17-jdk maven \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/*

# Install Python 3.11
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends \
    python3.11 \
    python3.11-venv \
    python3-pip \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/*

# Set Python 3.11 as default
RUN update-alternatives --install /usr/bin/python3 python3 /usr/bin/python3.11 1 \
    && update-alternatives --install /usr/bin/python python /usr/bin/python3.11 1

# Set environment variables
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Create workspace directory
RUN mkdir -p /workspace
WORKDIR /workspace

USER vscode
