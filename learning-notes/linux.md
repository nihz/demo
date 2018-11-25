#Linux相关学习

### 一、文件操作

	1. cp 文件复制
		-r 递归
		-v 显示详细信息

	2. mv 文件移动、重命名
	3. rm 删除命令
		-r 递归
		-i 交互式
		-f 强制删除

	4. mkdir 新建文件 
	5. x
	6. x
		
	
### 二、linux文件夹说明

	1. bin 二进制
	2. boot 引导目录
	3. dev 硬件设备
		1. sta、sta1 硬盘
	4. etc 系统配置文件	
	5. home 
	6. lib 库文件
	7. media 自动挂载
	8. mnt 手动挂载
	9. opt 大型软件
	10. proc 保存系统当时信息、进程文件夹
	11. sbin 超级用户的命令
	12. temp 临时目录 会自动删除
	13. usr 保存安装的软件
	14. var 经常变化的东西、操作系统的日志
 
### 三、linux常用命令

	1. date 查看、设置系统时间
		格式化显示时间:　+%Y--%m--%d
		-s 修改当前时间
	2. hwclock 显示硬件时钟时间
	3. cal 查看日历
	4. uptime 查看当前系统运行时间
	5. echo
	6. cat
	7. head -n 显示文件头几行
	8. tail -fn
	9. more\less 用户翻页显示文件内容
	10. lspci查看pic设备 -v
	11. lsusb查看usb设备 -v
	12. lsmod 查看加载的驱动模块
	13. zip\unzip 压缩文件
		zip myfile.zip myfile
		unzip myfile.zip
	14. tar 归档文件
		tar -cvf out.tar etc
		tar -xvf out.tar
		tar -cvzf out.tar /etc 归档后压缩
	15. locate 快速查找文件、文件夹 配合updatedb使用
	16. find 高级查找文件、文件夹
		find . -name *heikki* 当前文件夹所有文件名当中包含heikki的文件
		find / -name *.conf 
		find / -perm 777
		find / -type d 
		find . -name "a" -exec ls -l {} \ *exec 执行命令*
		【-name -perm -user -group -ctime -type -size】

### 四、vi文本编辑器
	1. :x 保存并退出
	2. :! 系统命令   执行一个系统命令并显示结果
	3. :sh 切换到命令行，使用ctrl+d切换回vim
	4. ctrl+f 屏幕『向下』移动一页，相当于 [Page Down]按键
	5. ctrl+b 屏幕『向上』移动一页，相当于 [Page Up] 按键
	6. ctrl+d 屏幕『向下』移动半页
	7. ctrl+u 屏幕『向上』移动半页
	8. n<space> 光标会向右移动这一行的 n 个字符
	9. $ 或功能键[End]	移动到这一行的最后面字符处
	10. ^  移动到这一行的最前面字符处
	11. n<Enter>	n 为数字。光标向下移动 n 行
	12. :n1,n2s/word1/word2/g	
		n1 与 n2 为数字。在第 n1 与 n2 行之间寻找 word1 这个字符串，
		并将该字符串取代为 word2 ！举例来说，在 100 到 200 行之间搜寻 vbird 
		并取代为 VBIRD 则：『:100,200s/vbird/VBIRD/g』。
	13. d1G	删除光标所在到第一行的所有数据
	14. dG	删除光标所在到最后一行的所有数据
	15. d$	删除游标所在处，到该行的最后一个字符
	16. d^	删除游标所在处，到该行的第一一个字符
	17. [Ctrl]+r	重做上一个动作。

### 五、磁盘基本概念
