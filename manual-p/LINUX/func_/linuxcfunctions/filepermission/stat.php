<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src=http://www.iteedu.com/include/js/jquery.min.js language="javascript"></script>
<script src=http://www.iteedu.com/include/js/jquery-ui.min.js language="javascript"></script>
<link href=http://www.iteedu.com/include/css/jquery-ui.min.css rel="stylesheet" type="text/css" />
<link href=http://www.iteedu.com/include/css/main.css rel="stylesheet" type="text/css" />
<STYLE>

</STYLE>
<SCRIPT>
	$(document).ready(function() {
		$( "#menu" ).menu({ position: { my: "left top", at: "right-100 top+35" } });
	  $('pre').each(function(i, block) {
		var tmp=$("code",block);
		if(!tmp.length) tmp=$(block);
		tmp.html(tmp.html().replace(new RegExp("<", "g"), '&lt;'));
		hljs.highlightBlock(block);
	  });
	});
</SCRIPT>


<title>stat函数_Linux C 中文函数手册_Linux编程_linux_操作系统__www.iteedu.com</title>
<link rel="stylesheet" type="text/css" href="../img_css/router.css">



<base target="_blank" />
</head>
<body>
<header>
    <section id="global-nav">
        <nav>
            <ul class="projects">
                    <li><a href="http://www.iteedu.com/">ITeedu.com</a></li>
                    <li><a href="http://www.iteedu.com/free/"><span style="color:red;">自由计划</span></a></li>
                    <li><a href="http://pan.baidu.com/share/home?uk=521333629&view=album">我的网盘</a></li>
		    <li><a href="http://www.iteedu.com/me/contactme.php">关于</a></li>
            </ul>
            <ul class="links">
                    <li><a>快捷导航</a>
                        <ul>
                            <li><a href="http://www.iteedu.com/plang/">编程语言</a></li>
                            <li><a href="http://www.iteedu.com/webtech/">WEB开发</a></li>
                            <li><a href="http://www.iteedu.com/handset/">手机开发</a></li>
                            <li><a href="http://www.iteedu.com/database/">数据库</a></li>
                            <li><a href="http://www.iteedu.com/os/">操作系统</a></li>
                            <li><a href="http://www.iteedu.com/emded/">嵌入式</a></li>
                            <li><a href="http://www.iteedu.com/opensource/">开源软件</a></li>
                            <li><a href="http://www.iteedu.com/works/">作品</a></li>

                        </ul>
                    </li>
                    <li><a href="http://www.iteedu.com/plang/asm/index.php">编辑语言</a>
                        <ul>
                                <li><a href="http://www.iteedu.com/plang/asm/">汇编</a></li>
                                <li><a href="http://www.iteedu.com/plang/ccpp/">C/C++</a> </li>
                                <li><a href="http://www.iteedu.com/plang/java/">JAVA</a> </li>
                                <li><a href="http://www.iteedu.com/plang/python/">Python</a> </li>
                                <li><a href="http://www.iteedu.com/plang/ruby/">Ruby</a></li>
                        </ul>
                    </li>
                    <li><a href="http://www.iteedu.com/webtech/">WEB开发</a>
                        <ul>
                            <li><a href="http://www.iteedu.com/webtech/javascript/index.php">javascript</a> </li>
                            <li><a href="http://www.iteedu.com/webtech/j2ee/index.php">J2EE</a> </li>
                            <li><a href="http://www.iteedu.com/webtech/php/index.php">PHP</a> </li>
                            <li><a href="http://www.iteedu.com/webtech/python/djangocn2/index.php">Django</a> </li>
                        </ul>
                    </li>
            </ul>
        </nav>
    </section>
</header><div id="container" style="margin: 35px 120px 0px 120px;">
	<div id="logo-wapper">
		<div style="float:left;width:150px;" ><h1 class="logo">ITEEDU</h1></div>
		<div style="float:right;width:960px">
					<script type="text/javascript">
			/*960*60 header*/
			var cpro_id = "u2325496";
			</script>
			<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
					</div>
		<div style="clear:both;"></div>
	</div>
