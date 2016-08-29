#!/bin/bash
rm -rf ./file_string*
rm origin_string_file
srcfile=origin_string_file


#cat file_path |xargs  sort  -k 1 -t ,
dir_tmp=`date +%Y-%m-%d-%H-%M`
echo string file modified "$dir_tmp" >file_log

#ls |grep rc_ |awk '{print $0 "/Str.rc"}'> file_path
find ./* |grep "\Str.rc$" > file_path
printf "index\t" > $srcfile
cat file_path |awk -F "/" '{print "#" $2}' >>$srcfile

string_line_Max="`sed -n 1p file_path |xargs  cat|awk 'END{print NR}' `"
language_max="`cat file_path |awk 'END{print NR}'`"


#sed -n 1p file_path|xargs cat|awk -F ";,"  '{printf("%s\n",$1)}' >file_string_0
#cat file_path

for ((i=1;i<=string_line_Max;i++))
do
{
	for ((j=0;j<language_max;j++))
	do
		k=$[$j+1]
	    temp="`sed -n "$k"p file_path|xargs sed -n "$i"p `"
	    #|xargs sed -e 's/;,/.$j/g' >>$srcfile
	    echo ${temp/;,/.$j=} >>$srcfile
	done
}&
done
wait

sed -n 1p file_path |xargs  cat |awk -F ";" '{print $1}'|sort -n >file_string_index
for ((i=1;i<=string_line_Max;i++))
do
    index="`sed -n "$i"p file_string_index`"
    cat $srcfile |grep "$index""." |	sort -k 2 -n -t . >>file_string_src_temp

    for line in "`cat $srcfile |grep "$index""."|awk -F ":" '{print $1}'|xargs`"
    do
    	sed -i "$line"d $srcfile
    done
done

cp file_string_src_temp $srcfile
#cat $srcfile | awk -F '"' '{ if(NF >3){printf("%s\t%s\"%s\n",$1,$2,$3)}else{printf("%s\t%s\n",$1,$2)}}' > file_string_temp1
#//在所有行末添加
rm "$srcfile"_*
rm file_string* file_path
echo "parse finish"




