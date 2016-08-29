# -*- coding: utf-8 -*-
import tkinter as tk
import sys
import random
import re

number = random.randint(0,1024)
running = True
num = 0
nmaxn = 1024
nminn = 0

def eBtnClose(event):
    root.destroy()
    
def eBtnGuess(event):
    global nmaxn
    global nminn
    global num
    global running
    #修改缺陷：用户答对了，提示标签还提示信息 Edit by Hongten 2013-09-09
    #即用户在答对了以后，提示标签不应该再随着用户点击'猜'按钮而变化
    if running:
        val_a = int(entry_a.get())
        if val_a == number:
            labelqval("恭喜答对了！")
            num+=1
            running = False
            numGuess()
        elif val_a < number:
            if val_a > nminn:
                nminn = val_a
                num+=1
                label_tip_min.config(label_tip_min,text=nminn)
            labelqval("小了哦")
        else:
            if val_a < nmaxn:
                nmaxn = val_a
                num+=1
                label_tip_max.config(label_tip_max,text=nmaxn)
            labelqval("大了哦")
    else:
        labelqval('你已经答对啦...')

def numGuess():
    if num == 1:
        labelqval('我靠！一次答对！')   
    elif num < 10:
        labelqval('= =十次以内就答对了牛逼。。。尝试次数：'+str(num))
    elif num < 50:
        labelqval('还行哦尝试次数：'+str(num))
    else:
        labelqval('好吧。。。。。您都试了超过50次了。。。。尝试次数：'+str(num))
        
def labelqval(vText):
    label_val_q.config(label_val_q,text=vText)   
   
root = tk.Tk(className="比大小游戏")
root.geometry("400x90+200+200")

line_a_tip = tk.Frame(root)
label_tip_max = tk.Label(line_a_tip,text=nmaxn)
label_tip_min = tk.Label(line_a_tip,text=nminn)
label_tip_max.pack(side = "top",fill = "x")
label_tip_min.pack(side = "bottom",fill = "x")
line_a_tip.pack(side = "left",fill = "y")

line_question = tk.Frame(root)
label_val_q = tk.Label(line_question,width="80")
label_val_q.pack(side = "left")
line_question.pack(side = "top",fill = "x")

line_input = tk.Frame(root)
entry_a = tk.Entry(line_input,width="40")
btnGuess = tk.Button(line_input,text="猜")
entry_a.pack(side = "left")
entry_a.bind('<Return>',eBtnGuess)
btnGuess.bind('<Button-1>',eBtnGuess)
btnGuess.pack(side = "left")
line_input.pack(side = "top",fill = "x")


line_btn = tk.Frame(root)
btnClose = tk.Button(line_btn,text="关闭")
btnClose.bind('<Button-1>',eBtnClose)
btnClose.pack(side="left")
line_btn.pack(side = "top")

labelqval("请输入0到1024之间任意整数：")
entry_a.focus_set()

print(number)
root.mainloop()