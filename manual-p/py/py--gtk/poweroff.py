#!/usr/bin/env python
# -*- coding: utf-8 -*-
import pygtk
import gtk
import gtk.glade
import sys      
import os
        
class powerOff:
   def __init__(self):
      self.ui_file=sys.path[0]+'/ui.glade'
             self.widgetTree=gtk.glade.XML(self.ui_file,'window1')
           dic={"on_exit_clicked":gtk.main_quit,\
                "on_window1_destroy":gtk.main_quit,\
         "on_shutdown_clicked":self.shutdown,\
         "on_reboot_clicked":self.reboot,\
         "on_loginout_clicked":self.loginout}
            self.widgetTree.signal_autoconnect(dic)    
#   def delete_event(self, widget, data=None):
#          print "delete_event"

#   def destroy(self, widget, data=None):
#       gtk.main_quit()

   def loginout(self, widget, data=None):
       os.system("openbox --exit")
#       print "clicked loginout"

   def shutdown(self, widget, data=None):
       os.system("sudo shutdown -h now")
#       print "clicked shutdown"

   def reboot(self, widget, data=None):
       os.system("sudo shutdown -r now")
#       print "clicked reboot"
#   def restart(self, widget, data=None):
#       os.system("openbox --restart")
#   def exit(self, widget, data=None):
#       sys.exit()
   def main(self):
                gtk.main()
        
if __name__=='__main__':
   poweroff=powerOff()    
        poweroff.main()