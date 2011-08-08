# My Nabaztag

## Description

Customer interface. Allows users to configure their rabbits, add services and send messages.

This is the **old** "green" website, not the most recent "purple" one.

## Database

You'll need some data in the database to be able to login. Check the `test-data.sql` file under the `platform` project for details.

All the message strings of the application were localized in the database, for which the schema nor the data was provided, so every label is replaced with an error message and a default value. Hopefully that's better that just failing if the localized string is not found and allow one to use the application.

## Running under Tomcat

The following system property must be set with recent versions of Tomcat: `org.apache.jasper.compiler.Parser.STRICT_QUOTE_ESCAPING=false`. Some JSP contains quotes that aren't escaped and without this property Tomcat will refuse to compile them.

You also need to set `net.violet.content-handler.script_root` to the path of the content handler scripts (Check the `content-handler` projects for details).
