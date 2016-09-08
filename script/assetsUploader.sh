export CLASSPATH="/home/nchaurasia/assets-upload-utility/*:/home/nchaurasia/assets-upload-utility/application-config/:/home/nchaurasia/assets-upload-utility/lib/*"
echo "CLASSPATH: " $CLASSPATH
cd /home/nchaurasia/assets-upload-utility
if [ "x$JAVA_OPTS" = "x" ]; then
     JAVA_OPTS="-server -Xms512m -Xmx1024m -Dfile.encoding=UTF-8"
fi
if [ "x$JAVA_HOME" = "x" ]; then
   JAVA_HOME="/usr/java/latest"
fi
"$JAVA_HOME"/bin/java $JAVA_OPTS assets.upload.start.Main DB FTP
echo "Assets upload Has been Completed"