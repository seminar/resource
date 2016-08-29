import gtk
import pygtk
import gtk.glade
import sys      
import os


class poweronoff():
	def __init__(self):
		print "aaa"
		self.ui_file='./gladetest.glade'
		self.widgetTree=gtk.glade.XML(self.ui_file,'window1')

		dic={"on_exit_clicked":gtk.main_quit,\
                "on_window1_destroy":gtk.main_quit,\
         "on_shutdown_clicked":self.shutdown,\
         "on_reboot_clicked":self.reboot,\
         "on_loginout_clicked":self.loginout}

		self.widgetTree.signal_autoconnect(dic)

	def loginout(self, widget, data=None):
   		os.system("openbox --exit")

  	def shutdown(self, widget, data=None):
   		os.system("sudo shutdown -h now")

  	def shutdown1(self, widget, data=None):
   		os.system("sudo shutdown -h now")

   	def reboot1(self,widget,data=None):
   		os.system("sudo shutdown -r now")

   	def reboot(self,widget,data=None):
   		os.system("sudo shutdown -r now")

   	def main(self):
   		gtk.main()


print __name__

#poo=poweronoff()
#poo.main()

if __name__=="__main__":
	poo=poweronoff()
	poo.main()
	gtk.main()