<nav id="main-nav">
<div id="menu">
    <li><a href="http://www.iteedu.com/">首页</a> </li>
	<li><a href="http://www.iteedu.com/os/">系统</a> </li>
    <li><a href="http://www.iteedu.com/os/linux/">LINUX</a> </li>
    <li><a href="http://www.iteedu.com/os/grub/">GRUB</a></li>
    <li><a href="http://www.iteedu.com/os/dos/">DOS</a></li>
    <li><a href="http://www.iteedu.com/os/bat/">批处理</a></li>
</div>
    </nav>
<div id="content-wrapper">
<div id="file-wrapper">
<div class="c_l">
  <h3>用户组函数 </h3>
  <ul>
  <li><a href="../user/utmpname.php">utmpname</a></li>
<li><a href="../user/setutent.php">setutent</a></li>
<li><a href="../user/setuid.php">setuid</a></li>
<li><a href="../user/setreuid.php">setreuid</a></li>
<li><a href="../user/setregid.php">setregid</a></li>
<li><a href="../user/setpwent.php">setpwent</a></li>
<li><a href="../user/setgroups.php">setgroups</a></li>
<li><a href="../user/setgrent.php">setgrent</a></li>
<li><a href="../user/setgid.php">setgid</a></li>
<li><a href="../user/setfsuid.php">setfsuid</a></li>
<li><a href="../user/setfsgid.php">setfsgid</a></li>
<li><a href="../user/seteuid.php">seteuid</a></li>
<li><a href="../user/pututline.php">pututline</a></li>
<li><a href="../user/initgroups.php">initgroups</a></li>
<li><a href="../user/getutline.php">getutline</a></li>
<li><a href="../user/getutid.php">getutid</a></li>
<li><a href="../user/getutent.php">getutent</a></li>
<li><a href="../user/getuid.php">getuid</a></li>
<li><a href="../user/getpwuid.php">getpwuid</a></li>
<li><a href="../user/getpwnam.php">getpwnam</a></li>
<li><a href="../user/getpwent.php">getpwent</a></li>
<li><a href="../user/getpw.php">getpw</a></li>
<li><a href="../user/getgroups.php">getgroups</a></li>
<li><a href="../user/getgrnam.php">getgrnam</a></li>
<li><a href="../user/getgrgid.php">getgrgid</a></li>
<li><a href="../user/getgrent.php">getgrent</a></li>
<li><a href="../user/getgid.php">agetgid</a></li>
<li><a href="../user/geteuid.php">geteuid</a></li>
<li><a href="../user/getegid.php">getegid</a></li>
<li><a href="../user/fgetpwent.php">fgetpwent</a></li>
<li><a href="../user/fgetgrent.php">fgetgrent</a></li>
<li><a href="../user/endutent.php">endutent</a></li>
<li><a href="../user/endpwent.php">endpwent</a></li>
<li><a href="../user/endgrent.php">endgrent</a></li>
</ul>
  <h3>信号处理函数 </h3>
  <ul>
<li><a href="../signal/alarm.php">alarm</a></li>
<li><a href="../signal/ferror.php">ferror</a></li>
<li><a href="../signal/kill.php">kill</a></li>
<li><a href="../signal/mkfifo.php">mkfifo</a></li>
<li><a href="../signal/pause.php">pause</a></li>
<li><a href="../signal/pclose.php">pclose</a></li>
<li><a href="../signal/perror.php">perror</a></li>
<li><a href="../signal/pipe.php">pipe</a></li>
<li><a href="../signal/popen.php">popen</a></li>
<li><a href="../signal/sigaction.php">sigaction</a></li>
<li><a href="../signal/sigaddset.php">sigaddset</a></li>
<li><a href="../signal/sigdelset.php">sigdelset</a></li>
<li><a href="../signal/sigemptyset.php">sigemptyset</a></li>
<li><a href="../signal/sigfillset.php">sigfillset</a></li>
<li><a href="../signal/sigismember.php">sigismember</a></li>
<li><a href="../signal/signal.php">signal</a></li>
<li><a href="../signal/sigpending.php">sigpending</a></li>
<li><a href="../signal/sigprocmask.php">sigprocmask</a></li>
<li><a href="../signal/sleep.php">sleep</a></li>
<li><a href="../signal/strerror.php">strerror</a></li>
</ul>
  <h3>内存控制函数 </h3>
  <ul>
