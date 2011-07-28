# Nabaztag Server

This is my attempt at doing something with the sources of the [Nabaztag](http://en.wikipedia.org/wiki/Nabaztag) connected rabbit.

The sources were released by Mindscape on 2011-07-27 and are available here: http://code.google.com/p/nabaztag-source-code/

## Current state

The sources are nearly identical to the original. The only major change is that I tried to switch every project from Ant to Maven, as the Ant scripts weren't really usable and because I didn't want to upload all the dependencies (JAR files) to GitHub. I also removed the project `CommonDev` which contained only one class nearly identical to another in `common` (I merged the two).

Having to switch a project to Maven is also a good way to understand it.

The project compiles, but the unit tests fail.

I managed to start the 3 web applications (my-nabaztag, vadmin, platform) however they don't do much for now as the database, XMPP server, and some config files are missing.

## Future

The biggest showstopper now is that the source doesn't contain the database schema. I've asked Mindscape for it, however I suspect I'll have to do without it...

My initial goal is to run a server for my Nabaztag and some friends, and have some fun hacking the project.

## Maven setup

The only problematic point is the `platform` project: It's used as a dependency from `vadmin` and `my-nabaztag`, but because it's a WAR it makes things difficult. In a standard setup this WAR would be split in two project, a JAR with the common classes, and a WAR with the web app. That's what I intend to do once I'll familiar with the sources.

For now there's a hack on the `pom.xml` to generate a JAR for the project next to the JAR. Unfortunately it's not very elegant and doesn't seem to work well when running the project under Eclipse (WTP) + Tomcat.

### Dependencies

Some dependencies aren't available on Maven public repositories, and some other were old versions that aren't available anymore. You'll need to install them locally (They're available in the `extra-libs` folder).

I'll try to replace them with more recent versions when the code base will be stable enough.

```bash
# Spread 4.0.0
# Not distributed using Maven
mvn install:install-file -Dfile=spread-4.0.0.jar -DgroupId=spread -DartifactId=spread -Dversion=4.0.0 -Dpackaging=jar

# Hadoop 0.18.0
# Old version not available with Maven
mvn install:install-file -Dfile=hadoop-0.18.0-core.jar -DgroupId=org.apache.hadoop -DartifactId=hadoop-core -Dversion=0.18.0 -Dpackaging=jar

# SnakeYAML
# Old version not available with Maven
mvn install:install-file -Dfile=SnakeYAML-1.0.1.jar -DgroupId=org.yaml -DartifactId=snakeyaml -Dversion=1.0.1 -Dpackaging=jar

# CSV Tools
# Not distributed using Maven
mvn install:install-file -Dfile=csvtools.jar -DgroupId=csvtools -DartifactId=csvtools -Dversion=1.0.0 -Dpackaging=jar

# Apache Nutch
# Old version not available with Maven, language-identifier version not identified
mvn install:install-file -Dfile=nutch-2008-04-30_04-01-32.jar -DgroupId=org.apache.nutch -DartifactId=nutch -Dversion=2008-04-30_04-01-32 -Dpackaging=jar
mvn install:install-file -Dfile=language-identifier.jar -DgroupId=org.apache.nutch -DartifactId=language-identifier -Dversion=2008-04-30_04-01-32 -Dpackaging=jar

# Jive Software Smack
# Couldn't find which version is used. v3.0.1 is NOT compatible.
mvn install:install-file -Dfile=smack.jar -DgroupId=jivesoftware -DartifactId=smack -Dversion=3.0-violet -Dpackaging=jar
mvn install:install-file -Dfile=smackx.jar -DgroupId=jivesoftware -DartifactId=smackx -Dversion=3.0-violet -Dpackaging=jar

# Rome by Violet
# rome customised by Violet, but the source isn't provided :(
mvn install:install-file -Dfile=romeByViolet.jar -DgroupId=net.violet -DartifactId=rome -Dversion=1.0.0 -Dpackaging=jar

# O'Reilly Servlets
# Not distributed using Maven
mvn install:install-file -Dfile=cos.jar -DgroupId=com.oreilly -DartifactId=servlets -Dversion=2002 -Dpackaging=jar

# GNU GetOpt Java
# Not distributed using Maven
mvn install:install-file -Dfile=java-getopt-1.0.13.jar -DgroupId=com.urbanophile -DartifactId=java-getopt -Dversion=1.0.13 -Dpackaging=jar

# JSON Simple w/ Serializer
# Couldn't find the source. Most of the packages are coming from the json-simple project,
# except the serializer for which I couldn't find any reference. Maybe a custom development ?
mvn install:install-file -Dfile=json-simple.jar -DgroupId=org.json -DartifactId=simple -Dversion=1.0.0 -Dpackaging=jar

# Erlang OTP
# Not distributed using Maven
mvn install:install-file -Dfile=OtpErlang.jar -DgroupId=com.ericsson.otp -DartifactId=erlang -Dversion=1.0.0 -Dpackaging=jar
```
