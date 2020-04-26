#!/bin/bash
rm -rf build/libs/*
./gradlew clean assemble
mv build/libs/*.war build/libs/ROOT.war
ssh root@covid19helpusa.com 'mkdir -p ~/oldDeploys/$(date +%Y%m%d)/ && cp -f /opt/tomcat/webapps/ROOT.war ~/oldDeploys/$(date +%Y%m%d)/ && rm -rf /opt/tomcat/webapps/ROOT && chown  tomcat:tomcat /opt/tomcat/webapps/ROOT.war'
scp -r $(pwd)/build/libs/ROOT.war root@covid19helpusa.com:/opt/tomcat/webapps/
ssh root@covid19helpusa.com 'sudo service tomcat restart'
ssh root@covid19helpusa.com 'tail -n 300 /opt/tomcat/logs/catalina.out'