<li><a href="../memctl/calloc.php">calloc</a></li>
<li><a href="../memctl/free.php">free</a></li>
<li><a href="../memctl/getpagesize.php">getpagesize</a></li>
<li><a href="../memctl/malloc.php">malloc</a></li>
<li><a href="../memctl/mmap.php">mmap</a></li>
<li><a href="../memctl/munmap.php">munmap</a></li>
</ul>
<h3>字符测试函数 </h3>
  <ul>
<li><a href="../char/isalnum.php">isalnum</a></li>
<li><a href="../char/isalpha.php">isalpha</a></li>
<li><a href="../char/isascii.php">isascii</a></li>
<li><a href="../char/isdigit.php">isdigit</a></li>
<li><a href="../char/isgraphis.php">isgraphis</a></li>
<li><a href="../char/islower.php">islower</a></li>
<li><a href="../char/isprint.php">isprint</a></li>
<li><a href="../char/ispunct.php">ispunct</a></li>
<li><a href="../char/isspace.php">isspace</a></li>
<li><a href="../char/isupper.php">isupper</a></li>
<li><a href="../char/isxdigit.php">isxdigit</a></li>
</ul>
  <h3>接口处理函数 </h3>
  <ul>
<li><a href="../interface/accept.php">accept</a></li>
<li><a href="../interface/bind.php">bind</a></li>
<li><a href="../interface/connect.php">connect</a></li>
<li><a href="../interface/endprotoent.php">endprotoent</a></li>
<li><a href="../interface/endservent.php">endservent</a></li>
<li><a href="../interface/getsockopt.php">getsockopt</a></li>
<li><a href="../interface/htonl.php">atan2</a></li>
<li><a href="../interface/htons.php">htons</a></li>
<li><a href="../interface/inet_addr.php">inet_addr</a></li>
<li><a href="../interface/inet_aton.php">inet_aton</a></li>
<li><a href="../interface/inet_ntoa.php">inet_ntoa</a></li>
<li><a href="../interface/listen.php">listen</a></li>
<li><a href="../interface/ntohl.php">ntohl</a></li>
<li><a href="../interface/ntohs.php">ntohs</a></li>
<li><a href="../interface/recv.php">recv</a></li>
<li><a href="../interface/recvfrom.php">recvfrom</a></li>
<li><a href="../interface/recvmsg.php">recvmsg</a></li>
<li><a href="../interface/send.php">send</a></li>
<li><a href="../interface/sendmsg.php">sendmsg</a></li>
<li><a href="../interface/sendto.php">sendto</a></li>
<li><a href="../interface/setprotoent.php">setprotoent</a></li>
<li><a href="../interface/setservent.php">setservent</a></li>
<li><a href="../interface/setsockopt.php">setsockopt</a></li>
<li><a href="../interface/shutdown.php">shutdown</a></li>
<li><a href="../interface/socket.php">socket</a></li>
</ul>
  <h3>文件处理函数 </h3>
  <ul>
<li><a href="../file/close.php">close</a></li>
<li><a href="../file/creat.php">creat</a></li>
<li><a href="../file/dup.php">dup</a></li>
<li><a href="../file/dup2.php">adup2</a></li>
<li><a href="../file/fcntl.php">fcntl</a></li>
<li><a href="../file/flock.php">flock</a></li>
<li><a href="../file/fsync.php">fsync</a></li>
<li><a href="../file/lseek.php">lseek</a></li>
<li><a href="../file/mkstemp.php">mkstemp</a></li>
<li><a href="../file/open.php">open</a></li>
<li><a href="../file/read.php">read</a></li>
<li><a href="../file/sync.php">sync</a></li>
<li><a href="../file/write.php">write</a></li>
</ul>
  <h3>时间上期函数 </h3>
  <ul>
