#include <windows.h>

#include <string.h>

HWND GetHwnd()

{

    POINT p;

    HWND hwd;

    GetCursorPos(&p);

    hwd = WindowFromPoint(p);

    while (GetParent(hwd))

        hwd = GetParent(hwd);

    return hwd;

}

int main(int argc, char* args[])

{

    if (argc < 2)

    {

        SetWindowPos(GetHwnd(),HWND_TOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_DRAWFRAME | SWP_NOSIZE);

        return 0;

    }

    else

    {

        if (!strcmp("/?", args[1]))

        {

            MessageBox(0,

                "Availableparameters:\n/?\n/no param: stay on top.\nother param: cancel\n\n         2014.2.21 LastAvenger",

                "Help",

                MB_OK

                );

        }

        else

            SetWindowPos(GetHwnd(),HWND_NOTOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_DRAWFRAME | SWP_NOSIZE);

    }

}
