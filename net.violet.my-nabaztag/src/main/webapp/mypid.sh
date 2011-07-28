
chaine="$(ps -ef | grep Djava | head -n 1 | awk '{print $2}')"

checkjava="$(ps -ef | grep Djava | grep $chaine)"

all="$(ls /proc/$chaine/fd/ |wc -l)"

socket="$(ls -l /proc/$chaine/fd/ | grep socket | wc -l)"

pipe="$(ls -l /proc/$chaine/fd/ | grep pipe | wc -l)"  

other="$(ls -l /proc/$chaine/fd/ | grep -v pipe | grep -v socket | wc -l)"

echo "check id tomcat : "$chaine " -> "$checkjava
echo ""
echo "all fd :"$all" socket :"$socket" pipe :" $pipe" other :"$other
