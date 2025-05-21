# jenkins-ci/Dockerfile
FROM jenkins/jenkins:lts

USER root

# Instalamos Docker
RUN apt-get update && \
    apt-get install -y docker.io docker-compose curl gnupg2 software-properties-common

# Instalamos Maven
ARG MAVEN_VERSION=3.9.6
ARG MAVEN_HOME=/usr/share/maven

RUN curl -fsSL https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -o /tmp/maven.tar.gz && \
    mkdir -p ${MAVEN_HOME} && \
    tar -xzf /tmp/maven.tar.gz -C ${MAVEN_HOME} --strip-components=1 && \
    ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn && \
    rm -f /tmp/maven.tar.gz

ENV MAVEN_HOME=/usr/share/maven
ENV PATH="${MAVEN_HOME}/bin:${PATH}"

USER jenkins
