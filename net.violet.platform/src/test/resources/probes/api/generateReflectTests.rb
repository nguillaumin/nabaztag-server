#!/usr/bin/env ruby

methodClasses = {
	"nosideeffect" => "GET",
	"update" => "UPDATE",
	"create" => "CREATE",
	"delete" => "DELETE",
}

methodDefRegex = /^\\methoddef\{(.*)\}/
methodClassRegex = /^\\methodclass(.*)/
methodErrorRegex = /^\\methoderror\{(.*)\}\{(.*)\}/
methodEndRegex = /^\\methodend/

def createMethodErrorsRegex(methodErrors)
  result = "\\["
  methodErrors.sort.each do |anError|
    result += "\"" + anError + "\",?"
  end
  result += "\\]"
  return result
end

def createTest(methodName, methodClass, methodErrors)
  File.open("methods.get_" + methodName + ".test", "w") do |aFile|
    aFile.write("application=webui\n")
    aFile.write("url=methods/get/" + methodName + "\n")
    aFile.write("content_type=text/json\n")
    methodErrorsRegex = createMethodErrorsRegex(methodErrors)
    aFile.write("regex=\"errors\":" + methodErrorsRegex + ",.*\"method_class\":\"" + methodClass + "\"\n")
  end
end


if (ARGV.length == 0)
  print "Usage : generateReflectTests.rb file.tex ... file.tex [method ... method]"
else
  filesList = []
  methodsList = []

  ARGV.each do |param|
    if(/\.tex$/.match(param))
      filesList << param
#    elsif (matchData = /^methods.get_(.*)\.test$/.match(param))
#      methodsList << matchData[1]
    elsif (matchData = /^(violet.*)$/.match(param))
      methodDefRegex = /^\\methoddef\{(#{matchData[1]}.*)\}/
      methodsList << param
    #else
    #  methodsList << param
    end
  end

  if(filesList.length == 0)
    print "Error: generateReflectTests.rb needs at least one latex source file\n"

  else
    filesList.each do |aLatexSourceFile|
      File.open(aLatexSourceFile, "r") do |aFile|
        currentMethod = nil
        currentMethodClass = nil
        currentMethodErrors = []
	previousMethod = nil

        aFile.each do |aLine|
	  #print "=>", methodDefRegex, '==', aLine, "\n"
          if (matchData = methodDefRegex.match(aLine))
            currentMethodClass = nil
            currentMethodErrors = []
            currentMethod = matchData[1]
          elsif (matchData = methodClassRegex.match(aLine))
            currentMethodClass = methodClasses[matchData[1]]
          elsif (matchData = methodErrorRegex.match(aLine))
            currentMethodErrors << matchData[1]
          elsif (methodEndRegex.match(aLine))
	    #if ((methodsList.length == 0) || (methodsList.include?(currentMethod)))
	    if (((methodsList.length == 0) || currentMethod != nil) && currentMethod != previousMethod)
              if (currentMethodClass == nil)
                puts "Error: method " + currentMethod + " has no method class in the latex source file\n"
              else
	        previousMethod = currentMethod
                createTest(currentMethod, currentMethodClass, currentMethodErrors)
              end
            end
          end
        end
      end
    end #-- For each latex file

  end #-- End if filesList.length != 0

end #--End if ARGV.length != 0
