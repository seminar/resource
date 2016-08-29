//<MStar Software>
//******************************************************************************
// MStar Software
// Copyright (c) 2010 - 2012 MStar Semiconductor, Inc. All rights reserved.
// All software, firmware and related documentation herein ("MStar Software") are
// intellectual property of MStar Semiconductor, Inc. ("MStar") and protected by
// law, including, but not limited to, copyright law and international treaties.
// Any use, modification, reproduction, retransmission, or republication of all 
// or part of MStar Software is expressly prohibited, unless prior written 
// permission has been granted by MStar. 
//
// By accessing, browsing and/or using MStar Software, you acknowledge that you
// have read, understood, and agree, to be bound by below terms ("Terms") and to
// comply with all applicable laws and regulations:
//
// 1. MStar shall retain any and all right, ownership and interest to MStar
//    Software and any modification/derivatives thereof.
//    No right, ownership, or interest to MStar Software and any
//    modification/derivatives thereof is transferred to you under Terms.
//
// 2. You understand that MStar Software might include, incorporate or be
//    supplied together with third party`s software and the use of MStar
//    Software may require additional licenses from third parties.  
//    Therefore, you hereby agree it is your sole responsibility to separately
//    obtain any and all third party right and license necessary for your use of
//    such third party`s software. 
//
// 3. MStar Software and any modification/derivatives thereof shall be deemed as
//    MStar`s confidential information and you agree to keep MStar`s 
//    confidential information in strictest confidence and not disclose to any
//    third party.  
//
// 4. MStar Software is provided on an "AS IS" basis without warranties of any
//    kind. Any warranties are hereby expressly disclaimed by MStar, including
//    without limitation, any warranties of merchantability, non-infringement of
//    intellectual property rights, fitness for a particular purpose, error free
//    and in conformity with any international standard.  You agree to waive any
//    claim against MStar for any loss, damage, cost or expense that you may
//    incur related to your use of MStar Software.
//    In no event shall MStar be liable for any direct, indirect, incidental or
//    consequential damages, including without limitation, lost of profit or
//    revenues, lost or damage of data, and unauthorized system use.
//    You agree that this Section 4 shall still apply without being affected
//    even if MStar Software has been modified by MStar in accordance with your
//    request or instruction for your use, except otherwise agreed by both
//    parties in writing.
//
// 5. If requested, MStar may from time to time provide technical supports or
//    services in relation with MStar Software to you for your use of
//    MStar Software in conjunction with your or your customer`s product
//    ("Services").
//    You understand and agree that, except otherwise agreed by both parties in
//    writing, Services are provided on an "AS IS" basis and the warranty
//    disclaimer set forth in Section 4 above shall apply.  
//
// 6. Nothing contained herein shall be construed as by implication, estoppels
//    or otherwise:
//    (a) conferring any license or right to use MStar name, trademark, service
//        mark, symbol or any other identification;
//    (b) obligating MStar or any of its affiliates to furnish any person,
//        including without limitation, you and your customers, any assistance
//        of any kind whatsoever, or any information; or 
//    (c) conferring any license or right under any intellectual property right.
//
// 7. These terms shall be governed by and construed in accordance with the laws
//    of Taiwan, R.O.C., excluding its conflict of law rules.
//    Any and all dispute arising out hereof or related hereto shall be finally
//    settled by arbitration referred to the Chinese Arbitration Association,
//    Taipei in accordance with the ROC Arbitration Law and the Arbitration
//    Rules of the Association by three (3) arbitrators appointed in accordance
//    with the said Rules.
//    The place of arbitration shall be in Taipei, Taiwan and the language shall
//    be English.  
//    The arbitration award shall be final and binding to both parties.
//
//******************************************************************************
//<MStar Software>
#ifdef DISABLE
#include "mlogger.h"
#include <stdio.h>
#include <stdarg.h>
#else
#include "MLoggerUISource.h"
#include "mlogger.h"
#include <limits.h>
#endif

#include "mthread.h"

#ifdef NOPRINT
#define printf(x)
#endif


extern "C" int MLogger_RegisterComponent(const char *name, MLOG_COLOR FG, MLOG_COLOR BG, pfTestFunc_t pfTestFunc, bool isDefaultShow)
{
#ifdef DISABLE
    return 0;
#else
    pthread_mutex_lock(&MLogger::mutex_Access);
    MLoggerBase *Base = MLoggerBase::GetInstance();
    const char *fgAnsi, *bgAnsi;
    fgAnsi = bgAnsi = NULL;
    fgAnsi = MLogger::GetInstance()->fg_ansi[FG];
    bgAnsi = MLogger::GetInstance()->bg_ansi[BG%8];

    ComponentItem itemAddCandidate;
    bool isExist = false;
    int i = 0;
    int id;
    int size = Base->componentVector.size();
    for(i=0; i<size; i++){
        if(!strcmp(Base->componentVector[i].GetName(), name)){
            isExist = true;
            id =  Base->componentVector[i].GetID();

            //override funcPtr address always ...
            Base->componentVector[i].SetCallBackFunction(pfTestFunc);
            pthread_mutex_unlock(&MLogger::mutex_Access);
            return id;
        }
    }

    if(!isExist)
    {
        Base->Iter = Base->componentVector.end();
        id = Base->GetComponentUniqueID();
        itemAddCandidate.SetName(name);
        itemAddCandidate.SetMsgEnable(isDefaultShow);
        itemAddCandidate.SetFgColor(fgAnsi);
        itemAddCandidate.SetBgColor(bgAnsi);
        itemAddCandidate.SetTraceEnable(false);
        itemAddCandidate.SetID(id);
        itemAddCandidate.SetCallBackFunction(pfTestFunc);
        Base->componentVector.insert(Base->componentVector.begin()+id-1, itemAddCandidate);
        Base->SaveCfgVectorToBinary(Base->componentVector);
    }
    pthread_mutex_unlock(&MLogger::mutex_Access);
    return id;
#endif
}

