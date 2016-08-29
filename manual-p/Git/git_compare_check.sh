#!/bin/bash

# $1current version, $1 compare 1 version, compare 2 version
rm -rf ../patch*

if [ "$1" == "" -o "$2" == "" -o "$3" == "" ]
	then 
	echo "please set current version, compare first version,compare second version"
	exit
fi

git log --pretty=format:"%an ~ %h %s" --graph > ../log_back
git diff $2  $3 > patch


grep "diff --git" ./patch >patch_temp

#cat patch_temp |awk  '{print $3}' >patch_files
#cat patch_temp |awk -F "" '{split($0,a,"[ab]");print a[2]}'

cat patch_temp |cut -b 13- > patch_files_back

cat patch_files_back  | awk -F" b\/" '{print $2}' >patch_files


#sort patch_files_old_1 | uniq -c | sort -rn >patch_files_old_2


dos2unix patch_files

grep -vi 'o$' patch_files |grep -vi 'd$' >patch_files_back
cp patch_files_back  patch_files

dos2unix patch_files

dir_tmp=`date +%Y-%m-%d-%H-%M`
echo $dir_tmp
mkdir ../"patch"_"$dir_tmp"_"old"_"$3"
mkdir ../"patch"_"$dir_tmp"_"new"_"$2"
mkdir ../"patch"_"$dir_tmp"_"package"


git reset --hard $2
oldifs=$IFS
IFS=$'\x0A'
for line in $(cat ./patch_files)
do
	tar cpf - ./${line} | tar xpf - -C ../"patch"_"$dir_tmp"_"new"_"$2"
	#cp  ./${line} ../git_update_new
done



#find ../git_update_old -name "*.o" -a -name "*.d" -exec rm -f {} \;

git reset --hard $3

for line in $(cat ./patch_files)
do
	tar cpf - ./${line} | tar xpf - -C ../"patch"_"$dir_tmp"_"old"_"$3"

	#tar -cpf - ./${line} | tar -xpf - -C ../git_update_old
	#cp  ./${line} ../git_update_old
done
IFS=$oldifs
#find ../git_update_old -name "*.o" -a -name "*.d" -exec rm -f {} \;
rm patch*
git reset --hard $1

mv  ../"patch"_"$dir_tmp"_"old"_"$3" ../"patch"_"$dir_tmp"_"package"
mv  ../"patch"_"$dir_tmp"_"new"_"$2" ../"patch"_"$dir_tmp"_"package"
mv ../log_back ../"patch"_"$dir_tmp"_"package"


echo "compare successful!!"