<li><a href="../datetime/asctime.php">asctime</a></li>
<li><a href="../datetime/ctime.php">ctime</a></li>
<li><a href="../datetime/gettimeofday.php">gettimeofday</a></li>
<li><a href="../datetime/gmtime.php">gmtime</a></li>
<li><a href="../datetime/localtime.php">localtime</a></li>
<li><a href="../datetime/mktime.php">mktime</a></li>
<li><a href="../datetime/settimeofday.php">settimeofday</a></li>
<li><a href="../datetime/time.php">time</a></li>
</ul>
  <h3>环境变量函数 </h3>
  <ul>
<li><a href="../env/getenv.php">getenv</a></li>
<li><a href="../env/putenv.php">putenv</a></li>
<li><a href="../env/setenv.php">setenv</a></li>
</ul>
  <h3>终端控制函数 </h3>
  <ul>
<li><a href="../console/getopt.php">getopt</a></li>
<li><a href="../console/isatty.php">isatty</a></li>
<li><a href="../console/ttyname.php">ttyname</a></li>
</ul>
  <h3>进程控制函数 </h3>
  <ul>
<li><a href="../process/atexit.php">atexit</a></li>
<li><a href="../process/execl.php">execl</a></li>
<li><a href="../process/execlp.php">execlp</a></li>
<li><a href="../process/execv.php">execv</a></li>
<li><a href="../process/execve.php">execve</a></li>
<li><a href="../process/execvp.php">execvp</a></li>
<li><a href="../process/exit.php">exit</a></li>
<li><a href="../process/fprintf.php">fprintf</a></li>
<li><a href="../process/fscanf.php">fscanf</a></li>
<li><a href="../process/getpgid.php">getpgid</a></li>
<li><a href="../process/getpgrp.php">getpgrp</a></li>
<li><a href="../process/getpid.php">getpid</a></li>
<li><a href="../process/getppid.php">getppid</a></li>
<li><a href="../process/getpriority.php">getpriority</a></li>
<li><a href="../process/nice.php">nice</a></li>
<li><a href="../process/on_exit.php">on_exit</a></li>
<li><a href="../process/printf.php">printf</a></li>
<li><a href="../process/sacnf.php">sacnf</a></li>
<li><a href="../process/setpgid.php">setpgid</a></li>
<li><a href="../process/setpgrp.php">setpgrp</a></li>
<li><a href="../process/setpriority.php">setpriority</a></li>
<li><a href="../process/sprintf.php">sprintf</a></li>
<li><a href="../process/sscanf.php">sscanf</a></li>
<li><a href="../process/system.php">system</a></li>
<li><a href="../process/vfork.php">vfork</a></li>
<li><a href="../process/vfprintf.php">vfprintf</a></li>
<li><a href="../process/vfscanf.php">vfscanf</a></li>
<li><a href="../process/vprintf.php">vprintf</a></li>
<li><a href="../process/vscanf.php">vscanf</a></li>
<li><a href="../process/vsprintf.php">vsprintf</a></li>
<li><a href="../process/vsscanf.php">vsscanf</a></li>
<li><a href="../process/wait.php">wait</a></li>
<li><a href="../process/waitpid.php">waitpid</a></li>
</ul>
  <h3>数学函数 </h3>
  <ul>
<li><a href="../math/abs.php">abs</a></li>
<li><a href="../math/acos.php">acos</a></li>
<li><a href="../math/asin.php">asin</a></li>
<li><a href="../math/atan.php">atan</a></li>
<li><a href="../math/atan2.php">atan2</a></li>
<li><a href="../math/ceil.php">ceil</a></li>
<li><a href="../math/cos.php">cos</a></li>
<li><a href="../math/cosh.php">cosh</a></li>
<li><a href="../math/exp.php">exp</a></li>
<li><a href="../math/frexp.php">frexp</a></li>
<li><a href="../math/ldexp.php">ldexp</a></li>
<li><a href="../math/log.php">log</a></li>
<li><a href="../math/log10.php">log10</a></li>
<li><a href="../math/pow.php">pow</a></li>
<li><a href="../math/sin.php">sin</a></li>
<li><a href="../math/sinh.php">sinh</a></li>
<li><a href="../math/sqrt.php">sqrt</a></li>
<li><a href="../math/tan.php">tan</a></li>
<li><a href="../math/tanh.php">tanh</a></li>
</ul>
  <h3>字符串转换函数 </h3>
  <ul>