extern "C" bool MLogger_UnRegisterComponent(int id, const char *name)
{
#ifdef DISABLE
    return FALSE;
#else
    pthread_mutex_lock(&MLogger::mutex_Access);
    MLoggerBase *Base = MLoggerBase::GetInstance();
    int i;
    for(i=0; i<(int)Base->componentVector.size(); i++){
        if((id == Base->componentVector[i].id) && (!strcmp(name, Base->componentVector[i].name))){
            Base->componentVector.erase(Base->componentVector.begin()+i);
            Base->SaveCfgVectorToBinary(Base->componentVector);
            pthread_mutex_unlock(&MLogger::mutex_Access);
            return true;
        }
    }
    pthread_mutex_unlock(&MLogger::mutex_Access);
    return false;
#endif
}

extern "C" void MLogger_Printf(int id, MLOG_LEVEL LV, const char* msg, ...)
{
#ifdef DISABLE
    char tmpbuf[1024];
    va_list list_ptr;
    va_start(list_ptr, msg);
    vsprintf(tmpbuf, msg, list_ptr);
    va_end(list_ptr);
    printf(tmpbuf);
    return;
#else
    char tmpbuf[_1024];
    va_list list_ptr;
    va_start(list_ptr, msg);
    vsprintf(tmpbuf, msg, list_ptr);
    va_end(list_ptr);
    MLoggerBase::GetInstance()->out(true, (EN_MLOG_COMPONENT) id, (EN_MLOG_LEVEL) LV, tmpbuf);
#endif
}

extern "C" void MLogger_SetSeverity(MLOG_LEVEL severity)
{
#ifdef DISABLE
    return;
#else
    MLoggerBase *Base = MLoggerBase::GetInstance();
    switch((EN_MLOG_LEVEL)severity)
    {
        case 0:
            strncpy(Base->globalFlag.severity, "AutoTest", 10);
            break;
        case 1:
            strncpy(Base->globalFlag.severity, "Fatal", 10);
            break;
        case 2:
            strncpy(Base->globalFlag.severity, "Error", 10);
            break;
        case 3:
            strncpy(Base->globalFlag.severity, "Warning", 10);
            break;
        case 4:
            strncpy(Base->globalFlag.severity, "Info", 10);
            break;
        case 5:
            strncpy(Base->globalFlag.severity, "Debug", 10);
            break;
        default:
            break;
    }
    Base->SaveCfgGlobalToBinary(Base->globalFlag);
#endif
}

extern "C" void MLogger_EnableTimeStamp(MLOG_SWITCH flag)
{
#ifdef DISABLE
    return;
#else
    MLoggerBase *Base = MLoggerBase::GetInstance();
    Base->globalFlag.time = flag;
    Base->SaveCfgGlobalToBinary(Base->globalFlag);
#endif
}

extern "C" void MLogger_EnableThreadId(MLOG_SWITCH flag)
{
#ifdef DISABLE
    return;
#else
    MLoggerBase *Base = MLoggerBase::GetInstance();
    Base->globalFlag.tid = flag;
    Base->SaveCfgGlobalToBinary(Base->globalFlag);
#endif
}

extern "C" void MLogger_Entrance()
{
#ifdef DISABLE
    return;
#else

    MLogger::GetInstance()->EnteringMLoggerMenu(true);
#endif
}

extern "C" void MLogger_DisableHotKey()
{
#ifdef DISABLE
    return;
#else
    MLogger *Mlog = MLogger::GetInstance();
    pthread_cancel(Mlog->threadKeyWaiter);
    Mlog->exit = true;
#endif
}

extern "C" void MLogger_EnableHotKey()
{
#ifdef DISABLE
    return;
#else
    pthread_attr_t attr;
    pthread_attr_init(&attr);
    pthread_attr_setstacksize(&attr, PTHREAD_STACK_SIZE);

    MLogger *Mlog = MLogger::GetInstance();
    Mlog->exit = false;
    if(pthread_create(&Mlog->threadKeyWaiter, &attr, Mlog->KeyWaiter, NULL))
        printf("Enable hot key waiter fail\n");
#endif
}

extern "C" void MLogger_Clear()
{
#ifdef DISABLE
    return;
#else
    system("clear");
#endif
}

