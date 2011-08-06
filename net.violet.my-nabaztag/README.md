# My Nabaztag

## Description

Customer interface. Allows users to configure their rabbits, add services and send messages.

## Running under Tomcat

The following system property must be set with recent versions of Tomcat: `org.apache.jasper.compiler.Parser.STRICT_QUOTE_ESCAPING=false`. Some JSP contains quotes that aren't escaped and without this property Tomcat will refuse to compile them.
