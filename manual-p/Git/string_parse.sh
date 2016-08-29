#!/bin/bash
rm -rf ./file_string*



#cat file_temp |xargs  sort  -k 1 -t ,
dir_tmp=`date +%Y-%m-%d-%H-%M`
echo string file modified "$dir_tmp" >file_log

find ./* |grep "\Str.rc$" > file_temp
printf "index\t" >file_string
cat file_temp |awk -F "/" '{printf $2 "\t"}' >>file_string
printf "\n">>file_string


sed -n 1p file_temp |xargs  cat  |awk '{printf "%s \t",NR}' >file_string_num
string_line_Max="`cat file_string_num |awk -F "\t" '{printf $(NF-1)}'`"
rm file_string_num

#echo "total num  "$string_line_Max





for ((i=1;i<string_line_Max;i++))
do
	#echo $i 
    
	read_file=0
	for line in $(cat file_temp)
	do
	{

		read_file=$(($read_file +1))
		sed -n "$i"p "$line"|awk -F"," -v file_index="$read_file" '{if(  file_index == "1"){ printf("%s\t%s\t", $1,$2)} else {printf("%s\t",$2) } }'>>file_string
		#sed -i 's/"//g' file_string_"$num" 
	}

	done
	
    printf "\n" >>file_string
done

sed -i 's/"//g' file_string
sed -i 's/;//g' file_string

mkdir file_string"$dir_tmp"
mv ./*file_string* ./file_string"$dir_tmp"
mv file_log ./file_string"$dir_tmp"
rm file_temp
export -n num
export -n read_file



