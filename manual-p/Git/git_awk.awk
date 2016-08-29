#!/bin/awk -f

BEGIN{
        FS="\/"
}

{
        for(x=1;x<NF+1;x++) { 
                if (x==1) 
                      ;
                else 
                    print \/$x
                    if(x == NF)     
                    print "\n" "\n"             
                  
        }
}