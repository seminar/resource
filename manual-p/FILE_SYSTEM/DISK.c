
//1 MBR 分区 检测：FILE_SYS_STRUCT_SIZE 512 byte MBR
#define DISK_DRIVE_NUM   //max drive 
#define FILE_SYS_STRUCT_SIZE   //512
#define MOUNTED_BUFFER     //4KB

typedef struct
{
    U8 DriveIndex;     //drive c d e f atc..
    U8 DeviceIndex;   //handware idex
    EN_FILE_SYSTEM_TYPE FileSystemType;
    U8 PartitionNo;   //分区号
    U32 LBA;           //逻辑块寻址模式(LBA)
    U32 u32MBRLogicBlockAddress; //逻辑块地址MBR  64BYTE
    U8 u8IndexInMBR;             // MBR 第几个分区
}MSDCtrl_Drive;

typedef struct
{

	FAT_ENVIRONMENT_ADR  //4kb
	FAT_DIR_ENTRY_CACHE_ADR  //4kb
	FAT_FSINFO_SECTOR_ADR  //4kb
	FAT_CURRENT_INFO_ADR   //4kb
}FILE_ENVIRONMENT_MEMORY_SIZE_struct

typedef struct
{
    U8 u8CurrentDirectoryIndex;
    U8 u8FileSystemID;
    U8 u8FileSystemType;
    U8 u8DeviceIndex;
    BOOLEAN bIsInit;
}FS_EnvironmentStruct;

FS_EnvironmentStruct g_environment_list;//code data

void APP_drive_INIT(void)
{
	//512 BYTE;  for one device MBR.
    int i;
	    for(i=0;i<DISK_DRIVE_NUM;i++)
    {
        API_FS_MIU_Copy(DRIVE_MEMORY_ADDR+driveID*sizeof(MSDCtrl_Drive),GET_DRAM_ADDR(pDrive),sizeof(MSDCtrl_Drive));
    }

}

// FILE SYSTEM POOL 0X6000  384KB
msAPI_FS_MMP_Init()
{

    USB_IO_TEMP_ADDR //
    USB_IO_TEMP_LEN  //4kb

    USB_IO_TEMP_ADDR2
	USB_IO_TEMP_LEN  //4KB

	DEVICE_IO_BUFFER_ADDR
	DEVICE_IO_BUFFER_LEN  //4kb

	STORAGE_DEVICE_MEMORY_ADDR
	STORAGE_DEVICE_MEMORY_LEN   //1kb

	DRIVE_MEMORY_ADDR
	DRIVE_MEMORY_LEN //1kb

	FILE_SYSTEM_MOUNTED_ADDR
	FILE_SYSTEM_MOUNTED_LEN //4kb

	FILE_ENVIRONMENT_POOL
	FILE_ENVIRONMENT_MEMORY_SIZE * FS_ENVIRONMENT_NUM  //64KB  16*4

	FILE_FAT_TABLE_CACHE
	FAT_TABLE_CACHE_LEN * DISK_DRIVE_NUM //4KB*16  64KB

	FILE_INODE_POOL
	FILE_INODE_MEMORY_SIZE * FS_FILE_HANDLE_NUM //30KB

	FILE_HANDLE_POOL
	FILE_HANDLE_MEMORY_SIZE * FS_FILE_HANDLE_NUM //30KB

    int i;
    for(i = 0; i<FS_ENVIRONMENT_NUM; i++)
    {
        XDataAddr[i][0] = FILE_ENVIRONMENT_POOL + FAT_ENVIRONMENT_ADR+i*FILE_ENVIRONMENT_MEMORY_SIZE;
        XDataAddr[i][1] = FILE_ENVIRONMENT_POOL + FAT_DIR_ENTRY_CACHE_ADR+i*FILE_ENVIRONMENT_MEMORY_SIZE;
        XDataAddr[i][2] = FILE_ENVIRONMENT_POOL + FAT_FSINFO_SECTOR_ADR+i*FILE_ENVIRONMENT_MEMORY_SIZE;
        XDataAddr[i][3] = FILE_ENVIRONMENT_POOL + FAT_CURRENT_INFO_ADR+i*FILE_ENVIRONMENT_MEMORY_SIZE;

        g_environment_list[i].u8CurrentDirectoryIndex = 0xFF;
        g_environment_list[i].u8DeviceIndex = 0xFF;
        g_environment_list[i].u8FileSystemID = 0xFF;
        g_environment_list[i].u8FileSystemType = FILE_SYSTEM_TYPE_NONE;
        g_environment_list[i].bIsInit = FALSE;
    }
}

API_FCtrl_GetDriveByIndex(U8 driveID,MSDCtrl_Drive *target_drive)
{
    // DRIVE MMP ADD   LENGTH
    API_FS_MIU_Copy(DRIVE_MEMORY_ADDR+driveID*sizeof(MSDCtrl_Drive),GET_DRAM_ADDR(pDrive),sizeof(MSDCtrl_Drive));

}
// mount add length;  512byte MBR * DISK mux 8  = = 4KB  FAT file system

API_FSEnv_Register(U8 driveID)
{
   
    U8 i;
    MSDCtrl_Drive target_drive;

    memset(&target_drive,0,sizeof(target_drive));

    for(i = 0; i<FS_ENVIRONMENT_NUM; i++)
    {
        if(g_environment_list[i].bIsInit==FALSE)
        {
            // do registeration
            if(!API_FCtrl_GetDriveByIndex(driveID, &target_drive))
            {
                return INVALID_FS_ENVIRONMENT_INDEX;
            }

            g_environment_list[i].u8FileSystemType = target_drive.FileSystemType;
            g_environment_list[i].u8FileSystemID = driveID;
            g_environment_list[i].u8DeviceIndex = target_drive.DeviceIndex;

            if(target_drive.FileSystemType==FILE_SYSTEM_TYPE_FAT)
            {
                if(!FAT_SaveEnvironment(i))
                {
                    return INVALID_FS_ENVIRONMENT_INDEX;
                }
            }
            else if(target_drive.FileSystemType==FILE_SYSTEM_TYPE_N51FS)
            {
                #if ENABLE_N51FS
                if(!N51FS_SaveEnvironment(i))
                {
                    return INVALID_FS_ENVIRONMENT_INDEX;
                }
                #else
                __ASSERT(0);
                return INVALID_FS_ENVIRONMENT_INDEX;
                #endif
            }
            else
            {
                return INVALID_FS_ENVIRONMENT_INDEX;
            }

            g_environment_list[i].bIsInit = TRUE;

            FSENV_DBG(printf("Register Drive %bu with environmentID=%bu for FSType=%bu\n",target_drive.DriveIndex,i,target_drive.FileSystemType));

            return i;
        }
    }

}