<li><a href="../string/atof.php">atof</a></li>
<li><a href="../string/atoi.php">atoi</a></li>
<li><a href="../string/atol.php">atol</a></li>
<li><a href="../string/gcvt.php">gcvt</a></li>
<li><a href="../string/strtod.php">strtod</a></li>
<li><a href="../string/strtol.php">strtol</a></li>
<li><a href="../string/strtoul.php">strtoul</a></li>
<li><a href="../string/toascii.php">toascii</a></li>
<li><a href="../string/tolower.php">Linhan</a></li>
<li><a href="../string/toupper.php">toupper</a></li>
</ul>
  <h3>文件权限控制函数 </h3>
  <ul>
<li><a href="access.php">access</a></li>
<li><a href="alphasort.php">alphasort</a></li>
<li><a href="chdir.php">Linhan</a></li>
<li><a href="chmod.php">chmod</a></li>
<li><a href="chown.php">chown</a></li>
<li><a href="chroot.php">chroot</a></li>
<li><a href="closedir.php">closedir</a></li>
<li><a href="fchdir.php">fchdir</a></li>
<li><a href="fchmod.php">fchmod</a></li>
<li><a href="fchown.php">fchown</a></li>
<li><a href="fstat.php">fstat</a></li>
<li><a href="ftruncate.php">ftruncate</a></li>
<li><a href="getcwd.php">getcwd</a></li>
<li><a href="link.php">link</a></li>
<li><a href="lstat.php">lstat</a></li>
<li><a href="opendir.php">opendir</a></li>
<li><a href="readdir.php">readdir</a></li>
<li><a href="readlink.php">readlink</a></li>
<li><a href="remove.php">remove</a></li>
<li><a href="rename.php">rename</a></li>
<li><a href="rewinddir.php">rewinddir</a></li>
<li><a href="seekdir.php">seekdir</a></li>
<li><a href="stat.php">stat</a></li>
<li><a href="symlink.php">symlink</a></li>
<li><a href="telldir.php">telldir</a></li>
<li><a href="truncate.php">truncate</a></li>
<li><a href="umask.php">umask</a></li>
<li><a href="unlink.php">unlink</a></li>
<li><a href="utime.php">utime</a></li>
<li><a href="utimes.php">utimes</a></li>
</ul>
  <h3>文件内容控制函数 </h3>
  <ul>
<li><a href="../filecontent/clearerr.php">clearerr</a></li>
<li><a href="../filecontent/fclose.php">fclose</a></li>
<li><a href="../filecontent/fdopen.php">fdopen</a></li>
<li><a href="../filecontent/feof.php">feof</a></li>
<li><a href="../filecontent/fflush.php">fflush</a></li>
<li><a href="../filecontent/fgetc.php">fgetc</a></li>
<li><a href="../filecontent/fgets.php">fgets</a></li>
<li><a href="../filecontent/fileno.php">fileno</a></li>
<li><a href="../filecontent/fopen.php">fopen</a></li>
<li><a href="../filecontent/fputc.php">fputc</a></li>
<li><a href="../filecontent/fputs.php">fputs</a></li>
<li><a href="../filecontent/fread.php">fread</a></li>
<li><a href="../filecontent/freopen.php">freopen</a></li>
<li><a href="../filecontent/fseek.php">fseek</a></li>
<li><a href="../filecontent/ftell.php">ftell</a></li>
<li><a href="../filecontent/fwrite.php">fwrite</a></li>
<li><a href="../filecontent/getc.php">getc</a></li>
<li><a href="../filecontent/getchar.php">getchar</a></li>
<li><a href="../filecontent/gets.php">gets</a></li>
<li><a href="../filecontent/mktemp.php">mktemp</a></li>
<li><a href="../filecontent/putc.php">putc</a></li>
<li><a href="../filecontent/putchar.php">putchar</a></li>
<li><a href="../filecontent/rewind.php">rewind</a></li>
<li><a href="../filecontent/setbuf.php">setbuf</a></li>
<li><a href="../filecontent/setbuffer.php">setbuffer</a></li>
<li><a href="../filecontent/setlinebuf.php">setlinebuf</a></li>
<li><a href="../filecontent/setvbuf.php">setvbuf</a></li>
<li><a href="../filecontent/ungetc.php">ungetc</a></li>
</ul>
  <h3>数据结构和算法 </h3>
  <ul>
