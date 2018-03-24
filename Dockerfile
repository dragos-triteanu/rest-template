FROM centos

RUN yum install -y wget
RUN wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/9.0.4+11/c2514751926b4512b076cc82f959763f/jdk-9.0.4_linux-x64_bin.rpm"
RUN yum install -y jdk-9.0.4_linux-x64_bin.rpm

VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} myapp.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/myapp.jar"]