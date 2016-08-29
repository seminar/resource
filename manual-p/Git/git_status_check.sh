#!/bin/bash

# $1 new  version,$2 old version

rm -rf ../patch*

git status > patch_files

#back1 ,2 for new files list
cat patch_files |awk -F" " '{if(NF>2 && $2=="modified:") print $3 }' >patch_files_back1
#cat patch_files |awk -F" " '{if(NF>2 && $2=="deleted:") print $3 }' >>patch_files_back1

#back3 ,4 for odl files llist
#cat patch_files |awk -F" " '{if(NF>2 && $2=="modified:") print $3 }' >patch_files_back3
#cat patch_files |awk -F" " '{if(NF<3 ) print $2 }' >>patch_files_back3

dos2unix patch_files_back1 

grep -vi 'o$' patch_files_back1 |grep -vi 'd$' >patch_files_back2

#grep -vi 'o$' patch_files_back3 |grep -vi 'd$' >patch_files_back4

cp patch_files_back2 patch_files_back1
#cp patch_files_back4 patch_files_back3

#grep -ri "Supernova" ./patch_files_back4  >patch_files_back3

#grep -ri "Supernova" ./patch_files_back2 >patch_files_back1



sed -i 's/"//g' patch_files_back1
#sed -i 's/"//g' patch_files_back3



dir_tmp=`date +%Y-%m-%d-%H-%M`
echo $dir_tmp


mkdir ../"patch"_"$dir_tmp"_"old"
mkdir ../"patch"_"$dir_tmp"_"new"
mkdir ../"patch"_"$dir_tmp"_"package"

if [ "`cat patch_files_back1`" == "" ]

	then rm -rf patch*;rm -rf ../patch* ; echo "nothing need do !!!" ;exit;
else
	git archive -o "patch"_"$dir_tmp"_"old".zip HEAD `cat patch_files_back1` 
fi

#git archive -o "patch"_"$dir_tmp"_"old".zip HEAD `cat patch_files_back1` 
mv "patch"_"$dir_tmp"_"old".zip  ../"patch"_"$dir_tmp"_"old"

echo "start unzip "

unzip -q ../"patch"_"$dir_tmp"_"old"/"patch"_"$dir_tmp"_"old".zip -d ../"patch"_"$dir_tmp"_"old" 

oldifs=$IFS
IFS=$'\x0A'
for line in $(cat patch_files_back1)
do
	tar cpf - ./${line} | tar xpf - -C ../"patch"_"$dir_tmp"_"new"
done

mv  ../"patch"_"$dir_tmp"_"old" ../"patch"_"$dir_tmp"_"package"
mv  ../"patch"_"$dir_tmp"_"new" ../"patch"_"$dir_tmp"_"package"

IFS=$oldifs

rm patch*
echo "git status successful!!"