<li><a href="../algorithm/bsearch.php">bsearch</a></li>
<li><a href="../algorithm/crypt.php">crypt</a></li>
<li><a href="../algorithm/lfind.php">lfind</a></li>
<li><a href="../algorithm/lsearch.php">lsearch</a></li>
<li><a href="../algorithm/qsort.php">qsort</a></li>
<li><a href="../algorithm/rand.php">rand</a></li>
<li><a href="../algorithm/srand.php">srand</a></li>
</ul>
  <h3>内存和字符串函数</h3>
  <ul>
<li><a href="../memstring/bcmp.php">bcmp</a></li>
<li><a href="../memstring/bcopy.php">bcopy</a></li>
<li><a href="../memstring/bzero.php">bzero</a></li>
<li><a href="../memstring/index.php">index</a></li>
<li><a href="../memstring/memccpy.php">memccpy</a></li>
<li><a href="../memstring/memchr.php">memchr</a></li>
<li><a href="../memstring/memcmp.php">memcmp</a></li>
<li><a href="../memstring/memcpy.php">memcpy</a></li>
<li><a href="../memstring/memmove.php">memmove</a></li>
<li><a href="../memstring/memset.php">memset</a></li>
<li><a href="../memstring/rindex.php">rindex</a></li>
<li><a href="../memstring/strcasecmp.php">strcasecmp</a></li>
<li><a href="../memstring/strcat.php">strcat</a></li>
<li><a href="../memstring/strchr.php">strchr</a></li>
<li><a href="../memstring/strcmp.php">strcmp</a></li>
<li><a href="../memstring/strcoll.php">strcoll</a></li>
<li><a href="../memstring/strcpy.php">strcpy</a></li>
<li><a href="../memstring/strcspn.php">strcspn</a></li>
<li><a href="../memstring/strdup.php">strdup</a></li>
<li><a href="../memstring/strlen.php">strlen</a></li>
<li><a href="../memstring/strncasecmp.php">strncasecmp</a></li>
<li><a href="../memstring/strncat.php">strncat</a></li>
<li><a href="../memstring/strncpy.php">strncpy</a></li>
<li><a href="../memstring/strpbrk.php">strpbrk</a></li>
<li><a href="../memstring/strrchr.php">strrchr</a></li>
<li><a href="../memstring/strspn.php">strspn</a></li>
<li><a href="../memstring/strstr.php">strstr</a></li>
<li><a href="../memstring/strtok.php">strtok</a></li>
</ul>
</div><div class="c_m">




<div align="center">
		<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" id="table36">
  <tr> 
    <td width="12" height="13"><img src="../img_css/frame6.gif" width="12" height="13"></td>
    <td width="473" background="../img_css/frame7.gif">
	</td>
    <td align="right" width="14">
	<img src="../img_css/frame9.gif" width="14" height="13"></td>
  </tr>
</table>

		<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" id="table40">
  <tr> 
    <td background="../img_css/frame10.gif" width="12">　</td>
    <td width="40">
<div align="left">&nbsp;&nbsp; </div>
	</td>
    <td>
...stat</td>
    <td background="../img_css/frame11.gif" width="14">　</td>
  </tr>
</table>

		<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" id="table39">
  <tr> 
    <td background="../img_css/frame10.gif" width="12">　</td>
    <td>
<div align="center">
<img border="0" src="../img_css/line.JPG" width="406" height="7"></div>
	</td>
    <td background="../img_css/frame11.gif" width="14">　</td>
  </tr>
</table>

		<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" id="table34">
  <tr> 
    <td background="../img_css/frame10.gif" width="12">　</td>
    <td>
