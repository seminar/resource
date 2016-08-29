import tkinter as tk
 
count=0
def btnHelloClicked():
                global count
                count=count+1
                labelHello.config(text = "Hello Tkinter!%d"%count)
 
top = tk.Tk()
top.title("Button Test")
top.geometry('800x400+0+0')
labelHello = tk.Label(top, text = "Press the button count", height = 5, width = 20, fg = "blue")
labelHello.pack()
 
btn = tk.Button(top, text = "Hello", command = btnHelloClicked)
btn.pack()

top.mainloop()