<table width="400" border="0" align="center" class="space" id="table35">
<tr><td align="right" width="398"> 
	<p align="left">stat（取得文件状态）<br>
	相关函数 fstat，lstat，chmod，chown，readlink，utime<br>
	表头文件 #include&lt;sys/stat.h&gt;<br>
	#include&lt;unistd.h&gt;<br>
	定义函数 int stat(const char * file_name,struct stat *buf);<br>
	函数说明 stat()用来将参数file_name所指的文件状态，复制到参数buf所指的结构中。<br>
	下面是struct stat内各参数的说明<br>
	struct stat<br>
	{<br>
	dev_t st_dev; /*device*/<br>
	ino_t st_ino; /*inode*/<br>
	mode_t st_mode; /*protection*/<br>
	nlink_t st_nlink; /*number of hard links */<br>
	uid_t st_uid; /*user ID of owner*/<br>
	gid_t st_gid; /*group ID of owner*/<br>
	dev_t st_rdev; /*device type */<br>
	off_t st_size; /*total size, in bytes*/<br>
	unsigned long st_blksize; /*blocksize for filesystem I/O */<br>
	unsigned long st_blocks; /*number of blocks allocated*/<br>
	time_t st_atime; /* time of lastaccess*/<br>
	time_t st_mtime; /* time of last modification */<br>
	time_t st_ctime; /* time of last change */<br>
	};<br>
	st_dev 文件的设备编号<br>
	st_ino 文件的i-node<br>
	st_mode 文件的类型和存取的权限<br>
	st_nlink 连到该文件的硬连接数目，刚建立的文件值为1。<br>
	st_uid 文件所有者的用户识别码<br>
	st_gid 文件所有者的组识别码<br>
	st_rdev 若此文件为装置设备文件，则为其设备编号<br>
	st_size 文件大小，以字节计算<br>
	st_blksize 文件系统的I/O 缓冲区大小。<br>
	st_blcoks 占用文件区块的个数，每一区块大小为512 个字节。<br>
	st_atime 文件最近一次被存取或被执行的时间，一般只有在用<br>
	mknod、utime、read、write与tructate时改变。<br>
	st_mtime 文件最后一次被修改的时间，一般只有在用mknod、utime<br>
	和write时才会改变<br>
	st_ctime i-node最近一次被更改的时间，此参数会在文件所有者、<br>
	组、权限被更改时更新先前所描述的st_mode 则定义了下列数种情<br>
	况<br>
	S_IFMT 0170000 文件类型的位遮罩<br>
	S_IFSOCK 0140000 scoket<br>
	S_IFLNK 0120000 符号连接<br>
	S_IFREG 0100000 一般文件<br>
	S_IFBLK 0060000 区块装置<br>
	S_IFDIR 0040000 目录<br>
	S_IFCHR 0020000 字符装置<br>
	S_IFIFO 0010000 先进先出<br>
	S_ISUID 04000 文件的（set user-id on execution）位<br>
	S_ISGID 02000 文件的（set group-id on execution）位<br>
	S_ISVTX 01000 文件的sticky位<br>
	S_IRUSR（S_IREAD） 00400 文件所有者具可读取权限<br>
	S_IWUSR（S_IWRITE）00200 文件所有者具可写入权限<br>
	S_IXUSR（S_IEXEC） 00100 文件所有者具可执行权限<br>
	S_IRGRP 00040 用户组具可读取权限<br>
	S_IWGRP 00020 用户组具可写入权限<br>
	S_IXGRP 00010 用户组具可执行权限<br>
	S_IROTH 00004 其他用户具可读取权限<br>
	S_IWOTH 00002 其他用户具可写入权限<br>
	S_IXOTH 00001 其他用户具可执行权限<br>
	上述的文件类型在POSIX 中定义了检查这些类型的宏定义<br>
	S_ISLNK （st_mode） 判断是否为符号连接<br>
	S_ISREG （st_mode） 是否为一般文件<br>
	S_ISDIR （st_mode）是否为目录<br>
	S_ISCHR （st_mode）是否为字符装置文件<br>
	S_ISBLK （s3e） 是否为先进先出<br>
	S_ISSOCK （st_mode） 是否为socket<br>
	若一目录具有sticky 位（S_ISVTX），则表示在此目录下的文件只<br>
	能被该文件所有者、此目录所有者或root来删除或改名。<br>
	返回值 执行成功则返回0，失败返回-1，错误代码存于errno<br>
	错误代码 ENOENT 参数file_name指定的文件不存在<br>
	ENOTDIR 路径中的目录存在但却非真正的目录<br>
	ELOOP 欲打开的文件有过多符号连接问题，上限为16符号连接<br>
	EFAULT 参数buf为无效指针，指向无法存在的内存空间<br>
	EACCESS 存取文件时被拒绝<br>
	ENOMEM 核心内存不足<br>
	ENAMETOOLONG 参数file_name的路径名称太长<br>
　</td>
	</tr>
</table>
　</td>
    <td background="../img_css/frame11.gif" width="14">　</td>
  </tr>
  <tr> 
    <td width="12"><img src="../img_css/frame12.gif" width="12" height="15"></td>
    <td background="../img_css/frame14.gif">
	<img src="../img_css/frame13.gif" width="3" height="15"></td>
    <td width="14"><img src="../img_css/frame15.gif" width="14" height="15"></td>
  </tr>
</table>
<br>


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span style="font-size: 9pt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
		<table width="500" border="0" align="center" cellpadding="0" cellspacing="0" id="table44">
  <tr> 
    <td width="12" height="13"><img src="../img_css/frame6.gif" width="12" height="13"></td>
    <td width="474" background="../img_css/frame7.gif">
	</td>
    <td align="right" width="14">
	<img src="../img_css/frame9.gif" width="14" height="13"></td>
  </tr>
  <tr> 
    <td background="../img_css/frame10.gif">　</td>
    <td width="474">
<table width="400" border="0" align="center" class="space" id="table45">
<tr><td align="right" width="398"> 
	<p align="left">范例:</p>
	<p align="left">#include&lt;sys/stat.h&gt;<br>
	#include&lt;unistd.h&gt;<br>
	mian()<br>
	{<br>
	struct stat buf;<br>
	stat (“/etc/passwd”,&amp;buf);<br>
	printf(“/etc/passwd file size = %d \n”,buf.st_size);<br>
	}<br>
	执行 /etc/passwd file size = 705</td>
	</tr>
</table>
　</td>
    <td background="../img_css/frame11.gif">　</td>
  </tr>
  <tr> 
    <td><img src="../img_css/frame12.gif" width="12" height="15"></td>
    <td background="../img_css/frame14.gif">
	<img src="../img_css/frame13.gif" width="3" height="15"></td>
    <td><img src="../img_css/frame15.gif" width="14" height="15"></td>
  </tr>
</table>

</div>



﻿<div style="float:right;margin-right:100px;" class="bdsharebuttonbox">
	<a href="stat.php#" class="bds_more" data-cmd="more"></a>
	<a href="stat.php#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
	<a href="stat.php#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
	<a href="stat.php#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
	<a href="stat.php#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>
	<a href="stat.php#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>
</div>
<div style="clear:both;"></div>
</div></div>
<!--end of file-wrpper-->
</div>
<!--end of content-wrapper-->
</div>
<!--end of container-->


<footer>
<p class="copyright">版权所有 2014 <a href="http://www.iteedu.com">ITEEDU</a>, 京ICP备09108222号</p>
</footer>

<link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/8.8.0/styles/default.min.css">
<script src="http://cdnjs.cloudflare.com/ajax/libs/highlight.js/8.8.0/highlight.min.js"></script>

<script type="text/javascript">
    /*120*300 两侧*/
var cpro_id = "u2328108";
</script>
<script src="http://cpro.baidustatic.com/cpro/ui/f.js" type="text/javascript"></script>
<script type="text/javascript">
    /*300*250 视窗*/
var cpro_id = "u2328156";
</script>
<script src="http://cpro.baidustatic.com/cpro/ui/f.js" type="text/javascript"></script>

<!--百度统计js-->
<p style="display:none">
<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fa5213b08a76d4fc6b8a80eb382490e0b' type='text/javascript'%3E%3C/script%3E"));
</script>
</p>


<!--分享js-->
<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdPic":"","bdStyle":"0","bdSize":"16"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>
</body>

</html>

